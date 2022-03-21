package com.shahroz.twitterpg.data.mapper

import com.shahroz.twitterpg.data.model.Person
import com.shahroz.twitterpg.util.DomainMapper
import twitter4j.User

class UserMapper : DomainMapper<User?, Person> {

    override fun mapToDomainModel(model: User?): Person {
        return model?.let {
            Person(
                id = model.id,
                username = model.screenName,
                displayName = model.name,
                image = model.profileImageURL,
                isVerified = model.isVerified
            )
        } ?: Person()

    }

    override fun mapFromDomainModel(domainModel: Person): User? {
        return null
    }

}
