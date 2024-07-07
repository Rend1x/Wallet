package ren.pj.main.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ren.pj.main.data.AppDatabase
import ren.pj.main.data.PurchaseDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "purchase_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePurchaseDao(database: AppDatabase): PurchaseDao {
        return database.purchaseDao()
    }
}