package com.example.sportsgo.sportsgo.presenter;

import android.widget.ArrayAdapter;

import com.example.sportsgo.sportsgo.view.BriefView;
import com.example.sportsgo.sportsgo.view.MainView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by apple on 4/4/17.
 */

public class BriefViewPresenter extends MvpBasePresenter<BriefView> {
    private ArrayAdapter mAdapter;
    public BriefViewPresenter(){

    }
    public void getAdapter(ArrayAdapter mAdapter){
        this.mAdapter = mAdapter;

    }
}
