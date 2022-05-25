package danyil.karabinovskyi.studenthub.core.data

data class Query(
    val take: Int,
    val skip: Int,
    val order: String,
    val sort: String,
    val tags: Int,
    val socialTag: List<String>,
    )
