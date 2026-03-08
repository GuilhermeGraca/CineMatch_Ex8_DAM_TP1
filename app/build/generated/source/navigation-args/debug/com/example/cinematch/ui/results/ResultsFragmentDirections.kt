package com.example.cinematch.ui.results

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.cinematch.R
import kotlin.Int
import kotlin.String

public class ResultsFragmentDirections private constructor() {
  private data class ActionResultsFragmentToDetailsFragment(
    public val imdbId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_resultsFragment_to_detailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("imdbId", this.imdbId)
        return result
      }
  }

  public companion object {
    public fun actionResultsFragmentToDetailsFragment(imdbId: String): NavDirections =
        ActionResultsFragmentToDetailsFragment(imdbId)
  }
}
