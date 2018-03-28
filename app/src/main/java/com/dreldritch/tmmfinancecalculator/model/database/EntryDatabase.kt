package com.dreldritch.tmmfinancecalculator.model.database

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.support.v4.content.ContextCompat
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryEntity
import java.util.concurrent.Executors
import android.arch.lifecycle.LiveData
import com.dreldritch.tmmfinancecalculator.model.dao.*


@Database(entities = [EntryEntity::class, DateEntity::class, CategoryEntity::class, AccountEntity::class], version = 1)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun getEntryDao(): EntryDao
    abstract fun getDateDao(): DateDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getFullTransactionDataDao(): FullTransactionDao

    private var mIsDatabaseCreated = MutableLiveData<Boolean>()

    companion object {
        private const val DB_NAME = "entry_db.db"
        private var INSTANCE: EntryDatabase? = null

        fun getDatabase(context: Context): EntryDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        EntryDatabase::class.java, DB_NAME)
                        .addCallback(PrePopulateCallback(context))
                        .addMigrations(Migration2())
                        .build()


    }

    /*fun destroyInstance() {
        INSTANCE = null
    }*/

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    fun getDatabaseCreated(): LiveData<Boolean> {
        return mIsDatabaseCreated
    }

    class Migration2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //Add migration code to ver 2
        }
    }

    class PrePopulateCallback(val context: Context) : Callback() {
        //Prepopulate database
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadExecutor()
                    .execute({
                        getDatabase(context).getAccountDao().insert(AccountEntity(null, "default"))
                        getDatabase(context).getCategoryDao().insert(
                                CategoryEntity(null, context.getString(R.string.category1), ContextCompat.getColor(context, R.color.blue)),
                                CategoryEntity(null, context.getString(R.string.category2), ContextCompat.getColor(context, R.color.green)),
                                CategoryEntity(null, context.getString(R.string.category3), ContextCompat.getColor(context, R.color.red)),
                                CategoryEntity(null, context.getString(R.string.category4), ContextCompat.getColor(context, R.color.orange)),
                                CategoryEntity(null, context.getString(R.string.category5), ContextCompat.getColor(context, R.color.purple)))
                    })
            getDatabase(context).setDatabaseCreated()
        }
    }
}