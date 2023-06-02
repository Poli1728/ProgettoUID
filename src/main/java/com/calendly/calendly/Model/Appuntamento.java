package com.calendly.calendly.Model;

public class Appuntamento {
    private String email;
    private String orario;
    private String dipendente;
    private String servizio;

    public Appuntamento(String e, String o, String d, String s){
        email = e;
        orario = o;
        dipendente = d;
        servizio = s;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
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
}
