package com.example.todoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.todoapp.data.local.TodoDatabase
import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.repository.TaskRepositoryImpl
import com.example.todoapp.domain.repository.TaskRepository
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

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTaskDao(database: TodoDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(impl: TaskRepositoryImpl): TaskRepository {
        return impl
    }
}
