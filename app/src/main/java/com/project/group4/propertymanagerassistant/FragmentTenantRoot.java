package com.project.group4.propertymanagerassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.project.group4.propertymanagerassistant.database.Property;

/**
 * Example about replacing fragments inside a ViewPager. I'm using
 * android-support-v7 to maximize the compatibility.
 * 
 * @author Dani Lao (@dani_lao)
 * 
 */
public class FragmentTenantRoot extends Fragment /*implements FragmentLifecycle*/ {

	private static final String TAG = "RootFragment";

    Long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty = false;//Default
    String fragmentType;//Type of incoming fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for(int i = 1; i <= 10; i++){
            menu.removeItem(i);
        }
        menu.add(menu.NONE, 4, 4, "Edit Tenant");

        menu.add(menu.NONE, 5, 5, "New Tenant");

        menu.add(menu.NONE, 6, 6, "Past Tenants");


    }
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment_tenant, container, false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */

        Bundle args = new Bundle();
        /** Set agruments to pass in pundle **/
        args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);



            FragmentTenantTab tenant = new FragmentTenantTab();
        tenant.setArguments(args);/****/
//            tenant.setPropertyArgs(this.propertyId, this.newProperty);
            transaction.replace(R.id.tenant_root_frame, tenant);



		transaction.commit();


		return view;
	}

    /**
     * Function to pass property arguments to this fragment.
     * Needed untill we figure out Bundle passing...
     */
//    public void setPropertyArgs(Long propertyId, Boolean newProperty, String incomingFragentType) {
//        this.propertyId = propertyId;
//        this.newProperty = newProperty;
//        this.fragmentType = incomingFragentType;
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        outState.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
    }
}
