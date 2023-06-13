package com.calendly.calendly.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {

    private String CF;
    private String email;
    private String nome;
    private String cognome;
    private String numero;


    public Cliente(String CF, String email, String nome, String cognome, String numero) {
        this.CF = CF;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.numero = numero;
    }


    //setters - supposed to update in db also
    public void setCF(String CF) { this.CF = CF; }
    public void setEmail(String email) { this.email = email; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setNumero(String numero) {this.numero = numero; }


    public String getCF() { return CF; }
    public String getEmail() { return email; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getNumero() { return numero; }
}
