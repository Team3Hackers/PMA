package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.group4.propertymanagerassistant.database.Property;

/**
 * Used as a root fragment to be replaced by other fragments. Needed with the
 * viewpager, as the main acivity has no managment of fragments held with in
 * the viewpager.
 *
 */
public class FragmentOwnerRoot extends Fragment /*implements FragmentLifecycle*/ {

	private static final String TAG = "RootFragment";

    Long propertyId;
    Boolean newProperty = false;//Default
    String fragmentType;//Type of incoming fragment



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

       if(savedInstanceState!=null) {//For saved state
           propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
           newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);

           FragmentOwnerTab owner = (FragmentOwnerTab) getActivity().getSupportFragmentManager().findFragmentByTag("current_owner_tab");
           FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
           ft.detach(owner);
           ft.attach(owner);
           ft.commit();

       }
       else {//New enrty to fragment
           propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
           newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);

           FragmentManager fm =  getActivity().getSupportFragmentManager();

           Bundle args = new Bundle();
           args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
           args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
           //
           FragmentOwnerTab owner = new FragmentOwnerTab();
           owner.setArguments(args);
           fm.beginTransaction().replace(R.id.owner_root_frame, owner, "current_owner_tab").commit();

       }


    }



    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment_owner, container, false);



		return view;
	}


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for(int i = 1; i <= PropertyDetailFragment.ITEM_COUNT; i++){
            menu.removeItem(i);
        }
        menu.add(menu.NONE, 7, 7, "Edit Owner");

        menu.add(menu.NONE, 8, 8, "New Owner");

        menu.add(menu.NONE, 9, 9, "Past Owner");


    }




    /**
     *
     *
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        outState.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
    }
}
