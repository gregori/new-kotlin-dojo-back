package tech.gregori.kotlindojoback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KotlinDojoBApplication

fun main(args: Array<String>) {
    runApplication<KotlinDojoBApplication>(*args)
}
