package com.fabrick.lab.demo.compose.countriesapp.data.impl.mock

import com.fabrick.lab.demo.compose.countriesapp.domain.model.Continent
import com.fabrick.lab.demo.compose.countriesapp.domain.model.CountryDetails
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Language


object MockData {
    fun getContinent(): List<Continent>{
        return listOf(
            Continent("c1","cname1"),
            Continent("c2","cname2"),
            Continent("c3","cname3"),
        )
    }

    fun getLanguages(): List<Language>{
        return listOf(
            Language("l1","lname1"),
            Language("l2","lname2"),
            Language("l3","lname3"),
        )
    }

    fun getCountries(): List<CountryDetails>{
        return listOf(
            CountryDetails(
                code = "cd1",
                name = "cdname1",
                emoji = "em1",
                phone = "+34",
                continent = "cname1",
                currency = "EUR",
                capital = "cdc1",
                languages = listOf(
                    "l1"
                )
            ),
            CountryDetails(
                code = "cd2",
                name = "cdname2",
                emoji = "em2",
                phone = "+34",
                continent = "cname1",
                currency = "EUR",
                capital = "cdc2",
                languages = listOf(
                    "l2"
                )
            ),
            CountryDetails(
                code = "cd3",
                name = "cdname3",
                emoji = "em3",
                phone = "+34",
                continent = "cname2",
                currency = "EUR",
                capital = "cdc1",
                languages = listOf(
                    "l3"
                )
            ),
            CountryDetails(
                code = "cd4",
                name = "cdname2",
                emoji = "em1",
                phone = "+34",
                continent = "cname1",
                currency = "EUR",
                capital = "cdc1",
                languages = listOf(
                    "l1"
                )
            ),
            CountryDetails(
                code = "cd5",
                name = "cdname3",
                emoji = "em3",
                phone = "+34",
                continent = "cname3",
                currency = "EUR",
                capital = "cdc1",
                languages = listOf(
                    "l1"
                )
            ),
            CountryDetails(
                code = "cd6",
                name = "cdname1",
                emoji = "em1",
                phone = "+34",
                continent = "cname3",
                currency = "EUR",
                capital = "cdc1",
                languages = listOf(
                    "l1"
                )
            ),
        )
    }
}