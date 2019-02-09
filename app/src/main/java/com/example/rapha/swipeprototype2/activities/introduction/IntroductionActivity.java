package com.example.rapha.swipeprototype2.activities.introduction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainStatisticsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainFinanceFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.HowToUseFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class IntroductionActivity extends AppCompatActivity
        implements
        ExplainFinanceFragment.OnFragmentInteractionListener,
        ExplainStatisticsFragment.OnFragmentInteractionListener,
        HowToUseFragment.OnFragmentInteractionListener{

    public final int HOW_TO_USE = 0;
    public final int EXPLAIN_STATISTIC = 1;
    public final int EXPLAIN_FINANCE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        if(appIsStartedTheFirstTime()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.introduction_placeholder, new HowToUseFragment());
            ft.commit();
        }
        else{
            finishIntroduction();
        }
    }

    public boolean appIsStartedTheFirstTime(){
        return false;
    }

    public void changeToFragment(int fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(fragment){
            case HOW_TO_USE:
                ft.replace(R.id.introduction_placeholder, new HowToUseFragment());
                ft.commit();
                break;
            case EXPLAIN_STATISTIC:
                ft.replace(R.id.introduction_placeholder, new ExplainStatisticsFragment());
                ft.commit();
                break;
            case EXPLAIN_FINANCE:
                ft.replace(R.id.introduction_placeholder, new ExplainFinanceFragment());
                ft.commit();
                break;
        }
    }

    public void finishIntroduction(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
