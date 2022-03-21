package com.shahroz.twitterpg.di

import com.shahroz.twitterpg.data.mapper.TweetMapper
import com.shahroz.twitterpg.data.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun provideUserMapper(): UserMapper = UserMapper()

    @Provides
    fun provideTweetMapper(): TweetMapper = TweetMapper()
}