package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
public class FragmentOwnerRoot extends Fragment /*implements FragmentLifecycle*/ {

	private static final String TAG = "RootFragment";

    Long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty = false;//Default
    String fragmentType;//Type of incoming fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
       if(savedInstanceState!=null) {//For saved state
           propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
           newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
       }
       else{//New enrty to fragment
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
       }
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment_owner, container, false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

                Bundle args = new Bundle();
            args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
            args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);


            FragmentOwnerTab owner = new FragmentOwnerTab();
            owner.setArguments(args);
            transaction.replace(R.id.owner_root_frame, owner);



		transaction.commit();



		return view;
	}


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for(int i = 1; i <= 10; i++){
            menu.removeItem(i);
        }
        menu.add(menu.NONE, 7, 7, "Edit Owner");

        menu.add(menu.NONE, 8, 8, "New Owner");

        menu.add(menu.NONE, 9, 9, "Past Owner");


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     *For rotation
     *
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        outState.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
    }
}
