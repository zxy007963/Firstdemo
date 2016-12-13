package com.example.z.caipu.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.z.caipu.Bean.FoodData;
import com.example.z.caipu.Bean.FoodDataDb;
import com.example.z.caipu.Bean.Steps;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class DBUtil {
    private MySQLite sqLite;
    private SQLiteDatabase database;
    private ArrayList<FoodDataDb> list;

    public DBUtil(Context context) {
        sqLite =new MySQLite(context);
        database =sqLite.getWritableDatabase();
    }

    public void addUrl(int id,String title,String tags,String imtro,String ingredients,String burden,String albums){//像数据库添加数据
        String sql = "insert into food_table(id,title,tags,imtro,ingredients,burden,albums) values('"+id+"','"+title+"','"+tags+"','"+imtro+"','"+ingredients+"','"+burden+"','"+albums+"') ";
        database.execSQL(sql);
    }

    public void deltitle(int id){//从数据库删除数据
        String sql = "delete from food_table where id = '"+id+"'";
        database.execSQL(sql);
    }

    public int seekConut(String imtro){
        String sql="select * from food_table where imtro=?";
        Cursor cursor=database.rawQuery(sql,new String[]{imtro});
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public ArrayList<FoodDataDb> getList(){//遍历数组
        list =new ArrayList<>();
        String sql ="select * from food_table";
        Cursor cursor =database.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id =cursor.getInt(cursor.getColumnIndex("id"));
            String title =cursor.getString(cursor.getColumnIndex("title"));
            String tags =cursor.getString(cursor.getColumnIndex("tags"));
            String imtro =cursor.getString(cursor.getColumnIndex("imtro"));
            String ingredients =cursor.getString(cursor.getColumnIndex("ingredients"));
            String burden =cursor.getString(cursor.getColumnIndex("burden"));
            String albums =cursor.getString(cursor.getColumnIndex("albums"));
            FoodDataDb data =new FoodDataDb(id,title,tags,imtro,ingredients,burden,albums);
            list.add(data);
            cursor.moveToNext();
        }
        return list;

    }
}
