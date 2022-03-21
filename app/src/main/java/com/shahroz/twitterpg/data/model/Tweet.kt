package com.shahroz.twitterpg.data.model

import java.util.*

data class Tweet(
    val id: Int = -1,
    val text: String = "",
    val author: String = "",
    val isAuthorVerified: Boolean =  false,
    val handle: String = "",
    val createdAt: Date = Date(),
    val authorImageId: String = "",
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val retweetCount: Int = 0,
    val source: String = "",
    val tweetImages: List<String> = emptyList()
)