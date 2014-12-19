package com.trasselbackstudios.dexter.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class PokemonDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "pokemon.db";
    private static final int DATABASE_VERSION = 2;

    public PokemonDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getPokemonItems() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PokemonContract.PokemonEntry.TABLE_NAME);
        Cursor c = qb.query(db, null, null, null, null, null, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor getPokemonId(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PokemonContract.PokemonEntry.TABLE_NAME);
        String[] names = {name};
        Cursor c = qb.query(db, null, "NAME = ?", names, null, null, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public Cursor getDetailsItems(String id) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PokemonContract.DetailsEntry.TABLE_NAME);
        String[] ids = {id};
        Cursor c = qb.query(db, null, "_id = ?", ids, null, null, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public static void forceDatabaseReload(Context context){
        PokemonDatabase dbHelper = new PokemonDatabase(context);
        dbHelper.setForcedUpgradeVersion(DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.setVersion(-1);
        db.close();
    }
}
