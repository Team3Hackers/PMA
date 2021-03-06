package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Owner;
import com.project.group4.propertymanagerassistant.database.OwnerActive;


/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentOwnerTab extends Fragment implements View.OnClickListener {

    public static String FRAGMENT_TYPE = "fragment_owner_tab";
//    private Menu thisMenu;
    private Owner mItem;
    private Owner tempItem;
    private OwnerActive assocationItem;
    private TextView textLastName;
    private TextView textFirstName;
    private TextView textAddress;
    private TextView textCity;
    private TextView textState;
    private TextView textZip;
    private TextView textPhone;

    Long propertyId;
    Boolean newProperty = false;//Default
    Boolean newOwner = false;
    Button saveButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//notify fragment it has option menu

        if (savedInstanceState!=null){//On a rotation, may not do this, root will give this.

            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = savedInstanceState.getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);

        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
            newProperty = getArguments().getBoolean(PropertyDetailFragment.ARG_ITEM_NEW);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_tab, container, false);

        saveButton = (Button) view.findViewById(R.id.owner_save_button);
        saveButton.setOnClickListener(this);
        saveButton.setVisibility(View.GONE);

        //Display current owner
        if (propertyId != null) {
            // Get property detail from database
            mItem = DatabaseHandler.getInstance(getActivity()).getOwnerJoinActive(propertyId);
            assocationItem = DatabaseHandler.getInstance(getActivity()).getOwnerActive(propertyId);
            Log.d("WTF-owner", "fail :: " + propertyId + " :: "+mItem.firstName);

        }


        if (mItem != null) {
            //Get text field
            textLastName = ((TextView) view.findViewById(R.id.textLastName));
            //Set text field with property address
            textLastName.setText(mItem.lastName);

            //Get text field
            textFirstName = ((TextView) view.findViewById(R.id.textFirstName));
            //Set text field with property address
            textFirstName.setText(mItem.firstName);


            //Get text field
            textAddress = ((TextView) view.findViewById(R.id.textAddress));
            //Set text field with property address
            textAddress.setText(mItem.address);

            textCity = ((TextView) view.findViewById(R.id.textCity));
            textCity.setText(mItem.city);

            textState = ((TextView) view.findViewById(R.id.textState));
            textState.setText(mItem.state);

            textZip = ((TextView) view.findViewById(R.id.textZip));
            textZip.setText(mItem.zip);

            textPhone = ((TextView) view.findViewById(R.id.textPhone));
            textPhone.setText(mItem.phone);

        }
        return view;
    }



    /**
     * This method is used to take the text fileds and update the database
     * Dont call this outside of android life cycles...
     */
    private void updateOwnerFromUI() {
        if (mItem != null) {

            mItem.lastName = textLastName.getText().toString();
            mItem.firstName = textFirstName.getText().toString();
            mItem.address = textAddress.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            mItem.phone = textPhone.getText().toString();
            DatabaseHandler.getInstance(getActivity()).updateOwner(mItem);//update

        }
    }

    /**
     * This method is used to take the text fileds and insert into the database.
     * Dont call this outside of android life cycles...
     * NEW OWNERS
     */
    private void insertOwnerFromUI() {

        if (mItem != null) {

            mItem.lastName = textLastName.getText().toString();
            mItem.firstName = textFirstName.getText().toString();
            mItem.address = textAddress.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            mItem.phone = textPhone.getText().toString();
            mItem.ownerActive = "1";//Add owner as active
            DatabaseHandler.getInstance(getActivity()).putOwner(mItem);//put
        }

    }


    private void insertOwnerActiveInFragment() {

        assocationItem.id = propertyId;
        assocationItem.idOwner = mItem.id;//Get this from query above
        DatabaseHandler.getInstance(getActivity()).updateOwnerActive(assocationItem);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==7) {
            newOwner = false;//Not a new owner, leave active flag alone...
            saveButton.setVisibility(View.VISIBLE);

        }
        else if (item.getItemId() == 8) {
            newOwner = true;//Used as new owner flag in onClick method
            tempItem = mItem;
            saveButton.setVisibility(View.VISIBLE);

        }
        else if(item.getItemId()==9) {
            /** Create bundle to send to next fragment **/
            Bundle args = new Bundle();
            args.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
            args.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);
            /** Get fragment manager from activity **/
            FragmentTransaction trans = getFragmentManager()
                    .beginTransaction();
            /** Build new fragment with arguments **/
            FragmentOwnerPast fragment = new FragmentOwnerPast();
            fragment.setArguments(args);
            /** Replace current fragment with newlly created one **/
            trans.replace(R.id.owner_root_frame, fragment);
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);//SOWER TRANSITION
            trans.addToBackStack("FragmentOwnerPast");
            trans.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        /**
         * Updates the empty table that was created when the user clicks "New Property", and then
         *  clicks the "New Owner" option.
         *  When a Property is made, there is a blank table for all relations for that
         *  property. So we do this logic to prevent a blank record when the user performs
         *  the steps: "New Property" and then "New Owner"
         **/
        if (newProperty){
            updateOwnerFromUI();//Update existing active owner
        }
        /** Stores the current owner in the owner table, but sets that owner's active field
         *  to 0. This is when the property exist with a owner and the user wants to
         *  add a new owner.
         *  This way we can track all past owners.
         */
        else if (newOwner) {//old property, new owner
            tempItem.ownerActive = "0";
            DatabaseHandler.getInstance(getActivity()).updateOwner(tempItem);//update old owner
            insertOwnerFromUI();//Insert new owner
            insertOwnerActiveInFragment();
            newOwner = false;//The owner is not new here now
        }
        /** This case is when the user selects  for updating the current owner.
         *
         */
        else {

            updateOwnerFromUI();//Update existing active owner

        }
        saveButton.setVisibility(View.GONE);

        Toast.makeText(getActivity(), mItem.firstName + " " + mItem.lastName + " saved to database", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
        outState.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW, newProperty);


    }
}