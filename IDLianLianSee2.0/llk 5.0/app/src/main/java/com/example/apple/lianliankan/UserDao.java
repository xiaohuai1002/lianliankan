package com.example.apple.lianliankan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by apple on 17/5/26.
 */

public class UserDao {

    private UserSqliteOpenHelper helper;

    public UserDao(Context context) {
        helper = new UserSqliteOpenHelper(context);
    }

    public void init(){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", 1);
        values.put("add", 0);
        db.insert("Book", null, values);
        values.clear();





    }


    public void update(String id, int add){
        SQLiteDatabase db = helper.getWritableDatabase();
        db. execSQL("update user set add = ? where id = ?", new Object[]{add, id});
        db.close();
    }


}
