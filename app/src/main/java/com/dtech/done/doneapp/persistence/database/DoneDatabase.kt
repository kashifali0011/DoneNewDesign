package com.dtech.done.doneapp.persistence.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dtech.done.doneapp.persistence.dao.ContactDao
import com.dtech.done.doneapp.persistence.typeconvertor.TypeConverter
/**
 * Develop By Messagemuse
 */
//@Database(entities = [(ContactSyncResponseModel.Data::class)], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class DoneDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: DoneDatabase? = null

        fun getDatabase(context: Context): DoneDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, DoneDatabase::class.java, "easypeasy.db").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}