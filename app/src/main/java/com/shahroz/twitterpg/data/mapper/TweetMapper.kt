package com.shahroz.twitterpg.data.mapper

import com.shahroz.twitterpg.data.model.Tweet
import com.shahroz.twitterpg.util.DomainMapper
import twitter4j.Status

class TweetMapper : DomainMapper<Status?, Tweet> {
    override fun mapToDomainModel(model: Status?): Tweet {
        return model?.let {
            Tweet(
                id = model.id.toInt(),
                text = model.text,
                createdAt = model.createdAt,
                author = model.user.name,
                isAuthorVerified = model.user.isVerified,
                authorImageId = model.user.profileImageURL,
                commentsCount = model.contributors.size,
                handle = model.user.screenName,
                likesCount = model.favoriteCount,
                retweetCount = model.retweetCount,
                source = model.source,
                tweetImages = model.mediaEntities.map { it.mediaURL }
            )
        } ?: Tweet()

    }

    override fun mapFromDomainModel(domainModel: Tweet): Status? {
        return null
    }
}