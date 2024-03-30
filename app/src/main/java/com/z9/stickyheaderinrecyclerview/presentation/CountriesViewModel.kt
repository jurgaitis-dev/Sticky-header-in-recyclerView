package com.z9.stickyheaderinrecyclerview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z9.stickyheaderinrecyclerview.data.repository.CountryRepositoryImpl
import com.z9.stickyheaderinrecyclerview.domain.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CountryState(listOf()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    countries = countryRepository.getCountries()
                )
            }
        }
    }
}