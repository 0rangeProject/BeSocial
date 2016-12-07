package com.example.agathe.tsgtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.agathe.tsgtest.dto.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 15/11/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "pathsManager";

    // Paths table name
    private static final String TABLE_PATHS = "paths";

    private static final String USER_ID = "userId";
    private static final String PATH_ID = "pathId";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String LON = "lon";
    private static final String LAT = "lat";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PATHS_TABLE = "CREATE TABLE " + TABLE_PATHS + " ("
                + USER_ID + " TEXT,"
                + PATH_ID + " TEXT,"
                + START_TIME + " TEXT,"
                + END_TIME + " TEXT,"
                + LON + " DOUBLE,"
                + LAT + " DOUBLE)";
        Log.i("TAG", CREATE_PATHS_TABLE);
        db.execSQL(CREATE_PATHS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATHS);

        // Create tables again
        onCreate(db);
    }

    // Adding new path
    public void addPath(Path path) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, path.getUserId());
        values.put(PATH_ID, path.getPathId());
        values.put(START_TIME, path.getStartTime());
        values.put(END_TIME, path.getEndTime());
        values.put(LON, path.getLon());
        values.put(LAT, path.getLat());

        // Inserting Row
        db.insertWithOnConflict(TABLE_PATHS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
    }

    // Getting single path
    public Path getPath(int path_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PATHS, new String[] { USER_ID,
                        PATH_ID, START_TIME, END_TIME, LON, LAT }, PATH_ID + "=?",
                new String[] { null, String.valueOf(path_id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Path path = new Path(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
                Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)));
        return path;
    }

    // Getting all paths
    public List<Path> getAllPaths() {
        List<Path> pathsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PATHS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Path path = new Path();
                path.setUserId(Integer.parseInt(cursor.getString(0)));
                path.setPathId(Integer.parseInt(cursor.getString(1)));
                path.setStartTime(cursor.getString(2));
                path.setEndTime(cursor.getString(3));
                path.setLon(Double.parseDouble(cursor.getString(4)));
                path.setLat(Double.parseDouble(cursor.getString(5)));
                // Adding path to list
                pathsList.add(path);
            } while (cursor.moveToNext());
        }

        // return paths list
        return pathsList;
    }

    // Getting paths count
    public int getPathsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PATHS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single path
    public int updatePath(Path path) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, path.getUserId());
        values.put(PATH_ID, path.getPathId());
        values.put(START_TIME, path.getStartTime());
        values.put(END_TIME, path.getEndTime());
        values.put(LON, path.getLon());
        values.put(LAT, path.getLat());

        // updating row
        return db.update(TABLE_PATHS, values, PATH_ID + " = ?",
                new String[] { String.valueOf(path.getPathId()) });
    }

    // Deleting single path
    public void deletePath(Path path) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATHS, PATH_ID + " = ?",
                new String[] { String.valueOf(path.getPathId()) });
        db.close();
    }
}
