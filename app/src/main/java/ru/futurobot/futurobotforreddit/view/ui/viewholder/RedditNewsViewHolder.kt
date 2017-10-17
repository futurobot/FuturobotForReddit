package ru.futurobot.futurobotforreddit.view.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import ru.futurobot.futurobotforreddit.R
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem

/**
 * Created by kenny on 17.10.2017.
 * Futurobot team
 */
class RedditNewsViewHolder private constructor(val view: View) : RecyclerView.ViewHolder(view) {
  companion object {
    fun create(layoutInflater: LayoutInflater): RedditNewsViewHolder {
      return RedditNewsViewHolder(layoutInflater.inflate(R.layout.reddit_news_item, null, false))
    }
  }

  fun bind(item: RedditNewsItem){

  }
}