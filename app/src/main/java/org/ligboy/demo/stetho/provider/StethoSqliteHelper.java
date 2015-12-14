package org.ligboy.demo.stetho.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class StethoSqliteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String  DB_NAME = "stetho.db";

    public StethoSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `users`(" +
                "`_id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`name` TEXT, " +
                "`username` TEXT, " +
                "`password` TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
