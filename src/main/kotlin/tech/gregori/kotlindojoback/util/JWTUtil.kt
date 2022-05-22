package tech.gregori.kotlindojoback.util

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import tech.gregori.kotlindojoback.model.UserDetailsImpl
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct


@Component
class JWTUtil(
    @Value("\${app.jwt-security.expiration-time}")
    private val expiration: Long,
    private val securityKey: Key
) {
    private val logger = LoggerFactory.getLogger(JWTUtil::class.java)

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(securityKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUsernameFromJwtToken(token: String): String {
//        val keyBytes = Decoders.BASE64.decode(jwtSecret)
//        val key: Key = Keys.hmacShaKeyFor(keyBytes)

        return Jwts.parserBuilder()
            .setSigningKey(securityKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(securityKey)
                .build()
                .parseClaimsJws(token)

            return true
        } catch (e: SignatureException) {
            logger.error("Assinatura JWT inválida: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Token JWT inválido: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("Token JWT está expirado: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("Token JWT não é suportado: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("String de claims JWT está vazio: {}", e.message)
        }

        return false
    }
}