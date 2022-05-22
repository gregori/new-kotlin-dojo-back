package tech.gregori.kotlindojoback.service

import com.opencsv.bean.CsvToBeanBuilder
import org.apache.commons.io.input.BOMInputStream
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import tech.gregori.kotlindojoback.model.Athlete
import tech.gregori.kotlindojoback.model.AthleteCsvDto

@Component
class AthleteImportService {
    fun import(file: MultipartFile): Collection<Athlete> =
        BOMInputStream(file.inputStream).bufferedReader().use { stream ->
            CsvToBeanBuilder<AthleteCsvDto>(stream)
                .withType(AthleteCsvDto::class.java)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(',')
                .build()
                .parse()
                .map { it.toAthlete() }
        }
}