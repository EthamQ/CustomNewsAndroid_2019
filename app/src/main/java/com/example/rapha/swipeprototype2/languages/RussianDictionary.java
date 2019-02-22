package com.example.rapha.swipeprototype2.languages;

public class RussianDictionary extends LanguageDictionary {

    public RussianDictionary(){
        createDictionary();
    }

    /**
     * Puts the russian translation for every relevant english word in the
     * dictionary hash map.
     * Doesn't always translate to the complete word to make it more likely
     * to find a variation of the word
     */
    @Override
    protected void createDictionary() {
        // Finance related
        dictionary.put("Economy".toUpperCase(), "экономика");
        dictionary.put("finance".toUpperCase(), "финансов");
        dictionary.put("stock market".toUpperCase(), " бирж");
        dictionary.put("Wages".toUpperCase(), "зарплат");
        dictionary.put("Investment".toUpperCase(), "инвестир");
        dictionary.put("Jobs".toUpperCase(), "работ");
        dictionary.put("Taxes".toUpperCase(), "налог");
        dictionary.put("insurance".toUpperCase(), "страховка заверение");
        dictionary.put("bank".toUpperCase(), "банк");
        dictionary.put("money".toUpperCase(), "деньги рубл");
        dictionary.put("market".toUpperCase(), "рынок рынк");
        dictionary.put("refund".toUpperCase(), "возмещ возмест");
        dictionary.put("fund".toUpperCase(), "фонд");
        dictionary.put("bills".toUpperCase(), "счёт");
        dictionary.put("customer".toUpperCase(), "покупател клиент");
        dictionary.put("employer".toUpperCase(), "работодател предприниматель");
        dictionary.put("employee".toUpperCase(), "служащий сотрудник");

        // Food related
        dictionary.put("pasta".toUpperCase(), "макарон");
        dictionary.put("cook".toUpperCase(), "варить готовить");
        dictionary.put("food".toUpperCase(), "еда");
        dictionary.put("meal".toUpperCase(), "блюд");
        dictionary.put("delicious".toUpperCase(), "вкус");
        dictionary.put("recipe".toUpperCase(), "рецепт");
        dictionary.put("vegetables".toUpperCase(), "овощ");
        dictionary.put("tomato".toUpperCase(), "помидор");
        dictionary.put("dinner".toUpperCase(), "ужин");
        dictionary.put("pan".toUpperCase(), "сковорода");
        dictionary.put("kitchen".toUpperCase(), "кухн");

        // Politics related
        dictionary.put("Russia".toUpperCase(), "росси");
        dictionary.put("Syria".toUpperCase(), "Сирия Сирий");
        dictionary.put("war".toUpperCase(), "войн");
        dictionary.put("weapons".toUpperCase(), "оружи");
        dictionary.put("politic".toUpperCase(), "политик");
        dictionary.put("president".toUpperCase(), "президент");
        dictionary.put("white house".toUpperCase(), "Белый дом");
        dictionary.put("foreign policy".toUpperCase(), "внешн политик");
        dictionary.put("treaty".toUpperCase(), "договор");
        dictionary.put("surveillance".toUpperCase(), "наблюдение");
        dictionary.put("europe".toUpperCase(), " Европ");
        dictionary.put("sanctions".toUpperCase(), " принудительн");

        dictionary.put("Communism".toUpperCase(), "коммунизм");
        dictionary.put("Law".toUpperCase(), "закон");
        dictionary.put("Capitalism".toUpperCase(), "капитализм");
        dictionary.put("Socialism".toUpperCase(), "социализм");
        dictionary.put("Dictator".toUpperCase(), "диктатор");
        dictionary.put("Democracy".toUpperCase(), "демократия");
        dictionary.put("Corruption".toUpperCase(), " коррупция ");
        dictionary.put("government".toUpperCase(), "государств");
        dictionary.put("parliament".toUpperCase(), "парламент");
        dictionary.put("regime".toUpperCase(), "государственный строй");
        dictionary.put("negotiation".toUpperCase(), "слушание");
        dictionary.put("diplomat".toUpperCase(), "дипломат");

        // Movie related
        dictionary.put("movie".toUpperCase(), "фильм");
        dictionary.put("cinema".toUpperCase(), "кино");
        dictionary.put("television".toUpperCase(), "телеви");
        dictionary.put("blockbuster".toUpperCase(), "blockbuster");
        dictionary.put("actor".toUpperCase(), "актёр актрис");

        // Technology related
        dictionary.put("Programming".toUpperCase(), "программир");
        dictionary.put("AI Artificial Intelligence".toUpperCase(), "искусственн интеллигенци");
        dictionary.put("technology".toUpperCase(), "технологи");
        dictionary.put("screen".toUpperCase(), "экран");
        dictionary.put("drone".toUpperCase(), "беспилотник");
        dictionary.put("application".toUpperCase(), "аппликация");

        //Sport related
        dictionary.put("Soccer".toUpperCase(), "футбо́ль");
        dictionary.put("Football".toUpperCase(), "футбо́ль");
        dictionary.put("Swimming".toUpperCase(), "пла́вать");
        dictionary.put("Boxing".toUpperCase(), "бокс");
        dictionary.put("MMA".toUpperCase(), "MMA");
        dictionary.put("Tennis".toUpperCase(), "те́ннис");
        dictionary.put("Hockey".toUpperCase(), "хокке́й");
        dictionary.put("Bike".toUpperCase(), "велосипе́д");
        dictionary.put("Hiking".toUpperCase(), "похо́д");
        dictionary.put("olympic".toUpperCase(), "олимпи́йск");
        dictionary.put("Rugby".toUpperCase(), "ре́гби");
        dictionary.put("Baseball".toUpperCase(), "бейсбо́л");
        dictionary.put("sport".toUpperCase(), "спорт");
        dictionary.put("score".toUpperCase(), "оконча́тельный счёт");
        dictionary.put("tournament".toUpperCase(), "турни́р");
        dictionary.put("championship".toUpperCase(), "пе́рвенство");
        dictionary.put("race".toUpperCase(), "бег");
        dictionary.put("athlete".toUpperCase(), "атле́т");
    }
}
