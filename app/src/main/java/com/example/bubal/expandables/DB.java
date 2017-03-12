package com.example.bubal.expandables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bubal on 05.03.2017.
 */

public class DB{

    private static final String DB_NAME = "relatives";
    private static final int DB_VERSION = 1;

    // имя таблицы групп контактов
    private static final String GROUPS_TABLE = "groups";
    public static final String GROUPS_COLUMN_ID = "_id";
    public static final String GROUPS_COLUMN_NAME = "name";
    private static final String GROUPS_TABLE_CREATE = "create table "
            + GROUPS_TABLE + "(" + GROUPS_COLUMN_ID
            + " integer primary key, " + GROUPS_COLUMN_NAME + " text" + ");";

    // имя таблицы контактов, поля и запрос создания
    private static final String CONTACTS_TABLE = "contacts";
    public static final String CONTACTS_COLUMN_ID = "_id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_NUMBER = "name";
    public static final String CONTACTS_COLUMN_GROUP = "group";
    private static final String CONTACTS_TABLE_CREATE = "create table "
            + CONTACTS_TABLE + "(" + CONTACTS_COLUMN_ID
            + " integer primary key autoincrement, " + CONTACTS_COLUMN_NAME
            + " text, " + CONTACTS_COLUMN_NUMBER + " integer, " + CONTACTS_COLUMN_GROUP + " integer, " +
            "FOREIGN KEY(" + CONTACTS_COLUMN_GROUP + ") REFERENCES groups(" + GROUPS_COLUMN_ID + "));";

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private Context context;

    public DB(Context context){
        this.context = context;
    }

    // открываем подключение
    public void open() {
        mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрываем подключение
    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public Cursor getGroupsData(){
        return mDB.query(GROUPS_TABLE, null, null, null, null, null, null);
    }

    public Cursor getContactData(long groupId){
        return mDB.query(CONTACTS_TABLE, null, CONTACTS_COLUMN_GROUP + " = " + groupId, null, null, null, null);
    }

    public void addContact(int id, String name, String number, String group){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_ID, id);
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_NUMBER, number);
        contentValues.put(CONTACTS_COLUMN_GROUP, group);
        mDB.insert(CONTACTS_TABLE, null, contentValues);
    }

    public Cursor getDataById(String id){
        Cursor cursor = mDB.query(CONTACTS_TABLE, new String[]{CONTACTS_COLUMN_ID, CONTACTS_COLUMN_NAME, CONTACTS_COLUMN_NUMBER, CONTACTS_COLUMN_GROUP},
                CONTACTS_COLUMN_ID + " = ?", new String[]{id}, null, null, null);
        return cursor;
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(GROUPS_TABLE_CREATE);
            db.execSQL(CONTACTS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
