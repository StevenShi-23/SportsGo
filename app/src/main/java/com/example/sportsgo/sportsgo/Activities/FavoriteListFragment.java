package com.example.sportsgo.sportsgo.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.presenter.BriefViewPresenter;
import com.example.sportsgo.sportsgo.presenter.FavoriteListPresenter;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.BriefView;
import com.example.sportsgo.sportsgo.view.FavoriteListView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;

/**
 * Created by NghiaNguyen on 4/8/2017.
 */

public class FavoriteListFragment extends MvpFragment<FavoriteListView, FavoriteListPresenter> implements FavoriteListView {
    private ListView mListView;
    private ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.brief_layout, null);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        ArrayList<Facility> arrayOfFacilities = new ArrayList<Facility>();
        mAdapter = new ListAdapter(MyApp.getContext(), arrayOfFacilities);
        mListView = (ListView) getView().findViewById(R.id.brief_list);
        mListView.setAdapter(mAdapter);
        presenter.getAdapter(mAdapter);
        mListView.setOnItemClickListener(new FavoriteListFragment.ListItemClickListener());
    }
    @Override
    public FavoriteListPresenter createPresenter(){
        return new FavoriteListPresenter();
    }

    public class ListItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Facility facility = mAdapter.getItem(position);
            Log.d("OnItemClick","In FavoriteListFragment");
            ((MainActivity)getActivity()).enterCompleteView(facility);
        }
    }
}
