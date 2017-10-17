package ru.futurobot.futurobotforreddit.view.top

import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
data class TopViewState(val loadingFirstPage: Boolean = false,
    val firstPageError: Throwable? = null,
    val items: List<RedditNewsItem>? = null,
    val loadingNextPage: Boolean = false,
    val loadingNextPageError: Throwable? = null,
    val loadingPullToRefresh: Boolean = false,
    val pullToRefreshError: Throwable? = null)

