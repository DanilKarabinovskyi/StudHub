package danyil.karabinovskyi.studenthub.features.auth.data.repository

import android.content.SharedPreferences
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.*
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.auth.data.remote.api.AuthApi
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.VerifyResponse
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPreferences: SharedPrefs
) :
    AuthRepository {
    override suspend fun register(
    password: RequestBody, email: RequestBody, group: RequestBody,
    file: MultipartBody.Part
    ): SimpleResource {
        return try {
            val response = api.register(password,email, group,
                file)
            if(response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg:String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_unknown)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_unknown)
            )
        }
    }

    override suspend fun login(loginRequest: LoginRequest): SimpleResource {
        return try {
            val response = api.login(loginRequest)
            if(response.successful) {
                response.data?.let { authResponse ->
                    println("Overriding token with ${authResponse.token}")
                    sharedPreferences.saveToken(authResponse.token)
                }
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun authenticate(): Resource<VerifyResponse> {
        return try {
            val response = api.authenticate()
            if(response.successful) {
                if(response.data != null){
                    Resource.Success(data = response.data)
                }else{
                    response.message?.let { msg ->
                        Resource.Error(UiText.DynamicString(msg))
                    } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
                }
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }

        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}