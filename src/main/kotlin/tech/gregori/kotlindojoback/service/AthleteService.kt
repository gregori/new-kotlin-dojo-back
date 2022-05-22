package tech.gregori.kotlindojoback.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import tech.gregori.kotlindojoback.model.Athlete
import tech.gregori.kotlindojoback.repository.AthleteRepository

@Service
class AthleteService(
    private val athleteRepository: AthleteRepository,
    private val athleteImportService: AthleteImportService
) {
    fun import(file: MultipartFile): Collection<Athlete> =
        athleteImportService.import(file).also { athleteRepository.saveAll(it) }
}