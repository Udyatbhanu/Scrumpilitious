package com.bhanu.baliyar.scrumpilitious.presentation.screens

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhanu.baliyar.scrumpilitious.core.ResultWrapper
import com.bhanu.baliyar.scrumpilitious.core.notifications.NotificationHelper
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import com.bhanu.baliyar.scrumpilitious.domain.usecases.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val getRecipesUseCase: GetRecipesUseCase, private val notificationHelper: NotificationHelper
) :
    ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun fetchRecipes() {
        notificationHelper.showNotification("Hello", "This is a test notification")

        getRecipesUseCase()
            .retry(1)
            .catch { e ->
                _state.value = HomeScreenState.Error(e.message ?: "Unknown Error")
            }
            .onEach { result ->
                _state.value = when (result) {
                    is ResultWrapper.Loading -> HomeScreenState.Loading
                    is ResultWrapper.Success -> HomeScreenState.Success(result.response.recipes)
                    is ResultWrapper.Error -> HomeScreenState.Error(
                        result.message ?: "Unknown Error"
                    )
                }
            }.launchIn(viewModelScope)
    }
}