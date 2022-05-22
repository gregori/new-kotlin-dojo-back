package tech.gregori.kotlindojoback.model

import net.minidev.json.annotate.JsonIgnore
import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class User(
    @NotBlank
    @Size(max = 20)
    val username: String,
    @NotBlank
    val name: String = "",
    @NotBlank
    @Email
    val email: String = "",
    @NotBlank
    var password: String = "",
    @ManyToMany(fetch = FetchType.EAGER)
    var roles: Set<Role> = HashSet()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email )"
    }
}
