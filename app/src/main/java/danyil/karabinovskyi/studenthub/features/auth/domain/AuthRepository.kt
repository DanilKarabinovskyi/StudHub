package danyil.karabinovskyi.studenthub.features.auth.domain

import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import danyil.karabinovskyi.studenthub.features.auth.domain.request.RegistrationRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {

    suspend fun register(password:RequestBody,email:RequestBody, group: RequestBody,
        file: MultipartBody.Part) : SimpleResource


    suspend fun login(
        loginRequest: LoginRequest
    ): SimpleResource

    suspend fun authenticate(): SimpleResource
}