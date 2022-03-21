package com.shahroz.twitterpg.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.shahroz.twitterpg.data.repositories.PreferenceRepository
import com.shahroz.twitterpg.data.repositories.PreferenceRepositoryImp
import com.shahroz.twitterpg.data.repositories.TwitterRepository
import com.shahroz.twitterpg.data.repositories.TwitterRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import twitter4j.Twitter

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideTwitterRepository(twitter: Twitter): TwitterRepository {
        return TwitterRepositoryImp(twitter = twitter)
    }

    @Provides
    fun providePreferenceRepository(dataStore: DataStore<Preferences>): PreferenceRepository {
        return PreferenceRepositoryImp(dataStore = dataStore)
    }


}