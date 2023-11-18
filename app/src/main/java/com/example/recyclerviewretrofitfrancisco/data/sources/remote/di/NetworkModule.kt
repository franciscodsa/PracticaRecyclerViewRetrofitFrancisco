package com.example.recyclerviewretrofitfrancisco.data.sources.remote.di

import com.example.recyclerviewretrofitfrancisco.data.sources.remote.CustomerService
import com.example.recyclerviewretrofitfrancisco.data.sources.remote.ServiceInterceptor
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

//TODO: RECUERDA LAS CONSTANTES

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Create and configure OkHttpClient here
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideConverterMoshiFactory(): MoshiConverterFactory {
        // Create and configure Moshi here
        val moshi = Moshi.Builder()
            .add(LocalDateAdapter())
            .add(LocalDateTimeAdapter())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://informatica.iesquevedo.es:2326/FranciscoRest/api/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCustomerService(retrofit: Retrofit): CustomerService =
        retrofit.create(CustomerService::class.java)

}



class LocalDateAdapter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @ToJson
    fun toJson( value: LocalDate): String {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @FromJson
    fun fromJson(value: String): LocalDate {
        return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

class LocalDateTimeAdapter {

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}
