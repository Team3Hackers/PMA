
package com.project.group4.propertymanagerassistant.database;


/**
 * Created by benhoelzel on 11/12/14.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A class representation of a row in table "PropertyTransaction".
 */
public class PropertyTransaction {

    //
    public static final String TABLE_NAME = "PropertyTransaction";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    // These fields can be anything you want.
    public static final String COL_PROPERTY = "property";
    public static final String COL_DATE = "date";
    public static final String COL_CATEGORY = "category";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_PAYEE = "payee";
    public static final String COL_NOTE = "note";

    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_ID, COL_PROPERTY, COL_AMOUNT, COL_DATE,
            COL_CATEGORY, COL_PAYEE, COL_NOTE };

    /*
     * The SQL code that creates a Table for storing Persons in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY, "
                    + COL_PROPERTY + " INTEGER , "
                    + COL_AMOUNT + " TEXT NOT NULL DEFAULT '', "
                    + COL_DATE + " DATE NOT NULL DEFAULT '', "//Change
                    + COL_CATEGORY + " TEXT NOT NULL DEFAULT '', "
                    + COL_PAYEE + " TEXT NOT NULL DEFAULT '', "
                    + COL_NOTE + " TEXT NOT NULL DEFAULT '' "
                    + ")";


    // Fields corresponding to database columns
    public long id = -1;
    public long property = -1;
    public String amount = "";
    public String date = "";
    public String category = "";
    public String payee = "";
    public String note = "";

    /**
     * No need to do anything, fields are already set to default values above
     */
    public PropertyTransaction() {
    }

    /**
     * Convert information from the database into a Person object.
     */
    public PropertyTransaction(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.property = cursor.getLong(1);
        this.amount = cursor.getString(2);
        this.date = cursor.getString(3);
        this.category = cursor.getString(4);
        this.payee = cursor.getString(5);
        this.note = cursor.getString(6);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_PROPERTY, property);
        values.put(COL_AMOUNT, amount);
        values.put(COL_DATE, date);
        values.put(COL_CATEGORY, category);
        values.put(COL_PAYEE, payee);
        values.put(COL_NOTE, note);

        return values;
    }
}
