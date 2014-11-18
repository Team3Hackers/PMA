package com.project.group4.propertymanagerassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by benhoelzel on 10/26/14.
 */
public class FragmentTransactionTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_transaction_tab, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(2);
        menu.add(menu.NONE, 2, 2, "Edit");
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        if ( 1 == menu.size() ){//MUST BE IN 2 PANE MODE
//            menu.removeItem(2);
//            menu.add(menu.NONE, 2, 2, "Edit");
//        }
//        else{//IN SINGLE PANE MODE
//            menu.clear();
//            menu.add(menu.NONE, 2, 2, "Edit");
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("FragmentTransactionsTab", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }
}
