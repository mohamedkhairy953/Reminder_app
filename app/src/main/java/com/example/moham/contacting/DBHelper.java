package com.example.moham.contacting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moham on 1/26/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "contacting";
    private static int DB_VERSION = 6;
    public static String TABLE = "contacters";
    public static String name = "name";
    public static String phone = "phone";
    public static String id = "_id";
    public static String facebook_username = "account";
    public static String image = "image";
    public static String remind_me = "remnder";
    public static String is_selected = "selection";
    public static String request_code = "Rcode";
    public static String Will_notify = "notfy_on_off";


    public static String CREATE_TABLE = "create table "
            + TABLE + "(" + id + " integer primary key autoincrement, " + name + " varchar," + request_code + " int," + phone + " varchar," + remind_me + " date not null," +
            image + " BLOB, " + is_selected + " boolean," + Will_notify + " boolean," + facebook_username + " varchar );";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE + "");
        onCreate(db);
    }
}
