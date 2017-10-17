package ru.futurobot.futurobotforreddit.view.top

import dagger.Module
import dagger.Provides
import ru.futurobot.futurobotforreddit.businesslogic.feed.PagingTopFeedLoader
import ru.futurobot.futurobotforreddit.businesslogic.feed.TopFeedLoader
import ru.futurobot.futurobotforreddit.businesslogic.http.RedditApiDecorator

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
@Module
class TopModule {

  @Provides fun providesTopFeedLoader(api: RedditApiDecorator): TopFeedLoader {
    return TopFeedLoader(PagingTopFeedLoader(api))
  }

  @Provides fun providesTopPresenter(topFeedLoader: TopFeedLoader): TopPresenter {
    return TopPresenter(topFeedLoader)
  }
}