package com.example.myapplicationjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationjetpackcompose.ui.theme.MyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                // A surface container using the 'background' color from the theme
//                Greetings(modifier = Modifier.fillMaxSize())
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}
@Composable
fun Greeting(name: String) {
//    val expand = remember { mutableStateOf(false) }
    val expand = rememberSaveable{ mutableStateOf(false) }
    val extraPadding by animateDpAsState(if (expand.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "")
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp,horizontal = 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)){
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(onClick = {
                expand.value =!expand.value
            }) {
                Text(if (expand.value) "Show More" else "Show Less")
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(modifier: Modifier= Modifier,
//                     names: List<String> = listOf("World", "Jetpack Compose", "Kotlin")
                             names: List<String> = List(100){"Greeting $it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit,
                     modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), // 为modifier赋值，设置为填充最大大小
        verticalArrangement = Arrangement.Center, // 垂直居中
        horizontalAlignment = Alignment.CenterHorizontally // 水平居中
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}
//深色预览
//@Preview(
//    showBackground = true,
//    widthDp = 320,
//    uiMode = UI_MODE_NIGHT_YES,
//    name = "Dark"
//)
@Preview(showBackground = true, name = "MyAPP",widthDp = 320, heightDp = 640)
@Composable
fun MyAppPreview() {
    MyTheme {
        MyApp(Modifier.fillMaxSize())
//        OnboardingScreen()
    }
}