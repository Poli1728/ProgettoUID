package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreGuadagno {

    private static final GestoreGuadagno instance = new GestoreGuadagno();

    public static GestoreGuadagno getInstance() {
        return instance;
    }
    private GestoreGuadagno(){}

    public ArrayList<Guadagno> guadagno(String data) throws SQLException {
        ArrayList<Guadagno> info = new ArrayList<Guadagno>();
        info.add(new Guadagno(data, GestoreDB.getInstance().conta(data, 0)));
        return info;
    }
}
