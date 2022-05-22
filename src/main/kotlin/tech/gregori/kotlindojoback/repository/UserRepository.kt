package tech.gregori.kotlindojoback.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import tech.gregori.kotlindojoback.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    @Transactional
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}