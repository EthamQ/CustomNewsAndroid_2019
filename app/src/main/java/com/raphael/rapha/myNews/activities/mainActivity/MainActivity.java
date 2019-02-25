package com.raphael.rapha.myNews.activities.mainActivity;


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


import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.InfoFragment;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SettingsFragment;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.StatisticFragment;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.temporaryDataStorage.StatusDataStorage;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeFragment.OnFragmentInteractionListener,
        StatisticFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        NewsOfTheDayFragment.OnFragmentInteractionListener{

    // To know what should happen when the user presses back.
    public int currentFragment = R.id.nav_swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_swipe);

        // Navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Check if a click on the news of the day notification opened this activity.
        String menuFragment = getIntent().getStringExtra("menuFragment");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // If yes open news of the day fragment
        if(menuFragment != null){
            switch(menuFragment){
                case "newsOfTheDayFragment":
                    fragmentTransaction.replace(R.id.your_placeholder, new NewsOfTheDayFragment()); break;
                default:
                    fragmentTransaction.replace(R.id.your_placeholder, new SwipeFragment()); break;
            }
            // By default always open the swipe fragment at first.
        } else{
            fragmentTransaction.replace(R.id.your_placeholder, new SwipeFragment());
        }
        fragmentTransaction.commit();
    }

    /**
     * To call by the swipe fragment when the introduction card was shown.
     * It won't be shown a second time afterwards during one session.
     */
    public void introductionCardWasShown(){
        StatusDataStorage.mainActivityStarted();
    }

    /**
     * Tell the swipe fragment if it should show an
     * introduction card as the first card.
     */
    public boolean showIntroductionCard(){
        return !StatusDataStorage.getMainActivityWasActive();
    }

    /**
     * Back pressed in swipe fragment simulates home button click.
     * Every other fragment will be directed to the swipe fragment.
     */
    @Override
    public void onBackPressed() {
            if(currentFragment == R.id.nav_swipe){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            else{
                loadFragment(R.id.nav_swipe);
            }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view swipe_card clicks here.
        int id = item.getItemId();
        loadFragment(id);
        currentFragment = id;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Open another fragment.
     * @param fragmentId
     */
    public void loadFragment(int fragmentId){
        Toolbar toolbar = findViewById(R.id.toolbar);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentFragment = fragmentId;
        if (fragmentId == R.id.nav_statistics) {
            toolbar.setTitle(R.string.nav_statistics);
            ft.replace(R.id.your_placeholder, new StatisticFragment());
            ft.commit();
        } else if(fragmentId == R.id.nav_swipe){
            toolbar.setTitle(R.string.nav_swipe);
            ft.replace(R.id.your_placeholder, new SwipeFragment());
            ft.commit();
        } else if(fragmentId == R.id.nav_settings){
            toolbar.setTitle(R.string.nav_settings);
            ft.replace(R.id.your_placeholder, new SettingsFragment());
            ft.commit();
        } else if(fragmentId == R.id.nav_info){
            toolbar.setTitle(R.string.nav_info);
            ft.replace(R.id.your_placeholder, new InfoFragment());
            ft.commit();
        } else if(fragmentId == R.id.nav_news){
            toolbar.setTitle(R.string.nav_daily);
            ft.replace(R.id.your_placeholder, new NewsOfTheDayFragment());
            ft.commitAllowingStateLoss();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


