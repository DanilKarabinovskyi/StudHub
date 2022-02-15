package danyil.karabinovskyi.studenthub.features.auth.domain.entity

data class LoginEntity(
    private var name: String = "Student",
    private var university: String = "VNTU",
    private var role : String = "student",
    private var token: String
)