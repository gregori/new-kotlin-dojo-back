package tech.gregori.kotlindojoback.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.gregori.kotlindojoback.model.Role
import tech.gregori.kotlindojoback.model.RoleType
import tech.gregori.kotlindojoback.model.User
import tech.gregori.kotlindojoback.model.UserDetailsImpl
import tech.gregori.kotlindojoback.payload.request.LoginRequest
import tech.gregori.kotlindojoback.payload.request.SignupRequest
import tech.gregori.kotlindojoback.payload.response.JwtResponse
import tech.gregori.kotlindojoback.payload.response.MessageResponse
import tech.gregori.kotlindojoback.repository.RoleRepository
import tech.gregori.kotlindojoback.repository.UserRepository
import tech.gregori.kotlindojoback.util.JWTUtil
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private var authenticationManager: AuthenticationManager,
    private var userRepository: UserRepository,
    private var roleRepository: RoleRepository,
    private var encoder: PasswordEncoder,
    private var jwtUtil: JWTUtil
) {
    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtil.generateToken(authentication)

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item -> item.authority }
            .collect(Collectors.toList())

        return ResponseEntity.ok(JwtResponse(jwt,
            userDetails.id, userDetails.username, userDetails.email, roles))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<MessageResponse> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity.badRequest().body(MessageResponse("Erro: Este username já está em uso!"))
        }
        if (userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity.badRequest().body(MessageResponse(("Erro: Este email já está em uso!")))
        }

        val user = User(
            username=signupRequest.username,
            name=signupRequest.name,
            email=signupRequest.email,
            password=encoder.encode(signupRequest.password))

        val strRoles = signupRequest.role
        val roles: MutableSet<Role> = HashSet()
        if (strRoles == null) {
            val userRole = roleRepository.findByName(RoleType.ROLE_USER)
            userRole?.also { roles.add(it) } ?: throw RuntimeException("Erro: Role não encontrada")
        } else {
            strRoles.forEach { role ->
                when (role) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                        adminRole?.also { roles.add(it) } ?: throw RuntimeException("Erro: Role não encontrada.")
                    }
                    "instructor" -> {
                        val instRole = roleRepository.findByName(RoleType.ROLE_INSTRUCTOR)
                        instRole?.also { roles.add(it) } ?: throw RuntimeException("Erro: Role não encontrada.")
                    }
                    else -> {
                        val userRole = roleRepository.findByName(RoleType.ROLE_USER)
                        userRole?.also { roles.add(it) } ?: throw RuntimeException("Erro: Role não encontrada")
                    }
                }
            }
        }

        user.roles = roles
        userRepository.save(user)

        return ResponseEntity.ok(MessageResponse("Usuário registrado com sucesso!"))
    }
}