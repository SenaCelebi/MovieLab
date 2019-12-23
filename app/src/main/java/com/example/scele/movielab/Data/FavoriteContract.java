package com.example.scele.movielab.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

    public static final String CONTENT_AUTHORITY = "com.example.scele.movielab.Data";
    public static final Uri FAVORITE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITIES = "favorities";


    public static final class FavoriteEntry implements BaseColumns{

        public static final Uri F_CONTENT_URI = FAVORITE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITIES).build();

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTERPATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildFavoriteUri(long id) {
            return ContentUris.withAppendedId(F_CONTENT_URI, id);
        }

    }
}
