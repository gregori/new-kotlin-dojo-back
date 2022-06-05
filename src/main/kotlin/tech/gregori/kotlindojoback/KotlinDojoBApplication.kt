package tech.gregori.kotlindojoback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@ServletComponentScan
class KotlinDojoBApplication

fun main(args: Array<String>) {
    runApplication<KotlinDojoBApplication>(*args)
}
