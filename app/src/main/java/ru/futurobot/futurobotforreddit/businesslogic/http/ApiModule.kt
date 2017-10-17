package ru.futurobot.futurobotforreddit.businesslogic.http

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
@Module
class ApiModule {

  @Provides @Singleton
  fun providesRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("https://reddit.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Provides @Singleton
  fun providesApi(retrofit: Retrofit): RedditApi {
    return retrofit.create(RedditApi::class.java)
  }

  @Provides @Singleton
  fun providesApiDecorator(api: RedditApi): RedditApiDecorator {
    return RedditApiDecorator(api)
  }
}