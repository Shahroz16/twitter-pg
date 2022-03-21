package com.shahroz.twitterpg.util

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val ACCESS_TOKEN_SECRET = stringPreferencesKey("access_token_secret")
}