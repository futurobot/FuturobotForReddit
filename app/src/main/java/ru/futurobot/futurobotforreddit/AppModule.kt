package ru.futurobot.futurobotforreddit

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
@Module
class AppModule(val app: RedditApplication) {

  @Provides
  @Singleton
  fun providesApplication(): RedditApplication {
    return this.app
  }

}
