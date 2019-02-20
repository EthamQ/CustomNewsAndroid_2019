package com.example.rapha.swipeprototype2.activities.mainActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.example.rapha.swipeprototype2.temporaryDataStorage.StatusDataStorage;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeFragment.OnFragmentInteractionListener,
        StatisticFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        NewsOfTheDayFragment.OnFragmentInteractionListener{

    public int currentFragment = R.id.nav_home;

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
        toolbar.setTitle("Swipe");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        String menuFragment = getIntent().getStringExtra("menuFragment");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(menuFragment != null){
            switch(menuFragment){
                case "newsOfTheDayFragment":
                    fragmentTransaction.replace(R.id.your_placeholder, new NewsOfTheDayFragment()); break;
                default:
                    fragmentTransaction.replace(R.id.your_placeholder, new SwipeFragment()); break;
            }
        } else{
            fragmentTransaction.replace(R.id.your_placeholder, new SwipeFragment());
        }
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
            if(currentFragment == R.id.nav_home){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            else{
                changeFragmentTo(R.id.nav_home);
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
        changeFragmentTo(id);
        currentFragment = id;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragmentTo(int id){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_statistics) {
            toolbar.setTitle("My Statistics");
            ft.replace(R.id.your_placeholder, new StatisticFragment());
            ft.commit();
        } else if(id == R.id.nav_home){
            toolbar.setTitle("Swipe");
            ft.replace(R.id.your_placeholder, new SwipeFragment());
            ft.commit();
        } else if(id == R.id.nav_settings){
            toolbar.setTitle("Settings");
            ft.replace(R.id.your_placeholder, new SettingsFragment());
            ft.commit();
        } else if(id == R.id.nav_info){
            toolbar.setTitle("Info");
            ft.replace(R.id.your_placeholder, new InfoFragment());
            ft.commit();
        } else if(id == R.id.nav_news){
            toolbar.setTitle("News of the day");
            ft.replace(R.id.your_placeholder, new NewsOfTheDayFragment());
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    }


