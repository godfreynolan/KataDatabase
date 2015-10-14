package com.riis.katadatabase;

import android.test.ApplicationTestCase;

import android.test.AndroidTestCase;

public class InMemoryDbTest extends AndroidTestCase {

      private UserOperations dbStore;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dbStore = new UserOperations(getContext());
    }

    public void testStoreOneName() throws Exception {
        dbStore.addUser("MyName");
        assertEquals(1, dbStore.getAllUsers());
    }

    public void testStoreTwoName() throws Exception {
        dbStore.addUser("MyName");
        dbStore.addUser("YourName");
        assertEquals(2, dbStore.getAllUsers());
    }

}