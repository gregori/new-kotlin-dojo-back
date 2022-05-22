package tech.gregori.kotlindojoback.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tech.gregori.kotlindojoback.model.Credentials
import tech.gregori.kotlindojoback.model.UserDetailsImpl
import tech.gregori.kotlindojoback.util.JWTUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(authenticationManager: AuthenticationManager?, private val jwtUtil: JWTUtil) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val (email, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
        val token = UsernamePasswordAuthenticationToken(email, password)

        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
//        val username = (authResult?.principal as UserDetailsImpl).username
        val token = authResult?.let { jwtUtil.generateToken(it) }

        response?.addHeader("Authorization", "Bearer $token")
    }
}