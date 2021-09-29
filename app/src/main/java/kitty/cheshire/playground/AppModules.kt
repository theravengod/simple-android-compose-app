package kitty.cheshire.playground

import kitty.cheshire.playground.ui.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { parameters ->
        MainViewModel()
    }
}
