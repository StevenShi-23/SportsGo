package com.example.sportsgo.sportsgo.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sportsgo.sportsgo.MyApp;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.presenter.BriefViewPresenter;
import com.example.sportsgo.sportsgo.view.BriefView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

/**
 * Created by StevenShi on 22/3/17.
 */

public class BriefViewFragment extends MvpFragment<BriefView, BriefViewPresenter> implements BriefView {
    private EditText mSearchBoxEditText;
    private ListView mList;
    private ArrayAdapter mAdapter;
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
        mAdapter = new ArrayAdapter<Facility>(MyApp.getContext(),
                R.layout.drawer_list_item);
        mList = (ListView) getView().findViewById(R.id.brief_list);
        presenter.getAdapter(mAdapter);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_field, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((MainActivity)getActivity()).getSupportActionBar().getThemedContext());
        //item.expandActionView();
            //sv.setIconified(false);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setActionView(item, sv);
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        //searchView.setIconified(false);
        //searchView.clearFocus();

        //MenuItemCompat.expandActionView(item);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
    }
    @Override
    public BriefViewPresenter createPresenter(){
        return new BriefViewPresenter();
    }

}
