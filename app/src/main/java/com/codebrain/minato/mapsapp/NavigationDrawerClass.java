package com.codebrain.minato.mapsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.support.design.widget.NavigationView;
import android.widget.Toast;

/**
 * Created by username on 12/6/2017.
 */

public abstract class NavigationDrawerClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {//MenuItem.OnMenuItemClickListener{
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.navigation_drawer_activity);// The base layout that contains your navigation drawer.

        //set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }

        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        View headerLayout = navigation_view.getHeaderView(0);

        headerLayout.findViewById(R.id.navigationBT_Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        if (navigation_view != null)
        {
            navigation_view.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        try
        {
            if (view_stub != null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                View stubView = inflater.inflate(layoutResID, view_stub, false);
                view_stub.addView(stubView, lp);
            }
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.nav_marker:
                Toast.makeText(getApplicationContext(), "En desarrollo....", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_travel:
                Intent intent = new Intent(getApplicationContext(), TravelActivity.class);
                startActivityForResult(intent, 3000);
                break;
            case R.id.edit_profile:
                Toast.makeText(getApplicationContext(), "En desarrollo....", Toast.LENGTH_LONG).show();
                break;
            case R.id.change_languaje:
                Toast.makeText(getApplicationContext(), "En desarrollo....", Toast.LENGTH_LONG).show();
                break;
            case R.id.app_settings:
                Intent intent1 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent1);
                break;
        }
        return false;
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.maps_launcher:
//                // handle it
//                break;
//            case R.id.nav_marker:
//                // do whatever
//                break;
//            // and so on...
//        }
//        return false;
//    }
}
