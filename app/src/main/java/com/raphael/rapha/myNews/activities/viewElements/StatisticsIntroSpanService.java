package com.raphael.rapha.myNews.activities.viewElements;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.introduction.IntroductionActivityFragments.ExplainStatisticsFragment;

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
