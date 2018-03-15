package com.dreldritch.tmmfinancecalculator.model.database
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.dreldritch.tmmfinancecalculator.model.dao.AccountDao
import com.dreldritch.tmmfinancecalculator.model.dao.CategoryDao
import com.dreldritch.tmmfinancecalculator.model.dao.DateDao
import com.dreldritch.tmmfinancecalculator.model.dao.EntryDao
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity

@Database(entities = arrayOf(EntryEntity::class, DateEntity::class, CategoryEntitiy::class, AccountEntity::class), version = 1)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun getEntryDao(): EntryDao
    abstract fun getDateDao(): DateDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "entry_db.db"
        private var INSTANCE: EntryDatabase? = null

        fun getDatabase(context: Context): EntryDatabase {
            if (INSTANCE == null) {
                synchronized(EntryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            EntryDatabase::class.java, DB_NAME)
                            .addMigrations(Migration_1_2())
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }

    class Migration_1_2: Migration(1, 2){
        override fun migrate(database: SupportSQLiteDatabase) {
            //TODO Add migration code to ver 2
        }
    }
}