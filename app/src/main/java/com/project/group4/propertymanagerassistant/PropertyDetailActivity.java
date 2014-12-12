package com.project.group4.propertymanagerassistant;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a single Property detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PropertyListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PropertyDetailFragment}.
 */
public class PropertyDetailActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_property_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();

            arguments.putLong(PropertyDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(PropertyDetailFragment.ARG_ITEM_ID, -1));
            arguments.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW,
                    getIntent().getBooleanExtra(PropertyDetailFragment.ARG_ITEM_NEW,false/*default*/));

            PropertyDetailFragment fragment = new PropertyDetailFragment();

            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();//get frag manager

            fragmentManager.beginTransaction().add(R.id.property_detail_container, fragment, "test").commit();//set it

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, PropertyListActivity.class));
                result = true;
                break;
            default:
                break;
        }
        return result;
    }



}
