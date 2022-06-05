package tech.gregori.kotlindojoback.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsImpl(private val user: User) : UserDetails {

    val id get() = user.id
    val name get() = user.name
    val email get() = user.email

    override fun getAuthorities(): MutableList<SimpleGrantedAuthority> = user.roles.stream().map {
            role -> SimpleGrantedAuthority(role.name.name)
    }.collect(Collectors.toList())

    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}