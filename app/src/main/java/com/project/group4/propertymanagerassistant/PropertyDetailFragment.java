package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single Property detail screen.
 * This fragment is either contained in a {@link PropertyListActivity}
 * in two-pane mode (on tablets) or a {@link PropertyDetailActivity}
 * on handsets.
 */
public class PropertyDetailFragment extends Fragment{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The fragment argument representing if this is a new property we are adding
     */
    public static final String ARG_ITEM_NEW = "new_property";


    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;

    //create viewPager
    ViewPager viewPager=null;
    //Declare TabAdaptor
    TabAdapter myTabAdapter;
    //int selectedTab = 0;//

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropertyDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);


       }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_tab_pager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);//get pager from app xml, need to assign it to current view
        myTabAdapter = new TabAdapter(getFragmentManager());
        viewPager.setAdapter(myTabAdapter);
/*Left this in long for clarity
 *replace next 2 lines with {lin1 and 2}
 * if(getArguments().getBoolean(ARG_ITEM_NEW))
 */
        Boolean selectedTab = getArguments().getBoolean(ARG_ITEM_NEW);//line1
        if(selectedTab){//line2
            viewPager.setCurrentItem(1, true);//This works,
        }

        /**
         * Here, I pass the selected ID from the list to the adaptor.
         * The adaptor will later set the active fragment with the id.
         * That fragment will use it to query the database.
         * There is a better way to do this, but I suck.
         * Should be able to use bundle like we did here. To be continued...
         */
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            myTabAdapter.setId(getArguments().getLong(ARG_ITEM_ID), getArguments().getBoolean(ARG_ITEM_NEW));//Send ID to adaptor to have the property id for all fragments

        }
        return rootView;
    }


////TEST
//    @Override
//    public void onPause() {
//        setHasOptionsMenu(true);
//       // how to clean up...
//        Log.d("PropDetailFrag", "PAUSED");
//        super.onPause();
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//        super.onPrepareOptionsMenu(menu);
//    }
}
