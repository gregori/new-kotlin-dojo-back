package tech.gregori.kotlindojoback.payload.response

data class JwtResponse(
    var token: String,
    var id: Long,
    var username: String,
    var email: String,
    var roles: List<String>,
    var type: String = "Bearer"
)
