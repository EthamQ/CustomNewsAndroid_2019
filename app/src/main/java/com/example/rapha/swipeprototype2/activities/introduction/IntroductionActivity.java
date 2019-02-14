package com.example.rapha.swipeprototype2.activities.introduction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainDailyNewsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainQuestionsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainStatisticsFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainFinanceFragment;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.HowToUseFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.sharedPreferencesAccess.IntroductionAccessService;

public class IntroductionActivity extends AppCompatActivity
        implements
        ExplainFinanceFragment.OnFragmentInteractionListener,
        ExplainStatisticsFragment.OnFragmentInteractionListener,
        HowToUseFragment.OnFragmentInteractionListener,
        ExplainQuestionsFragment.OnFragmentInteractionListener,
        ExplainDailyNewsFragment.OnFragmentInteractionListener{

    public final int EXPLAIN_ARTICLES = 0;
    public final int EXPLAIN_QUESTIONS = 1;
    public final int EXPLAIN_STATISTIC = 2;
    public final int EXPLAIN_DAILY_NEWS = 3;
    public final int EXPLAIN_FINANCE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        if(IntroductionAccessService.getIntroductionShouldBeShown(getApplicationContext())){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.introduction_placeholder, new HowToUseFragment());
            ft.commit();
        }
        else{
            finishIntroduction();
        }
    }

    public void changeToFragment(int fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragment){
            case EXPLAIN_ARTICLES:
                transaction.replace(R.id.introduction_placeholder, new HowToUseFragment());
                break;
            case EXPLAIN_QUESTIONS:
                transaction.replace(R.id.introduction_placeholder, new ExplainQuestionsFragment());
                break;
            case EXPLAIN_STATISTIC:
                transaction.replace(R.id.introduction_placeholder, new ExplainStatisticsFragment());
                break;
            case EXPLAIN_FINANCE:
                transaction.replace(R.id.introduction_placeholder, new ExplainFinanceFragment());
                break;
            case EXPLAIN_DAILY_NEWS:
                transaction.replace(R.id.introduction_placeholder, new ExplainDailyNewsFragment());
                break;
        }
        transaction.commit();
    }

    public void finishIntroduction(){
        IntroductionAccessService.setIntroductionShouldBeShown(getApplicationContext(), false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
