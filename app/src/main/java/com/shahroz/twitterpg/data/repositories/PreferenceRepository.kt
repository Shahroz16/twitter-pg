package com.shahroz.twitterpg.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.shahroz.twitterpg.util.PreferencesKeys.ACCESS_TOKEN
import com.shahroz.twitterpg.util.PreferencesKeys.ACCESS_TOKEN_SECRET
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferenceRepository {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessTokenSecret(token: String)
    fun getAccessTokenSecret(): Flow<String?>
    suspend fun saveTokens(accessToken: String, secret: String)
    fun getAccessTokens(): Flow<Pair<String?, String?>>
}

class PreferenceRepositoryImp(private val dataStore: DataStore<Preferences>) :
    PreferenceRepository {

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            return@map preferences[ACCESS_TOKEN]
        }
    }

    override suspend fun saveAccessTokenSecret(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_SECRET] = token
        }
    }

    override fun getAccessTokenSecret(): Flow<String?> {
        return dataStore.data.map { preferences ->
            return@map preferences[ACCESS_TOKEN_SECRET]
        }
    }

    override suspend fun saveTokens(accessToken: String, secret: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
            preferences[ACCESS_TOKEN_SECRET] = secret
        }
    }

    override fun getAccessTokens(): Flow<Pair<String?, String?>> {
        return dataStore.data.map { preferences ->
            return@map preferences[ACCESS_TOKEN] to preferences[ACCESS_TOKEN_SECRET]
        }
    }


}