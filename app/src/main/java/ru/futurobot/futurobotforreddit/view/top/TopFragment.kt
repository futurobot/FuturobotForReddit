package ru.futurobot.futurobotforreddit.view.top

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_top.*
import ru.futurobot.futurobotforreddit.R
import ru.futurobot.futurobotforreddit.RedditApplication
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class TopFragment : MviFragment<TopView, TopPresenter>(), TopView {

  @Inject lateinit var topPresenter: TopPresenter
  private lateinit var adapter: TopAdapter
  private lateinit var layoutManager: LinearLayoutManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerTopComponent.builder().appComponent(RedditApplication.appComponent)
        .topModule(TopModule())
        .build().inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_top, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = TopAdapter(LayoutInflater.from(view!!.context))
    layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = layoutManager
  }

  override fun createPresenter(): TopPresenter {
    Timber.d("createPresenter")
    return topPresenter
  }

  override fun loadFirstPageIntent(): Observable<Boolean> {
    return Observable.just(true).doOnComplete { Timber.d("firstPage completed") }
  }

  override fun loadNextPageIntent(): Observable<Boolean> {
    return RxRecyclerView.scrollEvents(recyclerView)
        .filter { !adapter.isLoadingNextPage }
        .filter { it.view().scrollState == RecyclerView.SCROLL_STATE_IDLE }
        .filter { layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1 }
        .map { true }
  }

  override fun pullToRefreshIntentIntent(): Observable<Boolean> {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).map { true }
  }

  override fun render(state: TopViewState) {
    Timber.d("render $state")
    if (!state.loadingFirstPage && state.firstPageError == null) {
      renderShowData(state)
    } else if (state.loadingFirstPage) {
      renderFirstPageLoading(state)
    } else if (state.firstPageError != null) {
      renderFirstPageError(state)
    } else {
      throw IllegalStateException("Unknown view state $state")
    }
  }

  private fun renderFirstPageError(state: TopViewState) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      TransitionManager.beginDelayedTransition(view as ViewGroup?)
    }
    loadingView.visibility = View.GONE
    errorView.visibility = View.VISIBLE
    swipeRefreshLayout.visibility = View.GONE
    errorText.text = state.firstPageError.toString()
  }

  private fun renderFirstPageLoading(state: TopViewState) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      TransitionManager.beginDelayedTransition(view as ViewGroup?)
    }
    loadingView.visibility = View.VISIBLE
    errorView.visibility = View.GONE
    swipeRefreshLayout.visibility = View.GONE
  }

  private fun renderShowData(state: TopViewState) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      TransitionManager.beginDelayedTransition(view as ViewGroup?)
    }
    loadingView.visibility = View.GONE
    errorView.visibility = View.GONE
    swipeRefreshLayout.visibility = View.VISIBLE

    val changed = adapter.setLoadingNextPage(state.loadingNextPage)
    if(changed && state.loadingNextPage){
      // scroll to the end of list so that the user sees the load more progress bar
      recyclerView.smoothScrollToPosition(adapter.itemCount)
    }
    adapter.setItems(state.items!!)

    val pullToRefreshFinished = swipeRefreshLayout.isRefreshing &&
        !state.loadingPullToRefresh
        && state.pullToRefreshError == null
    if(pullToRefreshFinished){
      recyclerView.smoothScrollToPosition(0)
    }

    swipeRefreshLayout.isRefreshing = state.loadingPullToRefresh

    if(state.loadingNextPageError != null){
      Snackbar.make(view!!, state.loadingNextPageError.toString(), Snackbar.LENGTH_LONG).show()
    }

    if(state.pullToRefreshError != null){
      Snackbar.make(view!!, state.pullToRefreshError.toString(), Snackbar.LENGTH_LONG).show()
    }
  }

}