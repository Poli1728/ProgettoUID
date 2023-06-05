package com.calendly.calendly.Model;

public class Appuntamento {
    private int id;
    private String email;
    private String identificativo;
    private String numero;
    private String data;
    private String dipendente;
    private String servizio;
    private double prezzo;
    public Appuntamento(int id, String email, String nome, String cognome, String numero, String data, String dipendente, String servizio, double prezzo){
        this.id = id;
        this.email = email;
        this.identificativo = nome + " " + cognome;
        this.numero = numero;
        this.data = data;
        this.dipendente = dipendente;
        this.servizio = servizio;
        this.prezzo = prezzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String orario) {
        this.data = data;
    }

    public String getDipendente() {
        return dipendente;
    }

    public void setDipendente(String dipendente) {
        this.dipendente = dipendente;
    }

    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificativo() {
        return identificativo;
    }

    public void setIdentificativo(String identificativo) {
        this.identificativo = identificativo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
