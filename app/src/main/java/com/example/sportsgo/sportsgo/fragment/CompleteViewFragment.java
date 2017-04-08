package com.example.sportsgo.sportsgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    Button Back, Add;

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
        FacName2 = (TextView) view.findViewById(R.id.FacName2);
        FacName2.setText(mfacility.facilityName);
        Descript = (TextView) view.findViewById(R.id.Descript);
        Descript.setText(mfacility.facilityDescription);
        temperature = (TextView) view.findViewById(R.id.Temperature);
        temperature.setText("Temperature: " + mfacility.temperature);
        PSI = (TextView) view.findViewById(R.id.Temperature);
        PSI.setText("PSI index: " + mfacility.PSI);
        weather_status = (TextView) view.findViewById(R.id.weather_status);
        weather_status.setText("Weather status: " + mfacility.weather_status);

        Back = (Button) view.findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Add = (Button) view.findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FavoriteList.getInstance().addToFavoriteList(mfacility);
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
        mMap.addMarker(new MarkerOptions().position(flocation).title("Marker in flocation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(flocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public CompleteViewPresenter createPresenter(){return new CompleteViewPresenter();}

    @Override
    public void setFacility(Facility facility){
        mfacility = facility;
    }

}
