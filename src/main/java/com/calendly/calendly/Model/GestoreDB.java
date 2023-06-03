package com.calendly.calendly.Model;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class GestoreDB {
    private static final GestoreDB instance = new GestoreDB();

    public static GestoreDB getInstance() {
        return instance;
    }
    private GestoreDB(){}

    private Connection con = null;

    public void createConnection() throws SQLException {
        File file = new File("src/main/resources/com/calendly/calendly/db/progetto.db");
        String url = "jdbc:sqlite:"+file.getAbsolutePath();
        con = DriverManager.getConnection(url);
        if (con != null && !con.isClosed())
            System.out.println("Connected!");
    }

    public void provaQuery() throws SQLException {
        createConnection();
        String query = "select * from Utenti;";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            String email = rs.getString("Email");
            String cf = rs.getString("CF");
            System.out.println("Id: " + cf + " last name: " + email);
        }
        stmt.close();
        closeConnection();
    }

    public void queryUtenti() throws SQLException {
        createConnection();
        String query = "select * from Utenti;";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            String email = rs.getString("Email");
            String cf = rs.getString("CF");
            System.out.println("Id: " + cf + " last name: " + email);
        }
        stmt.close();
        closeConnection();
    }
    public void queryDipendenti() throws SQLException {
        createConnection();
        String query = "select * from Dipendenti;";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Results:");
        while(rs.next()) {
            String email = rs.getString("Email");
            String cf = rs.getString("CF");
            System.out.println("Id: " + cf + " last name: " + email);
        }
        stmt.close();
        closeConnection();
    }
    public void queryPrenotazioni() throws SQLException {
        createConnection();
        String query = "select * from Prenotazioni;";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Results:");
        while(rs.next()) {
            String email = rs.getString("Email");
            String cf = rs.getString("CF");
            System.out.println("Id: " + cf + " last name: " + email);
        }
        stmt.close();
        closeConnection();
    }
    public void queryServizi() throws SQLException {
        createConnection();
        String query = "select * from Utenti;";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Results:");
        while(rs.next()) {
            String email = rs.getString("Email");
            String cf = rs.getString("CF");
            System.out.println("Id: " + cf + " last name: " + email);
        }
        stmt.close();
        closeConnection();
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }

}
