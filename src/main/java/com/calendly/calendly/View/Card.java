package com.calendly.calendly.View;

import com.calendly.calendly.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Objects;

public class Card extends AnchorPane {

    private AnchorPane card;
    private Integer identifier;
    private VBox parent;
    public enum cardType {PERSON, EMPLOYEE}
    public Card(cardType type, int identifier, VBox parent) {
        this.parent = parent;
        this.identifier = identifier;
        this.card = createCard(type);
    }
    public AnchorPane getCard() {
        return card;
    }

    private AnchorPane createCard(cardType type) {
        return switch (type) {
            case PERSON -> null;
            case EMPLOYEE -> createEmployee();
        };
    }

    private AnchorPane createEmployee() {
        Button edit = new Button("Modifica");
        edit.getStyleClass().add("thirdButton");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/user.png")));
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //GestoreDB.getInstance().cercaValore(GestoreDB.entità.Dipendenti, identifier.toString(), )
        String id = "11", nome = "Mario Giordano", cognome = "Bruno", ruolo = "Developer", salario = "3500.00";


        Label primaRiga = new Label("ID: \t" + id);
        Label secondaRiga = new Label("Dipendente: \t" + nome + " " + cognome);
        Label terzaRiga = new Label("Ruolo: \t" + ruolo);
        Label quartaRiga = new Label("Salario: \t" + salario);

        primaRiga.setFont(new Font(14));
        secondaRiga.setFont(new Font(14));
        terzaRiga.setFont(new Font(14));
        quartaRiga.setFont(new Font(14));


        VBox vbox = new VBox(1,
                primaRiga,
                secondaRiga,
                terzaRiga,
                quartaRiga
        );

        vbox.setFillWidth(true);
        Insets insets = new Insets(20, 15, 20, 15);
        vbox.setPadding(insets);

        HBox hBox = new HBox(imageView, vbox);

        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(edit, hBox);
        AnchorPane.setRightAnchor(edit, hBox.getWidth());
        AnchorPane.setTopAnchor(edit, 0.0);
        edit.toFront();
        ap.prefWidthProperty().bind(parent.widthProperty().divide(2).subtract(30));

        ap.getStyleClass().add("card-info");

        return ap;
    }


}
