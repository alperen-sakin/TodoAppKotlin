package com.example.todoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.todoapp.util.DefaultStringResourceProvider
import com.example.todoapp.util.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_details")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStringResourceProvider(
        provider: DefaultStringResourceProvider
    ): StringResourceProvider {
        return provider
    }

    @Provides
    @Singleton
    fun providePreferencesDataSource(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
