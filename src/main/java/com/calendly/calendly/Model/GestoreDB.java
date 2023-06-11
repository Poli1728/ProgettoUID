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
    public enum entità  {Dipendenti, Clienti, Appuntamenti, Servizi, Template};

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

    public void inserimento(entità ent, String [] info) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        switch (ent){
            case Dipendenti -> {
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Dipendenti(Username, Password, Nome, Cognome, Salario, Ruolo) VALUES(?,?, ?, ?, ?, ?);")) {
                    pstmt.setString(1, info[1]+"."+info[2]+"."+(conta("", false)+1));
                    pstmt.setString(2, BCrypt.hashpw(info[0], BCrypt.gensalt(12)));
                    pstmt.setString(3, info[1]);
                    pstmt.setString(4, info[2]);
                    pstmt.setDouble(5, Double.parseDouble(info[3]));
                    pstmt.setString(6, info[4]);
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Clienti -> {
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Clienti(CF, Email, Nome, Cognome, Numero) VALUES(?,?, ?, ?, ?);")) {
                    pstmt.setString(1, info[0]);
                    pstmt.setString(2, info[1]);
                    pstmt.setString(3, info[2]);
                    pstmt.setString(4, info[3]);
                    pstmt.setString(5, info[4]);
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Appuntamenti -> {
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Appuntamenti(Data, CF_Utente, Id_Dipendente, Id_Servizio) VALUES(?,?, ?, ?);")) {
                    pstmt.setString(1, info[0]);
                    pstmt.setString(2, info[1]);
                    pstmt.setInt(3, Integer.parseInt(info[2]));
                    pstmt.setInt(4, Integer.parseInt(info[3]));
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Servizi -> {
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Servizi(Tipo, Prezzo) VALUES(?,?);")) {
                    pstmt.setString(1, info[0]);
                    pstmt.setDouble(2, Double.parseDouble(info[1]));
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
        }

        closeConnection();
    }
    public void aggiornamento(entità ent, String [] info) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        switch (ent){
            case Dipendenti -> {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE Dipendenti SET Username LIKE ?, Password LIKE ?, Nome LIKE ?, Cognome LIKE ?, Salario = ?, Ruolo LIKE ? WHERE Id = ?;")) {
                    pstmt.setString(1, info [0]); // Username
                    pstmt.setString(2, BCrypt.hashpw(info[1], BCrypt.gensalt(12))); // Password
                    pstmt.setString(3, info[2]); // Nome
                    pstmt.setString(4, info[3]); // Cognome
                    pstmt.setDouble(5, Double.parseDouble(info[4])); // Salario
                    pstmt.setString(6, info[5]); // Ruolo
                    pstmt.setInt(7, Integer.parseInt(info[6])); // Id
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Clienti -> {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE Clienti SET CF LIKE ?, Email LIKE ?, Nome LIKE ?, Cognome LIKE ?, Numero LIKE ? WHERE CF LIKE ?;")) {
                    pstmt.setString(1, info[0]); // CF Nuovo
                    pstmt.setString(2, info[1]); // Email
                    pstmt.setString(3, info[2]); // Nome
                    pstmt.setString(4, info[3]); // Cognome
                    pstmt.setString(5, info[4]); // Numero
                    pstmt.setString(6, info[5]); // CF Vecchio
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Appuntamenti -> {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE Appuntamenti SET Data LIKE ?, CF_Utente = ?, Id_Dipendente = ?, Id_Servizio = ? WHERE Id = ?;")) {
                    pstmt.setString(1, info[0]); //Data
                    pstmt.setString(2, info[1]); // CF_Utente
                    pstmt.setInt(3, Integer.parseInt(info[2])); // Id_Dipendente
                    pstmt.setInt(4, Integer.parseInt(info[3])); // Id_Servizio
                    pstmt.setInt(5, Integer.parseInt(info[4])); // Id
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Servizi -> {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE Servizi SET Tipo LIKE ?, Prezzo = ? WHERE Id = ?;")) {
                    pstmt.setString(1, info[0]); // Tipo
                    pstmt.setDouble(2, Double.parseDouble(info[1])); // Prezzo
                    pstmt.setInt(2, Integer.parseInt(info[2])); // Id
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
            case Template -> {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE Template SET Tema = ?, Font = ? WHERE Id = 1;")) {
                    pstmt.setString(1, info[0]); //Tema
                    pstmt.setString(2, info[1]); //Font
                    pstmt.executeUpdate();
                } catch (SQLException e) {}
            }
        }
        closeConnection();
    }

    public String cercaValore (entità ent, String parametro, String chiave) throws SQLException {
        if(con == null || con.isClosed())
            createConnection();
        String query;
        if (!ent.equals(entità.Appuntamenti)) {
            query = "Select " + parametro + " From " + ent.toString() + " Where Id = " + chiave + ";";
        }else{
            query = "Select " + parametro + " From " + ent.toString() + " Where CF LIKE '" + chiave + "';";
        }
        System.out.println(query);
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        StringBuilder s= new StringBuilder();
        while(rs.next()) {
            s.append(rs.getString(parametro));
        }
        closeConnection();
        return s.toString();
    }

    public int conta(String data, boolean scelta) throws SQLException {
        createConnection();
        String sql;
        if (scelta) {
            sql = "Select Id From Appuntamenti Where Data LIKE ? ;";
        }else{
            sql = "Select Id From Dipendenti;";
        }
        PreparedStatement stmt = con.prepareStatement(sql);
        if(scelta){
            stmt.setString(1, data);
        }
        ResultSet query = stmt.executeQuery();
        int s= 0;
        while(query.next()) {
            s+=1;
        }
        stmt.close();
        closeConnection();
        return s;
    }

    public ArrayList<String> creaLista(boolean cerca, String filtro, String valore) throws SQLException {
        createConnection();
        ArrayList<String> risultato = new ArrayList<String>();
        String sql = "";
        if(cerca){
            sql = "Select A.Id, C.Email, C.Nome, C.Cognome, C.Numero, A.Data, D.Username, S.Tipo, S.Prezzo From Appuntamenti as A, Clienti as C, Dipendenti as D, Servizi as S Where A.CF_Utente = C.CF and A.Id_Dipendente = D.Id and A.Id_Servizio = S.id and "+filtro+" LIKE '"+valore+"';";
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
