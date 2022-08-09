package com.fabrick.lab.demo.compose.countriesapp.initialize

import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import com.fabrick.lab.demo.compose.countriesapp.CountriesApplication
import timber.log.Timber

class AppInitializer {

    fun initialize(countriesApplication: CountriesApplication) {
        initLogger()
        initEmoji(countriesApplication)
        Timber.d("initialize")
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initEmoji(app: CountriesApplication) {
        val config: EmojiCompat.Config = BundledEmojiCompatConfig(app)
        EmojiCompat.init(config)
    }
}