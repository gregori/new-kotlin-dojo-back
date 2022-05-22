package tech.gregori.kotlindojoback.model

import org.hibernate.Hibernate
import org.hibernate.validator.constraints.br.CPF
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@Entity
data class Athlete(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,

    @NotNull
    var register: String,

    @NotNull
    var name: String,

    @NotNull
    var dateOfBirth: LocalDate,
    var contractor: String?,
    @CPF
    var contractorCpf: String,
    var subscriptionDate: LocalDate = LocalDate.now(),

    // Address
    var street: String,
    @Pattern(regexp = "\\d{5}-\\d{3}")
    var zip: String,
    var city: String,
    var state: String,
    var neighbourhood: String?,

    // Contact info
    @Email
    var email: String?,
    @Pattern(regexp = "^\\+\\d{2}\\s?\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$")
    var phone: String?,

    var enabled: Boolean? = true,

    @LastModifiedDate
    var lastModified: LocalDateTime? = LocalDateTime.now(),
    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Athlete

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , register = $register , name = $name , dateOfBirth = $dateOfBirth , contractor = $contractor , contractorCpf = $contractorCpf , subscriptionDate = $subscriptionDate , street = $street , zip = $zip , city = $city , neighbourhood = $neighbourhood , email = $email , phone = $phone , enabled = $enabled , lastModified = $lastModified , createdDate = $createdDate )"
    }
}
