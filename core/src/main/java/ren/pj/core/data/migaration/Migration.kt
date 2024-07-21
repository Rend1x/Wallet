package ren.pj.core.data.migaration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS purchases_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                purchaseName TEXT NOT NULL,
                category TEXT NOT NULL,
                amount REAL NOT NULL,
                currencyCode TEXT NOT NULL,
                createdDate INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO purchases_new (id, purchaseName, category, amount, currencyCode, createdDate)
            SELECT id, purchaseName, category, amount, currencyCode, ${System.currentTimeMillis()} FROM purchases
        """.trimIndent()
        )

        db.execSQL("DROP TABLE purchases")

        db.execSQL("ALTER TABLE purchases_new RENAME TO purchases")
    }
}
