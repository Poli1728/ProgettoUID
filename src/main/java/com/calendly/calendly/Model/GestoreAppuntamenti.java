package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreAppuntamenti {

    private static final GestoreAppuntamenti instance = new GestoreAppuntamenti();

    public static GestoreAppuntamenti getInstance() {
        return instance;
    }
    private GestoreAppuntamenti(){}

    public ArrayList<Appuntamento> listaAppuntamenti (boolean cerca, String filtro, String valore) throws SQLException {
        ArrayList <Appuntamento> lista = new ArrayList<Appuntamento>();
        ArrayList <String> query = GestoreDB.getInstance().creaLista(cerca,filtro, valore);
        for (String i : query){
            Appuntamento a = new Appuntamento(i.split(";"));
            lista.add(a);

        }
        return lista;
    }

    public int numeroAppuntamenti(String data) throws SQLException {
        int numero = GestoreDB.getInstance().numeroApp(data);
        return numero;
    }

}
