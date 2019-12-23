package com.example.scele.movielab.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DBProvider extends ContentProvider {

    public static final int FAVORITIES = 100;
    public static final int WATCHLIST = 101;
    public static final int USERS = 102;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private              DBHelper mOpenHelper;

    public DBProvider(){
    }

    public static UriMatcher buildUriMatcher(){

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority   = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority,  Contract.PATH_FAVORITIES, FAVORITIES);
        matcher.addURI(authority,  Contract.PATH_WATCHLIST, WATCHLIST);
        matcher.addURI(authority, Contract.PATH_USERS, USERS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db    = mOpenHelper.getReadableDatabase();
        final int            match = sUriMatcher.match(uri);
        Cursor               retCursor;

        switch(match){
            case FAVORITIES: {
                retCursor = db.query(Contract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            case WATCHLIST: {
                retCursor = db.query(Contract.WatchListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            case USERS: {
                retCursor = db.query(Contract.UsersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case FAVORITIES         : return Contract.FavoriteEntry.CONTENT_TYPE;
            case WATCHLIST          : return Contract.WatchListEntry.CONTENT_TYPE;
            case USERS              : return Contract.UsersEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db    = mOpenHelper.getWritableDatabase();
        final int            match = sUriMatcher.match(uri);
        Uri                  returnUri;

        switch(match){
            case FAVORITIES: {
                long _id = db.insert(Contract.FavoriteEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = Contract.FavoriteEntry.buildFavoriteUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }

            } break;
            case WATCHLIST:{
                long _id = db.insert(Contract.WatchListEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = Contract.WatchListEntry.buildWatchListUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            } break;
            case USERS:{
                long _id = db.insert(Contract.UsersEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = Contract.UsersEntry.buildUsersUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            } break;
            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(selection == null)
            selection = "1";
        switch(match){
            case FAVORITIES:
                rowsDeleted = db.delete(Contract.FavoriteEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case WATCHLIST:
                rowsDeleted = db.delete(Contract.WatchListEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case USERS:
                rowsDeleted = db.delete(Contract.UsersEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch(match){
            case FAVORITIES:
                rowsUpdated = db.update(Contract.FavoriteEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case WATCHLIST:
                rowsUpdated = db.update(Contract.WatchListEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case USERS:
                rowsUpdated = db.update(Contract.UsersEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
