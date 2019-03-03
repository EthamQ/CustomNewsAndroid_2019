package com.raphael.rapha.myNews.swipeCardContent;

public class NewsArticleUtils {

    /**
     * The article preview shows how many chars are left in the string.
     * We don't want the user to see it so we just remove it from the string.
     * Format to remove "[xxx chars...]".
     * @param articleContent
     * @return
     */
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
