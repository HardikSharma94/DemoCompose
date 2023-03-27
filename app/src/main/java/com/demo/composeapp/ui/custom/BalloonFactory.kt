package com.demo.composeapp.ui.custom

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.demo.composeapp.R
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import com.skydoves.balloon.textForm

object BalloonFactory {

  fun create(context: Context, title: String, lifecycle: LifecycleOwner?): Balloon {
    val textForm = textForm(context) {
      setText(title)
      setTextSize(15f)
      setTextColorResource(R.color.white)
    }

    return createBalloon(context) {
      setArrowSize(10)
      setPadding(10)
      setCornerRadius(8f)
      setElevation(4)
      setTextForm(textForm)
      setArrowPosition(0.5f)
      setArrowOrientation(ArrowOrientation.TOP)
      setBackgroundColorResource(R.color.purple_500)
      setDismissWhenClicked(true)
      setDismissWhenShowAgain(true)
      setBalloonAnimation(BalloonAnimation.ELASTIC)
      setLifecycleOwner(lifecycle)
      build()
    }
  }
}
