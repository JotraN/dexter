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
    }
}
