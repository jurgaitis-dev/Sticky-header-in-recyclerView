package com.z9.stickyheaderinrecyclerview.domain.model

sealed class Country(
    open val name: String,
) {
    data class Letter(override val name: String) : Country(name)
    data class CountryBody(override val name: String, val code: String) : Country(name)

}