package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {
    /*
        The Database has the following properties:
        1. Database name is WhackAMole.db
        2. The Columns consist of
            a. Username
            b. Password
            c. Level
            d. Score
        3. Add user method for adding user into the Database.
        4. Find user method that finds the current position of the user and his corresponding
           data information - username, password, level highest score for each level
        5. Delete user method that deletes based on the username
        6. To replace the data in the database, we would make use of find user, delete user and add user

        The database shall look like the following:

        Username | Password | Level | Score
        --------------------------------------
        User A   | XXX      | 1     |    0
        User A   | XXX      | 2     |    0
        User A   | XXX      | 3     |    0
        User A   | XXX      | 4     |    0
        User A   | XXX      | 5     |    0
        User A   | XXX      | 6     |    0
        User A   | XXX      | 7     |    0
        User A   | XXX      | 8     |    0
        User A   | XXX      | 9     |    0
        User A   | XXX      | 10    |    0
        User B   | YYY      | 1     |    0
        User B   | YYY      | 2     |    0

     */


    static final String DATABASE_NAME = "MyRoutine.db";
    static final int DATABASE_VERSION = 2;

    //Columns
    static final String COLUMN_ID = "id";
    static final String COLUMN_USERNAME = "Username";
    static final String COLUMN_PASSWPRD = "Password";
    static final String COLUMN_LEVEL = "Level";
    static final String COLUMN_SCORE = "Score";

    static final String TABLE_NAME = "Name";

    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";



    public MyDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /* HINT:
            This is triggered on DB creation.
            Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);
         */

        db.execSQL( "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWPRD + " TEXT,"
                + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_SCORE + " INTEGER)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /* HINT:
            This is triggered if there is a new version found. ALL DATA are replaced and irreversible.
         */

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addUser(UserData userData)
    {
            /* HINT:
                This adds the user to the database based on the information given.
                Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
             */

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        for (int i = 0; i < userData.getScores().size(); i++) {

            values.put(COLUMN_USERNAME, userData.getMyUserName());
            values.put(COLUMN_PASSWPRD, userData.getMyPassword());
            values.put(COLUMN_SCORE, userData.getScores().get(i));
            values.put(COLUMN_LEVEL, userData.getLevels().get(i));

            Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
            db.insert(TABLE_NAME, null, values);
        }



    }

    public UserData findUser(String username)
    {
        /* HINT:
            This finds the user that is specified and returns the data information if it is found.
            If not found, it will return a null.
            Log.v(TAG, FILENAME +": Find user form database: " + query);

            The following should be used in getting the query data.
            you may modify the code to suit your design.

            if(cursor.moveToFirst()){
                do{
                    ...
                    .....
                    ...
                }while(cursor.moveToNext());
                Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
            }
            else{
                Log.v(TAG, FILENAME+ ": No data found!");
            }
         */


        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "='" + username +"' ORDER BY " +
                COLUMN_LEVEL + " ASC;";

        Log.v(TAG, FILENAME +": Find user form database: " + query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor queryData = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (queryData.moveToFirst()) {

            UserData userData = new UserData();

           String userName = queryData.getString(queryData.getColumnIndex(COLUMN_USERNAME));
           String password = queryData.getString(queryData.getColumnIndex(COLUMN_PASSWPRD));

           userData.setMyUserName(userName);
           userData.setMyPassword(password);

            do {

                userData.getLevels().add(queryData.getInt(queryData.getColumnIndexOrThrow(COLUMN_LEVEL)));
                userData.getScores().add(queryData.getInt(queryData.getColumnIndexOrThrow(COLUMN_SCORE)));

            } while (queryData.moveToNext());

            Log.v(TAG, FILENAME + ": QueryData: " + userData.getLevels().toString() + userData.getScores().toString());

            return userData;
        }  else{
            Log.v(TAG, FILENAME+ ": No data found!");
            return null;
        }




    }

    public boolean deleteAccount(String username) {
        /* HINT:
            This finds and delete the user data in the database.
            This is not reversible.
            Log.v(TAG, FILENAME + ": Database delete user: " + query);
         */

        SQLiteDatabase db = this.getWritableDatabase();




        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_USERNAME + " = \""
                + username + "\"";

        Log.v(TAG, FILENAME + ": Database delete user: " + query);

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {

            do {

                String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                Log.d(TAG, "deleteAccount: " + id);

                db.delete(TABLE_NAME, COLUMN_ID + "=?",
                        new String[]{id});


            } while (cursor.moveToNext());
        }


        db.close();

        return true;

    }
}
