package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;


class TabAdapter extends FragmentStatePagerAdapter {

    //Set current object propertyId
    Long propertyId;
    Boolean newProperty;
    Bundle bundle;

    //base constructor
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void SetBundle( Bundle args) {
        this.bundle = args;
    }

    //return current fragment
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new FragmentTransactionTab();
                break;
            case 1:
                fragment = new FragmentPropertyTab();
                break;
            case 2://Tenant Tab
                //This is the root view to be replaced by other fragments.
                fragment = new FragmentTenantRoot();
                break;
            case 3://Owner tab
                fragment = new FragmentOwnerRoot();
                break;
            case 4:

                fragment = new FragmentReportTab();
                break;
            default:
                //Never goes here
                 fragment = new FragmentTenantPast();
                break;
        }

        fragment.setArguments(this.bundle);
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