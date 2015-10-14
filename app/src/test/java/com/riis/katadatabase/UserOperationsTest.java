package com.riis.katadatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserOperationsTest {

    private UserOperations userDBoperation;
    private SQLiteDatabase mockDatabase;
    private DataBaseWrapper mockDBHelper;

    @Before
    public void setUp() {
        userDBoperation = Mockito.mock(UserOperations.class);
        // when(userDBoperation.getUserEmailById(1)).thenReturn("godfrey@riis.com");
        mockDatabase = Mockito.mock(SQLiteDatabase.class);
        mockDBHelper = Mockito.mock(DataBaseWrapper.class);
    }

    @Test
    public void testAddUser()
    {

            ContentValues values = new ContentValues();
            values.put(DataBaseWrapper.USER_NAME, "godfrey");
            values.put(DataBaseWrapper.USER_EMAIL, "godfrey@riis.com");

            when(mockDBHelper.getWritableDatabase()).thenReturn(mockDatabase);
            when(mockDatabase.insert(Mockito.anyString(), Mockito.anyString(), values)).thenReturn(1L);
            userDBoperation.addUser("godfrey", "godfrey@riis.com");

            Mockito.verify(mockDatabase.insert(Mockito.anyString(), Mockito.anyString(), values));



    }

    @After
    public void cleanUp() {
        userDBoperation.close();
    }
}
