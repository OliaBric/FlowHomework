package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.CatsViewModel.Result.Initial

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Result<Any>>(Initial)
    val uiState: StateFlow<Result<Any>> = _uiState

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch {
                    _uiState.value = Result.Error("Ошибка выполнения запроса")
                }
                .collect {
                    _uiState.value = Result.Success(it)
                }
        }
    }

    sealed class Result<out T> {
//        object Initial : Result()
//        class Success(val catData: Fact) : Result()
//        class Error(val message: String) : Result()

        object Initial : Result<Nothing>()
        data class Success<T>(val catData: Fact) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}