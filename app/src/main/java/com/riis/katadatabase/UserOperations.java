package com.riis.katadatabase;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserOperations {

    // Database fields
    private DataBaseWrapper dbHelper;
    private String[] USER_TABLE_COLUMNS = { DataBaseWrapper.USER_ID, DataBaseWrapper.USER_NAME, DataBaseWrapper.USER_EMAIL };
    private SQLiteDatabase database;

    public UserOperations(Context context) {
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User addUser(String name, String email) {

        ContentValues values = new ContentValues();
        values.put(DataBaseWrapper.USER_NAME, name);
        values.put(DataBaseWrapper.USER_EMAIL, email);

        long userId = database.insert(DataBaseWrapper.USERS, null, values);

        // now that the user is created return it ...
        Cursor cursor = database.query(DataBaseWrapper.USERS,
                USER_TABLE_COLUMNS, DataBaseWrapper.USER_ID + " = "
                        + userId, null, null, null, null);

        cursor.moveToFirst();

        User newComment = parseUser(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteUser(User comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DataBaseWrapper.USERS, DataBaseWrapper.USER_ID
                + " = " + id, null);
    }

    public List getAllUsers() {
        List users = new ArrayList();

        Cursor cursor = database.query(DataBaseWrapper.USERS,
                USER_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = parseUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }



    //Retrieves a single employee record with the given id
    public String getUserEmailById(long id) {

        User regUser = null;

        String sql = "SELECT " + DataBaseWrapper.USER_EMAIL + " FROM " + DataBaseWrapper.USERS
                + " WHERE " + DataBaseWrapper.USER_ID + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            return cursor.getString(2);
        } else {
            return "N/A";
        }

    }


    private User parseUser(Cursor cursor) {
        User user = new User();
        user.setId((cursor.getInt(0)));
        user.setName(cursor.getString(1));
        return user;
    }
}