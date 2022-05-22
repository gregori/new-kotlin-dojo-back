package tech.gregori.kotlindojoback.repository

import org.springframework.data.repository.CrudRepository
import tech.gregori.kotlindojoback.model.Athlete
import java.util.*

interface AthleteRepository : CrudRepository<Athlete, Int> {
    fun findAthleteByRegister(register: String): Athlete

    fun findAthleteByRegisterAndEnabledIsTrue(register: String): Athlete
}