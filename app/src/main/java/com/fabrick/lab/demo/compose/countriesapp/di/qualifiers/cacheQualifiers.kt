package com.fabrick.lab.demo.compose.countriesapp.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MemoryAndPersistentCache()


@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MemoryOnlyCache()
