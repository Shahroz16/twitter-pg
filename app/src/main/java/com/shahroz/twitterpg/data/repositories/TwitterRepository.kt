package com.shahroz.twitterpg.data.repositories

import twitter4j.*
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import java.io.File


interface TwitterRepository {
    suspend fun getTimeLine(page: Int): ResponseList<Status>
    fun isAuthenticated(): Boolean
    fun setAuthentication(token: String, secret: String)
    suspend fun verifyCredentials(): User?
    suspend fun getOAuthRequestToken(): RequestToken
    suspend fun getOAuthAccessToken(oauthVerifier: String): AccessToken
    suspend fun updateTextTweet(tweetText: String): Status
    suspend fun updateTextWithImageTweet(status: String, path: String): Status
}

class TwitterRepositoryImp(val twitter: Twitter) :
    TwitterRepository {

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

    override suspend fun verifyCredentials(): User? {
        return twitter.verifyCredentials()
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