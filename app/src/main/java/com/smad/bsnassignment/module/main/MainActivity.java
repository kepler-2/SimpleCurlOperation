package com.smad.bsnassignment.module.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.smad.bsnassignment.BaseActivity;
import com.smad.bsnassignment.R;
import com.smad.bsnassignment.module.feedback.FeedbackList;
import com.smad.bsnassignment.module.feedback.SendFeedback;
import com.smad.bsnassignment.module.main.fragment.AdminLogin;
import com.smad.bsnassignment.module.main.fragment.Gallery;
import com.smad.bsnassignment.module.main.fragment.Import;
import com.smad.bsnassignment.module.main.fragment.Send;
import com.smad.bsnassignment.module.main.fragment.Share;
import com.smad.bsnassignment.module.main.fragment.SignUp;
import com.smad.bsnassignment.module.main.fragment.SlideShow;
import com.smad.bsnassignment.module.main.fragment.Tools;
import com.smad.bsnassignment.module.main.fragment.UserLogin;
import com.smad.bsnassignment.support.ShardPref;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ShardPref preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = ShardPref.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addFragment(new Import(), null, true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_feed:
                if (preferences.isLoggedIn() && preferences.getLoggedUserType() == ShardPref.TYPE_ADMIN) {
                    startActivity(FeedbackList.class);
                } else
                    replaceFragment(new AdminLogin(), null, false);
                break;
            case R.id.send_feed:
                if (preferences.isLoggedIn() && preferences.getLoggedUserType() == ShardPref.TYPE_USER) {
                    startActivity(SendFeedback.class);
                } else
                    replaceFragment(new UserLogin(), null, false);
                break;
            case R.id.nav_user_login:
                if (preferences.isLoggedIn() && preferences.getLoggedUserType() == ShardPref.TYPE_USER) {
                    startActivity(SendFeedback.class);
                }else {
                    replaceFragment(new UserLogin(), null, false);
                }
                break;
            case R.id.nav_admin_login:
                if (preferences.isLoggedIn() && preferences.getLoggedUserType() == ShardPref.TYPE_ADMIN) {
                    startActivity(FeedbackList.class);
                }else {
                    replaceFragment(new AdminLogin(), null, false);
                }
                break;
            case R.id.nav_sign_up:
                replaceFragment(new SignUp(), null, false);
                break;
            case R.id.nav_camera:
                replaceFragment(new Import(), null, false);
                break;
            case R.id.nav_gallery:
                replaceFragment(new Gallery(), null, false);
                break;
            case R.id.nav_slideshow:
                replaceFragment(new SlideShow(), null, false);
                break;
            case R.id.nav_manage:
                replaceFragment(new Tools(), null, false);
                break;
            case R.id.nav_share:
                replaceFragment(new Share(), null, false);
                break;
            case R.id.nav_send:
                replaceFragment(new Send(), null, false);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

