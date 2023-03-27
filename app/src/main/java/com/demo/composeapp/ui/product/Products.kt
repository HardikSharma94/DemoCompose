package com.demo.composeapp.ui.product

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.demo.composeapp.R
import com.demo.composeapp.model.Products
import com.demo.composeapp.ui.main.MainViewModel
import com.demo.composeapp.ui.theme.purple200
import java.util.*
import kotlin.reflect.KProperty

@Composable
fun Posters(
    viewModel: MainViewModel,
    selectPoster: (Long) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    val isRefreshed: Boolean by viewModel._isRefresh

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver { source, event ->
            if(event == Lifecycle.Event.ON_RESUME){
                viewModel.changeValue(true)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    if(isRefreshed){

        val posters:List<Products> by viewModel.posterList.collectAsState(initial = listOf())
        val isLoading: Boolean by viewModel.isLoading

        ConstraintLayout {
            val (body, progress) = createRefs()
            Scaffold(
                backgroundColor = MaterialTheme.colors.primarySurface,
                modifier = Modifier.constrainAs(body) {
                    top.linkTo(parent.top)
                },
            ) { innerPadding ->
                val modifier = Modifier.padding(innerPadding)

                HomePosters(modifier, posters, selectPoster)
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(progress) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }

            if (!isLoading) {
                if (posters.isEmpty()) {
                    Surface(
                        modifier = Modifier
                            .constrainAs(progress) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .wrapContentWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ){
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center

                            ) {
                                Text(
                                    text = "No data found!",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontFamily = FontFamily.Default,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
private fun PosterAppBar() {
    TopAppBar(
        elevation = 6.dp,
        backgroundColor = purple200,
        modifier = Modifier
            .statusBarsPadding()
            .height(0.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

