package com.demo.composeapp.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.demo.composeapp.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PaletteLoadedListener
import com.skydoves.landscapist.palette.PalettePlugin

@Preview
@Composable
fun NetworkImage(
  @PreviewParameter(NetworkUrlPreviewProvider::class) url: String,
  modifier: Modifier = Modifier,
  circularRevealEnabled: Boolean = false,
  contentScale: ContentScale = ContentScale.Crop,
  paletteLoadedListener: PaletteLoadedListener? = null
) {
  CoilImage(
    imageModel = { url },
    modifier = modifier,
    imageOptions = ImageOptions(contentScale = contentScale),
    component = rememberImageComponent {
      if (circularRevealEnabled) {
        +CircularRevealPlugin()
      }
      if (paletteLoadedListener != null) {
        +PalettePlugin(paletteLoadedListener = paletteLoadedListener)
      }
      +CrossfadePlugin(duration = 350)
    },
    previewPlaceholder = R.drawable.thumbnail,
    failure = {
      Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = "image request failed.",
          style = MaterialTheme.typography.body2
        )
      }
    },
  )
}
