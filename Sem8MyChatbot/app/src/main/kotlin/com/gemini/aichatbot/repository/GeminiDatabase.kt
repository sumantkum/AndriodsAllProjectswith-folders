package com.gemini.aichatbot.repository

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gemini.aichatbot.models.ChatMessage
import com.gemini.aichatbot.models.ChatSession
import com.gemini.aichatbot.models.Converters
import kotlinx.coroutines.flow.Flow

// ─────────────────────────────────────────────────────────
// DAO for Chat Messages
// ─────────────────────────────────────────────────────────

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getMessagesForSession(sessionId: String): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Delete
    suspend fun deleteMessage(message: ChatMessage)

    @Query("DELETE FROM messages WHERE sessionId = :sessionId")
    suspend fun deleteAllMessagesForSession(sessionId: String)

    @Update
    suspend fun updateMessage(message: ChatMessage)

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: String): ChatMessage?
}

// ─────────────────────────────────────────────────────────
// DAO for Chat Sessions
// ─────────────────────────────────────────────────────────

@Dao
interface SessionDao {

    @Query("SELECT * FROM chat_sessions ORDER BY lastMessageAt DESC")
    fun getAllSessions(): Flow<List<ChatSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ChatSession)

    @Delete
    suspend fun deleteSession(session: ChatSession)

    @Update
    suspend fun updateSession(session: ChatSession)

    @Query("SELECT * FROM chat_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): ChatSession?

    @Query("DELETE FROM chat_sessions")
    suspend fun deleteAllSessions()
}

// ─────────────────────────────────────────────────────────
// Room Database
// ─────────────────────────────────────────────────────────

@Database(
    entities = [ChatMessage::class, ChatSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GeminiDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: GeminiDatabase? = null

        fun getDatabase(context: Context): GeminiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeminiDatabase::class.java,
                    "gemini_chat_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}