package ren.pj.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ren.pj.core.data.AppDatabase
import ren.pj.core.data.PurchaseDao
import ren.pj.core.data.migaration.MIGRATION_1_2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "purchase_database")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun providePurchaseDao(database: AppDatabase): PurchaseDao {
        return database.purchaseDao()
    }
}