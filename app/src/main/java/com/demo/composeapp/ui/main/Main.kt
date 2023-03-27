package com.demo.composeapp.ui.main

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.composeapp.ui.custom.ComposableLifecycle
import com.demo.composeapp.ui.details.ProductDetails
import com.demo.composeapp.ui.product.Posters
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun ProductsMainScreen() {
  val navController = rememberNavController()

  val colors = MaterialTheme.colors
  val systemUiController = rememberSystemUiController()

  var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
  var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }

  val animatedStatusBarColor by animateColorAsState(
    targetValue = statusBarColor,
    animationSpec = tween()
  )
  val animatedNavigationBarColor by animateColorAsState(
    targetValue = navigationBarColor,
    animationSpec = tween()
  )

  NavHost(navController = navController, startDestination = NavScreen.Home.route) {
    composable(NavScreen.Home.route) {
      Posters(
        viewModel = hiltViewModel(),
        selectPoster = {
          navController.navigate("${NavScreen.ProductDetails.route}/$it")
        }
      )

      LaunchedEffect(Unit) {
        statusBarColor = colors.primaryVariant
        navigationBarColor = colors.primaryVariant
      }
    }
    composable(
      route = NavScreen.ProductDetails.routeWithArgument,
      arguments = listOf(
        navArgument(NavScreen.ProductDetails.argument0) { type = NavType.LongType }
      )
    ) { backStackEntry ->
      val posterId =
        backStackEntry.arguments?.getLong(NavScreen.ProductDetails.argument0) ?: return@composable

      ProductDetails(productId = posterId, viewModel = hiltViewModel()) {
        navController.navigateUp()
      }

      LaunchedEffect(Unit) {
        statusBarColor = Color.Transparent
        navigationBarColor = colors.background
      }
    }
  }

  LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
    systemUiController.setStatusBarColor(animatedStatusBarColor)
    systemUiController.setNavigationBarColor(animatedNavigationBarColor)
  }

}

sealed class NavScreen(val route: String) {

  object Home : NavScreen("Home")

  object ProductDetails : NavScreen("PosterDetails") {

    const val routeWithArgument: String = "PosterDetails/{ids}"

    const val argument0: String = "ids"
  }

}
