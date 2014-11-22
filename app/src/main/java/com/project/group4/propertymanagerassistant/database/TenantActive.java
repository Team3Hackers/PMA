package com.project.group4.propertymanagerassistant.database;

/**
 * Created by benhoelzel on 11/12/14.
 */

import android.content.ContentValues;
import android.database.Cursor;

/**
 * A class representation of a row in table
 */
public class TenantActive {

    // SQL convention says Table name should be "singular"
    public static final String TABLE_NAME = "TenantActive";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_PROPERTY_ID = "_id_property";
    // These fields can be anything you want.
    public static final String COL_TENANT_ID = "_id_tenant";


    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_PROPERTY_ID, COL_TENANT_ID };

    /*
     * The SQL code that creates a Table for storing Persons in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_TENANT_ID + " INTEGER NOT NULL, "
                    + COL_PROPERTY_ID + " INTEGER NOT NULL, "
                    + " FOREIGN KEY(" + COL_PROPERTY_ID + " ) REFERENCES Property ( _id ) ," /*explicit string of _id */
                    + " FOREIGN KEY(" + COL_TENANT_ID + " ) REFERENCES Tenant ( _id ) )";

    // Fields corresponding to database columns
    public long id = -1;
    public long idTenant = -1;


    /**
     * No need to do anything, fields are already set to default values above
     */
    public TenantActive() {
    }

    /**
     * Convert information from the database into a Person object.
     */
    public TenantActive(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.idTenant= cursor.getLong(1);


    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here

        values.put(COL_PROPERTY_ID, id);
        values.put(COL_TENANT_ID, idTenant);



        return values;
    }
}
