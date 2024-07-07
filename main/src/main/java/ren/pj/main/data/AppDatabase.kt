package ren.pj.main.data

import androidx.room.Database
import androidx.room.RoomDatabase

// todo вынести в core
@Database(entities = [PurchaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
}