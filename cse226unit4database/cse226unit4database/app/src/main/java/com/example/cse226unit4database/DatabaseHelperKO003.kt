package com.example.cse226unit4database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperKO003(context:Context) : SQLiteOpenHelper(context,DATABASE_NAME, null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "UserDB.db"
        private  const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME("+
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOICREMENT," +
                "$COLUMN_NAME TEXT,"+
                "$COLUMN_AGE INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertUser(name : String, age : Int):Boolean{
        val  db = this.writableDatabase
        val  values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        val result = db.insert(TABLE_NAME,null,values)
        db.close()
        return result != -1L
    }
    fun  getAllUsers() : Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME",null)
    }
    fun  updateUser(name: String,newAge : Int) : Boolean{
        val db= this.writableDatabase
        val  values= ContentValues()
        values.put(COLUMN_AGE,newAge)
        val  result = db.update(TABLE_NAME,values,"$COLUMN_NAME=?", arrayOf(name))
        db.close()
        return  result > 0
    }
    fun deleteUser(name: String):Boolean{
        val  db = this.writableDatabase
        val result = db.delete(TABLE_NAME,"$COLUMN_NAME=?", arrayOf(name))
        db.close()
        return result > 0
    }
}