package com.trasselbackstudios.dexter.data;

import android.provider.BaseColumns;

public class PokemonContract {
    public static final class PokemonEntry implements BaseColumns {
        public static final String TABLE_NAME = "pokemon";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
    }

    public static final class DetailsEntry implements BaseColumns {
        public static final String TABLE_NAME = "details";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_EVO = "evo";
    }

    public static final class TypeEffectivenessEntry implements BaseColumns {
        public static final String TABLE_NAME = "type_effectiveness";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NORMAL = "Normal";
        public static final String COLUMN_BUG = "Bug";
        public static final String COLUMN_DARK = "Dark";
        public static final String COLUMN_DRAGON = "Dragon";
        public static final String COLUMN_ELECTRIC = "Electric";
        public static final String COLUMN_FAIRY = "Fairy";
        public static final String COLUMN_FIGHTING = "Fighting";
        public static final String COLUMN_FIRE = "Fire";
        public static final String COLUMN_FLYING = "Flying";
        public static final String COLUMN_GHOST = "Ghost";
        public static final String COLUMN_GRASS = "Grass";
        public static final String COLUMN_GROUND = "Ground";
        public static final String COLUMN_ICE = "Ice";
        public static final String COLUMN_POISON = "Poison";
        public static final String COLUMN_PSYCHIC = "Psychic";
        public static final String COLUMN_ROCK = "Rock";
        public static final String COLUMN_STEEL = "Steel";
        public static final String COLUMN_WATER = "Water";
    }
}
