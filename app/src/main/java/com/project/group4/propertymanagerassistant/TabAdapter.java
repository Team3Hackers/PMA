package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;


class TabAdapter extends FragmentStatePagerAdapter {//to save state, use statepageradptapter
                                                //will call savestate when scroll two fragments away, and it will detach, which destroys
                                                //usefull when you wan to save state



    //MY LOGGING ID STRING
    private static final String TAG = "TabAdaptor";

    //Set current object propertyId
    Long propertyId;
    Boolean newProperty;

    //
    Bundle bundle;


    //base constructor
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }


    public void SetBundle( Bundle args) {
        this.bundle = args;
    }

////This show the fragmentent count spiking, but thats because we dont rid the old view pager..
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Log.d("CRAP!", ""+container.getChildCount() + " "+ position);
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        Log.d("CRAP!", "destroy here");
//    }

    //return current fragment
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
           
                fragment = new FragmentTransactionTab();

//                fragment = new FragmentReportTab();

            break;
        case 1:
           fragment = new FragmentPropertyTab();// <-- problem with multiple views?? not sure how to fix, it is in the new fragment call
//          fragment = new FragmentReportTab();

//            ((FragmentPropertyTab) fragment).setPropertyId(propertyId, newProperty);//Passing property id and property status to the
            break;
        case 2://Tenant Tab

            //This is the root view to be replaced by other fragments.
            fragment = new FragmentTenantRoot();//<--problem with blanking after roation//HERRE
//            fragment = new FragmentReportTab();

//            ((FragmentTenantRoot) fragment).setPropertyArgs(propertyId, newProperty, FragmentTenantTab.FRAGMENT_TYPE );
                //fragment = new FragmentTenantTab();
                //((FragmentTenantTab) fragment).setPropertyId(propertyId, newProperty);
//            }
//            else
//            {
//                fragment = new StaticFragment();
//                break;
//            }

            break;

        case 3://Owner tab

            fragment = new FragmentOwnerRoot();
//            fragment = new FragmentReportTab();


//            ((FragmentOwnerRoot) fragment).setPropertyArgs(propertyId, newProperty, FragmentOwnerTab.FRAGMENT_TYPE);
//            fragment = new FragmentOwnerTab();
//            ((FragmentOwnerTab) fragment).setPropertyId(propertyId);//Passing property id to the fragment
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