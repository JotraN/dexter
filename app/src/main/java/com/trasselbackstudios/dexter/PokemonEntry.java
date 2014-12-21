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
    private int evoLevel = 0;

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
        for (String evolution : evolutions) {
            String name = evolution.substring(2);
            Cursor cursor = db.getPokemonId(name);
            String id = cursor.getString(0);
            PokemonEntry pokemonEntry = new PokemonEntry(context, id);
            pokemonEntry.setEvoLevel(Integer.parseInt(evolution.substring(0, 1)));
            if (pokemonEntry.getEvoLevel() <= this.evoLevel)
                this.evoLevel++;
            pokemonEntries.add(pokemonEntry);
            cursor.close();
        }
        db.close();
        return pokemonEntries;
    }

    public void setEvoLevel(int evoLevel) {
        this.evoLevel = evoLevel;
    }

    public int getEvoLevel() {
        return evoLevel;
    }
}
