package com.project.group4.propertymanagerassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
        Log.d("TenantRoot", "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
            FragmentTenantTab tenant = (FragmentTenantTab) getActivity().getSupportFragmentManager().findFragmentByTag("current_tenant_tab");
            FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();

            ft.detach(tenant);
            ft.attach(tenant);
            ft.commit();
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            //if (savedInstanceState == null){

            Bundle args = new Bundle();
            args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
            args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
            FragmentTenantTab tenant = new FragmentTenantTab();
            tenant.setArguments(args);
            fm.beginTransaction().replace(R.id.tenant_root_frame, tenant, "current_tenant_tab").commit();
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

//
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        //if (savedInstanceState == null){
//
//        Bundle args = new Bundle();
//        args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
//        args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
//        FragmentTenantTab tenant = new FragmentTenantTab();
//        tenant.setArguments(args);
//        fm.beginTransaction().replace(R.id.tenant_root_frame, tenant).commit();



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

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TenantRoot", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TenantRoot", "OnDestroy");
    }
}
