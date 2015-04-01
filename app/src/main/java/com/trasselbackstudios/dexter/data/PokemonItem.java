package com.trasselbackstudios.dexter.data;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class PokemonItem {
    public final String id;
    public final String name;
    public final String height;
    public final String weight;
    public final String desc;
    public final String species;
    public final String types;
    public final String evo;
    private int evoForm = 0;

    public PokemonItem(Context context, String id) {
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

    public ArrayList<PokemonItem> getEvolutions(Context context) {
        if (evo.equals("")) return null;
        ArrayList<PokemonItem> pokemonEntries = new ArrayList<>();
        String[] evolutions = evo.split(",");
        PokemonDatabase db = new PokemonDatabase(context);
        for (String evolution : evolutions) {
            // Evolution string format: 1@Name, where 1 is the evolution form.
            String name = evolution.substring(2);
            Cursor cursor = db.getPokemonId(name);
            String id = cursor.getString(0);
            PokemonItem pokemonItem = new PokemonItem(context, id);
            pokemonItem.setEvoForm(Integer.parseInt(evolution.substring(0, 1)));
            if (pokemonItem.getEvoForm() <= this.evoForm)
                this.evoForm++;
            pokemonEntries.add(pokemonItem);
            cursor.close();
        }
        db.close();
        return pokemonEntries;
    }

    public void setEvoForm(int evoForm) {
        this.evoForm = evoForm;
    }

    public int getEvoForm() {
        return evoForm;
    }
}
