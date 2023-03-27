package com.demo.composeapp.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class NetworkUrlPreviewProvider : PreviewParameterProvider<String> {
  override val count: Int
    get() = super.count
  override val values: Sequence<String>
    get() = sequenceOf("https://i.dummyjson.com/data/products/1/thumbnail.jpg")
}
