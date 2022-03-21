package com.shahroz.twitterpg.di

import com.shahroz.twitterpg.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConfig(): ConfigurationBuilder {
        return ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey(BuildConfig.CONSUMER_KEY)
            .setOAuthConsumerSecret(BuildConfig.CONSUMER_SECRET)
            .setIncludeEmailEnabled(true)
    }

    @Provides
    @Singleton
    fun provideTwitterFactory(configurationBuilder: ConfigurationBuilder): TwitterFactory {
        return TwitterFactory(configurationBuilder.build())
    }

    @Provides
    @Singleton
    fun provideTwitter(factory: TwitterFactory): Twitter {
        return factory.instance
    }
}
