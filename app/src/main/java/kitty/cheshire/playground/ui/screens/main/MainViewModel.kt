package kitty.cheshire.playground.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kitty.cheshire.playground.App
import kitty.cheshire.playground.db.model.Coffee
import kitty.cheshire.playground.net.Net
import kotlinx.coroutines.flow.*

class MainViewModel: ViewModel() {

    val coffeeStateFlow: StateFlow<List<Coffee>> = flow {
        App.instance.coffeeDAO.getAllCoffeeEntries().collect { coffeeList ->
            emit(coffeeList)
        }
    }.onStart {
        Net(App.instance.coffeeDAO).getSomeRandomCoffee(30)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}