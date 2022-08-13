package com.fabrick.lab.demo.compose.countriesapp.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.common.collectResourceStates
import com.fabrick.lab.demo.compose.countriesapp.domain.repo.CountriesRepo
import com.fabrick.lab.demo.compose.countriesapp.domain.model.CountryDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    data class UiState (
        val onLoading: Boolean = true,
        val countryDetails: CountryDetails? = null,
        val error: Exception? = null
    )

    //prevent multiple loading in parallel
    private var pendingJob: Job? = null

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var countryCode:String? = null

    fun setCountryCode(countryCode: String) {
        Timber.d("countryCode $countryCode")
        this.countryCode = countryCode
    }

    fun reload(){
        loadDetails(countryCode!!)
    }

    private fun loadDetails(countryCode: String) {
        pendingJob?.cancel()
        pendingJob = countriesRepo.getCountryDetails(countryCode).collectResourceStates(
            scope = viewModelScope,
            loading = { handleLoading() },
            success = { handleDetailsReady(it) },
            error = { handleError(it) }
        )
    }

    private fun handleLoading() {
        val state = uiState.value
        _uiState.value = state.copy(onLoading = true)
    }

    private fun handleDetailsReady(it: Resource.Success<CountryDetails>) {
        val state = uiState.value
        _uiState.value = state.copy(
            onLoading = false,
            countryDetails = it.data
        )
    }

    private fun handleError(it: Resource.Error<CountryDetails>) {
        Timber.d("handleError $it")
        val state = uiState.value
        _uiState.value = state.copy(
            onLoading = false,
            error = it.getExceptionIfNotHandled()
        )
    }


}