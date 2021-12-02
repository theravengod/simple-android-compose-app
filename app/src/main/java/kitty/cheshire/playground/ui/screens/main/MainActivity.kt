package kitty.cheshire.playground.ui.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import kitty.cheshire.playground.db.Element
import kitty.cheshire.playground.ui.theme.PlaygroundTheme
import org.koin.android.ext.android.inject

const val USE_FLOW = true

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

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
    val elements: List<Element> by viewModel.elementsStateFlow.collectAsState()
    ShowData(data = elements)
}

@Composable
fun ShowDataLiveData(viewModel: MainViewModel) {
    val elements: State<List<Element>?> = viewModel.elementsLiveData.observeAsState()
    elements.value?.let { ShowData(data = it) }
}

@Composable
fun ShowData(data: List<Element>) {
    LazyColumn(
        content = {
            items(data) { itemData ->
                Text(text = itemData.data ?: "NULL" )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}