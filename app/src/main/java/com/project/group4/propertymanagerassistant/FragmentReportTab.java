package com.project.group4.propertymanagerassistant;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentReportTab extends Fragment /*implements  FragmentLifecycle*/ {

    long propertyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState == null) {
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
        } else {
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_tab, container, false);
        //Get object
//        Spinner spinner = (Spinner) rootView.findViewById(R.id.report_selector_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                                                              R.array.planets_array ,
//                                                              android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Log.d("ReportTab", ""+adapter+ " :: "+ spinner);
//        spinner.setAdapter(adapter);


        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        for (int i = 1; i <= 10; i++) {
            menu.removeItem(i);
        }
        menu.add((int) propertyId, 10, 10, "Report Filter");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 10 && item.getGroupId() == (int) propertyId) {

//Last display
            DialogFragment tranactionPayeeDialog = new GetPropertyTransactionPayee();
            tranactionPayeeDialog.show(getActivity().getSupportFragmentManager(), "payeeSelector");

            DialogFragment tranactionTypeDialog = new GetPropertyTransactionTypes();
            tranactionTypeDialog.show(getActivity().getSupportFragmentManager(), "typeSelector");

            DialogFragment endDateDialog = new EndDatePickerFragment();
            endDateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");

            DialogFragment startDateDialog = new StartDatePickerFragment();
            startDateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
//End Display, perform query

//Display results in fragment


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Select payee alert dialog
     */
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Select Payee")
//                .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//                    }
//                });
//        return builder.create();
//    }

    /**
     * Dialog for retriveng type filter
     */
        public static class GetPropertyTransactionTypes extends DialogFragment {


        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Transaction Type Filter")
                    .setItems(new String[]{"One", "Two","One", "Two","One", "Two","One", "Two","One", "Two","One", "Two","One", "Two" }, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                        }
                    });
            return builder.create();
        }
    }

    /**
     * Dialog for getting payee filter
     */

    public static class GetPropertyTransactionPayee extends DialogFragment {


        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Transaction Payee Filter")
                    .setItems(new String[]{"One", "Two","One", "Two","One", "Two","One", "Two","One", "Two","One", "Two","One", "Two" }, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                        }
                    });
            return builder.create();
        }
    }

    /**
     *  Date picker inner class
     *              public void showDatePickerDialog(View v) {
     DialogFragment newFragment = new DatePickerFragment();
     newFragment.show(getActivity().getSupportFragmentManager() , "datePicker");
     }
     */
    public static class StartDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dp = new DatePickerDialog(getActivity(), this, year, month, day);
            dp.setTitle("Select Start Date");
            return dp;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String test = Integer.toString(year)+"/"+Integer.toString(month)+"/"+Integer.toString(day);
            Log.d("TEST", "Start date: "+test);
        }
    }
    /**
     * End date
     * 
     */
    public static class EndDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dp = new DatePickerDialog(getActivity(), this, year, month, day);
            dp.setTitle("Select End Date");
            return dp;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String test = Integer.toString(year)+"/"+Integer.toString(month)+"/"+Integer.toString(day);
            Log.d("TEST", "End date: "+test);
        }
    }





}

