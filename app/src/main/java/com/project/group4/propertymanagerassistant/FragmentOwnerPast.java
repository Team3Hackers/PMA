package com.project.group4.propertymanagerassistant;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Owner;

import java.util.ArrayList;

/**
 *
 * This fragment simply displays a list of past Owners for a property
 *
 *
 */



public class FragmentOwnerPast extends ListFragment {


    private Long propertyId;
    private Boolean newProperty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DatabaseHandler db = DatabaseHandler.getInstance(getActivity());
        Cursor crs = db.getOwnerJoinNotActive(this.propertyId);
        ArrayList<String> tenantList = new ArrayList<String>();

        if (crs != null) {
            while (crs.moveToNext()) {

                tenantList.add(
                        crs.getString(crs.getColumnIndex(Owner.COL_FIRST_NAME)) + " " +
                                crs.getString(crs.getColumnIndex(Owner.COL_LAST_NAME))
                );
            }
        }
        else{
            tenantList.add("No Past Tenants");
        }


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
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
        fm.popBackStack ("FragmentOwnerPast", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}


