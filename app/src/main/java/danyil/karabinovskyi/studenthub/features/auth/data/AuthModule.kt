package danyil.karabinovskyi.studenthub.features.auth.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.di.NetworkModule
import danyil.karabinovskyi.studenthub.features.auth.data.repository.AuthRepositoryImpl
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.auth.data.remote.api.AuthApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthApi(client: OkHttpClient) : AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi, prefs: SharedPrefs) : AuthRepository {
        return AuthRepositoryImpl(authApi, prefs)
    }
}