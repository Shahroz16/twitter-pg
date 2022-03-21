package com.shahroz.twitterpg.util

/**
 * Interface for the mapping of DTO models to Domain models
 * and vice versa
 *
 * @param T (DTO model)
 * @param DomainModel (Domain model)
 */

internal interface DomainMapper<T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

    fun toDomainList(initial: List<T>): List<DomainModel> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<DomainModel>): List<T> {
        return initial.map { mapFromDomainModel(it) }
    }
}