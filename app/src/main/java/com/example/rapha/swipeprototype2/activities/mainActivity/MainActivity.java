package com.example.rapha.swipeprototype2.activities.mainActivity;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.InfoFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SettingsFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.StatisticFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.dataStorage.StatusDataStorage;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeFragment.OnFragmentInteractionListener,
        StatisticFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        NewsOfTheDayFragment.OnFragmentInteractionListener{

    public void introductionCardWasShown(){
        StatusDataStorage.mainActivityStarted();
    }

    public boolean showIntroductionCard(){
        return !StatusDataStorage.getMainActivityWasActive();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, new SwipeFragment());
        fragmentTransaction.commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar swipe_card clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view swipe_card clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_statistics) {
            ft.replace(R.id.your_placeholder, new StatisticFragment());
            ft.commit();
        } else if(id == R.id.nav_home){
            ft.replace(R.id.your_placeholder, new SwipeFragment());
            ft.commit();
        } else if(id == R.id.nav_settings){
            ft.replace(R.id.your_placeholder, new SettingsFragment());
            ft.commit();
        } else if(id == R.id.nav_info){
            ft.replace(R.id.your_placeholder, new InfoFragment());
            ft.commit();
        } else if(id == R.id.nav_news){
            ft.replace(R.id.your_placeholder, new NewsOfTheDayFragment());
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}