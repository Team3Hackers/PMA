package com.project.group4.propertymanagerassistant.database;

/**
 * Created by benhoelzel on 11/12/14.
 */

        import android.content.ContentValues;
        import android.database.Cursor;

/**
 * A class representation of a row in table "Property".
 */
public class Property {

    //
    public static final String TABLE_NAME = "Property";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    // These fields can be anything you want.
    public static final String COL_ADDRESS = "address";
    public static final String COL_UNIT = "unit";
    public static final String COL_CITY = "city";
    public static final String COL_STATE = "state";
    public static final String COL_ZIP = "zip";

    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_ID, COL_ADDRESS, COL_UNIT,
            COL_CITY, COL_STATE, COL_ZIP };

    /*
     * The SQL code that creates a Table for storing Persons in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY,"
                    + COL_ADDRESS + " TEXT NOT NULL DEFAULT '',"
                    + COL_UNIT + " TEXT NOT NULL DEFAULT '',"
                    + COL_CITY + " TEXT NOT NULL DEFAULT '',"
                    + COL_STATE + " TEXT NOT NULL DEFAULT '',"
                    + COL_ZIP + " TEXT NOT NULL DEFAULT ''"
                    + ")";

    // Fields corresponding to database columns
    public long id = -1;
    public String address = "";
    public String unit = "";
    public String city = "";
    public String state = "";
    public String zip = "";

    /**
     * No need to do anything, fields are already set to default values above
     */
    public Property() {
    }

    /**
     * Convert information from the database into a Person object.
     */
    public Property(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.address = cursor.getString(1);
        this.unit = cursor.getString(2);
        this.city = cursor.getString(3);
        this.state = cursor.getString(4);
        this.zip = cursor.getString(5);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_ADDRESS, address);
        values.put(COL_UNIT, unit);
        values.put(COL_CITY, city);
        values.put(COL_STATE, state);
        values.put(COL_ZIP, zip);

        return values;
    }
}
