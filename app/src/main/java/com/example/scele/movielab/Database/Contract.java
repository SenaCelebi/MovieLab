package com.example.scele.movielab.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public static final String CONTENT_AUTHORITY = "com.example.scele.movielab";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITIES      = "favorities";
    public static final String PATH_WATCHLIST    = "watchlist";
    public static final String PATH_USERS    = "users";

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri F_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITIES).build();

        public static final String CONTENT_TYPE      = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITIES;

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

    public static final class WatchListEntry implements BaseColumns {

        public static final Uri W_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WATCHLIST).build();

        public static final String CONTENT_TYPE      = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WATCHLIST;

        public static final String TABLE_NAME = "watchlist";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTERPATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildWatchListUri(long id) {
            return ContentUris.withAppendedId(W_CONTENT_URI, id);
        }
    }

    public static final class UsersEntry implements BaseColumns {

        public static final Uri U_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USERS).build();

        public static final String CONTENT_TYPE      = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";

        public static Uri buildUsersUri(long id) {
            return ContentUris.withAppendedId(U_CONTENT_URI, id);
        }
    }
}
