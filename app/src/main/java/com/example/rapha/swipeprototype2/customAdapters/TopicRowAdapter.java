package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;

import java.util.ArrayList;

public class TopicRowAdapter extends ArrayAdapter {

    public TopicRowAdapter(@NonNull Context context, int resourceID, ArrayList<String[]> topicSet) {
        super(context, resourceID, topicSet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_row, parent, false);
        }
        String[] topicSet = (String[])getItem(position);

        String topic1 = topicSet[0];
        String topic2 = topicSet[1];
        String topic3 = topicSet[2];

        Button topic_button_1 = convertView.findViewById(R.id.button);
        Button topic_button_2 = convertView.findViewById(R.id.button2);
        Button topic_button_3 = convertView.findViewById(R.id.button3);

        setButtonText(topic_button_1, topic1);
        setButtonText(topic_button_2, topic2);
        setButtonText(topic_button_3, topic3);

        return convertView;
    }

    private void setButtonText(Button button, String text){
        if(text.isEmpty()){
            button.setVisibility(Button.INVISIBLE);
        }
        else{
            button.setText(text);
        }
    }
}
