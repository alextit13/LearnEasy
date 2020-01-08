package com.learn.easy.db

import android.content.ContentValues
import android.content.Context
import com.learn.easy.utils.Card
import com.learn.easy.utils.Diary
import com.learn.easy.utils.Paint
import com.learn.easy.utils.VideoNote

class DBGate(private val context: Context) {

    fun insertPaint(paint: Paint) {
        val cv = ContentValues()
        val dbHelper = DBHelperPaints.newInstance(context)
        val db = dbHelper.writableDatabase

        cv.put("name", paint.name)
        cv.put("imagePath", paint.imagePath)

        db.insert("paints", null, cv)
        dbHelper.close()
    }

    fun getPaints(): MutableList<Paint> {
        val list = mutableListOf<Paint>()
        val dbHelper = DBHelperPaints.newInstance(context)
        val db = dbHelper.writableDatabase

        val c = db.query(
            "paints", null, null, null,
            null, null, null
        )
        if (c.moveToFirst()) {
            val nameColIndex = c.getColumnIndex("name")
            val imagePathColIndex = c.getColumnIndex("imagePath")

            do {
                val name = c.getString(nameColIndex)
                val imagePath = c.getString(imagePathColIndex)

                val paint = Paint(name, imagePath)

                list.add(paint)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    fun deletePaint(name: String): Boolean {
        return DBHelperPaints.newInstance(context).writableDatabase
            .delete("paints", "name=?", arrayOf(name)) > 0
    }

    fun insertDiary(diary: Diary) {
        val cv = ContentValues()
        val dbHelper = DBHelperDiary.newInstance(context)
        val db = dbHelper.writableDatabase

        cv.put("date", diary.date)
        cv.put("title", diary.title)
        cv.put("text", diary.text)

        db.insert("diary", null, cv)
        dbHelper.close()
    }

    fun getAllDiaries(): MutableList<Diary> {
        val list = mutableListOf<Diary>()
        val dbHelper = DBHelperDiary.newInstance(context)
        val db = dbHelper.writableDatabase

        val c = db.query(
            "diary", null, null, null,
            null, null, null
        )
        if (c.moveToFirst()) {
            val idColIndex = c.getColumnIndex("date")
            val dateColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val textColIndex = c.getColumnIndex("text")

            do {
                val id = c.getInt(idColIndex)
                val date = c.getString(dateColIndex)
                val title = c.getString(titleColIndex)
                val text = c.getString(textColIndex)

                val diary = Diary(date, title, text)

                list.add(diary)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    fun getDiary(date: String): Diary? {
        val selectQuery = "SELECT * FROM diary WHERE date = $date"
        val db = DBHelperDiary.newInstance(context).writableDatabase
        val c = db.rawQuery(selectQuery, null)

        if (c.moveToFirst()) {
            val dateColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val textColIndex = c.getColumnIndex("text")

            val dateS = c.getString(dateColIndex)
            val title = c.getString(titleColIndex)
            val text = c.getString(textColIndex)

            c.close()
            return Diary(dateS, title, text)
        }

        return null
    }

    fun updateDiary(diary: Diary): Int {
        val cv = ContentValues()
        cv.put("date", diary.date)
        cv.put("title", diary.title)
        cv.put("text", diary.text)
        val db = DBHelperDiary.newInstance(context).writableDatabase
        return db.update("diary", cv, "date=?", arrayOf(diary.date))
    }

    fun deleteDiary(date: String): Boolean {
        return DBHelperDiary.newInstance(context).writableDatabase
            .delete("diary", "date=?", arrayOf(date)) > 0
    }

    fun getVideoNotes(): MutableList<VideoNote> {
        val list = mutableListOf<VideoNote>()
        val dbHelper = DBHelperVideoNotes.newInstance(context)
        val db = dbHelper.writableDatabase

        val c = db.query(
            "videoNotes", null, null, null,
            null, null, null
        )
        if (c.moveToFirst()) {
            val dateColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val subTitleColIndex = c.getColumnIndex("subtitle")
            val firstCharColIndex = c.getColumnIndex("firstChar")
            val pathVideoColIndex = c.getColumnIndex("pathVideo")

            do {
                val date = c.getString(dateColIndex)
                val title = c.getString(titleColIndex)
                val subtitle = c.getString(subTitleColIndex)
                val firstChar = c.getString(firstCharColIndex)
                val pathVideo = c.getString(pathVideoColIndex)

                val videoNote = VideoNote(
                    date, title, subtitle, firstChar, pathVideo
                )

                list.add(videoNote)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    fun insertVideoNote(note: VideoNote) {
        val cv = ContentValues()
        val dbHelper = DBHelperVideoNotes.newInstance(context)
        val db = dbHelper.writableDatabase

        cv.put("date", note.date)
        cv.put("title", note.title)
        cv.put("subtitle", note.subtitle)
        cv.put("firstChar", note.firstChar)
        cv.put("pathVideo", note.pathVideo)

        val result = db.insert("videoNotes", null, cv)
        dbHelper.close()
    }

    fun deleteVideoNote(note: VideoNote): Boolean {
        return DBHelperVideoNotes.newInstance(context).writableDatabase
            .delete("videoNotes", "date=?", arrayOf(note.date)) > 0
    }

    fun getAllCards(): MutableList<Card> {
        val list = mutableListOf<Card>()
        val dbHelper = DBHelperCards.newInstance(context)
        val db = dbHelper.writableDatabase

        val c = db.query(
            "cards", null, null, null,
            null, null, null
        )
        if (c.moveToFirst()) {
            val idColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val descriptionColIndex = c.getColumnIndex("description")
            val imagePathColIndex = c.getColumnIndex("imagePath")

            val audioPathColIndex = c.getColumnIndex("audioPath")
            val videoPathColIndex = c.getColumnIndex("videoPath")

            do {
                val id = c.getString(idColIndex)
                val title = c.getString(titleColIndex)
                val description = c.getString(descriptionColIndex)
                val imagePath = c.getString(imagePathColIndex)

                val audioPath = c.getString(audioPathColIndex)
                val videoPath = c.getString(videoPathColIndex)

                val card = Card(
                    id, title, description, imagePath, audioPath, videoPath
                )

                list.add(card)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    fun insertCard(card: Card) {
        val cv = ContentValues()
        val dbHelper = DBHelperCards.newInstance(context)
        val db = dbHelper.writableDatabase

        cv.put("date", card.date)
        cv.put("title", card.title)
        cv.put("description", card.description)
        cv.put("imagePath", card.imagePath)

        cv.put("audioPath", card.audioPath)
        cv.put("videoPath", card.videoPath)

        db.insert("cards", null, cv)
        dbHelper.close()
    }

    fun deleteCard(card: Card): Boolean {
        return DBHelperCards.newInstance(context).writableDatabase
            .delete("cards", "date=?", arrayOf(card.date)) > 0
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) = DBGate(context)
    }
}