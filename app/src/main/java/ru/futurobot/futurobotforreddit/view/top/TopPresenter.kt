package ru.futurobot.futurobotforreddit.view.top

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.futurobot.futurobotforreddit.businesslogic.feed.TopFeedLoader
import timber.log.Timber

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */

class TopPresenter(val topFeedLoader: TopFeedLoader) : MviBasePresenter<TopView, TopViewState>() {

  override fun bindIntents() {
    val loadFirstPage = intent(TopView::loadFirstPageIntent)
        .doOnNext { Timber.d("intent: load first page") }
        .flatMap { topFeedLoader.loadFirstPage() }
        .map { FirstPageLoaded(it) as PartialStateChanges }
        .startWith { FirstPageLoading() }
        .onErrorReturn { FirstPageError(it) }
        .subscribeOn(Schedulers.io())

    val nextPage = intent(TopView::loadNextPageIntent)
        .doOnNext { Timber.d("intent: load next page") }
        .flatMap { topFeedLoader.loadNextPage() }
        .map { NextPageLoaded(it) as PartialStateChanges }
        .startWith(NextPageLoading())
        .onErrorReturn { NextPageError(it) }
        .subscribeOn(Schedulers.io())

    val pullToRefresh = intent(TopView::loadNextPageIntent)
        .doOnNext { Timber.d("intent: pull to refresh") }
        .flatMap { topFeedLoader.loadFirstPage() }
        .map { PullToRefreshLoaded(it) as PartialStateChanges }
        .startWith(PullToRefreshLoading())
        .onErrorReturn { PullToRefreshError(it) }
        .subscribeOn(Schedulers.io())

    val allIntentsObservable = Observable.merge(loadFirstPage, nextPage, pullToRefresh)
        .observeOn(AndroidSchedulers.mainThread())

    val initialState = TopViewState(loadingFirstPage = true)
    subscribeViewState(
        allIntentsObservable.scan(initialState, this::viewStateReducer).distinctUntilChanged(),
        TopView::render)
  }

  private fun viewStateReducer(previousState: TopViewState,
      partialChange: PartialStateChanges): TopViewState {
    when (partialChange) {
      is FirstPageLoading -> return previousState.copy(loadingFirstPage = true,
          firstPageError = null)
      is FirstPageLoaded -> return previousState.copy(loadingFirstPage = false,
          firstPageError = null, items = partialChange.items)
      is FirstPageError -> return previousState.copy(loadingFirstPage = false,
          firstPageError = partialChange.error)

      is NextPageLoading -> return previousState.copy(loadingNextPage = true,
          loadingNextPageError = null)
      is NextPageLoaded -> return previousState.copy(loadingNextPage = false,
          loadingNextPageError = null, items = previousState.items!! + partialChange.items)
      is NextPageError -> return previousState.copy(loadingNextPage = false,
          loadingNextPageError = partialChange.error)

      is PullToRefreshLoading -> return previousState.copy(loadingPullToRefresh = true,
          firstPageError = null)
      is PullToRefreshLoaded -> return previousState.copy(loadingPullToRefresh = false,
          firstPageError = null, items = partialChange.items)
      is PullToRefreshError -> return previousState.copy(loadingPullToRefresh = false,
          firstPageError = partialChange.error)

      else -> throw IllegalStateException(
          "Don`t know how to reduce the partial state ${previousState::class}")
    }
  }

}