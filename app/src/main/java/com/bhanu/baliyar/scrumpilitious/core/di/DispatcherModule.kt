package com.bhanu.baliyar.scrumpilitious.core.di

import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProvider
import com.bhanu.baliyar.scrumpilitious.core.dispatchers.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    abstract fun provideDispatcherModule(dispatcherProvider : DispatcherProviderImpl) : DispatcherProvider
}