package com.example.scele.movielab.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 3;
    public  static final String DATABASE_NAME    = "movie.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + Contract.FavoriteEntry.TABLE_NAME + " (" +
                Contract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                Contract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.FavoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                Contract.FavoriteEntry.COLUMN_POSTERPATH + " TEXT NOT NULL, " +
                Contract.FavoriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL " +
                "); ";
        db.execSQL(CREATE_SQL_CREATE_FAVORITE_TABLE);

        final String CREATE_SQL_CREATE_WATCHLIST_TABLE = "CREATE TABLE " + Contract.WatchListEntry.TABLE_NAME + " (" +
                Contract.WatchListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.WatchListEntry.COLUMN_MOVIEID + " INTEGER, " +
                Contract.WatchListEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.WatchListEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                Contract.WatchListEntry.COLUMN_POSTERPATH + " TEXT NOT NULL, " +
                Contract.WatchListEntry.COLUMN_OVERVIEW + " TEXT NOT NULL " +
                "); ";
        db.execSQL(CREATE_SQL_CREATE_WATCHLIST_TABLE);

        final String CREATE_SQL_CREATE_USERS_TABLE = "CREATE TABLE " + Contract.UsersEntry.TABLE_NAME + " (" +
                Contract.UsersEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.UsersEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                Contract.UsersEntry.COLUMN_EMAIL + " TEXT NOT NULL " +
                "); ";
        db.execSQL(CREATE_SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Contract.FavoriteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Contract.WatchListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Contract.UsersEntry.TABLE_NAME);
        onCreate(db);

    }
}
