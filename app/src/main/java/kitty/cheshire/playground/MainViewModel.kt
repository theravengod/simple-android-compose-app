package kitty.cheshire.playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kitty.cheshire.playground.db.Element
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val elementData = liveData {
        emit(
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                App.instance.elementsDAO.getAllElements()
            }
        )
    }

    val otherElementData: StateFlow<List<Element>> = flow {
        App.instance.elementsDAO.getAllElementsAsFlow().collect { elements ->
            emit(elements)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )



}