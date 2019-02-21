package com.example.rapha.swipeprototype2.languages;

public class GermanDictionary extends LanguageDictionary{

    public GermanDictionary(){
        this.createDictionary();
    }


    @Override
    protected void createDictionary(){
        // Finance related
        dictionary.put("Economy".toUpperCase(), "Wirtschaft");
        dictionary.put("finance".toUpperCase(), "Finanz");
        dictionary.put("stock market".toUpperCase(), "Börse");
        dictionary.put("Wages".toUpperCase(), "Gehalt");
        dictionary.put("Investment".toUpperCase(), "Investier");
        dictionary.put("Jobs".toUpperCase(), "Arbeit");
        dictionary.put("Taxes".toUpperCase(), "Steuern");
        dictionary.put("insurance".toUpperCase(), "Versicherung");
        dictionary.put("bank".toUpperCase(), "Bank");
        dictionary.put("money".toUpperCase(), "Geld");
        dictionary.put("market".toUpperCase(), "Markt");
        dictionary.put("refund".toUpperCase(), "erstatten");
        dictionary.put("fund".toUpperCase(), "Fonds");
        dictionary.put("bills".toUpperCase(), "Rechnung");
        dictionary.put("customer".toUpperCase(), "Kunde");
        dictionary.put("employer".toUpperCase(), "Arbeitgeber");
        dictionary.put("employee".toUpperCase(), "Angestellter");

        // Food related
        dictionary.put("pasta".toUpperCase(), "Nudeln");
        dictionary.put("cook".toUpperCase(), "kochen");
        dictionary.put("food".toUpperCase(), "essen");
        dictionary.put("meal".toUpperCase(), "gericht");
        dictionary.put("delicious".toUpperCase(), "lecker");
        dictionary.put("recipe".toUpperCase(), "Rezept");
        dictionary.put("vegetables".toUpperCase(), "Gemüse");
        dictionary.put("tomato".toUpperCase(), "Tomate");
        dictionary.put("dinner".toUpperCase(), "Abendessen");
        dictionary.put("pan".toUpperCase(), "Pfanne");

        // Politics related
        dictionary.put("Russia".toUpperCase(), "Russland");
        dictionary.put("Syria".toUpperCase(), "Syrien");
        dictionary.put("war".toUpperCase(), "Krieg");
        dictionary.put("weapons".toUpperCase(), "Waffen");
        dictionary.put("politic".toUpperCase(), "Politik");
        dictionary.put("president".toUpperCase(), "President");
        dictionary.put("white house".toUpperCase(), "Weißes Haus");
        dictionary.put("foreign policy".toUpperCase(), "Außenpolitik");

        // Movie related
        dictionary.put("movie".toUpperCase(), "Film");
        dictionary.put("cinema".toUpperCase(), "Kino");
        dictionary.put("television".toUpperCase(), "Fernsehen");
        dictionary.put("blockbuster".toUpperCase(), "blockbuster");
        dictionary.put("actor".toUpperCase(), "Schauspieler");

        // Technology related
        dictionary.put("Programming".toUpperCase(), "Programmieren");
        dictionary.put("AI Artificial Intelligence".toUpperCase(), "Künstliche Intelligenz");
        dictionary.put("technology".toUpperCase(), "Technology");
    }
}
