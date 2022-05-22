package tech.gregori.kotlindojoback.filter

import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@Component
class SimpleCORSFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpResponse = response as HttpServletResponse

        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000")
        httpResponse.setHeader("Access-Control-Allow-Methods", "PATCH, POST, GET, PUT, OPTIONS, DELETE")
        httpResponse.setHeader("Access-Control-Max-Age", "3600")
        httpResponse.setHeader("Access-Control-Allow-Headers", "Location, Origin, X-Requested-With, Content-Type, Accept")

        chain?.doFilter(request, response)
    }
}