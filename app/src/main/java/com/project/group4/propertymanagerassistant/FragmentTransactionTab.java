package com.project.group4.propertymanagerassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.PropertyTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentTransactionTab extends Fragment  {

    private long propertyId;
    private SimpleCursorAdapter dataAdapter;
private boolean newTransaction;//MAY NOT NEED
private String inputText;

private long selectedId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
Log.d("FragTrans", "onCreate");
        setHasOptionsMenu(true);
        if (savedInstanceState!=null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
        }

    }

    @Override
    public void onDestroy() {
        Log.d("FragTrans", "onDestroy");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_transaction_tab, container, false);
        Cursor cursor = DatabaseHandler.getInstance(getActivity()).getAllPropertyTransaction(propertyId);
        registerForContextMenu(rootView);
        // The desired columns to be bound
        String[] columns = new String[] {
                PropertyTransaction.COL_DATE,
                PropertyTransaction.COL_CATEGORY,
                PropertyTransaction.COL_PAYEE,
                PropertyTransaction.COL_AMOUNT,
                PropertyTransaction.COL_NOTE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.date,
                R.id.category,
                R.id.payee,
                R.id.amount,
                R.id.note
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this.getActivity(), R.layout.property_transaction_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) rootView.findViewById( R.id.listHere );
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


/*****/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                // Puts item selected to top of view, if possible
                listView.setSelection(position);



                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);


                // Get the payee's name from this row in the database.
                String payee =
                        cursor.getString(cursor.getColumnIndexOrThrow(PropertyTransaction.COL_PAYEE));


                Toast.makeText(getActivity().getBaseContext(),
                        payee + " has been selected", Toast.LENGTH_SHORT).show();

                selectedId = cursor.getLong(cursor.getColumnIndexOrThrow(PropertyTransaction.COL_ID));



            }
       });

////Searchable not working......
        EditText myFilter = (EditText) rootView.findViewById( R.id.myFilter );


        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                 dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {

                return DatabaseHandler.getInstance(getActivity()).fetchPayeeByName(constraint.toString(), propertyId);


            }
        });

        return rootView;
    }




    @Override
    public void   onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for(int i = 1; i <= PropertyDetailFragment.ITEM_COUNT; i++){
            menu.removeItem(i);
        }

        menu.add((int)propertyId, 1, 1, "Edit");//Uho!! How do we do this??
        menu.add((int)propertyId, 2, 2, "New");
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Edit item
         */
        if(item.getItemId()==1 && item.getGroupId()==(int)propertyId) {
            if (selectedId == -1) {
                //Message user to select a row before edit
                Toast.makeText(getActivity().getBaseContext(),
                        "Please select row to edit first."
                        , Toast.LENGTH_SHORT).show();
            } else {

                final PropertyTransaction newTransaction  = DatabaseHandler.getInstance(getActivity()).getSinglePropertyTransaction(propertyId, selectedId);

                if (newTransaction != null) {


                    /****/
                    /**
                     * Editing transaction via a AlertDialog
                     */
                    //Alert Dialog Layout
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    //Alert Dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Enter New Transaction");

                    final EditText date = new EditText(getActivity());
                    date.setHint("Enter Date");
                    date.setText(newTransaction.date);
                    date.setInputType(InputType.TYPE_CLASS_DATETIME);
                    layout.addView(date);

                    final EditText category = new EditText(getActivity());
                    category.setHint("Enter Category");
                    category.setText(newTransaction.category);
                    category.setInputType(InputType.TYPE_CLASS_TEXT);
                    layout.addView(category);

                    final EditText payee = new EditText(getActivity());
                    payee.setHint("Enter Payee");
                    payee.setText(newTransaction.payee);
                    payee.setInputType(InputType.TYPE_CLASS_TEXT);
                    layout.addView(payee);

                    final EditText amount = new EditText(getActivity());
                    amount.setHint("Enter Amount");
                    amount.setText(newTransaction.amount);
                    amount.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout.addView(amount);

                    final EditText note = new EditText(getActivity());
                    note.setHint("Enter Notes");
                    note.setText(newTransaction.note);
                    note.setInputType(InputType.TYPE_CLASS_TEXT);
                    layout.addView(note);

                    //Set up save button
                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Build edits

//                            newTransaction.id = selectedCursor.getLong(selectedCursor.getColumnIndexOrThrow(PropertyTransaction.COL_ID));
//                            newTransaction.property = selectedCursor.getLong(selectedCursor.getColumnIndexOrThrow(PropertyTransaction.COL_PROPERTY));
                            //isValidDate will return null if given formatt is bad
                            newTransaction.date =  isValidDate( date.getText().toString() );
//                            newTransaction.category = category.getText().toString();
//                            newTransaction.payee = payee.getText().toString();
//                            newTransaction.amount = amount.getText().toString();
//                            newTransaction.note = note.getText().toString();


                            //Verify date input
                            if(  newTransaction.date  != null ){


                                //Update db row
                                if (DatabaseHandler.getInstance(getActivity()).updatePropertyTransaction(newTransaction)) {
                                    //Message success
                                    Toast.makeText(getActivity().getBaseContext(),
                                            newTransaction.date + " " + newTransaction.payee + " has been updated"
                                            , Toast.LENGTH_SHORT).show();
                                    //Update database and list view
                                    updateDatabaseAndList();
                                }
                                //Message un-successfull
                                else {
                                    Toast.makeText(getActivity().getBaseContext(),
                                            newTransaction.date + " " + newTransaction.payee + " has failed to updated"
                                            , Toast.LENGTH_SHORT).show();
                                }
                                dialog.cancel();


                            }
                            else
                            {
                                Toast.makeText(getActivity().getBaseContext(),
                                        newTransaction.date + " needs to be in "+ FragmentReportTab.DATE_FORMAT + " format"
                                        , Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                    //Set up cancel button
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setView(layout);
                    builder.show();

                } else {
                    /****/
                    Log.d("FragmentTransactionTab", "DatabaseQueryFail");

                }


            }
        }
         else {
            if (item.getItemId() == 2 &&
                            item.getGroupId() == (int) propertyId) {//New transaction...
/**
 * Adding transaction via a AlertDialog, should have built xml by now!
 */
                //Alert Dialog Layout
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                //Alert Dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter New Transaction");

                final EditText date = new EditText(getActivity());
                date.setHint("Enter Date");
                date.setInputType(InputType.TYPE_CLASS_DATETIME);
                layout.addView(date);

                final EditText category = new EditText(getActivity());
                category.setHint("Enter Category");
                category.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(category);

                final EditText payee = new EditText(getActivity());
                payee.setHint("Enter Payee");
                payee.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(payee);

                final EditText amount = new EditText(getActivity());
                amount.setHint("Enter Amount");
                amount.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(amount);

                final EditText note = new EditText(getActivity());
                note.setHint("Enter Notes");
                note.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(note);

                //Set up save button
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PropertyTransaction newTransaction = new PropertyTransaction();
                        newTransaction.property = propertyId;
                        newTransaction.date = date.getText().toString();
                        newTransaction.category = category.getText().toString();
                        newTransaction.payee = payee.getText().toString();
                        newTransaction.amount = amount.getText().toString();
                        newTransaction.note = note.getText().toString();
//Error check for date
                        if( isValidDate( date.getText().toString() ) == null) {
                            Toast.makeText(getActivity().getBaseContext(),
                                    newTransaction.date + " needs to be in "+ FragmentReportTab.DATE_FORMAT +" format"
                                    , Toast.LENGTH_LONG).show();
                            return;
                        }

                        //if() databasetransaction not null, go for it..
                        if (DatabaseHandler.getInstance(getActivity()).putPropertyTransaction(newTransaction)) {
                            //Message item was entered into database
                            Toast.makeText(getActivity().getBaseContext(),
                                    newTransaction.date + " " + newTransaction.payee + " added.", Toast.LENGTH_SHORT).show();
                            //Update database
                            updateDatabaseAndList();

                        } else {
                            //Message that item was not entered into database
                            Toast.makeText(getActivity().getBaseContext(),
                                    newTransaction.date + " " + newTransaction.payee
                                            + " failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();


                    }
                });
                //Set up cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setView(layout);
                builder.show();

            } else {
                Log.d("FragmentTransactionTab", "Not defined menu item: " + item.getItemId() + " group: " + item.getGroupId());
            }
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
    }

    private void updateDatabaseAndList(){
        //Update database
        Cursor cursor = DatabaseHandler.getInstance(getActivity()).getAllPropertyTransaction(propertyId);
        //Refresh list view
        ListView list = (ListView) getActivity().findViewById(R.id.listHere);
        SimpleCursorAdapter newer = (SimpleCursorAdapter) list.getAdapter();
        newer.changeCursor(cursor);
    }


    /**
     * Vaidate date. Needed for reports.
     * @param
     * @return
     */
    private String isValidDate(String date) {
        SimpleDateFormat df1 = new SimpleDateFormat(FragmentReportTab.DATE_FORMAT);
        Date d = null;
        String s = null;
        try {
           d = df1.parse(date);


        }catch (Exception e1) {

            return null;
        }

        s = df1.format(d);
//        if(date.equals(s))
            return s;
//        else
//            return null;
    }




}
