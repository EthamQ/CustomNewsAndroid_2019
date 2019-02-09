package com.example.rapha.swipeprototype2.activities.introduction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainQuestionsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainStatisticsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainFinanceFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.HowToUseFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class IntroductionActivity extends AppCompatActivity
        implements
        ExplainFinanceFragment.OnFragmentInteractionListener,
        ExplainStatisticsFragment.OnFragmentInteractionListener,
        HowToUseFragment.OnFragmentInteractionListener,
        ExplainQuestionsFragment.OnFragmentInteractionListener{

    public final int EXPLAIN_ARTICLES = 0;
    public final int EXPLAIN_QUESTIONS = 1;
    public final int EXPLAIN_STATISTIC = 2;
    public final int EXPLAIN_FINANCE = 3;

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
        return true;
    }

    public void changeToFragment(int fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(fragment){
            case EXPLAIN_ARTICLES:
                ft.replace(R.id.introduction_placeholder, new HowToUseFragment());
                ft.commit();
                break;
            case EXPLAIN_QUESTIONS:
                ft.replace(R.id.introduction_placeholder, new ExplainQuestionsFragment());
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
