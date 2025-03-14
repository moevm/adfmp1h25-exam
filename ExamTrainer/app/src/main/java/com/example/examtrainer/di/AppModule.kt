package com.example.examtrainer.di

import android.content.Context
import android.content.SharedPreferences
import com.example.examtrainer.data.local.ExamRepository
import com.example.examtrainer.data.local.TheoryRepository
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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideExamRepository(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): ExamRepository {
        return ExamRepository(sharedPreferences, context)
    }

    @Provides
    @Singleton
    fun provideTheoryRepository(): TheoryRepository {
        return TheoryRepository()
    }
}