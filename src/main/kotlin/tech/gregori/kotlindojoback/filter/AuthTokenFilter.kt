package tech.gregori.kotlindojoback.filter

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import tech.gregori.kotlindojoback.service.UserDetailsServiceImpl
import tech.gregori.kotlindojoback.util.JWTUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtUtil: JWTUtil
    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    private val logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            logger.info(request.getHeader("Authorization"))
            val jwt = parseJwt(request)
            logger.info(jwt)
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                val username = jwtUtil.getUsernameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Não foi possivel configurar a autenticação do usuário: {}", e)
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length)
        }

        return null
    }
}