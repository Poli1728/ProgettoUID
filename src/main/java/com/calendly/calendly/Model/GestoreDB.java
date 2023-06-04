package com.calendly.calendly.Model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
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
    public enum entità  {
            Dipendenti,
            Clienti,
            Appuntamenti,
            Servizi
    };
    public ArrayList<String> leggiEntità(entità ent) throws SQLException {
        createConnection();
        String query = "select * from "+ent.toString()+";";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        StringBuilder s= new StringBuilder();
        ArrayList<String> risultato = new ArrayList<String>();
        while(rs.next()) {
            switch (ent) {
                case Dipendenti -> {
                    s.append(rs.getString("Id")).append(";").append(rs.getString("Username")).append(";").append(rs.getString("Nome")).append(";").append(rs.getString("Cognome")).append(";").append(rs.getString("Password"));
                    risultato.add(s.toString());
                    s = new StringBuilder();
                }
                case Clienti -> {
                    s.append(rs.getString("CF")).append(";").append(rs.getString("Email")).append(";").append(rs.getString("Nome")).append(";").append(rs.getString("Cognome")).append(";").append(rs.getString("Numero"));
                    risultato.add(s.toString());
                    s = new StringBuilder();
                }
                case Appuntamenti -> {
                    s.append(rs.getString("Id")).append(";").append(rs.getString("Data")).append(";").append(rs.getString("CF_Utente")).append(";").append(rs.getString("Id_Dipendente")).append(";").append(rs.getString("Id_Servizio"));
                    risultato.add(s.toString());
                    s = new StringBuilder();
                }
                case Servizi -> {
                    s.append(rs.getString("Id")).append(";").append(rs.getString("Tipo")).append(";").append(rs.getString("Prezzo"));
                    risultato.add(s.toString());
                    s = new StringBuilder();
                }
            }
        }
        stmt.close();
        closeConnection();
        return risultato;
    }

    public void inserimentoClienti(String CF, String Email, String Nome, String Cognome, String Numero) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Clienti(CF, Email, Nome, Cognome, Numero) VALUES(?,?, ?, ?, ?)")) {
            pstmt.setString(1, CF);
            pstmt.setString(2, Email);
            pstmt.setString(3, Nome);
            pstmt.setString(4, Cognome);
            pstmt.setString(4, Numero);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public void inserimentoAppuntamenti(Date Data, String CF, Integer id_d, Integer id_s) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Appuntamenti(Data, CF_Utente, Id_Dipendenti, Id_Servizio) VALUES(?,?, ?, ?)")) {
            pstmt.setDate(1, Data);
            pstmt.setString(2, CF);
            pstmt.setInt(3, id_d);
            pstmt.setInt(4, id_s);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();

    }

    public void inserimentoServizi(String Tipo, Double Prezzo) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Servizi(Tipo, Prezzo) VALUES(?,?)")) {
            pstmt.setString(1, Tipo);
            pstmt.setDouble(2, Prezzo);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public void inserimentoDipendenti(String Username, String Password, String Nome, String Cognome) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Dipendenti(Username, Password, Nome, Cognome) VALUES(?,?, ?, ?)")) {
            pstmt.setString(1, Username);
            pstmt.setString(2, Password);
            pstmt.setString(3, Nome);
            pstmt.setString(4, Cognome);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public ResultSet eseguiQuery(String query) throws SQLException {
        createConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        stmt.close();
        closeConnection();
        return rs;
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }

}
