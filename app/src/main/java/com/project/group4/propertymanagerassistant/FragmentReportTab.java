package com.project.group4.propertymanagerassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.InputType;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.PropertyTransaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentReportTab extends Fragment {



    static final String DATE_FORMAT="yyyy-MM-dd";

    long propertyId;


    EditText startDate = null;
    EditText endDate = null;
     TextView categorty = null;

    TextView payee = null;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat(FragmentReportTab.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();


        //Build start date field and populate
        startDate = (EditText) rootView.findViewById(R.id.startDateText);
        startDate.setText(  dateFormat.format(cal.getTime()) );
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialogStartDate(v);
            }
        });
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDatePickerDialogStartDate(v);
                }
            }
        });

        //Build end date field and populate
        endDate = (EditText) rootView.findViewById(R.id.endDateText);
        endDate.setText(dateFormat.format(cal.getTime()));
        endDate.setInputType(InputType.TYPE_NULL);
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialogEndDate(v);
            }
        });
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDatePickerDialogEndDate(v);
                }
            }
        });

        /**
         * Build category spinner
         */

        categorty = (TextView) rootView.findViewById(R.id.categoryHolder);

        Cursor cursor = DatabaseHandler.
                getInstance(getActivity()).
                getAllPropertyTransactionWithUniqOnSearchToken(getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID),
                        PropertyTransaction.COL_CATEGORY);

        //Had to transfer to arraylist to add "None" element
        final ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("None");
        if(cursor != null) {
            while (cursor.moveToNext()) {

                categoryList.add(cursor.getString(cursor.getColumnIndex( PropertyTransaction.COL_CATEGORY )));
            }

        }

        Spinner categorySpinner = (Spinner)rootView.findViewById(R.id.categorySpinner);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                categoryList );
        categorySpinner.setAdapter(arrayAdapter);
//        categorySpinner.setOnItemSelectedListener(test);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorty.setText( parent.getItemAtPosition(position).toString() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categorty.setText( parent.getItemAtPosition(0).toString() );            }

        });

        /**
         * End of category builder
         */


        /**
         * Build payee spinner
         */

        payee = (TextView) rootView.findViewById(R.id.payeeHolder);

        cursor = DatabaseHandler.
                getInstance(getActivity()).
                getAllPropertyTransactionWithUniqOnSearchToken(getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID),
                        PropertyTransaction.COL_PAYEE);

        //Had to transfer to arraylist to add "None" element
        final ArrayList<String> payeeList = new ArrayList<String>();
        payeeList.add("None");
        if(cursor != null) {
            while (cursor.moveToNext()) {

                payeeList.add(cursor.getString(cursor.getColumnIndex( PropertyTransaction.COL_PAYEE )));
            }

        }

        Spinner payeeSpinner = (Spinner)rootView.findViewById(R.id.payeeSpinner);
        final ArrayAdapter<String> payeeArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                payeeList );
        payeeSpinner.setAdapter(payeeArrayAdapter);
//        categorySpinner.setOnItemSelectedListener(test);
        payeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payee.setText( parent.getItemAtPosition(position).toString() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                payee.setText( parent.getItemAtPosition(0).toString() );            }

        });
        /**
         * End of payee builder
         */
        return rootView;
    }


//    AdapterView.OnItemSelectedListener test  = new AdapterView.OnItemSelectedListener() {
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            categorty.setText(  parent.getItemAtPosition(position).toString() );
//
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            categorty.setText(  "null" );
//        }
//
//
//
//    };



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

                ListView reportList = (ListView) getView().findViewById(R.id.reportListView);

//            DialogFragment test = new GetPropertyTransactionTypes();
//            test.setArguments(getArguments());
//            test.setTargetFragment(this, 0);
//            test.show(getChildFragmentManager(),"test");


                String query = queryStringBuilder(startDate.getText().toString(), endDate.getText().toString(),
                        categorty.getText().toString(), payee.getText().toString());


                SQLiteDatabase db = DatabaseHandler.
                        getInstance(getActivity()).getReadableDatabase();
                Cursor curs = db.rawQuery(query, null);

                String[] columns = new String[]{
                        PropertyTransaction.COL_DATE,
                        PropertyTransaction.COL_CATEGORY,
                        PropertyTransaction.COL_PAYEE,
                        PropertyTransaction.COL_AMOUNT,
                        PropertyTransaction.COL_NOTE
                };
                // the XML defined views which the data will be bound to
                int[] to = new int[]{
                        R.id.date,
                        R.id.category,
                        R.id.payee,
                        R.id.amount,
                        R.id.note
                };

                SimpleCursorAdapter newer = new SimpleCursorAdapter(getActivity(), R.layout.property_transaction_info, curs,
                        columns,//Items that go into list
                        to, 0);//type of var that item is
                reportList.setAdapter(newer);

            }


        return super.onOptionsItemSelected(item);
    }


    private String queryStringBuilder(String start, String end, String cat, String payee)  {
        String query = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date startDate = dateFormat.parse(start);
            start = dateFormat.format(startDate);
        }catch (Exception e1){
            return null;
        }

        try{
            Date endDate = dateFormat.parse(end);
            end = dateFormat.format(endDate);
        }catch(Exception e2){
            return null;
        }






        if(cat.equalsIgnoreCase("none")){
            if(payee.equalsIgnoreCase("none")){//All fields not present
                query = "select * FROM " + PropertyTransaction.TABLE_NAME +
                        " WHERE " + PropertyTransaction.COL_PROPERTY + " = " +
                        propertyId + " AND " + PropertyTransaction.COL_DATE + " BETWEEN " +
                        "\"" + start + "\"" + " AND " + "\"" +  end + "\"" + ";";
            }
            else{//payee present, category is not present
                query = "select * FROM " + PropertyTransaction.TABLE_NAME +
                        " WHERE " + PropertyTransaction.COL_PROPERTY + " = " +
                        propertyId + " AND " + PropertyTransaction.COL_PAYEE + " = " +
                        "\"" + payee + "\"" +
                        " AND " + PropertyTransaction.COL_DATE + " BETWEEN " +
                        "\"" + start + "\"" + " AND " + "\"" +  end + "\"" + ";";
            }
        }
        else{
            if(payee.equalsIgnoreCase("none")){//Category present, but no payee
                query = "select * FROM " + PropertyTransaction.TABLE_NAME +
                        " WHERE " + PropertyTransaction.COL_PROPERTY + " = " +
                        propertyId + " AND " + PropertyTransaction.COL_CATEGORY + " = "
                        + "\"" + cat + "\"" +
                        " AND " + PropertyTransaction.COL_DATE + " BETWEEN " +
                        "\"" + start + "\"" + " AND " + "\"" +  end + "\"" + ";";
            }
            else{//category is present and payee is present
                query = "select * FROM " + PropertyTransaction.TABLE_NAME +
                        " WHERE " + PropertyTransaction.COL_PROPERTY + " = " +
                        propertyId + " AND " + PropertyTransaction.COL_CATEGORY + " = " +
                        "\"" + cat + "\"" +
                        " AND " + PropertyTransaction.COL_PAYEE + " = " +
                        "\"" + payee + "\"" +  " AND " +
                        PropertyTransaction.COL_DATE + " BETWEEN " +
                        "\"" + start + "\"" + " AND " + "\"" +  end + "\"" + ";";
            }
        }

        return query;
    }


    /**
     * crap
     */



    public Dialog onCreateDialogPayeeSelector(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Cursor cursor = DatabaseHandler.
                getInstance(getActivity()).
                getAllPropertyTransactionWithUniqOnSearchToken(getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID),
                        PropertyTransaction.COL_PAYEE);

        //Had to transfer to arraylist to add "None" element
        final ArrayList<String> payeeList = new ArrayList<String>();
        if(cursor != null) {
            while (cursor.moveToNext()) {

                payeeList.add(cursor.getString(cursor.getColumnIndex( PropertyTransaction.COL_PAYEE )));
            }
            payeeList.add("None");
        }
        else{
            payeeList.add("No payees listed");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                payeeList );
        builder.setTitle("Just a test");
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                //payee = payeeList.get(which);
            }
        });
        return builder.create();
    }


    public Dialog onCreateDialogCategorySelector(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Cursor cursor = DatabaseHandler.
                getInstance(getActivity()).
                getAllPropertyTransactionWithUniqOnSearchToken(getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID),
                        PropertyTransaction.COL_CATEGORY);

        //Had to transfer to arraylist to add "None" element
        final ArrayList<String> categoryList = new ArrayList<String>();
        if(cursor != null) {
            while (cursor.moveToNext()) {

                categoryList.add(cursor.getString(cursor.getColumnIndex( PropertyTransaction.COL_CATEGORY )));
            }
            categoryList.add("None");
        }
        else{
            categoryList.add("No category listed");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                categoryList );
        builder.setTitle("Select A Category");
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //catergory = categoryList.get(which);
                // The 'which' argument contains the index position
                // of the selected item

            }
        });
        return builder.create();
    }

    /**
     * crap
     */











    // showDatePickerDialog ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private void showDatePickerDialogStartDate( View view ) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog( getActivity(), startDateSetListener, year, month, day );
        dpd.setTitle("Select Start Date");
        dpd.show();

    } // showDatePickerDialog ----------------------------------------------------


    // OnDateSetListener +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override public void onDateSet( DatePicker view, int year, int month, int day ) {
            month++;
            startDate.setText( dateFormater(year, month, day ) );
        } // onDateSet
    }; // OnDateSetListener ------------------------------------------------------

    // showDatePickerDialog ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private void showDatePickerDialogEndDate( View view ) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog( getActivity(), endDateSetListener, year, month, day );
        dpd.setTitle("Select End Date");
        dpd.show();

    } // showDatePickerDialog ----------------------------------------------------


    // OnDateSetListener +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override public void onDateSet( DatePicker view, int year, int month, int day ) {
            month++;
            endDate.setText( dateFormater(year, month, day ) );
        } // onDateSet
    }; // OnDateSetListener ------------------------------------------------------


    private String dateFormater(int year, int month, int day){
        String date = year + "-" + month + "-" + day;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date startDate = dateFormat.parse(date);
            date = dateFormat.format(startDate);
        }catch (Exception e1){
            return null;
        }

        return date;

    }



}






//    /**
//     * Saving for adaptor example!!
//     */
//    public static class GetPropertyTransactionTypes extends DialogFragment {
//
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//            Cursor cursor = DatabaseHandler.
//                    getInstance(getActivity()).
//                    getAllPropertyTransactionWithUniqOnSearchToken(getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID),
//                            PropertyTransaction.COL_CATEGORY );
//
//
//
////Works great, but need to add element null
//            SimpleCursorAdapter newer = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, cursor,
//                    new String[]{ PropertyTransaction.COL_PAYEE },//Items that go into list
//                    new int[]{android.R.id.text1}, 0);//type of var that item is
//                        builder.setTitle("Select Transaction Type Filter");
//
//                        builder.setAdapter(newer, new DialogInterface.OnClickListener() {
//
//
//                public void onClick(DialogInterface dialog, int which) {
//
//                    //TODO: Add the onclick function
//                    // The 'which' argument contains the index position
//                    // of the selected item
//                }
//            });
//
//            return builder.create();
//        }
//
//
//    }




