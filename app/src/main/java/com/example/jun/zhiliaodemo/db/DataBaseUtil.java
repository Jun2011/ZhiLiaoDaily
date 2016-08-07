package com.example.jun.zhiliaodemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun on 2016/7/5.
 */
public class DataBaseUtil {

    public static final String TABLE_NAME = "Story";

    // 插入id数据
    public static void insertId(String storyId) {

        SQLiteDatabase database = DataBaseHelper
                .getInstance()
                .getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("storyId", storyId);

        database.insert(TABLE_NAME, null, values);
    }

    // 查询所有的id数据
    public static List<String> queryId() {

       List<String> list = new ArrayList<>();

        SQLiteDatabase database = DataBaseHelper
                .getInstance()
                .getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex("storyId")));
            } while (cursor.moveToNext());
        }

        return list;
    }

    // 删除表中所有数据
    public static void deleteAll() {

        SQLiteDatabase database = DataBaseHelper
                .getInstance()
                .getWritableDatabase();

        database.delete(TABLE_NAME, null, null);
    }
}
