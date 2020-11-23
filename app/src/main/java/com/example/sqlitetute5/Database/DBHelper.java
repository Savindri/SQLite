package com.example.sqlitetute5.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";
    private static final String TAG = "tag";

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /*This will be executed each time when an instance of this class is created.
    If the table exists, nothing will happen, else table will be created.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                        UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT,"+
                        UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";
        //use the details from the UsersMaster and Users classes we created.
        //Specify the primary key from the BaseColumns

        db.execSQL(SQL_CREATE_ENTRIES); //this will execute the contents of SQL_CREATE_ENTRIES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* add data.
    add the User Name and Password.
    (accepts UserName and passwords as parameters). */
    public long addInfo(String username, String password){

        //gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        //create a new map of values, where column names the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        //insert the new row,returning the primary key value of the new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME, null, values);
        return newRowId;
    }

    // read the user info
    public List readAllInfo(String req){

        SQLiteDatabase db = getReadableDatabase();

        //define a projection that specifies which columns from the database you will actually use after this query
        String[] projection = {
                UsersMaster.Users._ID, //primary key
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };
        //filter results WHERE "userName" = 'SLIIT USER'
        //String selection = Users.COLUMN_NAME_USERNAME + "=?";
        //String[] selectionArgs = {""};

        //How you want the results sorted in the resulting cursor
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC"; //sorting order is descending-latest will be come first

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,  //the table to query
                projection,                    //the column to return
                null,                 // the columns for the WHERE clause
                null,              //the values for the WHERE clause
                null,                  // do not group the rows
                null,                    // do not filter by row groups
                sortOrder                       //the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        Log.i(TAG, "readAllInfo: " + userNames);

        if (req == "user"){
            return userNames; //if user passes username,it returns username
        }else if(req == "password"){
            return passwords;
        }else{
            return null;
        }
    }

    //this will delete a particular user from the table according to the username passed
    public void deleteInfo(String userName){

        SQLiteDatabase db = getReadableDatabase();

        //define 'where' part of query
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ? ";

        //specify arguments and placeholder order
        String[] selectionArgs = { userName };

        //issue SQL statement
        db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);
    }

    // update user details for the given user name
    //pass username and update password
    public int updateInfo(String userName, String password){

        SQLiteDatabase db =getReadableDatabase();

        //new value for one column
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        //which row to update, based on the title
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        return count;
    }
}
