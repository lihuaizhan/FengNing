package com.example.tps900.tps900.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.godfery.Sqlite.DBUtils;


/**
 * Created by zxh on 2016/10/16.
 */

public class PdaSqliteOpenHelper extends SQLiteOpenHelper {

    //用来指定数据库的名称和数据库的版本号
    public PdaSqliteOpenHelper(Context context) {

        super(context, "galasys.db", null, 1);
    }

    //第一次创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        DBUtils.execSQL("create table info (_id integer primary key," +
                "_id     INTEGER     not null," +
                "name varchar(30)," +
                "value varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
