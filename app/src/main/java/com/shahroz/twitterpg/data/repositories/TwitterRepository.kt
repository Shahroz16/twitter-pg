package com.shahroz.twitterpg.data.repositories

import com.shahroz.twitterpg.data.mapper.TweetMapper
import com.shahroz.twitterpg.data.mapper.UserMapper
import com.shahroz.twitterpg.data.model.Person
import twitter4j.*
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import java.io.File
import javax.inject.Inject


interface TwitterRepository {
    suspend fun getTimeLine(page: Int): ResponseList<Status>
    fun isAuthenticated(): Boolean
    fun setAuthentication(token: String, secret: String)
    suspend fun verifyCredentials(): Person
    suspend fun getOAuthRequestToken(): RequestToken
    suspend fun getOAuthAccessToken(oauthVerifier: String): AccessToken
    suspend fun updateTextTweet(tweetText: String): Status
    suspend fun updateTextWithImageTweet(status: String, path: String): Status
}

class TwitterRepositoryImp @Inject constructor(
    val twitter: Twitter,
    val userMapper: UserMapper,
    val tweetMapper: TweetMapper
) : TwitterRepository {

    override suspend fun getTimeLine(page: Int): ResponseList<Status> {
        val paging = Paging(page)
        return twitter.getHomeTimeline(paging)
    }

    override fun isAuthenticated(): Boolean {
        return twitter.authorization.isEnabled
    }

    override fun setAuthentication(token: String, secret: String) {
        twitter.oAuthAccessToken = AccessToken(token, secret)
    }

    override suspend fun verifyCredentials(): Person {
        return userMapper.mapToDomainModel(twitter.verifyCredentials())

    }

    override suspend fun getOAuthRequestToken(): RequestToken {
        return twitter.oAuthRequestToken
    }

    override suspend fun getOAuthAccessToken(oauthVerifier: String): AccessToken {
        return twitter.getOAuthAccessToken(oauthVerifier)
    }

    override suspend fun updateTextTweet(tweetText: String): Status {
        return twitter.updateStatus(tweetText)
    }

    override suspend fun updateTextWithImageTweet(status: String, path: String): Status {
        val media = twitter.uploadMedia(File(path))
        val update = StatusUpdate(status)
        update.setMediaIds(media.mediaId)
        return twitter.updateStatus(update)
    }

}