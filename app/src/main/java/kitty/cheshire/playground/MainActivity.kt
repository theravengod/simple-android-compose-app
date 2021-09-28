package kitty.cheshire.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kitty.cheshire.playground.db.Element
import kitty.cheshire.playground.ui.theme.PlaygroundTheme

const val USE_FLOW = true

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    if (USE_FLOW) {
                        ShowDataFlow(viewModel = viewModel)
                    } else {
                        ShowDataLiveData(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowDataFlow(viewModel: MainViewModel) {
    val elements: List<Element> by viewModel.otherElementData.collectAsState()
    
    LazyColumn(
        content = {
            items(elements) { itemData ->
                Text(text = itemData.data ?: "NULL" )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ShowDataLiveData(viewModel: MainViewModel) {
    val elements: List<Element> by viewModel.elementData.observeAsState(listOf())

    LazyColumn(
        content = {
            items(elements) { itemData ->
                Text(text = itemData.data ?: "NULL" )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaygroundTheme {
        Greeting("Android")
    }
}