package com.calendly.calendly.Model;

public class Dipendente {

    private String id;
    private String name;
    private String lastName;

    private boolean useDefaultPic = true; //altrimenti cerca nelle immagini


    public Dipendente(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getLastName() { return lastName; }
}
