package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;



import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Property;
import com.project.group4.propertymanagerassistant.database.Tenant;
import com.project.group4.propertymanagerassistant.database.TenantActive;

/**
 * TODO: can add actions for edit:
 *          - query with the join, then put back into tenant for edit. should problaly check for
 *            check for same name?
 *          - add new tenant to options menu item. do a zero on active content, then blank the form,
 *            then insert into tenant and activetenant.
 *          - if time permits, add a list of past tenants item on options menu
 *            query and display into new fragment list.
 */


/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentTenantTab extends Fragment implements View.OnClickListener {
    private Tenant mItem;
    private TenantActive assocationItem;
    private TextView textLastName;
    private TextView textFirstName;
    private TextView textAddress;
    private TextView textCity;
    private TextView textState;
    private TextView textZip;
    private TextView textFico;

    Long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty = false;//Default
    Boolean newTenant = false;
    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_tab, container, false);
        //Button to save.
//        this.propertyId = savedInstanceState.getLong("item_id");
//        this.newProperty = savedInstanceState.getBoolean("new_property", false);
        saveButton = (Button) view.findViewById(R.id.property_save_button);
        saveButton.setOnClickListener(this);
//        if(newProperty)//new propert only, everyone else is a edit
//            newTenant = true;//Set the new tenant flag
//        else
        saveButton.setVisibility(View.GONE);

        setHasOptionsMenu(true);
//  //Need new db objects for new properties
//        if (newProperty ) {
//            mItem = DatabaseHandler.getInstance(getActivity()).getTenant(propertyId);
//            assocationItem = DatabaseHandler.getInstance(getActivity()).getTenantActive(propertyId);
//        }
//
////For aready active tenant, need join

        //Display current tenant
        if (propertyId != null) {
            // Get property detail from database
            mItem = DatabaseHandler.getInstance(getActivity()).getTenantJoinActive(propertyId);
            assocationItem = DatabaseHandler.getInstance(getActivity()).getTenantActive(propertyId);
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

            textFico = ((TextView) view.findViewById(R.id.textFico));
            textFico.setText(mItem.fico);

        } else {
            //Create new tenant object?
            mItem = new Tenant();
            //Create new tenant active object?
            assocationItem = new TenantActive();
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //Beause the menu is part of the root activity, so when you go through tabs we have delete so
        //we dont keep adding simalar menu options
        menu.removeItem(2);
        menu.removeItem(3);
        menu.removeItem(4);
        //

        menu.add(menu.NONE, 2, 2, "Edit Tenant");

        menu.add(menu.NONE, 3, 3, "New Tenant");

        menu.add(menu.NONE, 4, 4, "Past Tenants");


    }

    /**
     * This method is used to take the text fileds and update the database
     * Dont call this outside of android life cycles...
     */
    private void updateTenantFromUI() {
        if (mItem != null) {

            mItem.lastName = textLastName.getText().toString();
            mItem.firstName = textFirstName.getText().toString();
            mItem.address = textAddress.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            mItem.fico = textFico.getText().toString();
//            if(newTenant){
//                mItem.tenantActive = "0";//Set past tenant flag to not active
//            }
            DatabaseHandler.getInstance(getActivity()).updateTenant(mItem);//update

        }
    }

    /**
     * This method is used to take the text fileds and insert into the database.
     * Dont call this outside of android life cycles...
     * NEW TENANTS
     */
    private void insertTenantFromUI() {

        if (mItem != null) {

            mItem.lastName = textLastName.getText().toString();
            mItem.firstName = textFirstName.getText().toString();
            mItem.address = textAddress.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            mItem.fico = textFico.getText().toString();
            mItem.tenantActive = "1";//Add tenant as active
            DatabaseHandler.getInstance(getActivity()).putTenant(mItem);//put
        }

    }


    private void insertTenantActiveInFragment() {


        //if (assocationItem != null){//and mitem??
        assocationItem.id = propertyId;
        assocationItem.idTenant = mItem.id;//Get this from query above
        DatabaseHandler.getInstance(getActivity()).updateTenantActive(assocationItem);
        // }
    }

    //WHEN NEW PROPERTY, NEW OR EDIT TENANT IS BROKEN! MAYBE WE QUERY INSTEAD OF BOOL??
    @Override
//WORKING WITH EDIT ONLY RIGHT NOW, NEED GET THE BUTTON WHEN OUTSIDE ACTIVITY SELECTS NEW PROP
    public boolean onOptionsItemSelected(MenuItem item) {
        saveButton.setVisibility(View.VISIBLE);
        if (item.getItemId() == 3) {//CAn do a trick here to prevent the empty row, if row is !empty from function, else do edit...

            newTenant = true;//Used as new tenant flag in onClick method
            mItem.tenantActive = "0";//disable old tenants status
            updateTenantFromUI();//Update old tenants status
        } else {
            newTenant = false;//Not a new tenant, leave active flag alone...
        }
        if(item.getItemId()==4) {
            /** Create bundle to send to next fragment **/
            Bundle arguments = new Bundle();
            /** Set agruments to pass in pundle **/
            arguments.putLong(PropertyDetailFragment.ARG_ITEM_ID,
                    getActivity().getIntent().getLongExtra(PropertyDetailFragment.ARG_ITEM_ID, -1));
            arguments.putBoolean(PropertyDetailFragment.ARG_ITEM_NEW,
                    getActivity().getIntent().getBooleanExtra(PropertyDetailFragment.ARG_ITEM_NEW, false/*default*/));
            /** Get fragment manager from activity **/
            FragmentTransaction trans = getFragmentManager()
                    .beginTransaction();
            /** Build new fragment with arguments **/
            StaticFragment fragment = new StaticFragment();
            fragment.setArguments(arguments);
            /** Replace current fragment with newlly created one **/
            trans.replace(R.id.root_frame, fragment);
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);//SOWER TRANSITION
            trans.addToBackStack("StaticFragment");
            trans.commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        /** Updates the empty table that was created when the user clicks "New Property", and then
         *  clicks the "New Tenant" option.
         *  When a Property is made, there is a blank table for all relations for that
         *  property. So we do this logic to prevent a blank record when the user performs
         *  the steps: "New Property" and then "New Tenant"
         **/
        if (newTenant && newProperty){
            updateTenantFromUI();//Update existing active tenant
        }
        /** Stores the current tenant in the tenant table, but sets that tenant's active field
         *  to 0. This is when the property exist with a tenant and the user wants to
         *  add a new tenant.
         *  This way we can track all past tenants.
         */
        else if (newTenant) {//old property, new tenant


            insertTenantFromUI();//Insert new tenant
            insertTenantActiveInFragment();

            newTenant = false;//The tenant is not new here now
        }
        /** This case is when the user selects  for updating the current tenant.
         *
         */
        else {//This is a edit, will i get here on a new proerty add??

            updateTenantFromUI();//Update existing active tenant

        }
        saveButton.setVisibility(View.GONE);


//TODO: DONT ALLOW USER TO SAVE UNTILL CRITIICAL DATA IS INPUTTED!
        Toast.makeText(getActivity(), mItem.firstName + " " + mItem.lastName + " saved to database", Toast.LENGTH_SHORT).show();
    }




}