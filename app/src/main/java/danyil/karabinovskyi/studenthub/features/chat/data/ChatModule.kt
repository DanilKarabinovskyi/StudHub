package danyil.karabinovskyi.studenthub.features.chat.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.di.NetworkModule
import danyil.karabinovskyi.studenthub.features.auth.data.remote.api.AuthApi
import danyil.karabinovskyi.studenthub.features.auth.data.repository.AuthRepositoryImpl
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.chat.data.remote.api.ChatApi
import danyil.karabinovskyi.studenthub.features.chat.data.repository.ChatRepositoryImpl
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ChatModule {
    @Singleton
    @Provides
    fun provideChatApi(client: OkHttpClient) : ChatApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideChatRepository(chatApi: ChatApi) : ChatRepository {
        return ChatRepositoryImpl(chatApi)
    }
}