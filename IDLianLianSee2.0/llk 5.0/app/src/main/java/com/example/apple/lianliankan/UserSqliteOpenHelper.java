package com.example.apple.lianliankan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by apple on 17/5/26.
 */

public class UserSqliteOpenHelper extends SQLiteOpenHelper {



    private static final int versionNo = 1;

    public UserSqliteOpenHelper(Context context) {
        super(context, "user.db", null, versionNo);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log. i("" , "sqlite onCreate" );
        db.execSQL( "create table user (id Integer primary key autoincrement, name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log. i("" , "sqlite onUpgrade" );
        db.execSQL( "insert into user(name) values(?)", new Object[]{"version"+versionNo });

    }
}
