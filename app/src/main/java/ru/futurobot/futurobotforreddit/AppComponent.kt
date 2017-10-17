package ru.futurobot.futurobotforreddit

import dagger.Component
import ru.futurobot.futurobotforreddit.businesslogic.http.ApiModule
import ru.futurobot.futurobotforreddit.businesslogic.http.RedditApiDecorator
import javax.inject.Singleton

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
  fun application(): RedditApplication
  fun api(): RedditApiDecorator
}