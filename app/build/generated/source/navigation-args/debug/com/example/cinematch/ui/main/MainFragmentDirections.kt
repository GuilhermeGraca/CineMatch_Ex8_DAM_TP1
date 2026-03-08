package com.example.cinematch.ui.main

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.cinematch.R
import kotlin.Int
import kotlin.String

public class MainFragmentDirections private constructor() {
  private data class ActionMainFragmentToResultsFragment(
    public val genre: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_mainFragment_to_resultsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("genre", this.genre)
        return result
      }
  }

  public companion object {
    public fun actionMainFragmentToResultsFragment(genre: String): NavDirections =
        ActionMainFragmentToResultsFragment(genre)

    public fun actionMainFragmentToWatchlistFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_watchlistFragment)
  }
}
