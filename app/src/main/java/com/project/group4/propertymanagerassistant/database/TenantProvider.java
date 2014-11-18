package com.project.group4.propertymanagerassistant.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * The ContentProvider is a helpfull way to populate a list of items.
 */

public class TenantProvider extends ContentProvider {

    // All URIs share these parts
    public static final String AUTHORITY = "com.project.group4.propertymanagerassistant.provider";
    public static final String SCHEME = "content://";

    // URIs
    // Used for all persons
    public static final String TENANT = SCHEME + AUTHORITY + "/tenant";
    public static final Uri URI_TENANT = Uri.parse(TENANT);
    // Used for a single person, just add the id to the end
    public static final String TENANT_BASE = TENANT + "/";

    public TenantProvider() {
        Log.d("PropProvider: ", "PropProvider Constructor");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor result = null;
        if (URI_TENANT.equals(uri)) {
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(Tenant.TABLE_NAME, Tenant.FIELDS, null, null, null,
                            null, null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_TENANT);
        } else if (uri.toString().startsWith(TENANT_BASE)) {
            final long id = Long.parseLong(uri.getLastPathSegment());
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(Tenant.TABLE_NAME, Tenant.FIELDS,
                            Tenant.COL_ID + " IS ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_TENANT);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
