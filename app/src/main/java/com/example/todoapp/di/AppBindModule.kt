package com.example.todoapp.di

import com.example.todoapp.data.repository.UserDetailsRepositoryImpl
import com.example.todoapp.domain.repository.UserDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    @Singleton
    abstract fun bindUserDetailsRepository(
        userDetailsRepositoryImpl: UserDetailsRepositoryImpl
    ): UserDetailsRepository
}
