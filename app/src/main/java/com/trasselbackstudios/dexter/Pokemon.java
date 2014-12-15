package com.trasselbackstudios.dexter;

import android.content.Context;
import android.database.Cursor;

import com.trasselbackstudios.dexter.data.PokemonContract;
import com.trasselbackstudios.dexter.data.PokemonDatabase;

public class Pokemon {
    public final String id;
    public final String name;
    public final String height;
    public final String weight;
    public final String desc;
    public final String species;
    public final String types;

    public Pokemon(Context context, String id) {
        this.id = id;
        PokemonDatabase db = new PokemonDatabase(context);
        Cursor cursor = db.getDetailsItems(id);
        name = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_NAME));
        height = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_HEIGHT));
        weight = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_WEIGHT));
        desc = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_DESC));
        species = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_SPECIES));
        types = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_TYPE));
        cursor.close();
        db.close();
    }
}
