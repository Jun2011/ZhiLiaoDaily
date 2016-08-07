package com.example.jun.zhiliaodemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jun.zhiliaodemo.base.BaseApplication;

/**
 * Created by Jun on 2016/7/5.
 * 使用了单例模式
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // 数据库的名称
    public static final String DB_NAME = "ZhiLiaoStore.db";
    // 数据库的版本
    public static final int DB_VERSION = 3;
    // 表的名称
    public static final String TABLE_NAME = "Story";

    // 建表语句
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            "id integer primary key autoincrement," +
            "storyId String)";

    private static DataBaseHelper mDataBaseHelper;

    private DataBaseHelper(Context context,
                           String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {

        super(context, name, factory, version);
    }

    public static DataBaseHelper getInstance() {

        if (mDataBaseHelper == null) {
            synchronized (DataBaseHelper.class) {
                mDataBaseHelper = new DataBaseHelper(
                        BaseApplication.getContext(),
                        DB_NAME,
                        null,
                        DB_VERSION);
            }
        }

        return mDataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("drop table if exists Srtory");
                db.execSQL(CREATE_TABLE);
            case 2:
                db.execSQL("drop table if exists Story");
                db.execSQL(CREATE_TABLE);
        }
    }
}
