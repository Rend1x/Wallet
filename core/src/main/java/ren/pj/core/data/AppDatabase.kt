package ren.pj.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PurchaseEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
}