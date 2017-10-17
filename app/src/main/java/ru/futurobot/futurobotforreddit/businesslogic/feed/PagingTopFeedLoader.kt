package ru.futurobot.futurobotforreddit.businesslogic.feed

import io.reactivex.Observable
import ru.futurobot.futurobotforreddit.businesslogic.http.RedditApiDecorator
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditFeedItem

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class PagingTopFeedLoader(private val api: RedditApiDecorator) {
  private var currentPage: String? = null
  private var newestPageLoaded: Boolean = false

  fun newestPage(): Observable<RedditFeedItem> {
    return api.getTop()
        .doOnNext({
          newestPageLoaded = true
          currentPage = it.after
        })
  }

  fun nextPage(): Observable<RedditFeedItem> {
    return api.getTop(after = currentPage)
        .doOnNext({
          currentPage = it.after
        })
  }


}