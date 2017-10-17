package ru.futurobot.futurobotforreddit.businesslogic.model

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */

data class RedditFeedItem(val after: String?, val before: String?, val items: List<RedditNewsItem>)

data class RedditNewsItem(val id: String, val title: String, val author: String,
    val commentsCount: String, val url: String, val thumbnail: String,
    val permalink: String)