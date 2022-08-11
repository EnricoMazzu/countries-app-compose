package com.fabrick.lab.demo.compose.countriesapp.data.impl

import com.fabrick.lab.demo.compose.countriesapp.graphql.*
import com.fabrick.lab.demo.compose.countriesapp.model.Continent
import com.fabrick.lab.demo.compose.countriesapp.model.Country
import com.fabrick.lab.demo.compose.countriesapp.model.CountryDetails
import com.fabrick.lab.demo.compose.countriesapp.model.Language


fun CountriesQuery.Data?.mapToModel(): List<Country> {
    return this?.let {
        it.countries.map { c ->
            Country(
                code = c.code,
                name = c.name,
                emoji = c.emoji,
                languages = c.languages.map {  l-> Language(
                    code = l.code,
                    name = l.name.orEmpty()
                )
                }
            )
        }
    }?: ArrayList()
}


fun FilteredCountriesQuery.Data?.mapToModel(): List<Country> {
    return this?.let {
        it.countries.map { c ->
            Country(
                code = c.code,
                name = c.name,
                emoji = c.emoji,
                languages = c.languages.map {  l-> Language(
                    code = l.code,
                    name = l.name.orEmpty()
                )}
            )
        }
    }?: ArrayList()
}


fun CountryDetailsQuery.Data?.mapToModel(): CountryDetails? {
    return this?.country?.let {
        CountryDetails(
            code = it.code,
            name = it.name,
            phone = it.phone,
            emoji = it.emoji,
            capital = it.capital,
            continent = it.continent.name,
            currency = it.currency,
            languages = it.languages.map { l -> l.name.orEmpty() })
    }
}

fun ContinentsQuery.Data?.mapToModel(): List<Continent>{
    return this?.continents?.map {
        Continent(
            code = it.code,
            name = it.name
        )
    }?: ArrayList()
}

fun LanguagesQuery.Data?.mapToModel(): List<Language>{
    return this?.languages?.map {
        Language(
            code = it.code,
            name = it.name.orEmpty()
        )
    }?: ArrayList()
}