package tech.gregori.kotlindojoback.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {
    @GetMapping("/all")
    fun allAccess(): String = "Conteúdo público."

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    fun userAccess(): String = "Conteúdo de usuário."

    @GetMapping("/instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    fun instructorAccess(): String = "Quadro de instrutores."

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String = "Quadro de administradores"
}