package com.calendly.calendly.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Dipendente {

    SimpleStringProperty id;
    SimpleStringProperty name;
    SimpleStringProperty lastName;
    SimpleStringProperty role;
    SimpleDoubleProperty salary;


    public Dipendente(String id, String name, String lastName, String role, Double salary) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.role = new SimpleStringProperty(role);
        this.salary = new SimpleDoubleProperty(salary);
    }

    public void setId(String id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public void setRole(String role) { this.role.set(role); }
    public void setSalary(double salary) {this.salary.set(salary); }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getLastName() { return lastName.get(); }
    public String getRole() { return role.get(); }
    public Double getSalary() { return salary.get(); }
}
