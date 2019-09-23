package com.example.roomdatabindinglivedatakotlinmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabindinglivedatakotlinmvvm.db.dao.BorrowModelDao
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel


@Database(entities = [BorrowModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemAndPersonelModel(): BorrowModelDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java, "borrow_db").build()
                }

            }
            return INSTANCE
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}
