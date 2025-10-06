package com.example.todoapp.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.example.todoapp.presentation.common.FIVE_HUNDRED
import com.example.todoapp.presentation.common.ZERO_FOUR
import com.example.todoapp.presentation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
            setOnExitAnimationListener { screen ->
                setUpExitAnimation(screen)
            }
        }

        enableEdgeToEdge()
        setContent {
            val startDestination = viewModel.startDestination.collectAsState()

            Surface(modifier = Modifier.fillMaxSize()) {
                startDestination.value?.let {
                    Navigation(initialRoute = it)
                }
            }
        }
    }
}

private fun setUpExitAnimation(screen: SplashScreenViewProvider) {
    val zoomX = ObjectAnimator.ofFloat(
        screen.iconView,
        "scaleX",
        ZERO_FOUR,
        0.0f
    )
    zoomX.interpolator = OvershootInterpolator()
    zoomX.duration = FIVE_HUNDRED
    zoomX.doOnEnd { screen.remove() }

    val zoomY = ObjectAnimator.ofFloat(
        screen.iconView,
        "scaleY",
        ZERO_FOUR,
        0.0f
    )
    zoomY.interpolator = OvershootInterpolator()
    zoomY.duration = FIVE_HUNDRED
    zoomY.doOnEnd { screen.remove() }

    zoomX.start()
    zoomY.start()
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivity()
}
