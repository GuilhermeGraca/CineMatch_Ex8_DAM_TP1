package com.example.cinematch.ui.watchlist

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.cinematch.R
import kotlin.Int
import kotlin.String

public class WatchlistFragmentDirections private constructor() {
  private data class ActionWatchlistFragmentToDetailsFragment(
    public val imdbId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_watchlistFragment_to_detailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("imdbId", this.imdbId)
        return result
      }
  }

  public companion object {
    public fun actionWatchlistFragmentToDetailsFragment(imdbId: String): NavDirections =
        ActionWatchlistFragmentToDetailsFragment(imdbId)
  }
}
