package com.example.sportsgo.sportsgo.Activities;

/**
 * Created by apple on 1/4/17.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.utilities.RefreshService;
import com.example.sportsgo.sportsgo.Activities.CompleteViewFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.example.sportsgo.sportsgo.presenter.MainPresenter;
import com.example.sportsgo.sportsgo.view.MainView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.sportsgo.sportsgo.R;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {
    private String[] navTitles;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private String mActivityTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_navigation);
        mTitle = mDrawerTitle = getTitle();
        mActivityTitle = getTitle().toString();
        navTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Log.d("Starts","Main activity");
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(MyApp.getContext(),
                R.layout.drawer_list_item, navTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(presenter.getNewDrawerItemClickListener());
        setupDrawer();
        startService(new Intent(MyApp.getContext(), RefreshService.class));
        //if (savedInstanceState == null) {
        //    selectItem(0);
        //}
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("SportsGo Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * Create the presenter corresponding to this activity class
     *
     * @return Constructed presenter
     */
    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new BriefViewFragment();;
        Bundle args = new Bundle();
        args.putInt("ARG_INDEX_NUMBER", position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        //getSupportActionBar().setTitle(navTitles[position]);
        mActivityTitle = navTitles[position];
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(navTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void enterCompleteView(Facility facility){
        CompleteViewFragment completev = new CompleteViewFragment();
        completev.setFacility(facility);
        Bundle args = new Bundle();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, completev)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //getActionBar().setTitle(mTitle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
}
