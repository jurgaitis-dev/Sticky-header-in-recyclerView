package com.z9.stickyheaderinrecyclerview.domain.repository

import com.z9.stickyheaderinrecyclerview.domain.model.Country

interface CountryRepository {
    suspend fun getCountries() : List<Country>
}