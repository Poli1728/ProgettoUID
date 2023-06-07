package com.calendly.calendly.Model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

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
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Appuntamenti(Data, CF_Utente, Id_Dipendente, Id_Servizio) VALUES(?,?, ?, ?)")) {
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

    public void inserimentoDipendenti(String Username, String Password, String Nome, String Cognome, Double Salario, Integer Ruolo) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Dipendenti(Username, Password, Nome, Cognome) VALUES(?,?, ?, ?, ?, ?)")) {
            pstmt.setString(1, Username);
            pstmt.setString(2, BCrypt.hashpw(Password, BCrypt.gensalt(12)));
            pstmt.setString(3, Nome);
            pstmt.setString(4, Cognome);
            pstmt.setDouble(5, Salario);
            pstmt.setInt(6, Ruolo);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public ArrayList<String> creaLista(boolean cerca, String filtro, String valore) throws SQLException {
        createConnection();
        ArrayList<String> risultato = new ArrayList<String>();
        String sql = "";
        if(cerca){
            sql = "Select A.Id, C.Email, C.Nome, C.Cognome, C.Numero, A.Data, D.Username, S.Tipo, S.Prezzo From Appuntamenti as A, Clienti as C, Dipendenti as D, Servizi as S Where A.CF_Utente = C.CF and A.Id_Dipendente = D.Id and A.Id_Servizio = S.id and "+filtro+" LIKE "+valore+";";//? LIKE ?;";
        }else{
            sql = "Select A.Id, C.Email, C.Nome, C.Cognome, C.Numero, A.Data, D.Username, S.Tipo, S.Prezzo From Appuntamenti as A, Clienti as C, Dipendenti as D, Servizi as S Where A.CF_Utente = C.CF and A.Id_Dipendente = D.Id and A.Id_Servizio = S.id;";
        }
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet query = stmt.executeQuery();
        String s= "";
        while(query.next()) {
            s+=query.getString("Id")+";"+query.getString("Email")+";"+query.getString("Nome")+";"+query.getString("Cognome")+";"+query.getString("Numero")+";"+query.getString("Data")+";"+query.getString("Username")+";"+query.getString("Tipo")+";"+query.getString("Prezzo");
            risultato.add(s);
            s = "";
        }
        stmt.close();
        closeConnection();
        return risultato;
    }

    public int numeroApp(String data) throws SQLException {
        createConnection();
        String sql = "Select Id From Appuntamenti Where Data LIKE ? ;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, data);
        ResultSet query = stmt.executeQuery();
        int s= 0;
        while(query.next()) {
            s+=1;
        }
        stmt.close();
        closeConnection();
        return s;
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }

}
