package tech.gregori.kotlindojoback.model

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvDate
import java.time.LocalDate

class AthleteCsvDto(
    @CsvBindByName(required = true)
    var register: String = "",

    @CsvBindByName(required = true)
    var name: String = "",

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(required = true)
    var dateOfBirth: LocalDate = LocalDate.now(),

    @CsvBindByName(required = false)
    var contractor: String? = null,

    @CsvBindByName(required = true)
    var contractorCpf: String = "",

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(required = true)
    var subscriptionDate: LocalDate = LocalDate.now(),

    @CsvBindByName(required = true)
    var street: String = "",

    @CsvBindByName(required = true)
    var zip: String = "",

    @CsvBindByName(required = true)
    var city: String = "",

    @CsvBindByName(required = true)
    var state: String = "",

    @CsvBindByName(required = false)
    var neighbourhood: String? = null,

    @CsvBindByName(required = false)
    var email: String? = null,

    @CsvBindByName(required = false)
    var phone: String? = null,

    @CsvBindByName(required = false)
    var enabled: Boolean? = true
) {
    fun toAthlete() = Athlete(
        register = register,
        name = name,
        dateOfBirth = dateOfBirth,
        contractor = contractor,
        contractorCpf = contractorCpf,
        subscriptionDate = subscriptionDate,
        street = street,
        zip = zip,
        city = city,
        state = state,
        neighbourhood = neighbourhood,
        email = email,
        phone = phone,
        enabled = enabled
    )

}