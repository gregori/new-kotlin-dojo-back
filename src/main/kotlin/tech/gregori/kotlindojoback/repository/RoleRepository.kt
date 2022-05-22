package tech.gregori.kotlindojoback.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.gregori.kotlindojoback.model.Role
import tech.gregori.kotlindojoback.model.RoleType

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(name: RoleType): Role?
}