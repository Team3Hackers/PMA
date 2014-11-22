package com.project.group4.propertymanagerassistant.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Created by benhoelzel on 11/12/14.
 * We use the databaseHandler to give us a database object. This object has all nessary means of
 * doing our database work.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler singleton;

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "propertyDatabase";

    private final Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //for ass table
        Long propertyId;
        Long tenantId;

        db.execSQL(Property.CREATE_TABLE);
        db.execSQL(Tenant.CREATE_TABLE);
        db.execSQL(TenantActive.CREATE_TABLE);

//building dummy data into database

        Property property = new Property();
        property.address = "19 Camino Del Oro";
        property.city = "RSM";
        property.unit = "A";
        property.state = "California";
        property.zip = "92688";

      propertyId =  db.insert(Property.TABLE_NAME, null, property.getContent());


        Tenant tenant = new Tenant();
        tenant.firstName = "Diane";
        tenant.lastName = "Rojas";
        tenant.address = "123 Via Lara";
        tenant.city = "Mission Viejo";
        tenant.state = "CA";
        tenant.zip ="13254";
        tenant.fico ="1234";
        tenant.tenantActive = "1";
        tenantId = db.insert(Tenant.TABLE_NAME, null, tenant.getContent());

//should do query on this to get ids..
        TenantActive currentTenant = new TenantActive();
        currentTenant.id = propertyId;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

//---

        property.address  = "123 Lucky Street";
        property.city  = "Anytown USA";
        property.unit = "None";
        property.state = "Kentucky";
        property.zip = "12345";
        propertyId = db.insert(Property.TABLE_NAME, null, property.getContent());



        tenant.firstName = "Frank";
        tenant.lastName = "Stephen";
        tenant.address = "12 Pumpkin Lane";
        tenant.city = "Burbon Lane";
        tenant.state = "KY";
        tenant.zip ="13254";
        tenant.fico ="1234";
        tenant.tenantActive = "1";
        tenantId = db.insert(Tenant.TABLE_NAME, null, tenant.getContent());


        currentTenant.id = propertyId;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    public synchronized Property getProperty(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Property.TABLE_NAME, Property.FIELDS,
                Property.COL_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Property item = null;
        if (cursor.moveToFirst()) {
            item = new Property(cursor);
        }
        cursor.close();


        return item;
    }

    /****TESTING****/
    public synchronized boolean putNewProperty(final Property property) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

//insert instead
            final long id = db.insert(Property.TABLE_NAME, null,
                    property.getContent());

            if (id > -1) {
                property.id = id;
                success = true;
            }


        if (success) {
            notifyProviderOnPropertyChange();
        }



        Tenant tenant = new Tenant();
        tenant.firstName = "";
        tenant.lastName = "";
        tenant.address = "";
        tenant.city = "";
        tenant.state = "";
        tenant.zip ="";
        tenant.fico ="";
        tenant.tenantActive = "1";
        Long tenantId = db.insert(Tenant.TABLE_NAME, null, tenant.getContent());

        TenantActive currentTenant = new TenantActive();
        currentTenant.id = id;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

        return success;
    }
     /**END TESTING**/


    public synchronized boolean putProperty(final Property property) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (property.id > -1) {
            result += db.update(Property.TABLE_NAME, property.getContent(),
                    Property.COL_ID + " IS ?",
                    new String[] { String.valueOf(property.id) });
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(Property.TABLE_NAME, null,
                    property.getContent());

            if (id > -1) {
                property.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnPropertyChange();
        }

        return success;
    }

    public synchronized int removeProperty(final Property property) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Property.TABLE_NAME,
                Property.COL_ID + " IS ?",
                new String[] { Long.toString(property.id) });

        if (result > 0) {
            notifyProviderOnPropertyChange();
        }
        return result;
    }

    private void notifyProviderOnPropertyChange() {
        context.getContentResolver().notifyChange(
                PropertyProvider.URI_PROPERTY, null, false);
    }

/** ------Tenant---------**/

    public synchronized Tenant getTenant(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Tenant.TABLE_NAME, Tenant.FIELDS,
                Tenant.COL_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Tenant item = null;
        if (cursor.moveToFirst()) {
            item = new Tenant(cursor);
        }
        cursor.close();


        return item;
    }

    /**
     * getTenant query to get ACTIVE tenant. Inner join on tenant and active table.
     * @param id
     * @return
     */

    public synchronized Tenant getTenantJoinActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();

        //Create new querybuilder to query database with a join
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        //Specify books table and add join to categories table (use full_id for joining categories table)
        _QB.setTables(Tenant.TABLE_NAME +
                " INNER JOIN " + TenantActive.TABLE_NAME + " ON " +
                Tenant.COL_ID + " = " + TenantActive.COL_TENANT_ID + " WHERE " + TenantActive.COL_PROPERTY_ID
                    + " = " + String.valueOf(id) + " AND " + Tenant.COL_TENANT_ACTIVE + " != 0" );



        final Cursor cursor = _QB.query(db, null, null, null, null, null, null);

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Tenant item = null;
        if (cursor.moveToFirst()) {
            item = new Tenant(cursor);
        }
        cursor.close();
        return item;
    }

    /**
     * This query function retrives all non-active tenants from the joint tables tenantactive and tenant
     * @param Tenant
     * @return
     */
    public synchronized Cursor getTenantJoinNotActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();

        //Create new querybuilder to query database with a join
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        //Specify books table and add join to categories table (use full_id for joining categories table)
        _QB.setTables(Tenant.TABLE_NAME +
                " INNER JOIN " + TenantActive.TABLE_NAME + " ON " +
                Tenant.COL_ID + " = " + TenantActive.COL_TENANT_ID + " WHERE " + TenantActive.COL_PROPERTY_ID
                + " = " + String.valueOf(id) + " AND " + Tenant.COL_TENANT_ACTIVE + " = 0" );



        final Cursor cursor = _QB.query(db, null, null, null, null, null, null);

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

//        Tenant item = null;
//        if (cursor.moveToFirst()) {
//            item = new Tenant(cursor);
//        }
        //cursor.close();
        return cursor;
    }


    //TODO: update this to include the assciation table tenant_active
    public synchronized boolean updateTenant(final Tenant Tenant) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (Tenant.id > -1) {
            result += db.update(Tenant.TABLE_NAME, Tenant.getContent(),
                    Tenant.COL_ID + " IS ?",
                    new String[] { String.valueOf(Tenant.id) });
        }

        if (result > 0) {
            success = true;
        }
        if (success) {
            notifyProviderOnTenantChange();
        }

        return success;
    }

//TODO: update this to include the assciation table tenant_active
    public synchronized boolean putTenant(final Tenant Tenant) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();
/*
        if (Tenant.id > -1) {
            result += db.update(Tenant.TABLE_NAME, Tenant.getContent(),
                    Tenant.COL_ID + " IS ?",
                    new String[] { String.valueOf(Tenant.id) });
        }

        if (result > 0) {
            success = true;
        } else {
*/
            // insert into table
            final long id = db.insert(Tenant.TABLE_NAME, null,
                    Tenant.getContent());

            if (id > -1) {
                Tenant.id = id;//Why do we do this???
                success = true;
            }
//        }

        if (success) {
            notifyProviderOnTenantChange();
        }

        return success;
    }
    //NOT DELETING ANY TENANT!
    public synchronized int removeTenant(final Tenant Tenant) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Tenant.TABLE_NAME,
                Tenant.COL_ID + " IS ?",
                new String[] { Long.toString(Tenant.id) });

        if (result > 0) {
            notifyProviderOnTenantChange();
        }
        return result;
    }
    //INCUDE ACCOCIATION TABLE
    private void notifyProviderOnTenantChange() {
        context.getContentResolver().notifyChange(
                TenantProvider.URI_TENANT, null, false);//
    }

    /**--------- End of TENANT --------------**/
    /**--------- Start TenantActive ---------**/
    //TODO: update this to include the assciation table tenant_active
    public synchronized boolean updateTenantActive(final TenantActive TenantActive) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();
// insert into table
        final long id = db.insert(TenantActive.TABLE_NAME, null,
                TenantActive.getContent());

        if (id > -1) {
            success = true;
        }
//        }

        if (success) {
 //           notifyProviderOnTenantActiveChange();
        }

        return success;
    }

    /**
     * getTenant query to get ACTIVE tenant. Inner join on tenant and active table.
     * @param id
     * @return
     */

    public synchronized TenantActive getTenantActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();


        final Cursor cursor = db.query(TenantActive.TABLE_NAME, TenantActive.FIELDS,
                TenantActive.COL_PROPERTY_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);

        //final Cursor cursor = db.query(Tenant.TABLE_NAME, Tenant.FIELDS,
        //       Tenant.COL_ID + " IS ?", new String[] { String.valueOf(id) },
        //      null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        TenantActive item = null;
        if (cursor.moveToFirst()) {
            item = new TenantActive(cursor);
        }
        cursor.close();
        return item;
    }

    private void notifyProviderOnTenantActiveChange() {
        context.getContentResolver().notifyChange(
                TenantActiveProvider.URI_TENANT_ACTIVE, null, false);//
    }
    /**--------- End of TENANT --------------**/

}

