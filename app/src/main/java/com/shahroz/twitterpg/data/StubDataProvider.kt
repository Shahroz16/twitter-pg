package com.shahroz.twitterpg.data

import com.shahroz.twitterpg.data.model.Tweet

object StubDataProvider {
    val tweet = Tweet(
        1,
        "Jetpack compose is the next thing for andorid. Declarative UI is the way to go for all screens.",
        "The Verge",
        "@verge",
        "12m",
        android.R.drawable.star_big_on,
        100,
        12,
        15,
        "Twitter for web"
    )

    val tweetList = listOf(
        tweet,
        tweet.copy(
            id = 2,
            author = "Google",
            handle = "@google",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 3,
            author = "Amazon",
            handle = "@Amazon",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 4,
            author = "Facebook",
            handle = "@Facebook",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 7,
            author = "Netflix",
            handle = "@Netflix",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 6,
            author = "Tiktok",
            handle = "@Tiktok",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 7,
            author = "Samsung",
            handle = "@Samsung",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 8,
            author = "Youtube",
            handle = "@Youtube",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 9,
            author = "Gmail",
            handle = "@Gmail",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 3,
            author = "Android",
            handle = "@Android",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 4,
            author = "Whatsapp",
            handle = "@Whatsapp",
            authorImageId = android.R.drawable.star_big_on,
            time = "1h"
        ),
        tweet.copy(
            id = 5,
            author = "Telegram",
            handle = "@Telegram",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 6,
            author = "Spotify",
            handle = "@Spotify",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
        tweet.copy(
            id = 7,
            author = "WeChat",
            handle = "@WeChat",
            authorImageId = android.R.drawable.star_big_on,
            tweetImageId = android.R.drawable.star_big_on,
            time = "11m"
        ),
    )

}