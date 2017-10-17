package ru.futurobot.futurobotforreddit.view.top

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
interface TopView : MvpView {
  fun loadFirstPageIntent() : Observable<Boolean>
  fun loadNextPageIntent() : Observable<Boolean>
  fun pullToRefreshIntentIntent() : Observable<Boolean>
  fun render(state: TopViewState)
}
