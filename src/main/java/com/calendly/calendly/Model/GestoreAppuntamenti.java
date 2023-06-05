package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreAppuntamenti {

    private static final GestoreAppuntamenti instance = new GestoreAppuntamenti();

    public static GestoreAppuntamenti getInstance() {
        return instance;
    }
    private GestoreAppuntamenti(){}

    public ArrayList<Appuntamento> listaAppuntamenti () throws SQLException {
        ArrayList <Appuntamento> lista = new ArrayList<Appuntamento>();
        ArrayList <String> query = GestoreDB.getInstance().creaLista();
        for (String i : query){
            String [] riga = i.split(";");
            Appuntamento a = new Appuntamento(Integer.parseInt(riga[0]), riga[1], riga[2],riga[3], riga[4], riga[5], riga[6], riga[7], Double.parseDouble(riga[8]));
            lista.add(a);
        }
        return lista;
    }

}
