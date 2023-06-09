package com.calendly.calendly.View;

import com.calendly.calendly.Model.DialogResponse;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class Dialog {

    private final static Dialog instance = new Dialog();
    private boolean hasBeenSetUp = false;
    private Dialog() { }
    public static Dialog getInstance() { return instance; }

    private AnchorPane anchorPaneFather;

    public void setAnchorPaneFather(AnchorPane anchorPaneFather) {
        this.anchorPaneFather = anchorPaneFather;
    }

    public enum from {
        APPUNTAMENTI,
        DIPENDENTI
    }

    private AnchorPane popupContainer = new AnchorPane();
    private javafx.scene.control.Dialog<DialogResponse> dialog;

    private void setDialog(from fromView) {
        javafx.scene.control.Dialog<DialogResponse> dialog = new javafx.scene.control.Dialog<>();
        this.dialog = dialog;
        dialog.setTitle("Calendly - Aggiungi");
        dialog.setHeaderText("Completa i seguenti campi:");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox vbox = switch (fromView) {
            case DIPENDENTI -> setComponentsForDipendenti();
            case APPUNTAMENTI -> setComponentsForAppuntamenti();
        };
        dialogPane.setContent(vbox);
        dialogPane.getStyleClass().add("external-pane");

        for (String style : Settings.styles)
            dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

        //todo fare prendere da database il giusto tema
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(Settings.themes[0])).toExternalForm());



    }

    private VBox setComponentsForAppuntamenti() {
        VBox vbox = new VBox();
        //add here your components like textfields, datapicker, etc.
        //dialog.setResultConverter((ButtonType bt) -> {
            //if (bt == ButtonType.OK) {
                //return new DialogResponse()
            //}
        //});
        return vbox;
    }

    private VBox setComponentsForDipendenti() {
        TextField name = new TextField();
        name.setPromptText("Nome");
        name.getStyleClass().add("generalField");

        TextField lastName = new TextField();
        lastName.setPromptText("Cognome");
        lastName.getStyleClass().add("generalField");


        ObservableList<Settings.roles> options = FXCollections.observableArrayList(Settings.roles.values());
        ComboBox role = new ComboBox<>(options);
        role.getSelectionModel().selectLast();

        TextField salary = new TextField();
        salary.setPromptText("Salario");
        salary.getStyleClass().add("generalField");


        dialog.setResultConverter((ButtonType bt) -> {
            if (bt == ButtonType.OK) {
                LinkedList<String> res = new LinkedList<>();
                res.add(name.getText());
                res.add(lastName.getText());
                res.add(role.getValue().toString());
                res.add(salary.getText());
                return new DialogResponse(res);
            }
            return null;
        });

        VBox vbox = new VBox(8, name, lastName, role , salary);
        return vbox;
    }

    //prende come parametro l'attuale ancorPane della view
    public Optional<DialogResponse> requestDialog(from fromView, AnchorPane anchorPane)  { //prende come parametri i nomi in minuscolo delle singole opzioni da richedere all'utente
        if (anchorPaneFather == null || anchorPane == null)
            return null;    //se null, non è stato possibile effettuare la richiesta


        Pane rightHomePane = (Pane) anchorPane.getParent();
        if (!hasBeenSetUp) {
            setUpBlurEffect(rightHomePane);
        }

        setDialog(fromView);
        Optional<DialogResponse> optionalResult = dialog.showAndWait();
        disableBlurEffect(rightHomePane);


        //aggiungo ciò che l'utente ha messo nelle textField nell'ordine in cui vengono passati in params
        return optionalResult;

        //todo fare direttamente in .setResultConverter il salvataggio nel db + reload della tabella

    }

    private void setUpBlurEffect(Pane rightHomePane) {
        hasBeenSetUp = true;
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        rightHomePane.setEffect(adj);
        rightHomePane.setMouseTransparent(true);
    }

    private void disableBlurEffect(Pane rightHomePane) {
        hasBeenSetUp = false;
        rightHomePane.setEffect(null);
        rightHomePane.setMouseTransparent(false);

    }
}
