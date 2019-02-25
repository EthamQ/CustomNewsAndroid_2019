package com.raphael.rapha.myNews.loading;

import java.util.List;

public class LoadingService {

    public static final int MAX_LOADING_TIME_MILLS_DEFAULT = 7000;
    private static int numberOfSentRequests;
    private static int numberOfAnswersReceived;

    public static String getLoadingText(int loadingType){
        switch(loadingType){
            case SwipeLoadingService.CHANGE_LANGUAGE: return "Loading articles in another language, this may take a while...";
            case DailyNewsLoadingService.LOAD_DAILY_NEWS: return "Loading your daily news, please wait a moment...";
            case SwipeLoadingService.FIRST_TIME_LOADING: return "Loading articles for the first time, this may take a while";
            default: return "Loading articles, please wait a moment...";
        }
    }


    public static void addNewLanguageLoadingJob(List<LoadingJob> loadingJobs){
        loadingJobs.add(new LoadingJob());
    }

    public static void setLastLanguageLoadingJobSuccessful(List<LoadingJob> loadingJobs){
        if(!loadingJobs.isEmpty()){
            loadingJobs.get(loadingJobs.size() - 1).finishedSuccessful = true;
        }
    }

    public static boolean getLastLanguageChangeJobSuccess(List<LoadingJob> loadingJobs){
        boolean success = true;
        if(!loadingJobs.isEmpty()){
            success = loadingJobs.get(0).finishedSuccessful;
            loadingJobs.remove(0);
        }
        return success;
    }

    public static void setNumberOfSentRequests(int requestsSent){
        numberOfAnswersReceived = 0;
        numberOfSentRequests = requestsSent;
    }

    public static void answerReceived(){
        numberOfAnswersReceived++;
    }

    public static String getNumberOfAnswersReceivedText(){
        double percentage = ((double)numberOfAnswersReceived / (double)numberOfSentRequests) * 100;
        return "Loading progress: " + (int)percentage + "%\n"
                + "Request " + numberOfAnswersReceived +  " of " + numberOfSentRequests + " finished";
    }


}
