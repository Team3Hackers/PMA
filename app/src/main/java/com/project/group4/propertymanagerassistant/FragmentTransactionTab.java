package com.project.group4.propertymanagerassistant;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

import com.project.group4.propertymanagerassistant.database.DatabaseHandler;
import com.project.group4.propertymanagerassistant.database.PropertyTransaction;

/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentTransactionTab extends Fragment {

    private long propertyId;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState!=null){
            propertyId = savedInstanceState.getLong(PropertyDetailFragment.ARG_ITEM_ID);
        }
        else{
            propertyId = getArguments().getLong(PropertyDetailFragment.ARG_ITEM_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_transaction_tab, container, false);
        Cursor cursor = DatabaseHandler.getInstance(getActivity()).getPropertyTransaction(propertyId);

        // The desired columns to be bound
        String[] columns = new String[] {
                PropertyTransaction.COL_DATE,
                PropertyTransaction.COL_CATEGORY,
                PropertyTransaction.COL_PAYEE,
                PropertyTransaction.COL_AMOUNT,
                PropertyTransaction.COL_NOTE
//                CountriesDbAdapter.KEY_REGION
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

/*****/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the payee's name from this row in the database.
                String payee =
                        cursor.getString(cursor.getColumnIndexOrThrow(PropertyTransaction.COL_PAYEE));


                Toast.makeText(getActivity().getBaseContext(),
                        payee, Toast.LENGTH_SHORT).show();

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
            {dataAdapter.getFilter().filter(s.toString());}
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return DatabaseHandler.getInstance(getActivity()).fetchPayeeByName(constraint.toString(), propertyId);
            }
        });

        return rootView;
    }

    //Called because we have call back in PropertyDetailFragment
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for(int i = 1; i <= 10; i++){
            menu.removeItem(i);
        }

        menu.add(menu.NONE, 1, 1, "Edit");//Uho!! How do we do this??
        menu.add(menu.NONE, 2, 2, "New");
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("FragmentTransactionsTab", "onOptionsItemSelected");
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PropertyDetailFragment.ARG_ITEM_ID, propertyId);
    }

}
