package com.example.scele.movielab.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavoriteProvider extends ContentProvider {

    public static final int FAVORITIES= 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoriteDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority   = FavoriteContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,  FavoriteContract.PATH_FAVORITIES, FAVORITIES);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new FavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db    = mOpenHelper.getWritableDatabase();
        final int            match = sUriMatcher.match(uri);
        Uri                  returnUri;

        long _id = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);

        if (_id > 0){
            returnUri = FavoriteContract.FavoriteEntry.buildFavoriteUri(_id);
        }
        else{
            throw new android.database.SQLException("Failed to insert row into" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        rowsDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);

        return rowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
