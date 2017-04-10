package com.example.sportsgo.sportsgo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.*;
import android.content.*;

import com.example.sportsgo.sportsgo.Activities.MainActivity;
import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.model.FavoriteList;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.presenter.CompleteViewPresenter;
import com.example.sportsgo.sportsgo.view.CompleteView1;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

/**
 * Created by apple on 6/4/17.
 */

public class CompleteViewFragment extends MvpFragment<CompleteView1, CompleteViewPresenter> implements CompleteView1, OnMapReadyCallback {
    //private Facility
    private MapView mapView;
    private GoogleMap mMap;
    private Facility mfacility;

    TextView FacName, FacName2, Descript, weather_status, PSI, temperature;
    ImageButton Back;
    ImageButton Call;
    ImageButton Add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        Log.d("Start","Here");
        //setContentView(R.layout.activity_complete_view);
        View view = inflater.inflate(R.layout.activity_complete_view, null);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        FacName = (TextView) view.findViewById(R.id.FacName);
        FacName.setText(mfacility.facilityName);
        Descript = (TextView) view.findViewById(R.id.Descript);
        Descript.setText(mfacility.facilityDescription);
        temperature = (TextView) view.findViewById(R.id.Temperature);
        temperature.setText("Temperature: " + mfacility.temperature);
        PSI = (TextView) view.findViewById(R.id.PSI);
        PSI.setText("PSI index: " + mfacility.PSI);
        weather_status = (TextView) view.findViewById(R.id.weather_status);
        weather_status.setText("Weather status: " + mfacility.weather_status);

        Back = (ImageButton) view.findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Call = (ImageButton) view.findViewById(R.id.Call);
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:93715689"));
                startActivity(callIntent);
            }
        });

        Add = (ImageButton) view.findViewById(R.id.Add);
        if(FavoriteList.getInstance().inFavoriteList(mfacility)){
            Add.setImageResource(R.drawable.heart_check);
        }
        else Add.setImageResource(R.drawable.heart_uncheck);

        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(FavoriteList.getInstance().inFavoriteList(mfacility)==true) {
                    FavoriteList.getInstance().removeFromFavoriteList(mfacility);
                    Add.setImageResource(R.drawable.heart_uncheck);

                }
                else{
                    FavoriteList.getInstance().addToFavoriteList(mfacility);
                    Add.setImageResource(R.drawable.heart_check);
                }
            }
        });

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstance);
        mapView.onResume();
        mapView.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near flocation, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in flocation and move the camera
        LatLng flocation = new LatLng(mfacility.latitude, mfacility.longitude);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(flocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(flocation,17));
        boolean b = true;
        mMap.addMarker(new MarkerOptions().position(flocation).title(mfacility.facilityName));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public CompleteViewPresenter createPresenter(){return new CompleteViewPresenter();}

    @Override
    public void setFacility(Facility facility){
        mfacility = facility;
    }

}
