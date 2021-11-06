package com.jcs.canbuy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jcs.canbuy.data.database.dao.ProductDao
import com.jcs.canbuy.data.database.entities.ProductEntity
import com.jcs.canbuy.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Jardson Costa on 03/11/2021.
 */

@Database(entities = [ProductEntity::class], version = 1)
abstract class CanBuyDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{
        private var INSTANCE: CanBuyDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CanBuyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context,
                        CanBuyDatabase::class.java,
                        Constants.DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(CanBuyDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class CanBuyDatabaseCallback(private val scope: CoroutineScope) : Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let{
                scope.launch { Dispatchers.IO }
            }
        }
    }
}