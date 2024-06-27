package com.example.tipcalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenUI {
                // Navigate to the next screen after delay
                navigateToNextScreen()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private fun navigateToNextScreen() {
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()
    }
}

@Composable
fun SplashScreenUI(onTimeout: () -> Unit) {
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        delay(2000) // Initial delay before starting animation
        visible = false // Start animation by hiding the logo
        onTimeout()
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedLogo(visible = visible)
            Text(
                text = "Tip Calculator",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
@Composable
fun AnimatedLogo(visible: Boolean) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0f) }

    LaunchedEffect(visible) {
        if (visible) {
            alpha.animateTo(1f, animationSpec = tween(durationMillis = 1000))
            scale.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        } else {
            alpha.animateTo(0f, animationSpec = tween(durationMillis = 500))
            scale.animateTo(0f, animationSpec = tween(durationMillis = 500))
        }
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .scale(scale.value)
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Logo",
            modifier = Modifier.alpha(alpha.value)
        )
    }
}

fun Modifier.scale(scale: Float): Modifier {
    return this.then(
        Modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale
        )
    )
}
