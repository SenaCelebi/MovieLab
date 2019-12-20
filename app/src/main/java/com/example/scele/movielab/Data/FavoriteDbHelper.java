package com.example.scele.movielab.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.scele.movielab.Models.mMovie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;


    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL " +
                "); ";


        db.execSQL(CREATE_SQL_CREATE_FAVORITE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addMovie(mMovie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE , movie.getOriginalTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH, movie.getPosterPath());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW, movie.getOverview());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + "="+id,null);
    }

    public List<mMovie> getAllFavorites(){
        String columns [] = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH,
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW
        };

        String sortOrder = FavoriteContract.FavoriteEntry._ID + " ASC";
        List<mMovie> favoriteList= new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()){
            do{
                mMovie movie = new mMovie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTERPATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW)));

                favoriteList.add(movie);

            }while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return favoriteList;
    }

}
