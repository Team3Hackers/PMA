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
        Long ownerId;

        /** Build tables **/
        db.execSQL(Property.CREATE_TABLE);
        db.execSQL(Tenant.CREATE_TABLE);
        db.execSQL(TenantActive.CREATE_TABLE);
        db.execSQL(Owner.CREATE_TABLE);
        db.execSQL(OwnerActive.CREATE_TABLE);
        db.execSQL(PropertyTransaction.CREATE_TABLE);

        /**
         * building dummy property #1 into database
         */

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

        Owner owner = new Owner();
        owner.firstName = "Sharon";
        owner.lastName = "Smith";
        owner.phone ="(949) 589-1234";
        owner.address = "123 Legato St.";
        owner.city = "Laguna Beach";
        owner.state = "CA";
        owner.zip = "92123";
        owner.ownerActive = "1";
        ownerId = db.insert(Owner.TABLE_NAME, null, owner.getContent());


        TenantActive currentTenant = new TenantActive();
        currentTenant.id = propertyId;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

        OwnerActive currentOwner = new OwnerActive();
        currentOwner.id = propertyId;
        currentOwner.idOwner = ownerId;
        db.insert(OwnerActive.TABLE_NAME, null, currentOwner.getContent());

        PropertyTransaction propertyTransaction = new PropertyTransaction();
        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Al's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());




        /**
         * building dummy property #2 into database
         */

        property.address  = "123 Lucky Street";
        property.city  = "Anytown USA";
        property.unit = "None";
        property.state = "Kentucky";
        property.zip = "12345";
        propertyId = db.insert(Property.TABLE_NAME, null, property.getContent());



        tenant.firstName = "Frank";
        tenant.lastName = "Stephen";
        tenant.address = "12 Pumpkin Lane";
        tenant.city = "Anytown USA";
        tenant.state = "KY";
        tenant.zip ="13254";
        tenant.fico ="1234";
        tenant.tenantActive = "1";
        tenantId = db.insert(Tenant.TABLE_NAME, null, tenant.getContent());

        owner.firstName = "Frank";
        owner.lastName = "Wilson";
        owner.phone ="(222) 222-2222";
        owner.address = "123 1st Street";
        owner.city = "Pinole";
        owner.state = "CA";
        owner.zip = "91345";
        owner.ownerActive = "1";
        ownerId = db.insert(Owner.TABLE_NAME, null, owner.getContent());

        currentTenant.id = propertyId;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

        currentOwner.id = propertyId;
        currentOwner.idOwner = ownerId;
        db.insert(OwnerActive.TABLE_NAME, null, currentOwner.getContent());

        propertyTransaction.property  = propertyId;
        propertyTransaction.amount = "1500.00";
        propertyTransaction.category = "Repair";
        propertyTransaction.date = "10/14/14";
        propertyTransaction.payee = "Plumbers are us";
        propertyTransaction.note = "Water heater replacment";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());


        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Smith's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());

        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Margo's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());


        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Rusty's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());

        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Billy's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());

        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Nick's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());


        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "George's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());


        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Franks's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());

        propertyTransaction.property = propertyId;
        propertyTransaction.amount = "50.00";
        propertyTransaction.category = "Maintenance";
        propertyTransaction.date = "5/1/14";
        propertyTransaction.payee = "Bob's Gardening";
        propertyTransaction.note = "Monthly yard maintenance";
        db.insert(PropertyTransaction.TABLE_NAME, null, propertyTransaction.getContent());

        /** End of dummy build **/
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

    /** **/
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
/** Empty table saves us from null pointer exception when selecting new property **/
/**Build empty tenant table for current property **/

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

/**Build empty tenantactive table for current property **/

        TenantActive currentTenant = new TenantActive();
        currentTenant.id = id;
        currentTenant.idTenant = tenantId;
        db.insert(TenantActive.TABLE_NAME, null, currentTenant.getContent());

/**Build empty owner table for current property **/

        Owner owner = new Owner();
        owner.firstName = "";
        owner.lastName = "";
        owner.address = "";
        owner.city = "";
        owner.state = "";
        owner.zip ="";
        owner.phone ="";
        owner.ownerActive = "1";
        Long ownerId = db.insert(Owner.TABLE_NAME, null, owner.getContent());

/**Build empty owneractive table for current property **/

        OwnerActive currentOwner = new OwnerActive();
        currentOwner.id = id;
        currentOwner.idOwner = ownerId;
        db.insert(OwnerActive.TABLE_NAME, null, currentOwner.getContent());

/** Build empty property transaction for current property **/

        PropertyTransaction trans = new PropertyTransaction();
        trans.amount ="";
        trans.category = "";
        trans.date = "";
        trans.note = "";
        trans.payee = "";
        trans.property = id;


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
     * @param
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

    /** Start of Owner **/

    /** ------Owner---------**/

    public synchronized Owner getOwner(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Owner.TABLE_NAME, Owner.FIELDS,
                Owner.COL_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Owner item = null;
        if (cursor.moveToFirst()) {
            item = new Owner(cursor);
        }
        cursor.close();


        return item;
    }

    /**
     * getOwner query to get ACTIVE owner. Inner join on owner and active table.
     * @param id
     * @return
     */

    public synchronized Owner getOwnerJoinActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();

        //Create new querybuilder to query database with a join
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        //Specify books table and add join to categories table (use full_id for joining categories table)
        _QB.setTables(Owner.TABLE_NAME +
                " INNER JOIN " + OwnerActive.TABLE_NAME + " ON " +
                Owner.COL_ID + " = " + OwnerActive.COL_OWNER_ID + " WHERE " + OwnerActive.COL_PROPERTY_ID
                + " = " + String.valueOf(id) + " AND " + Owner.COL_OWNER_ACTIVE + " != 0" );



        final Cursor cursor = _QB.query(db, null, null, null, null, null, null);

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Owner item = null;
        if (cursor.moveToFirst()) {
            item = new Owner(cursor);
        }
        cursor.close();
        return item;
    }

    /**
     * This query function retrives all non-active owners from the joint tables owneractive and owner
     * @param
     * @return
     */
    public synchronized Cursor getOwnerJoinNotActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();

        //Create new querybuilder to query database with a join
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        //Specify books table and add join to categories table (use full_id for joining categories table)
        _QB.setTables(Owner.TABLE_NAME +
                " INNER JOIN " + OwnerActive.TABLE_NAME + " ON " +
                Owner.COL_ID + " = " + OwnerActive.COL_OWNER_ID + " WHERE " + OwnerActive.COL_PROPERTY_ID
                + " = " + String.valueOf(id) + " AND " + Owner.COL_OWNER_ACTIVE + " = 0" );


        final Cursor cursor = _QB.query(db, null, null, null, null, null, null);

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

//        Owner item = null;
//        if (cursor.moveToFirst()) {
//            item = new Owner(cursor);
//        }
        //cursor.close();
        return cursor;
    }


    //
    public synchronized boolean updateOwner(final Owner Owner) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (Owner.id > -1) {
            result += db.update(Owner.TABLE_NAME, Owner.getContent(),
                    Owner.COL_ID + " IS ?",
                    new String[] { String.valueOf(Owner.id) });
        }

        if (result > 0) {
            success = true;
        }
        if (success) {
            notifyProviderOnOwnerChange();
        }

        return success;
    }

    //TODO: update this to include the assciation table owner_active
    public synchronized boolean putOwner(final Owner Owner) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();
/*
        if (Owner.id > -1) {
            result += db.update(Owner.TABLE_NAME, Owner.getContent(),
                    Owner.COL_ID + " IS ?",
                    new String[] { String.valueOf(Owner.id) });
        }

        if (result > 0) {
            success = true;
        } else {
*/
        // insert into table
        final long id = db.insert(Owner.TABLE_NAME, null,
                Owner.getContent());

        if (id > -1) {
            Owner.id = id;//Why do we do this???
            success = true;
        }
//        }

        if (success) {
            notifyProviderOnOwnerChange();
        }

        return success;
    }
//    //NOT DELETING ANY OWNER!
//    public synchronized int removeOwner(final Owner Owner) {
//        final SQLiteDatabase db = this.getWritableDatabase();
//        final int result = db.delete(Owner.TABLE_NAME,
//                Owner.COL_ID + " IS ?",
//                new String[] { Long.toString(Owner.id) });
//
//        if (result > 0) {
//            notifyProviderOnOwnerChange();
//        }
//        return result;
//    }
    //INCUDE ACCOCIATION TABLE
    private void notifyProviderOnOwnerChange() {
        context.getContentResolver().notifyChange(
                OwnerProvider.URI_OWNER, null, false);//
    }

    /**--------- End of OWNER --------------**/
    /**--------- Start OwnerActive ---------**/
    //TODO: update this to include the assciation table owner_active
    public synchronized boolean updateOwnerActive(final OwnerActive OwnerActive) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();
// insert into table
        final long id = db.insert(OwnerActive.TABLE_NAME, null,
                OwnerActive.getContent());

        if (id > -1) {
            success = true;
        }
//        }

        if (success) {
                      notifyProviderOnOwnerActiveChange();
        }

        return success;
    }

    /**
     * getOwner query to get ACTIVE owner. Inner join on owner and active table.
     * @param id
     * @return
     */

    public synchronized OwnerActive getOwnerActive(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();


        final Cursor cursor = db.query(OwnerActive.TABLE_NAME, OwnerActive.FIELDS,
                OwnerActive.COL_PROPERTY_ID + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);

        //final Cursor cursor = db.query(Owner.TABLE_NAME, Owner.FIELDS,
        //       Owner.COL_ID + " IS ?", new String[] { String.valueOf(id) },
        //      null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        OwnerActive item = null;
        if (cursor.moveToFirst()) {
            item = new OwnerActive(cursor);
        }
        cursor.close();
        return item;
    }

    private void notifyProviderOnOwnerActiveChange() {
        context.getContentResolver().notifyChange(
                OwnerActiveProvider.URI_OWNER_ACTIVE, null, false);//
    }
    /**--------- End of OWNER --------------**/

/** Transaction **/

    public synchronized Cursor getSinglePropertyTransaction(final long id, final long trans_id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(PropertyTransaction.TABLE_NAME, PropertyTransaction.FIELDS,
                PropertyTransaction.COL_PROPERTY + " IS ? AND " + PropertyTransaction.COL_ID + " IS ? ", new String[] { String.valueOf(id), Long.toString(trans_id) },
                null, null, null, null);

        cursor.moveToFirst();

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }
        else{

            return cursor;
        }

    }
    public synchronized Cursor getAllPropertyTransaction(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(PropertyTransaction.TABLE_NAME, PropertyTransaction.FIELDS,
                PropertyTransaction.COL_PROPERTY + " IS ?", new String[] { String.valueOf(id) },
                null, null, null, null);


        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }
        else{

            return cursor;
        }

    }

    public synchronized Cursor fetchPayeeByName(String searchToken, final long id){
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(PropertyTransaction.TABLE_NAME, PropertyTransaction.FIELDS,
                PropertyTransaction.COL_PROPERTY + " IS ? AND " + PropertyTransaction.COL_PAYEE + " IS ? ",
                new String[] { String.valueOf(id), searchToken },
                null, null, null, null);
        while (cursor.moveToNext()) {
//test search....
            Log.d("WTF",

                    cursor.getString(cursor.getColumnIndex(PropertyTransaction.COL_PAYEE)) + " " +
                            cursor.getString(cursor.getColumnIndex(PropertyTransaction.COL_AMOUNT))
            );
        }


        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }
        else{

            return cursor;
        }
    }

    public synchronized boolean putPropertyTransaction(final PropertyTransaction propertyTransaction) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (propertyTransaction.id > -1) {
            result += db.update(PropertyTransaction.TABLE_NAME, propertyTransaction.getContent(),
                    PropertyTransaction.COL_ID + " IS ?",
                    new String[] { String.valueOf(propertyTransaction.id) });
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(PropertyTransaction.TABLE_NAME, null,
                    propertyTransaction.getContent());

            if (id > -1) {
                propertyTransaction.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnTransactionChange();
        }

        return success;
    }

    public synchronized boolean updatePropertyTransaction(final PropertyTransaction pTrans) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (pTrans.id > -1) {
            result += db.update(PropertyTransaction.TABLE_NAME, pTrans.getContent(),
                    PropertyTransaction.COL_ID + " IS ?",
                    new String[] { String.valueOf(pTrans.id) });
        }

        if (result > 0) {
            success = true;
        }
        if (success) {
            notifyProviderOnPropertyChange();
        }

        return success;
    }

    public synchronized int removePropertyTransaction(final PropertyTransaction propertyTransaction) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(PropertyTransaction.TABLE_NAME,
                PropertyTransaction.COL_ID + " IS ?",
                new String[] { Long.toString(propertyTransaction.id) });

        if (result > 0) {
            notifyProviderOnTransactionChange();
        }
        return result;
    }

    private void notifyProviderOnTransactionChange() {
        context.getContentResolver().notifyChange(
                PropertyTransactionProvider.URI_PROPERTY_TRANSACTION, null, false);
    }
    /*** End of Transaction ***/




}

