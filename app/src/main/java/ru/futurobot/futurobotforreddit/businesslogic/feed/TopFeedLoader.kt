package ru.futurobot.futurobotforreddit.businesslogic.feed

import io.reactivex.Observable
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class TopFeedLoader(private val pagingTopFeedLoader: PagingTopFeedLoader) {
  fun loadFirstPage(): Observable<List<RedditNewsItem>> {
    return pagingTopFeedLoader.newestPage().map { it.items }
  }

  fun loadNextPage(): Observable<List<RedditNewsItem>> {
    return pagingTopFeedLoader.nextPage().map { it.items }
  }
}