package com.trasselbackstudios.dexter;

import android.content.Context;
import android.database.Cursor;

import com.trasselbackstudios.dexter.data.PokemonContract;
import com.trasselbackstudios.dexter.data.PokemonDatabase;

import java.util.ArrayList;

public class PokemonEntry {
    public final String id;
    public final String name;
    public final String height;
    public final String weight;
    public final String desc;
    public final String species;
    public final String types;
    public final String evo;

    public PokemonEntry(Context context, String id) {
        this.id = id;
        PokemonDatabase db = new PokemonDatabase(context);
        Cursor cursor = db.getDetailsItems(id);
        name = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_NAME));
        height = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_HEIGHT));
        weight = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_WEIGHT));
        desc = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_DESC));
        species = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_SPECIES));
        types = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_TYPE));
        evo = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_EVO));
        cursor.close();
        db.close();
    }

    public ArrayList<PokemonEntry> getEvolutions(Context context) {
        if (evo.equals("")) return null;
        ArrayList<PokemonEntry> pokemonEntries = new ArrayList<>();
        String[] evolutions = evo.split(",");
        PokemonDatabase db = new PokemonDatabase(context);
        for(String evolution : evolutions){
            Cursor cursor = db.getPokemonId(evolution);
            String id = cursor.getString(0);
            pokemonEntries.add(new PokemonEntry(context, id));
            cursor.close();
        }
        db.close();
        return pokemonEntries;
    }
}
