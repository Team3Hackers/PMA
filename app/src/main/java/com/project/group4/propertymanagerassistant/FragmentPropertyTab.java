package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Property;

/**
 * Created by benhoelzel on 10/26/14.
 */



public class FragmentPropertyTab extends Fragment implements /*FragmentLifecycle,*/  View.OnClickListener {
    private Property mItem;
    private TextView textAddress;
    private TextView textUnit;
    private TextView textCity;
    private TextView textState;
    private TextView textZip;


    String textString;
    Long propertyId;
    Boolean newProperty = false;
    TextView text;
    Button saveButton;

    private Menu thisMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState!=null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty =  getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_tab, container, false);
        //Button to save.
        saveButton = (Button) view.findViewById(R.id.property_save_button);
        saveButton.setOnClickListener(this);
        if (!newProperty)
            saveButton.setVisibility(View.GONE);

        if(savedInstanceState==null) {

            if (propertyId != null) {
                // Get property detail from database
                mItem = DatabaseHandler.getInstance(getActivity()).getProperty(propertyId);
            }

            if (mItem != null) {
                //Get text field
                textAddress = ((TextView) view.findViewById(R.id.textAddress));
                //Set text field with property address
                textAddress.setText(mItem.address);

                textUnit = ((TextView) view.findViewById(R.id.textUnit));
                textUnit.setText(mItem.unit);

                textCity = ((TextView) view.findViewById(R.id.textCity));
                textCity.setText(mItem.city);

                textState = ((TextView) view.findViewById(R.id.textState));
                textState.setText(mItem.state);

                textZip = ((TextView) view.findViewById(R.id.textZip));
                textZip.setText(mItem.zip);


            }
        }
        return view;
    }


    /**
     * This is the method we use to set the property id from the adaptor
     *
     */
    public void setPropertyId(Long data, Boolean status){
        this.propertyId = data;
        this.newProperty = status;
    }


    @Override
    public void onPause() {
        Log.d("OnPaused?" , "Yes...");
        super.onPause();
    }

    //MAYNOT NEED RIGHT NOW
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // text = (TextView) getActivity().findViewById(R.id.textView);//Set text view


    }

//Saving state of text?
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        outState.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
    }


    /*
    //Testing to insure we got the correct property ID, we do need to make sure stuff is good before we acces the text view. Because we call this from adaptor, the view is not creting
    public void setPropertyId(String data){
        this.propertyId = data;//setting the string
        Log.d("FragmentOwnerTab", "Here now, with " + data);//testing
    }
*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        for(int i = 1; i <= 10; i++){
            menu.removeItem(i);
        }
        menu.add(menu.NONE, 3, 3, "Edit");
    }

    /**
     * This method is used to take the text fileds and u[date the database.
     * Dont call this outside of android life cycles...
     */
    private void updatePropertyFromUI() {

        if (mItem != null) {

            mItem.address = textAddress.getText().toString();
            mItem.unit = textUnit.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            DatabaseHandler.getInstance(getActivity()).putProperty(mItem);

        }
    }

    @Override//WORKING WITH EDIT ONLY RIGHT NOW, NEED GET THE BUTTON WHEN OUTSIDE ACTIVITY SELECTS NEW PROP
    public boolean onOptionsItemSelected(MenuItem item) {
        saveButton.setVisibility(View.VISIBLE);

        Log.d("FragmentPropertyTab","onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        updatePropertyFromUI();

       saveButton.setVisibility(View.GONE);
        Toast.makeText(getActivity(), mItem.address + " saved to database", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onPauseFragment() {
////        this.thisMenu.clear();
//
//    }
//
//    @Override
//    public void onResumeFragment() {
//       //getActivity().invalidateOptionsMenu();
//
//    }
}