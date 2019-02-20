package com.example.rapha.swipeprototype2.languages;

import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;

import java.util.List;

public class LanguageCombinationService {

    public static int getIdOfLanguageCombination(List<LanguageCombinationRoomModel> allLanguageSelections, boolean[] currentLanguageSelection){
        for (int i = 0; i < allLanguageSelections.size(); i++) {
            LanguageCombinationRoomModel currentCombination = allLanguageSelections.get(i);
            boolean alreadyExists = LanguageCombinationDbService.languageSelectionIsEqual(
                    currentLanguageSelection,
                    currentCombination
            );
            if (alreadyExists) {
                return currentCombination.id;
            }
        }
        return -1;
    }
}
