package ru.futurobot.futurobotforreddit.view.top

import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem

abstract class PartialStateChanges

class FirstPageLoading : PartialStateChanges()
class FirstPageError(val error: Throwable) : PartialStateChanges()
class FirstPageLoaded(val items: List<RedditNewsItem>) : PartialStateChanges()

class NextPageLoading : PartialStateChanges()
class NextPageError(val error: Throwable) : PartialStateChanges()
class NextPageLoaded(val items: List<RedditNewsItem>) : PartialStateChanges()

class PullToRefreshLoading : PartialStateChanges()
class PullToRefreshError(val error: Throwable) : PartialStateChanges()
class PullToRefreshLoaded(val items: List<RedditNewsItem>) : PartialStateChanges()