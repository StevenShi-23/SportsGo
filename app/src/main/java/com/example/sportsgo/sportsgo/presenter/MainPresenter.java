package com.example.sportsgo.sportsgo.presenter;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.example.sportsgo.sportsgo.view.MainView;

import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.widget.AdapterView;
import com.example.sportsgo.sportsgo.R;
/**
 * Created by apple on 1/4/17.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {

    public MainPresenter() {

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getView().selectItem(position);
        }
    }
    public DrawerItemClickListener getNewDrawerItemClickListener(){
        return new DrawerItemClickListener();
    }
}
