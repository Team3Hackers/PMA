package com.project.group4.propertymanagerassistant;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Property;


/**
 * An activity representing a list of Properties. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PropertyDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PropertyListFragment} and the item details
 * (if present) is a {@link PropertyDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link PropertyListFragment.Callbacks} interface
 * to listen for item selections.
 */


public class PropertyListActivity extends FragmentActivity
        implements PropertyListFragment.Callbacks {

    


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean newProperty = false;
private boolean test = false;
    private boolean propertySelected = false ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        if (findViewById(R.id.property_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            //Force to landscape because I cant fix this shit!
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.


                ((PropertyListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.property_list))
                        .setActivateOnItemClick(true);


        }

        // TODO: If exposing deep links into your app, handle intents here.
    }



    /**
     * Callback method from {@link PropertyListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Long id) {


        //propertyId = id;//only used when item is selected in Options menu, careful
       // invalidateOptionsMenu(); DONT THINK I NEED THIS, BUT CHECK BIG SCREEN DEVICE

        Bundle arguments = new Bundle();
        arguments.putLong(PropertyDetailFragment.ARG_ITEM_ID, id);
        arguments.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);//Selected in options menu
        newProperty = false;//Reset this flag AFTER USAGE

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
//Log.d("Crap", ""+getSupportFragmentManager().findFragmentByTag("test"));
/**
 * try to remove this..
 */

        //Test-this wrked
//            PropertyDetailFragment fragment = new PropertyDetailFragment();//
//            getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction().replace(R.id.property_detail_container, fragment).addToBackStack(null).commit();
            //OG
                PropertyDetailFragment fragment = new PropertyDetailFragment();//
                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.property_detail_container, fragment, "test")
                        .commit();





        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PropertyDetailActivity.class);
            detailIntent.putExtras(arguments);
            startActivity(detailIntent);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
/** Only offer "New Property" item in main activity.
 *  All other menu items will be handled in their respective fragments
 */
        //inflater.inflate(R.menu.list_activity_small, menu);

        menu.add(menu.NONE, 0, 0, "Add New Property");



        return true;
    }

    /**
     * Action to perform when the user selects an item from menu
     * @param item : Item the user selected
     * @return     : Success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()){
            case 0://New property = 1. no xml right now
                newProperty = true;

                result = true;
                // Create a new person.
                Property p = new Property();
                DatabaseHandler.getInstance(this).putNewProperty(p);//putNewProperty is hail mary
                // Open a new fragment with the new id
                onItemSelected(p.id);
                break;

            default:
                result = false;
                break;

        }

        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("test",true);
    }


//    @Override
//    public void onClickItem(DialogFragment dialog) {
//
//    }
}
