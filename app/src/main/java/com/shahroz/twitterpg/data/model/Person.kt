package com.shahroz.twitterpg.data.model

data class Person(
    val id: Long = -1L,
    val username: String = "",
    val displayName: String = "",
    val image: String = "",
    val isVerified: Boolean = false
) {
    fun isValid() = id != -1L
}