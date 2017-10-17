package ru.futurobot.futurobotforreddit.businesslogic.http

import io.reactivex.Observable
import ru.futurobot.futurobotforreddit.businesslogic.extensions.mapToModel
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditFeedItem

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class RedditApiDecorator(val redditApi: RedditApi) {

  fun getTop(after: String? = null, before: String? = null,
      limit: Int = 20): Observable<RedditFeedItem> {
    return redditApi.getTop(before = before, after = after, limit = limit)
        .map { it.data.mapToModel() }
    // TODO: Handle 403 ?
//        .map { response ->
//          {
//            if (response.isSuccessful) {
//              response.body().data.mapToModel()
//            } else if (response.code() == 403) {
//              throw AuthenticationException()
//            } else {
//              throw Exception()
//            }
//          }
//        }.toObservable()
  }

}