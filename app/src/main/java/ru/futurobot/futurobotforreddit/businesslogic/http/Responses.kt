package ru.futurobot.futurobotforreddit.businesslogic.http

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
class RedditNewsResponse(val data: RedditDataResponse)

class RedditDataResponse(val children: List<RedditChildrenResponse>, val after: String?,
    val before: String?)

class RedditChildrenResponse(val data: RedditNewsDataResponse)

class RedditNewsDataResponse(val id: String, val title: String, val author: String,
    val num_comments: String, val url: String, val permalink: String, val thumbnails: String)