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
    private enum entità  {Dipendenti, Clienti, Appuntamenti, Servizi, Template};

    public entità getDipendenti(){ return entità.Dipendenti;}
    public entità getClienti(){ return entità.Clienti;}
    public entità getAppuntamenti(){ return entità.Appuntamenti;}
    public entità getServizi(){ return entità.Servizi;}
    public entità getTemplate(){ return entità.Template;}
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
                    s.append(rs.getString("Id")).append(";").append(rs.getString("Username")).append(";").append(rs.getString("Nome")).append(";").append(rs.getString("Cognome")).append(";").append(rs.getString("Password")).append(";").append(rs.getString("Salario")).append(";").append(rs.getString("Ruolo"));
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
                case Template -> {
                    s.append(rs.getString("Tema")).append(";").append(rs.getString("Font")).append(";").append(rs.getString("Size"));
                    risultato.add(s.toString());
                    s = new StringBuilder();
                }
            }
        }
        stmt.close();
        closeConnection();
        return risultato;
    }

    private int numeroDip() throws SQLException {
        createConnection();
        String sql = "Select Id From Dipendenti;";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet query = stmt.executeQuery();
        int s= 0;
        while(query.next()) {
            s+=1;
        }
        stmt.close();
        closeConnection();
        return s;
    }

    public void inserimentoClienti(String CF, String Email, String Nome, String Cognome, String Numero) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Clienti(CF, Email, Nome, Cognome, Numero) VALUES(?,?, ?, ?, ?);")) {
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
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Appuntamenti(Data, CF_Utente, Id_Dipendente, Id_Servizio) VALUES(?,?, ?, ?);")) {
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
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Servizi(Tipo, Prezzo) VALUES(?,?);")) {
            pstmt.setString(1, Tipo);
            pstmt.setDouble(2, Prezzo);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public void inserimentoDipendenti(String Password, String Nome, String Cognome, Double Salario, String Ruolo) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Dipendenti(Username, Password, Nome, Cognome, Salario, Ruolo) VALUES(?,?, ?, ?, ?, ?);")) {
            pstmt.setString(1, Nome+"."+Cognome+"."+(numeroDip()+1));
            pstmt.setString(2, BCrypt.hashpw(Password, BCrypt.gensalt(12)));
            pstmt.setString(3, Nome);
            pstmt.setString(4, Cognome);
            pstmt.setDouble(5, Salario);
            pstmt.setString(6, Ruolo);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
        closeConnection();
    }

    public void aggiornaTemplate(String Tema, String Font) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        try (PreparedStatement pstmt = con.prepareStatement("UPDATE Template SET Tema = ?, Font = ? WHERE Id = 1;")) {
            pstmt.setString(1, Tema);
            pstmt.setString(2, Font);
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

    public boolean login(String username, String password) throws SQLException {
        createConnection();
        String sql = "Select * From Dipendenti Where Username LIKE ? and Ruolo LIKE 'Proprietario' or Ruolo LIKE 'Manager';";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet query = stmt.executeQuery();
        while(query.next()) {
            if (BCrypt.checkpw(password, query.getString("Password"))){
                stmt.close();
                closeConnection();
                return true;
            }
        }
        stmt.close();
        closeConnection();
        return false;
    }

    public void closeConnection() throws SQLException {
        if(con != null)
            con.close();
        con = null;
    }

}
