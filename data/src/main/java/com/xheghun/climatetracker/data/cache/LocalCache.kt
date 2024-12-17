package com.xheghun.climatetracker.data.cache

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import java.io.IOException

class LocalCache<T>(
    private val context: Context,
    private val gson: Gson,
    private val clazz: Class<T>
) : Cache<T> {
    private val Context.dataStore by preferencesDataStore(name = "local_cache")

    override suspend fun get(key: String): T? {
        try {
            val preference = context.dataStore.data.first()
            val value = stringPreferencesKey(key)

            val cacheValue = preference[value]

            if (cacheValue != null) {
                Log.d("", "Retrieving ${gson.fromJson(cacheValue, clazz)}")
                return gson.fromJson(cacheValue, clazz)
            }
            return null
        } catch (e: IOException) {
            return null
        }
    }

    override suspend fun put(key: String, value: T) {
        runCatching {
            context.dataStore.edit { preference ->
                val valueKey = stringPreferencesKey(key)

                Log.d("saving", "Saving Key: $key, value: $value")

                preference[valueKey] = gson.toJson(value)
            }
        }
    }

    override suspend fun remove(key: String) {
        runCatching {
            context.dataStore.edit { preference ->
                val valueKey = stringPreferencesKey(key)

                preference.remove(valueKey)
            }
        }
    }
}