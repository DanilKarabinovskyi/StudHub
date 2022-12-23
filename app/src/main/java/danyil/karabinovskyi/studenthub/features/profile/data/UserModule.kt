package danyil.karabinovskyi.studenthub.features.profile.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.core.di.NetworkModule
import danyil.karabinovskyi.studenthub.features.chat.data.remote.api.ChatApi
import danyil.karabinovskyi.studenthub.features.chat.data.repository.ChatRepositoryImpl
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import danyil.karabinovskyi.studenthub.features.profile.data.remote.UserApi
import danyil.karabinovskyi.studenthub.features.profile.data.repository.UserRepositoryImpl
import danyil.karabinovskyi.studenthub.features.profile.domain.repository.UserRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class UserModule {
    @Singleton
    @Provides
    fun provideAuthApi(client: OkHttpClient) : UserApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userApi: UserApi) : UserRepository {
        return UserRepositoryImpl(userApi)
    }
}