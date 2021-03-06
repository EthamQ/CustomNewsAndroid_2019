package com.raphael.rapha.myNews.languages;

public class FrenchDictionary extends LanguageDictionary{

    public FrenchDictionary(){
        createDictionary();
    }

    /**
     * Puts the french translation for every relevant english word in the
     * dictionary hash map.
     * Doesn't always translate to the complete word to make it more likely
     * to find a variation of the word
     */
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
        dictionary.put("kitchen".toUpperCase(), "cuisine");

        // Politics related
        dictionary.put("Russia".toUpperCase(), "Russie");
        dictionary.put("Syria".toUpperCase(), "Syrie");
        dictionary.put("war".toUpperCase(), "guerre");
        dictionary.put("weapons".toUpperCase(), "arme");
        dictionary.put("politic".toUpperCase(), "politique");
        dictionary.put("president".toUpperCase(), "président");
        dictionary.put("white house".toUpperCase(), "Maison Blanche");
        dictionary.put("foreign policy".toUpperCase(), "politique étrangère");
        dictionary.put("treaty".toUpperCase(), "traité");
        dictionary.put("surveillance".toUpperCase(), "surveillance");
        dictionary.put("sanctions".toUpperCase(), "sanctions");

        dictionary.put("Communism".toUpperCase(), "communisme");
        dictionary.put("Law".toUpperCase(), "loi");
        dictionary.put("Capitalism".toUpperCase(), "capitalisme");
        dictionary.put("Socialism".toUpperCase(), "socialisme");
        dictionary.put("Dictator".toUpperCase(), "dictateur");
        dictionary.put("Democracy".toUpperCase(), "democracy");
        dictionary.put("Corruption".toUpperCase(), "corruption");
        dictionary.put("government".toUpperCase(), "gouvernement");
        dictionary.put("parliament".toUpperCase(), "parlement");
        dictionary.put("regime".toUpperCase(), "régime");
        dictionary.put("negotiation".toUpperCase(), "négociation");
        dictionary.put("diplomat".toUpperCase(), "diplomate ");

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
        dictionary.put("screen".toUpperCase(), "écran");
        dictionary.put("application".toUpperCase(), "application");

        //Sport related
        dictionary.put("Soccer".toUpperCase(), "foot");
        dictionary.put("Football".toUpperCase(), "Football");
        dictionary.put("Swimming".toUpperCase(), " nage");
        dictionary.put("Boxing".toUpperCase(), "boxe");
        dictionary.put("MMA".toUpperCase(), "MMA OR UFC");
        dictionary.put("Tennis".toUpperCase(), "tennis");
        dictionary.put("Hockey".toUpperCase(), "hockey");
        dictionary.put("Bike".toUpperCase(), "vélo OR bicyclette");
        dictionary.put("Hiking".toUpperCase(), "marche");
        dictionary.put("olympic".toUpperCase(), "olympien");
        dictionary.put("Rugby".toUpperCase(), "Rugby");
        dictionary.put("Baseball".toUpperCase(), "base-ball");
        dictionary.put("sport".toUpperCase(), "Sport");
        dictionary.put("score".toUpperCase(), "résultat");
        dictionary.put("tournament".toUpperCase(), "tournoi");
        dictionary.put("championship".toUpperCase(), "championnat");
        dictionary.put("race".toUpperCase(), "course");
        dictionary.put("athlete".toUpperCase(), "athlète");

        // Health related
        dictionary.put("Healthcare".toUpperCase(), "santé publique");
        dictionary.put("Fitness".toUpperCase(), "Fitness");
        dictionary.put("Disease".toUpperCase(), "maladie");
        dictionary.put("Virus".toUpperCase(), "Virus");
        dictionary.put("Nutrition".toUpperCase(), "nutrition");
        dictionary.put("Diet".toUpperCase(), "nourriture");
        dictionary.put("Vaccination".toUpperCase(), "vaccine");
        dictionary.put("Mental illness".toUpperCase(), "déséquilibr");
        dictionary.put("health".toUpperCase(), "santé");
        dictionary.put("doctor".toUpperCase(), "docteur");
        dictionary.put("exercise".toUpperCase(), "entraîn");
        dictionary.put("hospital".toUpperCase(), "hôpital");
        dictionary.put("pharmacy".toUpperCase(), "pharmacie");
        dictionary.put("medicare".toUpperCase(), "santé publique");
        dictionary.put("prevention".toUpperCase(), "prévent");
        dictionary.put("disorder".toUpperCase(), "handicap OR empêchement");
        dictionary.put("cancer".toUpperCase(), "cancer OR chancreu");
        dictionary.put("heart".toUpperCase(), "cœur");

        // Crime related
        dictionary.put("Murder".toUpperCase(), "meurtrier");
        dictionary.put("Police".toUpperCase(), "police");
        dictionary.put("Prison".toUpperCase(), "prison");
        dictionary.put("Terror".toUpperCase(), "terreur");
        dictionary.put("Mafia".toUpperCase(), "mafia");
        dictionary.put("Death".toUpperCase(), "mort");
        dictionary.put("kill".toUpperCase(), "tuer");
        dictionary.put("crime".toUpperCase(), "crime");
        dictionary.put("choke".toUpperCase(), "strangul");
        dictionary.put("steal".toUpperCase(), "vole");
        dictionary.put("shoot".toUpperCase(), "abattr");
        dictionary.put("felony".toUpperCase(), "délit");
        dictionary.put("rape".toUpperCase(), "viol");
        dictionary.put("fraud".toUpperCase(), "fraude");
        dictionary.put("misdemeanour".toUpperCase(), "infraction");
        dictionary.put("robbery".toUpperCase(), "attaque");
        dictionary.put("offence".toUpperCase(), "hôpital");
        dictionary.put("victim".toUpperCase(), "victime");
        dictionary.put("kidnap".toUpperCase(), "kidnapper");
        dictionary.put("plagiarize".toUpperCase(), "contrefai OR falsifi");
        dictionary.put("violent".toUpperCase(), "violence force");
        dictionary.put("gang".toUpperCase(), "Gang");
        dictionary.put("stab".toUpperCase(), "poignard");

        //Science related
        dictionary.put("Physics".toUpperCase(), "physique");
        dictionary.put("Climate change".toUpperCase(), "changement climatique");
        dictionary.put("Biology".toUpperCase(), "biologie");
        dictionary.put("Chemistry".toUpperCase(), "chimie");
        dictionary.put("Genetics".toUpperCase(), "génétique");
        dictionary.put("Mathematics".toUpperCase(), "mathématique");
        dictionary.put("Neuroscience".toUpperCase(), "neuroscience OR neurologie");
        dictionary.put("Engineering".toUpperCase(), "ingénieur");
        dictionary.put("cryptography".toUpperCase(), "cryptographie");
        dictionary.put("science".toUpperCase(), "science");
        dictionary.put("scientist".toUpperCase(), "scientifique");
        dictionary.put("study".toUpperCase(), "étude recherche");
        dictionary.put("atom".toUpperCase(), "l'atome");
        dictionary.put("laboratory".toUpperCase(), "laboratoire");
        dictionary.put("radioactive".toUpperCase(), "radioacti");
        dictionary.put("quantum".toUpperCase(), "quant");
    }
}
