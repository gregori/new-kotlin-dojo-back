package tech.gregori.kotlindojoback.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.gregori.kotlindojoback.model.User
import tech.gregori.kotlindojoback.service.UserService
import java.net.URI

@RestController
@RequestMapping("/signup")
class SignupController(private var userService: UserService) {
    @PostMapping
    fun signup(@RequestBody user: User): ResponseEntity<User> {
        val userCreated = userService.create(user)
        return ResponseEntity.created(URI("")).body(userCreated)
    }
}