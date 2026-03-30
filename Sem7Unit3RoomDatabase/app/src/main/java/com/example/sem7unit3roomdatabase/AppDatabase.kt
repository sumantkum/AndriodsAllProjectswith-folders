package com.example.sem7unit3roomdatabase

import android.content.Context

@Database(entities = [Note::class], version=2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notes ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance  =Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    note_db
                )
            }
        }

    }

}