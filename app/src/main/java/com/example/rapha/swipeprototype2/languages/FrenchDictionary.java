package com.example.rapha.swipeprototype2.languages;

public class FrenchDictionary extends LanguageDictionary{

    public FrenchDictionary(){
        createDictionary();
    }

    @Override
    protected void createDictionary(){
        // Finance related
        dictionary.put("Economy".toUpperCase(), "économie");
        dictionary.put("finance".toUpperCase(), "finance");
        dictionary.put("stock market".toUpperCase(), "bourse");
        dictionary.put("Wages".toUpperCase(), "salaire");
        dictionary.put("Investment".toUpperCase(), "investir");
        dictionary.put("Jobs".toUpperCase(), "travail");
        dictionary.put("Taxes".toUpperCase(), "taxe");
        dictionary.put("insurance".toUpperCase(), "assurance");
        dictionary.put("bank".toUpperCase(), "banque");
        dictionary.put("money".toUpperCase(), "argent");
        dictionary.put("market".toUpperCase(), "marché");
        dictionary.put("refund".toUpperCase(), "rembours");
        dictionary.put("fund".toUpperCase(), " fonds");
        dictionary.put("bills".toUpperCase(), "compte");
        dictionary.put("customer".toUpperCase(), "client");
        dictionary.put("employer".toUpperCase(), "employeu");
        dictionary.put("employee".toUpperCase(), "employé");

        // Food related
        dictionary.put("pasta".toUpperCase(), "nouille");
        dictionary.put("cook".toUpperCase(), "bouillir");
        dictionary.put("food".toUpperCase(), "mange");
        dictionary.put("meal".toUpperCase(), "plat");
        dictionary.put("delicious".toUpperCase(), "délicieu");
        dictionary.put("recipe".toUpperCase(), "recette");
        dictionary.put("vegetables".toUpperCase(), "légumes");
        dictionary.put("tomato".toUpperCase(), "tomate");
        dictionary.put("dinner".toUpperCase(), "dîner");
        dictionary.put("pan".toUpperCase(), "poêle");

        // Politics related
        dictionary.put("Russia".toUpperCase(), "Russie");
        dictionary.put("Syria".toUpperCase(), "Syrie");
        dictionary.put("war".toUpperCase(), "guerre");
        dictionary.put("weapons".toUpperCase(), "arme");
        dictionary.put("politic".toUpperCase(), "politique");
        dictionary.put("president".toUpperCase(), "président");
        dictionary.put("white house".toUpperCase(), "Maison Blanche");
        dictionary.put("foreign policy".toUpperCase(), "politique étrangère");

        // Movie related
        dictionary.put("movie".toUpperCase(), "film");
        dictionary.put("cinema".toUpperCase(), "cinéma");
        dictionary.put("television".toUpperCase(), "télé");
        dictionary.put("blockbuster".toUpperCase(), "blockbuster");
        dictionary.put("actor".toUpperCase(), "acteur actrice");

        // Technology related
        dictionary.put("Programming".toUpperCase(), "programm");
        dictionary.put("AI Artificial Intelligence".toUpperCase(), "intelligence artificielle");
        dictionary.put("technology".toUpperCase(), "technologie");
    }
}
