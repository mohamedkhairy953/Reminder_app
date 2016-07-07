package com.example.moham.contacting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by moham on 1/26/2016.
 */
public class DBController {
    DBHelper helper;
    Context c2;
    SQLiteDatabase database;

    public DBController(Context c) {
        c2 = c;
        helper = new DBHelper(c);
    }

    public DBController open() {
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public int insert(ContacterModel model) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.name, model.getName());
        values.put(DBHelper.facebook_username, model.getFacebook_usernae());
        values.put(DBHelper.image, model.getImage());
        values.put(DBHelper.is_selected, model.is_selected());
        values.put(DBHelper.phone, model.getPhone());
        values.put(DBHelper.remind_me, model.getRemindMe());
        long insert = database.insert(DBHelper.TABLE, null, values);
        close();
        return (int) insert;
    }

    public int delete(int id) {
        open();
        int delete = database.delete(DBHelper.TABLE, DBHelper.id + " = " + id, null);
        close();
        return delete;
    }

    public int delete_all() {
        open();
        int delete = database.delete(DBHelper.TABLE, null, null);
        close();
        return delete;
    }

    public ArrayList<ContacterModel> get_all_people() {
        open();
        ArrayList<ContacterModel> models = new ArrayList<>();
        Cursor query = database.query(DBHelper.TABLE, null, null, null, null, null, null);
        if (query.moveToFirst()) {
            do {
                ContacterModel model = new ContacterModel();
                model.setIs_selected(query.getInt(query.getColumnIndex(DBHelper.is_selected)) > 0);
                model.setFacebook_usernae(query.getString(query.getColumnIndex(DBHelper.facebook_username)));
                model.setImage(query.getBlob(query.getColumnIndex(DBHelper.image)));
                try {
                    model.setName(query.getString(query.getColumnIndex(DBHelper.name)));
                    model.setPhone(query.getString(query.getColumnIndex(DBHelper.phone)));
                    model.setRemindMe(query.getString(query.getColumnIndex(DBHelper.remind_me)));
                    model.setId(query.getInt(query.getColumnIndex(DBHelper.id)));
                    model.setWill_notify(query.getInt(query.getColumnIndex(DBHelper.Will_notify)) > 0);
                } catch (Exception e) {

                }
                models.add(model);
            } while (query.moveToNext());

        }
        close();
        return models;
    }

    public ContacterModel get_someone(int id) {
        open();
        ContacterModel model = new ContacterModel();
        Cursor query = database.query(DBHelper.TABLE, null, DBHelper.id + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (query.moveToFirst()) {

            model.setIs_selected(query.getInt(query.getColumnIndex(DBHelper.is_selected)) > 0);
            model.setFacebook_usernae(query.getString(query.getColumnIndex(DBHelper.facebook_username)));
            model.setImage(query.getBlob(query.getColumnIndex(DBHelper.image)));
            try {
                model.setName(query.getString(query.getColumnIndex(DBHelper.name)));
                model.setPhone(query.getString(query.getColumnIndex(DBHelper.phone)));
                model.setRemindMe(query.getString(query.getColumnIndex(DBHelper.remind_me)));
                model.setId(query.getInt(query.getColumnIndex(DBHelper.id)));
            } catch (Exception e) {

            }
        }
        close();
        return model;
    }

    public void update_willNotifyBoolean(ContacterModel model, int id) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.name, model.getName());
        values.put(DBHelper.facebook_username, model.getFacebook_usernae());
        values.put(DBHelper.image, model.getImage());
        values.put(DBHelper.is_selected, model.is_selected());
        values.put(DBHelper.phone, model.getPhone());
        values.put(DBHelper.remind_me, model.getRemindMe());
        values.put(DBHelper.Will_notify, model.isWill_notify());
        database.update(DBHelper.TABLE, values, DBHelper.id + " = ?", new String[]{String.valueOf(id)});
        close();
    }


}
