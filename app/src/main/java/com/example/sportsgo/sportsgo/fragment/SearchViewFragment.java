package com.example.sportsgo.sportsgo.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sportsgo.sportsgo.Activities.MainActivity;
import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.presenter.SearchViewPresenter;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.mSearchView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;

/**
 * Created by StevenShi on 22/3/17.
 */

public class SearchViewFragment extends MvpFragment<mSearchView, SearchViewPresenter> implements mSearchView {
    private EditText mSearchBoxEditText;
    private ListView mListView;
    private ListAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        //mSearchBoxEditText = (EditText) getView().findViewById(R.id.et_search_box);
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
        mListView.setOnItemClickListener(new ListItemClickListener());
    }
    private class ListItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Facility facility = mAdapter.getItem(position);
            Log.d("OnItemClick","In BriefViewFragment");
            ((MainActivity)getActivity()).enterCompleteView(facility);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_field, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((MainActivity)getActivity()).getSupportActionBar().getThemedContext());
        sv.setIconified(false);
        Resources res = getResources();
        String default_message = res.getString(R.string.search_default);
        sv.setQuery(default_message, false); // fill in the search term by default
        sv.clearFocus(); // close the keyboard on load
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setActionView(item, sv);

        //MenuItemCompat.expandActionView(item);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("search query submit","");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("tap","");
                return false;
            }
        });
    }
    @Override
    public SearchViewPresenter createPresenter(){
        return new SearchViewPresenter();
    }

}
