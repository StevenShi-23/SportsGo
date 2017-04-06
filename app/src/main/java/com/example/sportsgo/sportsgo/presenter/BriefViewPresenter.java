package com.example.sportsgo.sportsgo.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.BriefView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by apple on 4/4/17.
 */

public class BriefViewPresenter extends MvpBasePresenter<BriefView> {
    private ListAdapter mAdapter;
    public BriefViewPresenter(){

    }
    public void getAdapter(ListAdapter mAdapter){
        this.mAdapter = mAdapter;
        mAdapter.addAll(FacilityList.getInstance().get_all_facilities());
    }


}
