package kitty.cheshire.playground.ui.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kitty.cheshire.playground.ui.theme.PlaygroundTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ShowCoffee(mainViewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ShowCoffee(mainViewModel: MainViewModel) {
    val coffeeItems = mainViewModel.coffeeStateFlow.collectAsState()
    LazyColumn(content = {
        items(coffeeItems.value) { item ->
            Column {
                Text(text = item.blendName, style = MaterialTheme.typography.h6)
                Text(text = item.origin, style = MaterialTheme.typography.body2)
            }
        }
    })
}