package com.project.group4.propertymanagerassistant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


class TabAdapter extends FragmentStatePagerAdapter {//to save state, use statepageradptapter
                                                //will call savestate when scroll two fragments away, and it will detach, which destroys
                                                //usefull when you wan to save state



    //MY LOGGING ID STRING
    private static final String TAG = "TabAdaptor";

    //Set current object propertyId
    Long propertyId;
    Boolean newProperty;


//base constructor
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }


    //return current fragment
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        if(i==0){
            fragment = new FragmentTransactionTab();
        }
        if(i==1){
            fragment = new FragmentPropertyTab();
            ((FragmentPropertyTab) fragment).setPropertyId(propertyId, newProperty);//Passing property id and property status to the
            // fragment. Must be a better way...
        }
        if(i==2){
            fragment = new FragmentTenantTab();
            ((FragmentTenantTab) fragment).setPropertyId(propertyId, newProperty);

        }
        if(i==3){
            fragment = new FragmentOwnerTab();
            ((FragmentOwnerTab) fragment).setPropertyId(propertyId);//Passing property id to the fragment
        }
        if(i==4){
            fragment = new FragmentReportTab();
        }

        
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;//static assignment
    }
//return active frag for title
    @Override
    public CharSequence getPageTitle(int position) {

        String returnString=null;
        if(position==0){
            returnString = "Transaction";
        }
        if(position==1){
            returnString = "Property";
        }
        if(position==2){
            returnString = "Tenant";
        }
        if(position==3){
            returnString = "Owner";
        }
        if(position==4){
            returnString = "Report";
        }
        return returnString;
    }


    //Simply reloads fragment...
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    //Sets the property id for any fragment
    public void setId(Long data, Boolean status){
        propertyId = data;
        newProperty = status;
    }



}