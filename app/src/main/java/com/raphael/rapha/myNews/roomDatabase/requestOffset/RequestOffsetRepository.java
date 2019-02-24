package com.raphael.rapha.myNews.roomDatabase.requestOffset;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.roomDatabase.AppDatabase;
import com.raphael.rapha.myNews.roomDatabase.LanguageCombinationDbService;
import com.raphael.rapha.myNews.roomDatabase.OffsetDbService;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;

import java.util.LinkedList;
import java.util.List;

public class RequestOffsetRepository {

    private IRequestOffsetDao dao;

    public RequestOffsetRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.requestOffsetDao();
    }

    public LiveData<List<RequestOffsetRoomModel>> getAll(){
        return this.dao.getAll();
    }

    public void insert(RequestOffsetRoomModel requestOffsetRoomModel){
        new InsertAsyncTask(dao).execute(requestOffsetRoomModel);
    }

    public void update(RequestOffsetRoomModel requestOffsetRoomModel){
        new UpdateAsyncTask(dao).execute(requestOffsetRoomModel);
    }

    public MutableLiveData<LinkedList<RequestOffsetRoomModel>> getDateOffsetsForLanguageCombination(MainActivity mainActivity, boolean[] languageCombination){
        MutableLiveData<LinkedList<RequestOffsetRoomModel>> offsetsForCombinationId = new MutableLiveData<>();
        LanguageCombinationDbService languageComboDbService = LanguageCombinationDbService.getInstance(mainActivity.getApplication());
        languageComboDbService.getAll().observe(mainActivity, new Observer<List<LanguageCombinationRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<LanguageCombinationRoomModel> combinations) {
                        for(int i = 0; i < combinations.size(); i++){
                            final LanguageCombinationRoomModel currentCombination = combinations.get(i);
                            boolean combinationExists = LanguageCombinationDbService.languageSelectionIsEqual(languageCombination, currentCombination);
                            if(combinationExists && !(mainActivity == null)){
                                setDateOffsetsForLanguageCombinationId(mainActivity, currentCombination.id, offsetsForCombinationId);
                                languageComboDbService.getAll().removeObserver(this);
                            }
                        }
                    }
                }
        );
        return offsetsForCombinationId;
    }

    private void setDateOffsetsForLanguageCombinationId(MainActivity mainActivity, int combinationId, MutableLiveData<LinkedList<RequestOffsetRoomModel>> offsetsForCombinationId){
        OffsetDbService dateOffsetDbService = OffsetDbService.getInstance(mainActivity.getApplication());
        dateOffsetDbService.getAll().observe(mainActivity, new Observer<List<RequestOffsetRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<RequestOffsetRoomModel> offsets) {
                LinkedList<RequestOffsetRoomModel> filteredOffsets = new LinkedList<>();
                for(int j = 0; j < offsets.size(); j++) {
                    if (offsets.get(j).languageCombination == combinationId) {
                        filteredOffsets.add(offsets.get(j));
                    }
                    if(j == offsets.size() - 1){
                        offsetsForCombinationId.setValue(filteredOffsets);
                        dateOffsetDbService.getAll().removeObserver(this);
                    }
                }
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<RequestOffsetRoomModel, Void, Void> {
        private IRequestOffsetDao dao;
        private InsertAsyncTask(IRequestOffsetDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(RequestOffsetRoomModel... requestOffsetRoomModels) {
            dao.insertOne(requestOffsetRoomModels[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<RequestOffsetRoomModel, Void, Void> {
        private IRequestOffsetDao dao;
        private UpdateAsyncTask(IRequestOffsetDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(RequestOffsetRoomModel... requestOffsetRoomModels) {
            dao.update(requestOffsetRoomModels[0]);
            return null;
        }
    }
}
