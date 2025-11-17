package hu.bme.ait.newsretrofitpulltorefreshplaceholderloadingdemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.ait.newsretrofitpulltorefreshplaceholderloadingdemo.network.MoneyAPI
import hu.bme.ait.newsretrofitpulltorefreshplaceholderloadingdemo.network.NewsAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoneyExchangeAPIHost
//"https://api.exchangerate-api.com/"


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsAPIHost
//https://newsapi.org/v2/top-headlines?country=hu&apiKey=e670040e6e2c42988d4725bf2413afb4



@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @MoneyExchangeAPIHost
    @Singleton
    fun provideMoneyExchangeAPIRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(
                Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()) )
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoneyAPI(@MoneyExchangeAPIHost retrofit: Retrofit): MoneyAPI {
        return retrofit.create(MoneyAPI::class.java)
    }

    @Provides
    @NewsAPIHost
    @Singleton
    fun provideNewsAPIRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()) )
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsAPI(@NewsAPIHost retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }
}