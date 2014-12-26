package com.trasselbackstudios.dexter.data;

import android.content.Context;
import android.database.Cursor;

public class TypeEffectivenessItem {
    public final String id;
    public final String name;
    private String weakness = "";
    private String normal = "";
    private String resistance = "";
    private String immune = "";

    public TypeEffectivenessItem(Context context, String id) {
        this.id = id;
        PokemonDatabase db = new PokemonDatabase(context);
        Cursor cursor = db.getTypeEffectivenessItems(id);
        name = cursor.getString(cursor.getColumnIndex(PokemonContract.TypeEffectivenessEntry.COLUMN_NAME));
        int index = cursor.getColumnIndex(PokemonContract.TypeEffectivenessEntry.COLUMN_NAME) + 1;
        while (index < cursor.getColumnCount()) {
            String type = cursor.getColumnName(index);
            type = PokemonTypes.getColoredType(type);
            float dmg = cursor.getFloat(index);
            if (dmg <= 0) {
                immune += type + " ";
            } else if (dmg == 1) {
                normal += type + " ";
            } else if (dmg < 1) {
                resistance += type + " ";
            } else
                weakness += type + " ";
            index++;
        }
        cursor.close();
        db.close();
    }

    public String getWeakness() {
        return weakness;
    }

    public String getNormal() {
        return normal;
    }

    public String getResistance() {
        return resistance;
    }

    public String getImmune() {
        return immune;
    }
}
