package com.enduringstack.retrofitdemo7.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enduringstack.retrofitdemo7.model.Repository;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public class DbHelper {

    private SQLiteHelper sqLiteHelper;
    private static DbHelper instance;

    /**
     * Use DbHelper to do operations on database
     *
     * @param context Context
     * @return instance of DbHelper
     */
    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }

        return instance;
    }

    private DbHelper(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public long insert(String tableName, ContentValues values) {
        return sqLiteHelper.getWritableDatabase().insert(
                tableName,
                null,
                values);
    }

    public Cursor select(String tableName, String[] columns, String where, String[] whereValues, String orderBy) {
        Cursor cursor = sqLiteHelper.getReadableDatabase().query(tableName, columns, where, whereValues, null, null, orderBy);

        return cursor;
    }

    public int delete(String tableName, String where, String[] whereArgs) {
        return sqLiteHelper.getReadableDatabase().delete(tableName, where != null ? where : "1", whereArgs);
    }

    public void update(String tableName, ContentValues values, String where, String[] whereArgs) {
        sqLiteHelper.getReadableDatabase().update(tableName, values, where, whereArgs);
    }

    private static class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context) {
            super(context, "MaterialHub.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Repository.getCreateTableSql());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //not implemented yet
        }
    }

}
