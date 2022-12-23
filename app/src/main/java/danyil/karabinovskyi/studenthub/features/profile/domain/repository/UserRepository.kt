package danyil.karabinovskyi.studenthub.features.profile.domain.repository

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.entity.User

interface UserRepository {
    suspend fun getUser() : Resource<User>
}