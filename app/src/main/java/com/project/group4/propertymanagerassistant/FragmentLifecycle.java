package com.project.group4.propertymanagerassistant;

/**
 *  This is to handle swipes between the tabs. I used it to reload the fragment, which writes to the database.
 *  I left it in here in case there is a need to do something else with it...
 */
public interface FragmentLifecycle {
    public void onPauseFragment();
    public void onResumeFragment();

}
