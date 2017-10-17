package ru.futurobot.futurobotforreddit.view.top

import dagger.Component
import ru.futurobot.futurobotforreddit.AppComponent

@TopComponentScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(TopModule::class))
interface TopComponent {
  fun presenter(): TopPresenter
  fun inject(fragment: TopFragment)
}
