package com.calendly.calendly.Model;

import com.calendly.calendly.View.Dialog;

public class InputCheck {

    public InputCheck() { }

    public boolean checkFloat(String newValue) {
        try {
            Double.parseDouble(newValue);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean checkNameLastname(String newValue) {
        if (newValue.length() < 3)
            return false;

        if (!newValue.matches("[a-zA-Z ]+[a-zA-Z ]")) {
            System.out.println("match -> false: " + newValue);
            return false;
        }

        return true;
    }

    public boolean checkDate(String toString) {
        return true;
        //todo checkdata
    }

}
