package com.example.sportsgo.sportsgo.Activities;

/**
 * Created by apple on 1/4/17.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.fragment.FavoriteListFragment;
import com.example.sportsgo.sportsgo.fragment.SearchViewFragment;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FavoriteList;
import com.example.sportsgo.sportsgo.utilities.RefreshService;
import com.example.sportsgo.sportsgo.fragment.CompleteViewFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.example.sportsgo.sportsgo.presenter.MainPresenter;
import com.example.sportsgo.sportsgo.view.MainView;


import com.example.sportsgo.sportsgo.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.example.sportsgo.sportsgo.model.User;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private String[] navTitles;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private String mActivityTitle;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private int MY_PERMISSION_ACCESS_FINE_LOCATION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_navigation);
        mTitle = mDrawerTitle = getTitle();
        mActivityTitle = getTitle().toString();
        navTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Log.d("Starts", "Main activity");
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(MyApp.getContext(),
                R.layout.drawer_list_item, navTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(presenter.getNewDrawerItemClickListener());
        setupDrawer();

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("mGoogleApiClient", "Created ");
        }

        if (savedInstanceState == null) {
            selectItem(0);
        }

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


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle connectionHint) {
        //check if location access permission has been granted by user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if not, promt to ask for permission
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        Log.d("mGoogleApiClient", "Location permission granted. ");

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mCurrentLocation == null) {
            Log.e("mGoogleApiClient", "onConnected: user location not found");
        }
        else {
            User.getInstance().setLocation(mCurrentLocation);
            Log.d("Main Activity", "Usr Lat = "+User.getInstance().getUserLocation().latitude);
        }

        LocationRequest mLocationRequest = new LocationRequest();
        //location update interval : 10 seconds
        mLocationRequest.setInterval(10*1000);
        mLocationRequest.setFastestInterval(5*000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onLocationChanged (Location location){
        User.getInstance().setLocation(location);
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
        if(position == 2){
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Logout");
            alert.setMessage("Are you sure to logout?");


            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do your work here
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    dialog.dismiss();
                    return;
                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();
            return;

        }
        Fragment fragment = null;
        if(position == 0){
            fragment = new SearchViewFragment();
        }
        else if(position == 1){
            fragment = new FavoriteListFragment();
        }

        Bundle args = new Bundle();
        args.putInt("ARG_INDEX_NUMBER", position);
        fragment.setArguments(args);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment, "Favorite")
                    .commit();
        }
        else{
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Main Activity", "Connection Failed");
    }
}
