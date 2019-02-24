package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRepository;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.LinkedList;
import java.util.List;

public class OffsetDbService {

    private static OffsetDbService instance;
    RequestOffsetRepository repository;

    public OffsetDbService(Application application){
        this.repository = new RequestOffsetRepository(application);
    }

    public static synchronized OffsetDbService getInstance(Application application){
        if(instance == null){
            instance = new OffsetDbService(application);
        }
        return instance;
    }

    public void saveRequestOffset(String requestOffset, int categoryId, long languageCombinationId) {
        RequestOffsetRoomModel offsetModel = new RequestOffsetRoomModel();
        offsetModel.requestOffset = requestOffset;
        offsetModel.categoryId = categoryId;
        offsetModel.languageCombination = languageCombinationId;
        repository.insert(offsetModel);
    }

    public void update(RequestOffsetRoomModel offsetModel) {
        repository.update(offsetModel);
    }

    public MutableLiveData<LinkedList<RequestOffsetRoomModel>> getOffsetsForLanguageCombination(MainActivity mainActivity, boolean[] languageCombination){
        return repository.getDateOffsetsForLanguageCombination(mainActivity, languageCombination);
    }

    public LiveData<List<RequestOffsetRoomModel>> getAll(){
        return repository.getAll();
    }

}
