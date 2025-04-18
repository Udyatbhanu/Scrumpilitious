package com.bhanu.baliyar.scrumpilitious.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(val repository: RecipeRepository) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state


    fun fetchRecipes() {
        viewModelScope.launch {
            when (val result = repository.getRecipes()) {
                is ResultWrapper.Success -> _state.value = HomeScreenState.Success(result.response.recipes)
                is ResultWrapper.Error -> _state.value =
                    HomeScreenState.Error(result.message ?: "There was an error processing the response")
            }
        }
    }
}