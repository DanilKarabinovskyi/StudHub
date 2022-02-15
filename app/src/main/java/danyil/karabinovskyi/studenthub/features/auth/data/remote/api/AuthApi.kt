package danyil.karabinovskyi.studenthub.features.auth.data.remote.api


import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.LoginResponse
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.RegistrationResponse
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.VerifyResponse
import danyil.karabinovskyi.studenthub.features.auth.domain.entity.VerifyEntity
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi {

    @Multipart
    @POST("auth/registration")
    suspend fun register(
        @Part("password") password: RequestBody,
        @Part("group") group: RequestBody,
        @Part("email") email: RequestBody,
        @Part ticketImage: MultipartBody.Part ) : BasicApiResponse<RegistrationResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : BasicApiResponse<LoginResponse>

    @POST("auth/verify")
    suspend fun authenticate() : BasicApiResponse<VerifyResponse>

}