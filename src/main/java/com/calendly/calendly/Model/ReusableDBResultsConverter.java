package com.calendly.calendly.Model;

import com.calendly.calendly.Settings;

import java.util.ArrayList;
import java.util.LinkedList;

public class ReusableDBResultsConverter {

    private LinkedList<Servizi> servizi;
    private LinkedList<Dipendente> dipendenti;
    //private LinkedList<Cliente> clienti;


    private static ReusableDBResultsConverter instance = new ReusableDBResultsConverter();
    public static ReusableDBResultsConverter getInstance() {
        return instance;
    }
    private ReusableDBResultsConverter() { }



    public LinkedList<Dipendente> getDipendenti(ArrayList<String> dbResults) {
        this.dipendenti = convertiFormatoGestoreDBToListDipendenti(dbResults);
        return dipendenti;
    }

    /*public LinkedList<Cliente> getClienti(ArrayList<String> dbResults) {
        this.clienti = convertiFormatoGestoreDBToListClienti(dbResults);
        return clienti;
    }*/



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

    /*private LinkedList<Cliente> convertiFormatoGestoreDBToListClienti(ArrayList<String> dbResults) {
        LinkedList<Cliente> results = new LinkedList<>();
        for (String i : dbResults) {
            String[] rowValues = i.split(";");
            System.out.println(i);
            Cliente cliente = new Cliente(rowValues[0],
                    rowValues[1],
                    rowValues[2],
                    rowValues[3],
                    rowValues[4]);

            results.add(cliente);
        }

        return results;
    }*/

    private void notAvailable(String[] rowValues) {
        for(int i = 0; i < rowValues.length; i++)
            if (rowValues[i].equals("null") || rowValues[i].isEmpty())
                rowValues[i] = "n/a";
    }


}
