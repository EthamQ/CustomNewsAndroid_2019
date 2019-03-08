package com.raphael.rapha.myNews.languages;

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
        dictionary.put("insurance".toUpperCase(), "страховка OR заверение");
        dictionary.put("bank".toUpperCase(), "банк");
        dictionary.put("money".toUpperCase(), "деньги рубл");
        dictionary.put("market".toUpperCase(), "рынок рынк");
        dictionary.put("refund".toUpperCase(), "возмещ возмест");
        dictionary.put("fund".toUpperCase(), "фонд");
        dictionary.put("bills".toUpperCase(), "счёт");
        dictionary.put("customer".toUpperCase(), "покупател клиент");
        dictionary.put("employer".toUpperCase(), "работодател OR предприниматель");
        dictionary.put("employee".toUpperCase(), "служащий сотрудник");

        // Food related
        dictionary.put("pasta".toUpperCase(), "макарон");
        dictionary.put("cook".toUpperCase(), "варить OR готовить");
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
        dictionary.put("Syria".toUpperCase(), "Сирия OR Сирий");
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
        dictionary.put("Trump".toUpperCase(), "трамп");
        dictionary.put("Putin".toUpperCase(), "путин");
        dictionary.put("Macron".toUpperCase(), "макрон");
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
        dictionary.put("Programming".toUpperCase(), "программир OR алгоритм");
        dictionary.put("AI Artificial Intelligence".toUpperCase(), "искусственн интеллигенци");
        dictionary.put("technology".toUpperCase(), "технологи");
        dictionary.put("screen".toUpperCase(), "экран");
        dictionary.put("drone".toUpperCase(), "беспилотник");
        dictionary.put("application".toUpperCase(), "аппликация");
        dictionary.put("Machine Learning".toUpperCase(), "машинное обучение");


        //Sport related
        dictionary.put("Soccer".toUpperCase(), "футболь");
        dictionary.put("Football".toUpperCase(), "футболь");
        dictionary.put("Swimming".toUpperCase(), "плавать");
        dictionary.put("Boxing".toUpperCase(), "бокс");
        dictionary.put("MMA".toUpperCase(), "MMA OR UFC");
        dictionary.put("Tennis".toUpperCase(), "теннис");
        dictionary.put("Hockey".toUpperCase(), "хоккей");
        dictionary.put("Bike".toUpperCase(), "велосипед");
        dictionary.put("Hiking".toUpperCase(), "поход");
        dictionary.put("olympic".toUpperCase(), "олимпийск");
        dictionary.put("Rugby".toUpperCase(), "регби");
        dictionary.put("Baseball".toUpperCase(), "бейсбол");
        dictionary.put("sport".toUpperCase(), "спорт");
        dictionary.put("score".toUpperCase(), "окончательный счёт");
        dictionary.put("tournament".toUpperCase(), "турнир");
        dictionary.put("championship".toUpperCase(), "первенство");
        dictionary.put("race".toUpperCase(), "бег");
        dictionary.put("athlete".toUpperCase(), "атлет");

        // Health related
        dictionary.put("Healthcare".toUpperCase(), "здравоохранение");
        dictionary.put("Fitness".toUpperCase(), "фитнес");
        dictionary.put("Disease".toUpperCase(), "болезн");
        dictionary.put("Virus".toUpperCase(), "вирус");
        dictionary.put("Nutrition".toUpperCase(), "питание");
        dictionary.put("Diet".toUpperCase(), "питание");
        dictionary.put("Vaccination".toUpperCase(), " прививк nпривить");
        dictionary.put("Mental illness".toUpperCase(), "психическ");
        dictionary.put("health".toUpperCase(), "здоровье");
        dictionary.put("doctor".toUpperCase(), "врач");
        dictionary.put("exercise".toUpperCase(), "тренир");
        dictionary.put("hospital".toUpperCase(), "больниц");
        dictionary.put("pharmacy".toUpperCase(), "фармаци");
        dictionary.put("medicare".toUpperCase(), "здравоохранение");
        dictionary.put("prevention".toUpperCase(), "предотвра");
        dictionary.put("disorder".toUpperCase(), "инвалид");
        dictionary.put("cancer".toUpperCase(), "Рак");
        dictionary.put("heart".toUpperCase(), "сердце");

        // Crime related
        dictionary.put("Murder".toUpperCase(), "уби́йц");
        dictionary.put("Police".toUpperCase(), "полици");
        dictionary.put("Prison".toUpperCase(), "тюрьм");
        dictionary.put("Terror".toUpperCase(), "террор");
        dictionary.put("Mafia".toUpperCase(), "ма́фия");
        dictionary.put("Death".toUpperCase(), "смерть");
        dictionary.put("kill".toUpperCase(), "убить убил убива");
        dictionary.put("crime".toUpperCase(), "преступлен");
        dictionary.put("choke".toUpperCase(), "душить");
        dictionary.put("steal".toUpperCase(), "красть OR крал OR ворова OR вор OR краж");
        dictionary.put("shoot".toUpperCase(), "стрел стреля");
        dictionary.put("felony".toUpperCase(), "преступление");
        dictionary.put("rape".toUpperCase(), "изнасилован OR насилова");
        dictionary.put("fraud".toUpperCase(), "обман");
        dictionary.put("misdemeanour".toUpperCase(), "нарушение");
        dictionary.put("robbery".toUpperCase(), "атака OR набег OR нападение");
        dictionary.put("offence".toUpperCase(), "больница");
        dictionary.put("victim".toUpperCase(), "жертв пострадав");
        dictionary.put("kidnap".toUpperCase(), "похищен OR похити OR похища");
        dictionary.put("plagiarize".toUpperCase(), "изврати OR извраща");
        dictionary.put("violent".toUpperCase(), "насилие");
        dictionary.put("gang".toUpperCase(), "братва");
        dictionary.put("stab".toUpperCase(), "зареза OR заколо OR закалыва");

        //Science related
        dictionary.put("Physics".toUpperCase(), "физик");
        dictionary.put("Climate change".toUpperCase(), "изменение климата");
        dictionary.put("Biology".toUpperCase(), "биология");
        dictionary.put("Chemistry".toUpperCase(), "химия");
        dictionary.put("Genetics".toUpperCase(), "генетика");
        dictionary.put("Mathematics".toUpperCase(), "математика");
        dictionary.put("Neuroscience".toUpperCase(), "неврология");
        dictionary.put("Engineering".toUpperCase(), "инженер");
        dictionary.put("cryptography".toUpperCase(), "криптография");
        dictionary.put("science".toUpperCase(), "наук");
        dictionary.put("scientist".toUpperCase(), "учён научн");
        dictionary.put("study".toUpperCase(), "исследован");
        dictionary.put("atom".toUpperCase(), "атом");
        dictionary.put("laboratory".toUpperCase(), "лаборатория");
        dictionary.put("radioactive".toUpperCase(), "радиоактивн");
        dictionary.put("quantum".toUpperCase(), "квант");
    }
}
