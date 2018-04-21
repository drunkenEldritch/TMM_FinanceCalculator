package com.dreldritch.tmmfinancecalculator

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.dreldritch.tmmfinancecalculator.model.dao.DateDao
import com.dreldritch.tmmfinancecalculator.model.database.TransactionDatabase
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import org.junit.After
import org.junit.Assert.assertSame
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AndroidDateDbTest {
    private var mDateDao: DateDao? = null
    private var mDb: TransactionDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, TransactionDatabase::class.java!!).build()
        mDateDao = mDb!!.getDateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {

        val date = DateEntity(null, "2018-12-22")
        mDateDao!!.insert(date)
        val dates = mDateDao!!.getDateId(date.date)
        assertSame(1L, dates)
    }
}