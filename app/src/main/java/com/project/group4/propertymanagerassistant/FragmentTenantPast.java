package com.project.group4.propertymanagerassistant;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Property;
import com.project.group4.propertymanagerassistant.database.PropertyProvider;
import com.project.group4.propertymanagerassistant.database.Tenant;
import com.project.group4.propertymanagerassistant.database.TenantProvider;

import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;

/**
 *
 * This fragment simply displays a list of past tenants
 *
 *
 */

public class FragmentTenantPast extends ListFragment {


    private Long propertyId;
    private Boolean newProperty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){//On a rotation, may not do this, root will give this.

            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DatabaseHandler db = DatabaseHandler.getInstance(getActivity());
        Cursor crs = db.getTenantJoinNotActive(this.propertyId);//propertyId
        ArrayList<String> tenantList = new ArrayList<String>();

        if(crs != null) {


            if (crs != null) {
                while (crs.moveToNext()) {

                    tenantList.add(
                            crs.getString(crs.getColumnIndex(Tenant.COL_FIRST_NAME)) + " " +
                                    crs.getString(crs.getColumnIndex(Tenant.COL_LAST_NAME))
                    );
                }
            }
        }
        else{
            tenantList.add("No Past Tenants");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                tenantList );

        setListAdapter(arrayAdapter);
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FragmentManager fm = getActivity()
                .getSupportFragmentManager();

        /** Tap any item to go back **/
        fm.popBackStack ("StaticFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}



