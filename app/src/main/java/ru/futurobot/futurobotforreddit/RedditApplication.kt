package ru.futurobot.futurobotforreddit

import android.app.Application
import ru.futurobot.futurobotforreddit.businesslogic.http.ApiModule
import timber.log.Timber

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class RedditApplication : Application() {

  companion object {
    var appComponent: AppComponent? = null
  }

  override fun onCreate() {
    super.onCreate()
    initTimber()
    initDagger()
  }

  private fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      // TODO: Plant crashlytics tree
    }
  }

  private fun initDagger() {
    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .apiModule(ApiModule())
        .build()
  }
}