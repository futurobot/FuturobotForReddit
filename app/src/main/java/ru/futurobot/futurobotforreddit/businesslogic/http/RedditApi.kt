package ru.futurobot.futurobotforreddit.businesslogic.http

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
interface RedditApi {

  @GET("/top.json")
  fun getTop(@Query("before") before: String?,
      @Query("after") after: String?,
      @Query("limit") limit: Int): Observable<RedditNewsResponse>

}