package com.raphael.rapha.myNews.swipeCardContent;

public class NewsArticleUtils {

    public static String removeCharInformation(String articleContent){
        char[] content = articleContent.toCharArray();
        int endIndex = articleContent.length();
        for(int i = content.length - 1; i > 0; i--){
            if(content[i] == '['){
                endIndex = i;
            }
        }
        return articleContent.substring(0, endIndex);
    }
}
