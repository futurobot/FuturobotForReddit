package ru.futurobot.futurobotforreddit.businesslogic.extensions

import ru.futurobot.futurobotforreddit.businesslogic.http.RedditDataResponse
import ru.futurobot.futurobotforreddit.businesslogic.http.RedditNewsDataResponse
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditFeedItem
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */

/**
 * Mapper RedditNewsDataResponse -> RedditNewsItem
 */
fun RedditNewsDataResponse.mapToModel(): RedditNewsItem {
  return RedditNewsItem(id = this.id,
      title = this.title,
      author = this.author,
      commentsCount = this.num_comments,
      url = this.url,
      thumbnail = this.thumbnails,
      permalink = this.permalink)
}

/**
 * RedditDataResponse -> RedditFeedItem
 */
fun RedditDataResponse.mapToModel(): RedditFeedItem {
  return RedditFeedItem(after = this.after,
      before = this.before,
      items = this.children.map { it.data.mapToModel() }.toList())
}