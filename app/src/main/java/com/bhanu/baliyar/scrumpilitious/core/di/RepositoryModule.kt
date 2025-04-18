package com.bhanu.baliyar.scrumpilitious.core.di

import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepository
import com.bhanu.baliyar.scrumpilitious.data.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepositoryModule(recipeRepository: RecipeRepositoryImpl): RecipeRepository
}