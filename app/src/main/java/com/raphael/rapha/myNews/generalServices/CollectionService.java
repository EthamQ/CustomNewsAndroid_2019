package com.raphael.rapha.myNews.generalServices;


import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;


import java.util.LinkedList;
import java.util.List;

public class CollectionService {

    public static LinkedList orderListRandomly(LinkedList list){
        LinkedList listCopy = new LinkedList();
        listCopy.addAll(list);
        LinkedList random = new LinkedList();
        int originalSize = listCopy.size();
        for(int i = 0; i < originalSize; i++){
            int randomIndex = RandomService.getRandomNumber(0, listCopy.size());
            random.add(listCopy.get(randomIndex));
            listCopy.remove(randomIndex);
        }
        return random;
    }

    public static List removeAllEntriesStartingAt(List list, int startIndex){
        for(int i = list.size() - 1; i > startIndex; i--){
            if(i >= 0){
                list.remove(i);
            }
        }
        return list;
    }

    public static boolean arrayHasNullValues(Object[] array){
        for(int i = 0; i < array.length; i++){
            if(array[i] == null){
                return true;
            }
        }
        return false;
    }

    public static void deleteEqualEntries(List<NewsArticle> checkEntries, List<NewsArticle> deleteEntries){
        for(int i = 0; i < checkEntries.size(); i++){
            for(int j = 0; j < deleteEntries.size(); j++){
                if(checkEntries.get(i).title.equals(deleteEntries.get(j).title)){
                        deleteEntries.remove(j);
                }
            }
        }
    }

    public static void removeDuplicatesArticleList(List<ISwipeCard> articles) {
        LinkedList<ISwipeCard> newList = new LinkedList<>();
        for (ISwipeCard swipeCard : articles) {
            // If this element is not present in newList
            // then add it
            if(swipeCard instanceof NewsArticle){
                if(!containsTitle(newList, ((NewsArticle)swipeCard).title)) {
                    newList.add(swipeCard);
                }
            }
            else{
                newList.add(swipeCard);
            }

        }
        articles.clear();
        articles.addAll(newList);
    }

    public static void removeDuplicatesNewsArticleList(List<NewsArticle> articles) {
        LinkedList<NewsArticle> newList = new LinkedList<>();
        for (NewsArticle swipeCard : articles) {
            // If this element is not present in newList
            // then add it
                if(!containsTitleNewsArticle(newList, swipeCard.title)) {
                    newList.add(swipeCard);
                }
        }
        articles.clear();
        articles.addAll(newList);
    }

    private static boolean containsTitle(final List<ISwipeCard> list, final String title){
        return list.stream().filter(article -> article instanceof NewsArticle && ((NewsArticle)article).title.equals(title)).findFirst().isPresent();
    }

    public static boolean containsTopicNewsArticle(final List<NewsArticle> list, final String topic){
        return list.stream().filter(article -> article.foundWithKeyWord.equals(topic)).findFirst().isPresent();
    }

    private static boolean containsTitleNewsArticle(final List<NewsArticle> list, final String title){
        return list.stream().filter(article -> article.title.equals(title)).findFirst().isPresent();
    }

}
