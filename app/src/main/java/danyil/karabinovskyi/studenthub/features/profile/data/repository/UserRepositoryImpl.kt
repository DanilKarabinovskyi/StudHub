package danyil.karabinovskyi.studenthub.features.profile.data.repository

import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.core.data.handleErrors
import danyil.karabinovskyi.studenthub.features.profile.data.remote.UserApi
import danyil.karabinovskyi.studenthub.features.profile.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    //todo maybe call this earlier to avoid problems on chat screen with id comparison
    override suspend fun getUser(): Resource<User> {
        return handleErrors {
            val response = userApi.getUser()
            if(response.successful) {
                Resource.Success(response.data?.toUser())
            } else {
                response.message?.let { msg:String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }
}