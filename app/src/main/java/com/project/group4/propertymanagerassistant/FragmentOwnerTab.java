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
public class FragmentOwnerTab extends Fragment implements  View.OnClickListener {

    public static String FRAGMENT_TYPE = "fragment_owner_tab";
    private Menu thisMenu;
    private Owner mItem;
    private OwnerActive assocationItem;
    private TextView textLastName;
    private TextView textFirstName;
    private TextView textAddress;
    private TextView textCity;
    private TextView textState;
    private TextView textZip;
    private TextView textPhone;

    Long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty = false;//Default
    Boolean newOwner = false;
    Button saveButton;

    Bundle arguments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//notify fragment it has option menu
        /****/
        arguments = savedInstanceState;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_tab, container, false);
        //Button to save.
//        this.propertyId = savedInstanceState.getLong("item_id");
//        this.newProperty = savedInstanceState.getBoolean("new_property", false);
        saveButton = (Button) view.findViewById(R.id.owner_save_button);
        saveButton.setOnClickListener(this);
//        if(newProperty)//new propert only, everyone else is a edit
//            newOwner = true;//Set the new owner flag
//        else
        saveButton.setVisibility(View.GONE);


//  //Need new db objects for new properties
//        if (newProperty ) {
//            mItem = DatabaseHandler.getInstance(getActivity()).getOwner(propertyId);
//            assocationItem = DatabaseHandler.getInstance(getActivity()).getOwnerActive(propertyId);
//        }
//
////For aready active owner, need join

        //Display current owner
        if (propertyId != null) {
            // Get property detail from database
            Log.d("WTF", "::craper");
            mItem = DatabaseHandler.getInstance(getActivity()).getOwnerJoinActive(propertyId);
            assocationItem = DatabaseHandler.getInstance(getActivity()).getOwnerActive(propertyId);
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

        } else {//May never reach this since we build the tables in database handler construction
            //Create new owner object?
            mItem = new Owner();
            //Create new owner active object?
            assocationItem = new OwnerActive();
        }






        return view;
    }


    /**
     * Function to pass property arguments to this fragment.
     * Needed untill we figure out Bundle passing...
     * @param propertyId
     *          Thid id the primary key of the property table in database
     * @param newProperty
     *          This is true when the user selects the menu option to create new property
     */
    public void setPropertyArgs(Long propertyId, Boolean newProperty) {
        this.propertyId = propertyId;
        this.newProperty = newProperty;
    }



    //MAYNOT NEED RIGHT NOW
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }




    /*
    //Testing to insure we got the correct property ID, we do need to make sure stuff is good before we acces the text view. Because we call this from adaptor, the view is not creting
    public void setPropertyId(String data){
        this.propertyId = data;//setting the string
        Log.d("FragmentOwnerTab", "Here now, with " + data);//testing
    }
*/

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//    }

//        @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        //Beause the menu is part of the root activity, so when you go through tabs we have delete so
//        //we dont keep adding simalar menu options
//        menu.removeItem(2);
//        menu.removeItem(3);
//        menu.removeItem(4);
//        //
//
//        menu.add(menu.NONE, 2, 2, "Edit Owner");
//
//        menu.add(menu.NONE, 3, 3, "New Owner");
//
//        menu.add(menu.NONE, 4, 4, "Past Owner");
//
//
//    }

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
//            if(newOwner){
//                mItem.ownerActive = "0";//Set past owner flag to not active
//            }
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


        //if (assocationItem != null){//and mitem??
        assocationItem.id = propertyId;
        assocationItem.idOwner = mItem.id;//Get this from query above
        DatabaseHandler.getInstance(getActivity()).updateOwnerActive(assocationItem);
        // }
    }

    //WHEN NEW PROPERTY, NEW OR EDIT OWNER IS BROKEN! MAYBE WE QUERY INSTEAD OF BOOL??
    @Override
//WORKING WITH EDIT ONLY RIGHT NOW, NEED GET THE BUTTON WHEN OUTSIDE ACTIVITY SELECTS NEW PROP
    public boolean onOptionsItemSelected(MenuItem item) {
        saveButton.setVisibility(View.VISIBLE);
        if (item.getItemId() == 8) {//CAn do a trick here to prevent the empty row, if row is !empty from function, else do edit...

            newOwner = true;//Used as new owner flag in onClick method
            mItem.ownerActive = "0";//disable old owners status
            updateOwnerFromUI();//Update old owners status

        } else if (item.getItemId()==7) {
            newOwner = false;//Not a new owner, leave active flag alone...
        }
        /**GET THIS WORKING LATER**/
        if(item.getItemId()==9) {
            /** Create bundle to send to next fragment **/
            //Bundle arguments = new Bundle();
//            /** Set agruments to pass in pundle **/
//            arguments.putLong(PropertyDetailFragment.ARG_ITEM_ID,
//                    getActivity().getIntent().getLongExtra(PropertyDetailFragment.ARG_ITEM_ID, -1)
//            );
//
            Log.d("ItemSel", "::" + getActivity().getIntent().getLongExtra(PropertyDetailFragment.ARG_ITEM_ID, -1) );
//
//            arguments.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW,
//                    getActivity().getIntent().getBooleanExtra(PropertyDetailFragment.ARG_ITEM_NEW, false/*default*/));
            /** Get fragment manager from activity **/
            FragmentTransaction trans = getFragmentManager()
                    .beginTransaction();
            /** Build new fragment with arguments **/
            FragmentOwnerPast fragment = new FragmentOwnerPast();
            fragment.setPropertyArgs(this.propertyId, this.newProperty);
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

        /** Updates the empty table that was created when the user clicks "New Property", and then
         *  clicks the "New Owner" option.
         *  When a Property is made, there is a blank table for all relations for that
         *  property. So we do this logic to prevent a blank record when the user performs
         *  the steps: "New Property" and then "New Owner"
         **/
        if (newOwner && newProperty){
            updateOwnerFromUI();//Update existing active owner
        }
        /** Stores the current owner in the owner table, but sets that owner's active field
         *  to 0. This is when the property exist with a owner and the user wants to
         *  add a new owner.
         *  This way we can track all past owners.
         */
        else if (newOwner) {//old property, new owner


            insertOwnerFromUI();//Insert new owner
            insertOwnerActiveInFragment();

            newOwner = false;//The owner is not new here now
        }
        /** This case is when the user selects  for updating the current owner.
         *
         */
        else {//This is a edit, will i get here on a new proerty add??

            updateOwnerFromUI();//Update existing active owner

        }
        saveButton.setVisibility(View.GONE);


//TODO: DONT ALLOW USER TO SAVE UNTILL CRITIICAL DATA IS INPUTTED!
        Toast.makeText(getActivity(), mItem.firstName + " " + mItem.lastName + " saved to database", Toast.LENGTH_SHORT).show();
    }


}