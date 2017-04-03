package com.example.sportsgo.sportsgo.view;

/**
 * Created by apple on 1/4/17.
 */
import com.google.android.gms.maps.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;
public interface MainView extends MvpView {

    public void selectItem(int position);
}
