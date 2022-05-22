package tech.gregori.kotlindojoback.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import tech.gregori.kotlindojoback.service.AthleteService

@RestController
@RequestMapping("/api/athletes")
class AthleteController(private val athleteService: AthleteService) {
    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    fun import(@RequestParam("file") multipartFile: MultipartFile) = athleteService.import(multipartFile)
}