package com.example.sportsgo.sportsgo.presenter;

import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.model.FavoriteList;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.FavoriteListView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by NghiaNguyen on 4/8/2017.
 */

public class FavoriteListPresenter extends MvpBasePresenter<FavoriteListView> {
    private ListAdapter mAdapter;
    public FavoriteListPresenter(){

    }
    public void getAdapter(ListAdapter mAdapter){
        this.mAdapter = mAdapter;
        mAdapter.addAll(FavoriteList.getInstance().getFavoriteList());
    }
}
