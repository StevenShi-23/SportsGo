package com.example.sportsgo.sportsgo.presenter;

import android.util.Log;

import com.example.sportsgo.sportsgo.model.Facility;
import com.example.sportsgo.sportsgo.model.FacilityList;
import com.example.sportsgo.sportsgo.utilities.ExactSearch;
import com.example.sportsgo.sportsgo.utilities.SortStrategy;
import com.example.sportsgo.sportsgo.utilities.filterContext;
import com.example.sportsgo.sportsgo.utilities.ListAdapter;
import com.example.sportsgo.sportsgo.view.mSearchView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by apple on 4/4/17.
 */

public class SearchViewPresenter extends MvpBasePresenter<mSearchView> {
    private ListAdapter mAdapter;
    private String sort_option = "", filter_option = "", search_keywords = "";

    private String DEFAULT_KEYWORD = "all facilities";
    private filterContext myFilter = new filterContext();
    // filter creataria
    private String DEFAULT_CRETARIA = "";
    //sort option
    private String DEFAULT_OPTION = "";

    public SearchViewPresenter(){

    }
    public void getAdapter(ListAdapter mAdapter){
        this.mAdapter = mAdapter;
        mAdapter.addAll(FacilityList.getInstance().get_all_facilities());
    }
    public void update(String opt, String field){
        if(opt == "sort_option" && field != sort_option){
            //sort option should be either "by distance" or "popularity"
            this.sort_option = field;
        }
        else if(opt == "filter_option" && field != filter_option){
            //filter should be wither "water" or "dry"
            this.filter_option = field;
        }
        else if(opt == "search_keywords" && field.toLowerCase() != search_keywords){
            this.search_keywords = field.toLowerCase();
        }
        else{
            Log.d("SearchViewPresenter:", "invalid update opt");
            return;
        }
        perform_search();
    }
    private void perform_search(){
        List<Facility> facilities = FacilityList.getInstance().get_all_facilities();
        facilities = ExactSearch.exact_search(search_keywords, facilities, DEFAULT_KEYWORD);
        //TODO filter strategy && sort strategy
        facilities = myFilter.setFilterByStrategy(filter_option, facilities, DEFAULT_KEYWORD);

        mAdapter.clear();
        mAdapter.addAll(facilities);
    }
}
