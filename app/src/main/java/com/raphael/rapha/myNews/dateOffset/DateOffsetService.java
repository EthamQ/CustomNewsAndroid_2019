package com.raphael.rapha.myNews.dateOffset;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.roomDatabase.LanguageCombinationDbService;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.List;

public class DateOffsetService {

    /**
     * Resets the date offset in the database for the language combination that is currently active
     * and corresponds to the category id.
     * @param swipeFragment
     * @param categoryId The news category to reset.
     */
    public static void resetDateOffsetForCurrentLanguages(SwipeFragment swipeFragment, int categoryId){
        // Get current language selection and get its id from the database if it exists.
        LiveData<List<LanguageCombinationRoomModel>> allLanguageCombinationsLiveData
                = swipeFragment.languageComboDbService.getAll();
        allLanguageCombinationsLiveData.observe(
                swipeFragment.getActivity(),
                new Observer<List<LanguageCombinationRoomModel>>() {
                    @Override
                    public void onChanged(@Nullable List<LanguageCombinationRoomModel> languageCombinations) {
                        Observer comboObserver = this;
                        boolean[] currentSelection = LanguageSettingsService.loadChecked(swipeFragment.mainActivity);
                        for(int i = 0; i < languageCombinations.size(); i++){
                            if(LanguageCombinationDbService.languageSelectionIsEqual(
                                    currentSelection,
                                    languageCombinations.get(i))
                                    ){
                                // Language combination exists in database, store its id.
                                int comboId = languageCombinations.get(i).id;
                                // Get all date offsets from the database and reset it if
                                // the language combination id matches
                                LiveData<List<RequestOffsetRoomModel>> allDateOffsetsLiveData
                                        = swipeFragment.dateOffsetDbService.getAll();
                                allDateOffsetsLiveData.observe(swipeFragment.getActivity(),
                                        new Observer<List<RequestOffsetRoomModel>>() {
                                            @Override
                                            public void onChanged(@Nullable List<RequestOffsetRoomModel> requestOffsetRoomModels) {
                                                for(int i = 0; i < requestOffsetRoomModels.size(); i++){
                                                    RequestOffsetRoomModel currentOffset = requestOffsetRoomModels.get(i);
                                                    if(currentOffset.languageCombination == comboId
                                                            && currentOffset.categoryId == categoryId){
                                                        // Date offset with combination id found
                                                        currentOffset.requestOffset = "";
                                                        swipeFragment.dateOffsetDbService.update(requestOffsetRoomModels.get(i));
                                                        allDateOffsetsLiveData.removeObserver(this);
                                                        allLanguageCombinationsLiveData.removeObserver(comboObserver);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }
}
