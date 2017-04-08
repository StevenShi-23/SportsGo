package com.example.sportsgo.sportsgo.presenter;

import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.mSearchView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by apple on 4/4/17.
 */

public class SearchViewPresenter extends MvpBasePresenter<mSearchView> {
    private ListAdapter mAdapter;
    public SearchViewPresenter(){

    }
    public void getAdapter(ListAdapter mAdapter){
        this.mAdapter = mAdapter;
        mAdapter.addAll(FacilityList.getInstance().get_all_facilities());
    }


}
