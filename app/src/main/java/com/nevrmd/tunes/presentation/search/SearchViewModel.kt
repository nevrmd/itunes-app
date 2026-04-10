package com.nevrmd.tunes.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevrmd.tunes.domain.use_case.SearchMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaUseCase: SearchMediaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        _searchQuery
            .debounce(500L)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    executeSearch(query) // Now matches the signature below
                } else {
                    _state.update { it.copy(results = emptyList(), isLoading = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        _state.update { it.copy(query = newQuery) }
    }

    private fun executeSearch(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = searchMediaUseCase(query)

            result.onSuccess { items ->
                _state.update { it.copy(results = items, isLoading = false) }
            }.onFailure { error ->
                _state.update { it.copy(error = error.localizedMessage, isLoading = false) }
            }
        }
    }
}