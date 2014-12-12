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
 * Used as a root fragment to be replaced by other fragments. Needed with the
 * viewpager, as the main acivity has no managment of fragments held with in
 * the viewpager.
 *
 */

public class FragmentTenantRoot extends Fragment /*implements FragmentLifecycle*/ {

	private static final String TAG = "RootFragment";

    long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty = false;//Default

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
        for(int i = 1; i <= PropertyDetailFragment.ITEM_COUNT; i++){
            menu.removeItem(i);
        }
        menu.add((int)propertyId, 4, 4, "Edit Tenant");

        menu.add((int)propertyId, 5, 5, "New Tenant");

        menu.add((int)propertyId, 6, 6, "Past Tenants");


    }



    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment_tenant, container, false);
		return view;
	}



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
