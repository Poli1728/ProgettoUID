package com.calendly.calendly.View;

import com.calendly.calendly.Main;
import com.calendly.calendly.Model.Cliente;
import com.calendly.calendly.Model.Dipendente;
import com.calendly.calendly.Model.Servizio;
import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.Objects;


public class Card extends AnchorPane {
    private Integer identifier;
    private VBox parent;
    private Object obj;

    private String IMAGE_USER = "img/user.png";
    private String IMAGE_SERVIZI = "img/task.png";


    public Card(Object obj, VBox parent) {
        super();
        this.maxWidth(100);
        this.prefWidth(100);

        this.parent = parent;
        this.obj = obj;

        setLayout();
    }



    private void setLayout() {

        //Le variabili da impostare nell'if-elseif-else per riconoscere la classe
        String identifier = null;
        String imagePath = null;
        Dialog.from from = null;
        //-----------------------------------------


        LinkedList<Label> labelsKey = new LinkedList<>();
        LinkedList<Label> labelsValue = new LinkedList<>();

        VBox vboxKeys = new VBox(1);
        VBox vboxValues = new VBox(1);
        LinkedList[] groups = new LinkedList[]{labelsKey, labelsValue};


        if (obj.getClass().equals(Dipendente.class)) {
            imagePath = IMAGE_USER ;
            from = Dialog.from.DIPENDENTI;
            Dipendente dip = (Dipendente) obj;
            identifier = dip.getId();

            createLabelsLines(groups, "ID:", dip.getId());
            createLabelsLines(groups, "Dipendente:", dip.getName() + " " + dip.getLastName());
            createLabelsLines(groups, "Ruolo", dip.getRole());
            createLabelsLines(groups, "Salario", dip.getSalary());

        } else if (obj.getClass().equals(Servizio.class)) {
            imagePath = IMAGE_SERVIZI;
            from = Dialog.from.SERVIZI;
            Servizio serv = (Servizio) obj;
            identifier = serv.getId();

            createLabelsLines(groups, "ID:", serv.getId());
            createLabelsLines(groups, "Servizio:", serv.getTipo());
            createLabelsLines(groups, "Prezzo", serv.getPrezzo());

        } else if (obj.getClass().equals(Cliente.class)) {
            imagePath = IMAGE_USER;
            from = Dialog.from.CLIENTI;
            Cliente cliente = (Cliente) obj;
            identifier = cliente.getCF();
            
            createLabelsLines(groups, "Codice Fiscale:", cliente.getCF());
            createLabelsLines(groups, "Nome:", cliente.getNome());
            createLabelsLines(groups, "Cognome:", cliente.getCognome());
            createLabelsLines(groups, "Cellulare:", cliente.getNumero());
            createLabelsLines(groups, "Email:", cliente.getEmail());

        } else {
            System.out.println("Imposta bene il riconoscimento degli oggetti in Card.setLayout()");
            return;
        }




        // ------------------- NON TOCCARE SOTTO --------------------

        CustomButton edit = new CustomButton("Modifica","img/edit.png", "Modifica i dati della scheda selezionata");
        edit.getStyleClass().add("thirdButton");


        String finalIdentifier = identifier;
        Dialog.from finalFrom = from;

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Dialog.getInstance().requestDialog(
                        finalFrom,
                        Dialog.actions.MODIFICA,
                        finalIdentifier,
                        (AnchorPane) parent.getParent().getParent().getParent().getParent() );
            }
        });


        ImageView imageView = new ImageView();
        try {
            Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            imageView.setImage(image);
            imageView.setFitWidth(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            SceneHandler.getInstance().generaAlert("Qualcosa è andato storto!", false);
        }



        HBox hBox = new HBox(imageView, vboxKeys, vboxValues);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0, 0, 0, 5));


        for (LinkedList g : groups)
            for (Object l : g) {
                Label label = (Label) l;
                label.setFont(new Font(15));
                label.setWrapText(false);
                label.maxWidthProperty().bind(parent.widthProperty().divide(4.5));
                label.getStyleClass().add("text-ellipsis");
                label.setTextOverrun(OverrunStyle.ELLIPSIS);

                if (groups[0].equals(g))
                    vboxKeys.getChildren().add(label);
                else
                    vboxValues.getChildren().add(label);
                }

        vboxKeys.setFillWidth(true);
        vboxValues.setFillWidth(true);

        vboxKeys.setPadding(new Insets(15, 5, 15, 15));
        vboxValues.setPadding(new Insets(15, 15, 15, 5));

        
        this.getChildren().addAll(edit, hBox);
        AnchorPane.setRightAnchor(edit, hBox.getWidth()+1);
        AnchorPane.setTopAnchor(edit, 1.0);
        edit.toFront();
        this.prefWidthProperty().bind(parent.widthProperty().divide(2).subtract(15));

        this.getStyleClass().add("card-info");
    }


    private void createLabelsLines(LinkedList[] list, String key, String value) {
        //keylist
        list[0].add(new Label(key));
        //keyvalue
        list[1].add(new Label(value));

    }

}
