package tech.gregori.kotlindojoback.util

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationEntryPointJwt: AuthenticationEntryPoint {
    private val logger = LoggerFactory.getLogger(AuthenticationEntryPointJwt::class.java)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.error("Erro de não autorizado: {}", authException?.message)
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erro: Não autorizado")
    }
}