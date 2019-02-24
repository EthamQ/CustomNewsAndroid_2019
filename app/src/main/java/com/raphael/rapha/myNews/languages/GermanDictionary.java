package com.raphael.rapha.myNews.languages;

public class GermanDictionary extends LanguageDictionary{

    public GermanDictionary(){
        this.createDictionary();
    }


    /**
     * Puts the german translation for every relevant english word in the
     * dictionary hash map.
     * Doesn't always translate to the complete word to make it more likely
     * to find a variation of the word
     */
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
        dictionary.put("kitchen".toUpperCase(), "Küche");

        // Politics related
        dictionary.put("Russia".toUpperCase(), "Russland");
        dictionary.put("Syria".toUpperCase(), "Syrien");
        dictionary.put("war".toUpperCase(), "Krieg");
        dictionary.put("weapons".toUpperCase(), "Waffen");
        dictionary.put("politic".toUpperCase(), "Politik");
        dictionary.put("president".toUpperCase(), "President");
        dictionary.put("white house".toUpperCase(), "Weißes Haus");
        dictionary.put("foreign policy".toUpperCase(), "Außenpolitik");
        dictionary.put("treaty".toUpperCase(), "Abkommen");
        dictionary.put("surveillance".toUpperCase(), "Überwachung");
        dictionary.put("europe".toUpperCase(), "Europa");
        dictionary.put("sanctions".toUpperCase(), "Sanktionen");
        dictionary.put("Communism".toUpperCase(), "Kommunismus");
        dictionary.put("Law".toUpperCase(), "Gesetz");
        dictionary.put("Capitalism".toUpperCase(), "Kapitalismus");
        dictionary.put("Socialism".toUpperCase(), "Sozialismus");
        dictionary.put("Dictator".toUpperCase(), "Diktator");
        dictionary.put("Democracy".toUpperCase(), "Demokratie");
        dictionary.put("Corruption".toUpperCase(), "Korruption");
        dictionary.put("government".toUpperCase(), "Regierung");
        dictionary.put("parliament".toUpperCase(), "Parlament");
        dictionary.put("regime".toUpperCase(), "Regime");
        dictionary.put("negotiation".toUpperCase(), "Verhandlung");
        dictionary.put("diplomat".toUpperCase(), "Diplomat");

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
        dictionary.put("screen".toUpperCase(), "Bildschirm");
        dictionary.put("drone".toUpperCase(), "Drohne");
        dictionary.put("application".toUpperCase(), "Applikation");

        //Sport related
        dictionary.put("Soccer".toUpperCase(), "Fußball");
        dictionary.put("Football".toUpperCase(), "Football");
        dictionary.put("Swimming".toUpperCase(), "Schwimmen");
        dictionary.put("Boxing".toUpperCase(), "Boxen");
        dictionary.put("MMA".toUpperCase(), "MMA");
        dictionary.put("Tennis".toUpperCase(), "Tennis");
        dictionary.put("Hockey".toUpperCase(), "Hockey");
        dictionary.put("Bike".toUpperCase(), "Fahrrad");
        dictionary.put("Hiking".toUpperCase(), "Wandern");
        dictionary.put("olympic".toUpperCase(), "Olympisch");
        dictionary.put("Rugby".toUpperCase(), "Rugby");
        dictionary.put("Baseball".toUpperCase(), "Baseball");
        dictionary.put("sport".toUpperCase(), "Sport");
        dictionary.put("score".toUpperCase(), "Ergebnis");
        dictionary.put("tournament".toUpperCase(), "Turnier");
        dictionary.put("championship".toUpperCase(), "Meisterschaft");
        dictionary.put("race".toUpperCase(), "Rennen");
        dictionary.put("athlete".toUpperCase(), "Athlet");

        // Health related
        dictionary.put("Healthcare".toUpperCase(), "Gesundheitsversorgung");
        dictionary.put("Fitness".toUpperCase(), "Fitness");
        dictionary.put("Disease".toUpperCase(), "Krankheit");
        dictionary.put("Virus".toUpperCase(), "Virus");
        dictionary.put("Nutrition".toUpperCase(), "Ernährun");
        dictionary.put("Diet".toUpperCase(), "Nahrung");
        dictionary.put("Vaccination".toUpperCase(), "Impfen");
        dictionary.put("Mental illness".toUpperCase(), "psychisch krank");
        dictionary.put("health".toUpperCase(), "Gesundheit");
        dictionary.put("doctor".toUpperCase(), "Arzt Ärzte");
        dictionary.put("exercise".toUpperCase(), "trainieren");
        dictionary.put("hospital".toUpperCase(), "Krankenhaus");
        dictionary.put("pharmacy".toUpperCase(), "Pharma");
        dictionary.put("medicare".toUpperCase(), "Gesundheitssystem");
        dictionary.put("prevention".toUpperCase(), "vorbeug");
        dictionary.put("disorder".toUpperCase(), "Behinderung");
        dictionary.put("cancer".toUpperCase(), "Krebs");
        dictionary.put("heart".toUpperCase(), "Herz");

        // Crime related
        dictionary.put("Murder".toUpperCase(), "ermorden Mörder");
        dictionary.put("Police".toUpperCase(), "Polizei");
        dictionary.put("Prison".toUpperCase(), "Gefängnis");
        dictionary.put("Terror".toUpperCase(), "Terror");
        dictionary.put("Mafia".toUpperCase(), "Mafia");
        dictionary.put("Death".toUpperCase(), "Tod");
        dictionary.put("kill".toUpperCase(), "töten");
        dictionary.put("crime".toUpperCase(), "Verbrechen");
        dictionary.put("choke".toUpperCase(), "erwürg");
        dictionary.put("steal".toUpperCase(), "stehlen stiehl Diebstahl");
        dictionary.put("shoot".toUpperCase(), "erschießen erschoss schießen");
        dictionary.put("felony".toUpperCase(), "Straftat");
        dictionary.put("rape".toUpperCase(), "Vergewaltig");
        dictionary.put("fraud".toUpperCase(), "Betrug betrüg");
        dictionary.put("misdemeanour".toUpperCase(), "Ordnungswidrigkeit");
        dictionary.put("robbery".toUpperCase(), "Überfall");
        dictionary.put("offence".toUpperCase(), "Krankenhaus");
        dictionary.put("victim".toUpperCase(), "Opfer");
        dictionary.put("kidnap".toUpperCase(), "entführ");
        dictionary.put("plagiarize".toUpperCase(), "fälschung fälschen");
        dictionary.put("violent".toUpperCase(), "gewalt");
        dictionary.put("gang".toUpperCase(), "Gang");
        dictionary.put("stab".toUpperCase(), "erstechen ersticht erstach");

        //Science related
        dictionary.put("Physics".toUpperCase(), "Physik");
        dictionary.put("Climate change".toUpperCase(), "Klimawandel");
        dictionary.put("Biology".toUpperCase(), "Biologie");
        dictionary.put("Chemistry".toUpperCase(), "Chemie");
        dictionary.put("Genetics".toUpperCase(), "Genetik genetisch");
        dictionary.put("Mathematics".toUpperCase(), "Mathematik");
        dictionary.put("Neuroscience".toUpperCase(), "Neurolog");
        dictionary.put("Engineering".toUpperCase(), "Ingenieur");
        dictionary.put("cryptography".toUpperCase(), "Kryptografie");
        dictionary.put("science".toUpperCase(), "Wissenschaft");
        dictionary.put("scientist".toUpperCase(), "Wissenschaftler");
        dictionary.put("study".toUpperCase(), "Studie");
        dictionary.put("atom".toUpperCase(), "Atom");
        dictionary.put("laboratory".toUpperCase(), "Labor");
        dictionary.put("radioactiv".toUpperCase(), "radioaktiv");
        dictionary.put("quantum".toUpperCase(), "quantum");
    }
}
