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
public class FragmentReportTab extends Fragment implements  FragmentLifecycle{

    private Menu thisMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_tab, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        menu.removeItem(2);
//        menu.removeItem(3);
//        menu.removeItem(4);
        for(int i = 1; i <= 10; i++){
            menu.removeItem(i);
        }
        menu.add(menu.NONE, 10, 10, "Edit");
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        if ( 1 == menu.size() ){//MUST BE IN 2 PANE MODE
//            menu.removeItem(2);
//            menu.add(menu.NONE, 2, 2, "Edit");
//        }
//        else{//IN SINGLE PANE MODE
//          //  menu.clear();
//            menu.add(menu.NONE, 2, 2, "Edit");
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("FragmentReportTab", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
