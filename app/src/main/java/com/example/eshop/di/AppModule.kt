package com.example.eshop.di

import com.example.eshop.api.ProductApiService
import com.example.eshop.data.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideApi(): ProductApiService = Retrofit.Builder()
        .baseUrl("https://everuts-codetest.s3.ap-southeast-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApiService::class.java)

    @Provides
    fun provideRepository(api: ProductApiService): ProductRepository = ProductRepository(api)



}