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
import java.util.concurrent.Executors


@Database(entities = [EntryEntity::class, DateEntity::class, CategoryEntitiy::class, AccountEntity::class], version = 1)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun getEntryDao(): EntryDao
    abstract fun getDateDao(): DateDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getCategoryDao(): CategoryDao

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
                        .addCallback(object : Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadScheduledExecutor()
                                        .execute({
                                            getDatabase(context).getAccountDao().insert(AccountEntity(null, "default"))
                                        })
                            }
                        })
                        .addMigrations(Migration_1_2())
                        .build()
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