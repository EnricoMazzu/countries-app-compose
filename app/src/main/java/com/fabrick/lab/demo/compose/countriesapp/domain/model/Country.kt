package com.fabrick.lab.demo.compose.countriesapp.domain.model

data class Country(val code: String, val name: String, val emoji: String, val languages: List<Language>)
