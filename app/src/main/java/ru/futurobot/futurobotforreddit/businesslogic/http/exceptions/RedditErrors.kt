package ru.futurobot.futurobotforreddit.businesslogic.http.exceptions

/**
 * Created by kenny on 16.10.2017.
 * Futurobot team
 */
interface RedditErrors {
  class AuthenticationError : Exception()
  class SomeOtherError: Exception()
}