package com.riis.katadatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserOperationsTest {
    private UserOperations userDBOperation;
    private SQLiteDatabase mockDatabase;
    private DataBaseWrapper mockDBHelper;
    private Cursor userCursor;

    @Before
    public void setUp() {
        mockDatabase = Mockito.mock(SQLiteDatabase.class);
        mockDBHelper = Mockito.mock(DataBaseWrapper.class);
        userDBOperation = new UserOperations(Mockito.mock(Context.class));
        userDBOperation.database = mockDatabase;
        userCursor = getUserCursor();
    }

    @Test
    public void testAddUser() {
        when(mockDBHelper.getWritableDatabase()).thenReturn(mockDatabase);
        when(mockDatabase.insert(anyString(), anyString(), (ContentValues) anyObject())).thenReturn(1L);
        when(mockDatabase.query(anyString(),
                any(String[].class), anyString(), any(String[].class), anyString(),
                anyString(), anyString())).thenReturn(userCursor);

        userDBOperation.addUser("godfrey", "godfrey@riis.com");

        Mockito.verify(mockDatabase).insert(Mockito.anyString(), Mockito.anyString(), (ContentValues) anyObject());
    }

    @Test
    public void testDeleteUser() {
        when(mockDatabase.delete(anyString(), anyString(), any(String[].class))).thenReturn(1);
        userDBOperation.deleteUser(new User());

        Mockito.verify(mockDatabase).delete(anyString(), anyString(), any(String[].class));
    }

    @Test
    public void testGetAllUsers() {
        when(mockDatabase.query(anyString(),
                any(String[].class), anyString(), any(String[].class), anyString(),
                anyString(), anyString())).thenReturn(userCursor);

        userDBOperation.getAllUsers();
        Mockito.verify(mockDatabase).query(anyString(),
                any(String[].class), anyString(), any(String[].class), anyString(),
                anyString(), anyString());
    }

    @Test
    public void testGetUserEmailById() {
        when(mockDatabase.rawQuery(anyString(), any(String[].class))).thenReturn(userCursor);
        userDBOperation.getUserEmailById(1);

        Mockito.verify(mockDatabase).rawQuery(anyString(), any(String[].class));
    }

    private Cursor getUserCursor() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                { DataBaseWrapper.USER_ID, DataBaseWrapper.USER_NAME, DataBaseWrapper.USER_EMAIL });
        matrixCursor.addRow(new String[] {"1", "godfrey", "godfrey@riis.com"});

        return matrixCursor;
    }
}
