package tech.gregori.kotlindojoback.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import tech.gregori.kotlindojoback.model.User
import tech.gregori.kotlindojoback.repository.UserRepository

@Service
class UserService(private var userRepository: UserRepository, private var bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun create(user: User): User {
        user.password = bCryptPasswordEncoder.encode(user.password)
        return userRepository.save(user)
    }
}