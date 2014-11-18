package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.Tenant;

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
    private TextView textLastName;
    private TextView textFirstName;
    private TextView textAddress;
    private TextView textCity;
    private TextView textState;
    private TextView textZip;
    private TextView textFico;


    String textString;
    Long propertyId;///Using this as a test, before we get to transaction tables. REFACTOR WHEN WOKRING
    Boolean newProperty;
    TextView text;
    Button saveButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_tab, container, false);
        //Button to save.

        saveButton = (Button) view.findViewById(R.id.property_save_button);
        saveButton.setOnClickListener(this);
        if(!newProperty)//new propert only, everyone else is a edit
            saveButton.setVisibility(View.GONE);

        setHasOptionsMenu(true);

        if (propertyId != null) {
            // Get property detail from database
            mItem = DatabaseHandler.getInstance(getActivity()).getTenant(propertyId);
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




        }

        return view;
    }


    /**
     * This is the method we use to set the property id from the adaptor
     *
     */
    public void setPropertyId(Long data, Boolean status){
        this.propertyId = data;
        this.newProperty = status;//Probablly not needed
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
        // outState.putString("text",textString);
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
        menu.removeItem(2);
        menu.add(menu.NONE, 2, 2, "Edit");
    }

    /**
     * This method is used to take the text fileds and u[date the database.
     * Dont call this outside of android life cycles...
     * TODO: update add to include tenant_active table. Set old, if it exist to 0, and add new with a 1 in assocation table.
     */
    private void updatePropertyFromUI() {

        if (mItem != null) {

            mItem.lastName = textLastName.getText().toString();
            mItem.firstName = textFirstName.getText().toString();
            mItem.address = textAddress.getText().toString();
            mItem.city = textCity.getText().toString();
            mItem.state = textState.getText().toString();
            mItem.zip = textZip.getText().toString();
            mItem.fico = textFico.getText().toString();
            DatabaseHandler.getInstance(getActivity()).putTenant(mItem);

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
        Toast.makeText(getActivity(), mItem.firstName +" "+ mItem.lastName + " saved to database", Toast.LENGTH_SHORT).show();
    }
}