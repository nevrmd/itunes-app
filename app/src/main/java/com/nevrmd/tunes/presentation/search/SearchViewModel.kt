package com.nevrmd.tunes.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevrmd.tunes.domain.use_case.SearchMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMediaUseCase: SearchMediaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        combine(
            _state.map { it.query }.distinctUntilChanged(),
            _state.map { it.selectedFilter }.distinctUntilChanged()
        ) { query, filter -> query to filter }
            .transformLatest { (query, filter) ->
                if (query.isBlank()) {
                    emit(Result.success(emptyList()))
                } else {
                    _state.update { it.copy(isLoading = true, error = null) }
                    delay(500L)
                    emit(searchMediaUseCase(query, filter))
                }
            }
            .onEach { result ->
                result.onSuccess { items ->
                    _state.update { it.copy(results = items, isLoading = false) }
                }.onFailure { error ->
                    _state.update { it.copy(error = error.localizedMessage, isLoading = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                _state.update { it.copy(query = event.query) }
            }

            is SearchEvent.OnMediaChipSelect -> {
                _state.update { it.copy(selectedFilter = event.mediaType) }
            }

            is SearchEvent.OnItemClick -> {
                // Handle click
            }
        }
    }
}