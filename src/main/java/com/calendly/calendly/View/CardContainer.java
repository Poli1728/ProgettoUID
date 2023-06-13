package com.calendly.calendly.View;

import com.calendly.calendly.Model.Dipendente;
import com.calendly.calendly.Model.Servizi;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class CardContainer {
    private static CardContainer istance = new CardContainer();

    private CardContainer() { }

    public static CardContainer getInstance() {
        return istance;
    }


    public <T> void setCardContainer(LinkedList<T> res, VBox vboxEsterno) {
        if (res.size() != 0)
            for (int i = 0; i < res.size(); i = i+2) {
                Card obj1 = new Card(res.get(i), vboxEsterno);

                HBox hbox1 = new HBox(10, obj1);

                Card obj2;
                if (i+1 < res.size()) {
                    obj2 = new Card(res.get(i + 1), vboxEsterno);
                    hbox1.getChildren().add(obj2);
                }

                hbox1.setFillHeight(true);
                hbox1.setAlignment(Pos.BASELINE_LEFT);

                vboxEsterno.getChildren().add(hbox1);
            }
        else {
            //todo put a label saying no results
        }
    }





}
