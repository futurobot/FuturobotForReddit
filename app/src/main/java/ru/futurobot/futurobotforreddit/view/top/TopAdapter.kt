package ru.futurobot.futurobotforreddit.view.top

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.futurobot.futurobotforreddit.businesslogic.model.RedditNewsItem
import ru.futurobot.futurobotforreddit.view.ui.viewholder.LoadingViewHolder
import ru.futurobot.futurobotforreddit.view.ui.viewholder.RedditNewsViewHolder

/**
 * Created by kenny on 17.10.2017.
 * Futurobot team
 */
class TopAdapter(
    val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var isLoadingNextPage: Boolean = false
    private set
  private var items: List<RedditNewsItem>? = null

  companion object {
    val VIEW_TYPE_LOADING_MORE_NEXT_PAGE: Int = 0
    val VIEW_TYPE_NEWS_ITEM: Int = 1
  }

  override fun getItemCount(): Int {
    return if (items == null) 0 else (items!!.size + (if (isLoadingNextPage) 1 else 0))
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    when(holder){
      is RedditNewsViewHolder -> holder.bind(items!![position])
      is LoadingViewHolder -> {}
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    when(viewType){
      VIEW_TYPE_LOADING_MORE_NEXT_PAGE -> return LoadingViewHolder.create(layoutInflater)
      VIEW_TYPE_NEWS_ITEM ->  return RedditNewsViewHolder.create(layoutInflater)
    }
    throw IllegalStateException("Don`t know about item type $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    if (isLoadingNextPage && position == items!!.size) {
      return VIEW_TYPE_LOADING_MORE_NEXT_PAGE
    }
    return VIEW_TYPE_NEWS_ITEM
  }

  fun setLoadingNextPage(loadingNextPage: Boolean): Boolean {
    val hasLoadingMoreChanges = loadingNextPage != isLoadingNextPage

    val notifyInserted = loadingNextPage && hasLoadingMoreChanges
    val notifyRemoved = !loadingNextPage && hasLoadingMoreChanges
    isLoadingNextPage = loadingNextPage

    if (items != null) {
      if (notifyInserted) {
        notifyItemInserted(items!!.size)
      } else if (notifyRemoved) {
        notifyItemRemoved(items!!.size)
      }
    }
    return hasLoadingMoreChanges
  }

  fun setItems(newItems: List<RedditNewsItem>) {
    val oldItems = items
    items = newItems
    if (oldItems == null) {
      notifyDataSetChanged()
    } else {
      DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
          return oldItems.size
        }

        override fun getNewListSize(): Int {
          return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
          val oldItem = oldItems[oldItemPosition]
          val newItem = newItems[newItemPosition]
          return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
          val oldItem = oldItems[oldItemPosition]
          val newItem = newItems[newItemPosition]
          return oldItem == newItem
        }

      }, true).dispatchUpdatesTo(this)
    }
  }
}