package com.fabrick.lab.demo.compose.countriesapp.model

import com.mzzlab.demo.countriesapp.model.Language

data class Country(val code: String, val name: String, val emoji: String, val languages: List<Language>)
