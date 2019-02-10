package com.example.rapha.swipeprototype2.activities.viewElements;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.introduction.IntroductionActivityFragments.ExplainStatisticsFragment;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.StatisticFragment;

public class StatisticsIntroSpanService {

    public static void statisticFragmentSetColor(ExplainStatisticsFragment explainStatisticsFragment){
       setColorFirstParagraph(explainStatisticsFragment);
        setColorSecondParagraph(explainStatisticsFragment);
        setColorThirdParagraph(explainStatisticsFragment);
        setColorFourthParagraph(explainStatisticsFragment);
    }

    public static void setColorFirstParagraph(ExplainStatisticsFragment explainStatisticsFragment){
        SpannableStringBuilder spannable = new SpannableStringBuilder(explainStatisticsFragment.getString(R.string.explain_cat_statistic));

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                11, // start
                24, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                39, // start
                49, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        ((TextView)explainStatisticsFragment.view.findViewById(R.id.explain_category)).setText(spannable);
    }

    public static void setColorSecondParagraph(ExplainStatisticsFragment explainStatisticsFragment){
        SpannableStringBuilder spannable = new SpannableStringBuilder(explainStatisticsFragment.getString(R.string.explain_topic_statistic));

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                13, // start
                23, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                37, // start
                44, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        ((TextView)explainStatisticsFragment.view.findViewById(R.id.explain_topic)).setText(spannable);
    }

    public static void setColorThirdParagraph(ExplainStatisticsFragment explainStatisticsFragment){
        SpannableStringBuilder spannable = new SpannableStringBuilder(explainStatisticsFragment.getString(R.string.explain_statistic_below_text1));

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                62, // start
                82, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                86, // start
                93, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        ((TextView)explainStatisticsFragment.view.findViewById(R.id.explain_statistic_below1)).setText(spannable);
    }

    public static void setColorFourthParagraph(ExplainStatisticsFragment explainStatisticsFragment){
        SpannableStringBuilder spannable = new SpannableStringBuilder(explainStatisticsFragment.getString(R.string.explain_statistic_below_text2));

        spannable.setSpan(
                new ForegroundColorSpan(explainStatisticsFragment.getResources().getColor(R.color.turquis_dark)),
                3, // start
                17, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        ((TextView)explainStatisticsFragment.view.findViewById(R.id.explain_statistic_below2)).setText(spannable);
    }



}
