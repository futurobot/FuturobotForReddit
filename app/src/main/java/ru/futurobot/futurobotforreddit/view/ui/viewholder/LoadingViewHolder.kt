package ru.futurobot.futurobotforreddit.view.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import ru.futurobot.futurobotforreddit.R

/**
 * Created by kenny on 17.10.2017.
 * Futurobot team
 */
class LoadingViewHolder private constructor(val view: View) : RecyclerView.ViewHolder(view) {
  companion object {
    fun create(layoutInflater: LayoutInflater): LoadingViewHolder {
      return LoadingViewHolder(layoutInflater.inflate(R.layout.item_loading, null, false))
    }
  }
}