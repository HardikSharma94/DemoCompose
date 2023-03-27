package com.demo.composeapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.demo.composeapp.model.Products
import com.demo.composeapp.ui.custom.ImageBalloonAnchor
import com.demo.composeapp.utils.NetworkImage
import com.skydoves.landscapist.palette.rememberPaletteState

@Composable
fun ProductDetails(
  productId: Long,
  viewModel: DetailViewModel,
  pressOnBack: () -> Unit = {}
) {
  LaunchedEffect(key1 = productId) {
    viewModel.loadProductById(productId)
  }

  val details: Products? by viewModel.productDetailsFlow.collectAsState(initial = null)
  details?.let { products ->
    ProductDetailsBody(products,pressOnBack)
  }
}

@Composable
private fun ProductDetailsBody(
  products: Products,
  pressOnBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .background(MaterialTheme.colors.background)
      .fillMaxHeight()
  ) {
    var palette by rememberPaletteState(value = null)

    ConstraintLayout {
      val (arrow, image, price, brand, stock, title, content) = createRefs()

      NetworkImage(
        url = products.thumbnail,
        modifier = Modifier
          .constrainAs(image) {
            top.linkTo(parent.top)
          }
          .fillMaxWidth()
          .aspectRatio(0.85f),
        circularRevealEnabled = true,
        paletteLoadedListener = { palette = it }
      )

      Text(
        text = products.title,
        style = MaterialTheme.typography.h1,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
          .constrainAs(title) {
            top.linkTo(image.bottom)
          }
          .padding(start = 16.dp, top = 12.dp)
      )

      Text(
        text = "$"+products.price,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
          .constrainAs(content) {
            top.linkTo(title.bottom)
          }
          .padding(16.dp)
      )

      Text(
        text = products.description,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
          .constrainAs(price) {
            top.linkTo(content.bottom)
          }
          .padding(16.dp,0.dp,10.dp,16.dp)
      )

      ImageBalloonAnchor(
        reference = image,
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(0.85f),
        content = products.title,
        onClick = { balloon, anchor -> balloon.showAlignBottom(anchor) }
      )

      Icon(
        imageVector = Icons.Filled.ArrowBack,
        tint = Color.White,
        contentDescription = null,
        modifier = Modifier
          .constrainAs(arrow) {
            top.linkTo(parent.top)
          }
          .padding(12.dp)
          .statusBarsPadding()
          .clickable(onClick = { pressOnBack() })
      )
    }
  }

}

