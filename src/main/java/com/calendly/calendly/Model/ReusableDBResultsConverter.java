package com.calendly.calendly.Model;

import com.calendly.calendly.Settings;

import java.util.ArrayList;
import java.util.LinkedList;

public class ReusableDBResultsConverter {

    private LinkedList<Servizi> servizi;
    private LinkedList<Dipendente> dipendenti;


    private static ReusableDBResultsConverter instance = new ReusableDBResultsConverter();
    public static ReusableDBResultsConverter getInstance() {
        return instance;
    }
    private ReusableDBResultsConverter() { }



    public LinkedList<Dipendente> getDipendenti(ArrayList<String> dbResults) {
        this.dipendenti = convertiFormatoGestoreDBToListDipendenti(dbResults);
        return dipendenti;
    }





    private LinkedList<Dipendente> convertiFormatoGestoreDBToListDipendenti(ArrayList<String> dbResults) {
        LinkedList<Dipendente> results = new LinkedList<>();
        for (String i : dbResults) {
            String[] rowValues = i.split(";");
            System.out.println(i);

            if (rowValues.length < Settings.CELL_DB_DIPENDENTI) {
                System.out.println("celle dipendenti < celle attese");
                continue;
            }

            notAvailable(rowValues);

            Dipendente dipendente = new Dipendente(rowValues[0],
                    rowValues[1],
                    rowValues[2],
                    rowValues[3],
                    rowValues[4]);

            results.add(dipendente);
        }

        return results;
    }

    private void notAvailable(String[] rowValues) {
        for(String v : rowValues)
            if (v.equals("null") || v.equals(""))
                v = "n/a";
    }


}
