package tech.gregori.kotlindojoback.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.gregori.kotlindojoback.model.UserDetailsImpl
import tech.gregori.kotlindojoback.repository.UserRepository

@Service
class UserDetailsServiceImpl(private var userRepository: UserRepository) : UserDetailsService {
   @Transactional
   override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)

        return user?.let { UserDetailsImpl(it) } ?:
            throw UsernameNotFoundException("O usuário $username não foi encontrado")
    }
}