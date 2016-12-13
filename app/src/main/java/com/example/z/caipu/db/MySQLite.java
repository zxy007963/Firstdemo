package com.example.z.caipu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class MySQLite extends SQLiteOpenHelper {
    public static final String DB_NAME ="food_db";
    public static final String TABLE_NAME ="food_table";

    public MySQLite(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                +TABLE_NAME+
                " (id int ," +
                "title varchar(255) ," +
                "tags varchar(255) ," +
                "imtro varchar(255) ," +
                "ingredients varchar(255) ," +
                "burden varchar(255) ," +
                "albums varchar(255) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
