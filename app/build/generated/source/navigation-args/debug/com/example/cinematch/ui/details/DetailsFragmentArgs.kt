package com.example.cinematch.ui.details

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DetailsFragmentArgs(
  public val imdbId: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("imdbId", this.imdbId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("imdbId", this.imdbId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DetailsFragmentArgs {
      bundle.setClassLoader(DetailsFragmentArgs::class.java.classLoader)
      val __imdbId : String?
      if (bundle.containsKey("imdbId")) {
        __imdbId = bundle.getString("imdbId")
        if (__imdbId == null) {
          throw IllegalArgumentException("Argument \"imdbId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"imdbId\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__imdbId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DetailsFragmentArgs {
      val __imdbId : String?
      if (savedStateHandle.contains("imdbId")) {
        __imdbId = savedStateHandle["imdbId"]
        if (__imdbId == null) {
          throw IllegalArgumentException("Argument \"imdbId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"imdbId\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__imdbId)
    }
  }
}
