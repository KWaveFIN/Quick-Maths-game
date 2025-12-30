package com.example.quickmaths.di

import android.content.Context
import com.example.quickmaths.data.HighScoreRepository
import com.example.quickmaths.data.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }

    @Provides
    @Singleton
    fun provideHighScoreRepository(@ApplicationContext context: Context): HighScoreRepository {
        return HighScoreRepository(context)
    }
}