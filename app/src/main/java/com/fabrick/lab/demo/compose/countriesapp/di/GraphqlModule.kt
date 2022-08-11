package com.fabrick.lab.demo.compose.countriesapp.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory

import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.fabrick.lab.demo.compose.countriesapp.BuildConfig
import com.fabrick.lab.demo.compose.countriesapp.di.qualifiers.MemoryAndPersistentCache
import com.fabrick.lab.demo.compose.countriesapp.di.qualifiers.MemoryOnlyCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GraphqlModule {

    @Provides
    fun provideApolloClient(@MemoryOnlyCache cache: NormalizedCacheFactory): ApolloClient {
        return with(ApolloClient.Builder()){
            serverUrl(getServerUrlConfig())
            if(shouldUseCache()){
                normalizedCache(cache)
            }
            this.build()
        }
    }

    @Provides
    @MemoryAndPersistentCache
    fun providePersistentCacheFactory(@ApplicationContext context: Context): NormalizedCacheFactory {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context, cacheDbName())
        val memoryCacheFactory = MemoryCacheFactory()
        return memoryCacheFactory
            .chain(sqlNormalizedCacheFactory)
    }

    @Provides
    @MemoryOnlyCache
    fun provideMemoryCacheFactory(): NormalizedCacheFactory {
        return MemoryCacheFactory()
    }


    private fun cacheDbName(): String = BuildConfig.CACHE_DB_NAME

    private fun shouldUseCache(): Boolean = BuildConfig.CONFIGURE_CACHE

    private fun getServerUrlConfig(): String = BuildConfig.COUNTRY_SERVER_URL


}