package com.learn.easy.db

import android.content.ContentValues
import android.content.Context
import com.learn.easy.utils.Paint

class DBGate(private val context: Context) {

    fun insertPaint(paint: Paint) {
        val cv = ContentValues()
        val dbHelper = DBHelper.newInstance(context)
        val db = dbHelper.writableDatabase

        cv.put("name", paint.name)
        cv.put("imagePath", paint.imagePath)

        db.insert("paints", null, cv)
        dbHelper.close()
    }

    fun getPaints(): MutableList<Paint> {
        val list = mutableListOf<Paint>()
        val dbHelper = DBHelper.newInstance(context)
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

    fun deletePaint(name: String) {

    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) = DBGate(context)
    }
}