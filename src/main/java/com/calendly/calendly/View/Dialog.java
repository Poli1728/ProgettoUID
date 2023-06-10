package com.calendly.calendly.View;

import com.calendly.calendly.Model.DialogResponse;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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


    public enum from { APPUNTAMENTI, DIPENDENTI, SERVIZI, SIGNUP }

    private javafx.scene.control.Dialog<DialogResponse> dialog;

    private Button okButton;
    private void setDialog(from fromView) {
        javafx.scene.control.Dialog<DialogResponse> dialog = new javafx.scene.control.Dialog<>();
        this.dialog = dialog;
        dialog.setTitle("Calendly - Aggiungi");
        dialog.setHeaderText("Completa i seguenti campi:");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        this.okButton = okButton;

        VBox vbox = switch (fromView) {
            case DIPENDENTI -> setComponentsForDipendenti();
            case APPUNTAMENTI -> setComponentsForAppuntamenti();
            case SERVIZI -> setComponentsForServizi();
            case SIGNUP -> setComponentsForRegistrazione();
        };

        dialogPane.setContent(vbox);
        dialogPane.getStyleClass().add("external-pane");

        for (String style : Settings.styles)
            dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

        //todo fare prendere da database il giusto tema
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(Settings.themes[0])).toExternalForm());


    }

    private VBox setComponentsForRegistrazione() {
        return null;
    }

    private VBox setComponentsForServizi() {
        return null;
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

    private enum stato { NEVER_CLICKED, CLICKED, FORM_CORRECT}
    private LinkedList<stato> clicked;
    private Label errors;

    private VBox setComponentsForDipendenti() {
        initializeClickedList(3);
        TextField name = new TextField();
        name.setPromptText("Nome");
        name.getStyleClass().add("generalField");
        name.requestFocus();
        name.textProperty().addListener((observableValue, s, t1) -> {
            okButton.setDisable(!allFieldsFilled(name, 0, t1, type.NAME_LASTNAME));
            System.out.println(clicked.get(0));
            System.out.println(clicked.get(1));
            System.out.println(clicked.get(2));
            System.out.println("------------");

        });

        TextField lastName = new TextField();
        lastName.setPromptText("Cognome");
        lastName.getStyleClass().add("generalField");
        lastName.textProperty().addListener((observableValue, s, t1) -> {
            okButton.setDisable(!allFieldsFilled(lastName, 1, t1, type.NAME_LASTNAME));
            System.out.println(clicked.get(0));
            System.out.println(clicked.get(1));
            System.out.println(clicked.get(2));
            System.out.println("------------");
        });


        ObservableList<Settings.roles> options = FXCollections.observableArrayList(Settings.roles.values());
        ComboBox role = new ComboBox<>(options);
        role.getSelectionModel().selectLast();

        TextField salary = new TextField();
        salary.setPromptText("Salario");
        salary.getStyleClass().add("generalField");
        salary.textProperty().addListener((observableValue, s, t1) -> {
            okButton.setDisable(!allFieldsFilled(salary, 2, t1, type.FLOAT));
            System.out.println(clicked.get(0));
            System.out.println(clicked.get(1));
            System.out.println(clicked.get(2));
            System.out.println("------------");
        });


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

        VBox vbox = new VBox(8,
                createHbox("Nome", name),
                createHbox("Cognome", lastName),
                createHbox("Ruolo", role),
                createHbox("Salario", salary)
        );

        Label errors = new Label();
        this.errors = errors;
        errors.getStyleClass().add("errors-label");
        vbox.getChildren().add(errors);
        return vbox;
    }


    //todo return boolean per poi inserire nei vari controller se bisogna aggiornare la tabella o meno.
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


    private HBox createHbox(String labelText, Node node) {
        Label label = new Label(labelText);
        label.setPrefWidth(65);
        label.setAlignment(Pos.CENTER_LEFT);
        return new HBox(20, label, node);
    }


    //per il comboBox deve essere sempre selezionata tramite codice un'opzione di partenza (la più mediamente utilizzata)
    public enum type {FLOAT, NAME_LASTNAME, DATE}
    private String[] errorMessages = {
            "Inserire un numero intero o decimale valido",
            "Può contenere solo lettere e spazi",
            "Formato data errato",
    };
    private boolean allFieldsFilled(Node node, int index, String newValue, type tipo) {

        if (clicked.get(index) == stato.NEVER_CLICKED)
            clicked.set(index, stato.CLICKED);

        if (!newValue.toString().equals("")) {
            switch (tipo) {
                case FLOAT -> checkFloat(index, newValue.toString());
                case NAME_LASTNAME -> checkNameLastname(index, newValue.toString());
                case DATE -> checkDate(index, newValue.toString());
            }

            StringBuilder e = new StringBuilder();
            int count = 0;
            int i = 0;
            for(stato state : clicked) {
                if (state == stato.FORM_CORRECT)
                    count++;
            }

            if (count == clicked.size()) {
                return true;
            }
            
        } else
            clicked.set(index, stato.CLICKED);
        return false;
    }

    private boolean checkFloat(int index, String newValue) {
        try {
            Double.parseDouble(newValue);
            clicked.set(index, stato.FORM_CORRECT);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    private boolean checkNameLastname(int index, String newValue) {
        if (newValue.length() < 2)
            return false;

        else if (newValue.matches("[a-zA-Z ]+[a-zA-Z ]")) {
            System.out.println(newValue);
            clicked.set(index, stato.FORM_CORRECT);
            return true;
        }

        return false;
    }

    private boolean checkDate(int index, String toString) {
        return true;
        //todo checkdata
    }

    private void initializeClickedList(int size) {
        clicked = new LinkedList<>();
        for(int i = 0; i < size; i++)
            clicked.add(stato.NEVER_CLICKED);
    }


}
