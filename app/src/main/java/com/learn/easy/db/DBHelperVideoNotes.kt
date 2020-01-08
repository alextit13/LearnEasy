package com.learn.easy.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperVideoNotes(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table videoNotes ("
                    + "date text,"
                    + "title text,"
                    + "subtitle text,"
                    + "firstChar text,"
                    + "pathVideo text" + ");"
        );
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    companion object {
        const val DB_NAME = "db_app_learn_easy_video_notes"
        @JvmStatic
        fun newInstance(context: Context) = DBHelperVideoNotes(context)
    }
}