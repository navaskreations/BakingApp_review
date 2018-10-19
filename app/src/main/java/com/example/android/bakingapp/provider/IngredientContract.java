package com.example.android.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class IngredientContract {

    public static final String AUTHORITY = "com.example.android.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECEIPE = "ingredients";

    public static final long INVALID_RECEIPE_ID = -1;

    public static final class ReceipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECEIPE).build();

        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_RECEIPE_ID = "receipeId";
        public static final String COLUMN_RECEIPE_NAME = "receipeName";
        public static final String COLUMN_INGREDEINT = "ingredeint";
    }
}
