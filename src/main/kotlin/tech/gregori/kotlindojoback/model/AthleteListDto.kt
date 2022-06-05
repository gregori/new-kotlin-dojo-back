package tech.gregori.kotlindojoback.model

data class AthleteListDto (
    val data: Iterable<Athlete>,
    val totalElements: Int?
)