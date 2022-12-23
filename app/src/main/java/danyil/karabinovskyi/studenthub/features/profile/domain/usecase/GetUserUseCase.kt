package danyil.karabinovskyi.studenthub.features.profile.domain.usecase

import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.features.profile.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPrefs: SharedPrefs
) {
    suspend fun execute(): User {
        var user = sharedPrefs.getUser()
        if (user == null) {
            val response = userRepository.getUser()
            user = if(response.data!=null){
                sharedPrefs.saveUser(response.data)
                response.data
            }else{
                User()
            }
        }
        return user
    }

}