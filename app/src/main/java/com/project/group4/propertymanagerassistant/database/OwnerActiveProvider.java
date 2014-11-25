//package com.project.group4.propertymanagerassistant.database;

/**
* Created by benhoelzel on 11/18/14.
*/
//public class OwnerActiveProvider {


//}
package com.project.group4.propertymanagerassistant.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
* The ContentProvider is a helpfull way to populate a list of items.
*/

public class OwnerActiveProvider extends ContentProvider {

    // All URIs share these parts
    public static final String AUTHORITY = "com.project.group4.propertymanagerassistant.provider";
    public static final String SCHEME = "content://";

    // URIs
    // Used for all persons
    public static final String OWNER_ACTIVE = SCHEME + AUTHORITY + "/OWNER_ACTIVE_active";
    public static final Uri URI_OWNER_ACTIVE = Uri.parse(OWNER_ACTIVE);
    // Used for a single person, just add the id to the end
    public static final String OWNER_ACTIVE_BASE = OWNER_ACTIVE + "/";

    public OwnerActiveProvider() {
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
        if (URI_OWNER_ACTIVE.equals(uri)) {
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(OwnerActive.TABLE_NAME, OwnerActive.FIELDS, null, null, null,
                            null, null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_OWNER_ACTIVE);
        } else if (uri.toString().startsWith(OWNER_ACTIVE_BASE)) {
            final long id = Long.parseLong(uri.getLastPathSegment());
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(OwnerActive.TABLE_NAME, OwnerActive.FIELDS,
                            OwnerActive.COL_PROPERTY_ID + " IS ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_OWNER_ACTIVE);
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
