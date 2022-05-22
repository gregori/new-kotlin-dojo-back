package tech.gregori.kotlindojoback.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt-security")
class SecurityProperties {
    var expirationTime: Int = 31 // in days
    var tokenPrefix = "Bearer "
    var headerString = "Authorization"
    var strength = 10
}