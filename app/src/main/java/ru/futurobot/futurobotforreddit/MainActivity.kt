package ru.futurobot.futurobotforreddit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.futurobot.futurobotforreddit.view.top.TopFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
        .replace(R.id.container, TopFragment())
        .commit()
  }
}
