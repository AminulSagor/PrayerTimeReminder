package com.somadhan.prayerrmindere.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.somadhan.prayerrmindere.data.database.PrayerDatabase
import com.somadhan.prayerrmindere.data.repository.PrayerRepository
import com.somadhan.prayerrmindere.util.PrayTimeCalculator
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
    fun providePrayerDatabase(@ApplicationContext context: Context): PrayerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PrayerDatabase::class.java,
            "prayer_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePrayerRepository(db: PrayerDatabase): PrayerRepository {
        // Ensure you're passing the DAOs in the correct order
        return PrayerRepository(db.userSettingsDao(), db.prayerDao())
    }

    @Provides
    @Singleton
    fun providePrayTimeCalculator(): PrayTimeCalculator {
        return PrayTimeCalculator()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}
