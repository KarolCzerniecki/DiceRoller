package pl.pieczara.diceroller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DiceRollerDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_SCORES = "scores"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_SCORES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_SCORE TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }

    fun addScore(results: List<Int>) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SCORE, results.joinToString(", "))
        db.insert(TABLE_SCORES, null, values)
        db.close()
    }

    fun getAllScores(): List<String> {
        val scores = ArrayList<String>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_SCORES"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val score = cursor.getString(cursor.getColumnIndex(COLUMN_SCORE))
                scores.add(score)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return scores
    }

    fun clearScores() {
        val db = this.writableDatabase
        db.delete(TABLE_SCORES, null, null)
        db.close()
    }
}