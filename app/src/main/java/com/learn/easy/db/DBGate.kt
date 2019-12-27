package com.learn.easy.db

import android.content.ContentValues
import android.content.Context
import com.learn.easy.utils.Diary
import com.learn.easy.utils.Paint

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

        cv.put("id", diary.id)
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
            val idColIndex = c.getColumnIndex("id")
            val dateColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val textColIndex = c.getColumnIndex("text")

            do {
                val id = c.getInt(idColIndex)
                val date = c.getString(dateColIndex)
                val title = c.getString(titleColIndex)
                val text = c.getString(textColIndex)

                val diary = Diary(id, date, title, text)

                list.add(diary)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    fun getDiary(id: Int): Diary? {
        val selectQuery = "SELECT * FROM diary WHERE id = $id"
        val db = DBHelperDiary.newInstance(context).writableDatabase
        val c = db.rawQuery(selectQuery, null)

        if (c.moveToFirst()) {
            val dateColIndex = c.getColumnIndex("date")
            val titleColIndex = c.getColumnIndex("title")
            val textColIndex = c.getColumnIndex("text")

            val date = c.getString(dateColIndex)
            val title = c.getString(titleColIndex)
            val text = c.getString(textColIndex)

            c.close()
            return Diary(id, date, title, text)
        }

        return null
    }

    fun updateDiary(diary: Diary) {
        val cv = ContentValues()
        cv.put("id", diary.id)
        cv.put("title", diary.title)
        cv.put("text", diary.text)
        cv.put("date", diary.date)
        val db = DBHelperDiary.newInstance(context).writableDatabase
        db.update("diary", cv, "id = ${diary.id}", null)
    }

    fun deleteDiary(id: Int): Boolean {
        return DBHelperDiary.newInstance(context).writableDatabase
            .delete("diary", "id=?", arrayOf(id.toString())) > 0
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) = DBGate(context)
    }
}