package danyil.karabinovskyi.studenthub.features.auth.domain

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.VerifyResponse
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {

    suspend fun register(password:RequestBody,email:RequestBody, group: RequestBody,
        file: MultipartBody.Part) : SimpleResource


    suspend fun login(
        loginRequest: LoginRequest
    ): SimpleResource

    suspend fun authenticate(): Resource<VerifyResponse>
}