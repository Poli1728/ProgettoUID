package com.calendly.calendly.View;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Dialog {

    private final static Dialog instance = new Dialog();
    private Dialog() { }
    public static Dialog getInstance() { return instance; }


    private AnchorPane anchorPaneFather;
    public void setAnchorPaneFather(AnchorPane anchorPaneFather) {
        this.anchorPaneFather = anchorPaneFather;
    }
    private boolean hasBeenSetUp = false;


    public enum from { APPUNTAMENTI, CLIENTI, DIPENDENTI, SERVIZI }
    public enum actions {AGGIUNGI(0), MODIFICA(1), RIMUOVI(2);
        actions(int i) { }
    }

    private String[] headerDescriptions = {
            "Compila campi sottostanti",
            "Modifica i campi sottostanti",
            "Conferma la rimozione dei dati sottostanti dal sistema" };

    private javafx.scene.control.Dialog<DialogResponse> dialog;
    private Button okButton;


    private void setDialog(from fromView, actions exeAction, Integer id) {
        javafx.scene.control.Dialog<DialogResponse> dialog = new javafx.scene.control.Dialog<>();
        this.dialog = dialog;
        dialog.setTitle("Calendly - " + exeAction.toString().toLowerCase());
        dialog.setHeaderText(headerDescriptions[exeAction.ordinal()]);

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        this.okButton = okButton;

        if (exeAction == actions.RIMUOVI){
            okButton.setText("Rimuovi");
            okButton.getStylesheets().add("-fx-background-color:systemGray5; -fx-text-fill:systemRed;");
        }

        VBox vbox = switch (fromView) {
            case DIPENDENTI -> setComponentsForDipendenti(fromView, exeAction, id);
            case APPUNTAMENTI -> setComponentsForAppuntamenti(fromView, exeAction, id);
            case CLIENTI -> setComponentsForClienti(fromView, exeAction, id);
            case SERVIZI -> setComponentsForServizi(fromView, exeAction, id);
        };

        dialogPane.setContent(vbox);
        dialogPane.getStyleClass().add("external-pane");

        for (String style : Settings.styles)
            dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

        //todo fare prendere da database il giusto tema
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(Settings.themes[0])).toExternalForm());

    }

    private VBox setComponentsForAppuntamenti(from fromView, actions exeAction, Integer id) {
        LinkedList<Node> nodes = new LinkedList<>();

        String[] labels = {
                "Data appuntamento",
                "Codice fiscale",
                "Dipendente",
                "Servizio"
        };

        TextField data = new TextField();
        nodes.add(data);
        configTextField(data, "Data appuntamento", 0, type.DATE);

        TextField cf = new TextField();
        nodes.add(cf);
        configTextField(cf, "Codice fiscale", 1, type.NAME_LASTNAME);

        ObservableList<String> optionsDip = FXCollections.observableArrayList();
        try {
            LinkedList<Dipendente> res_dip = ReusableDBResultsConverter.getInstance().getDipendenti(GestoreDB.getInstance().leggiEntità(GestoreDB.entità.Dipendenti));
            for (Dipendente d : res_dip) {
                optionsDip.add(d.getId() + " - " + d.getName() + " " + d.getLastName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ComboBox idDip = new ComboBox<>(optionsDip);
        nodes.add(idDip);
        idDip.getSelectionModel().selectLast();


        ObservableList<String> optionsServizi = FXCollections.observableArrayList();
        try {
            LinkedList<Servizio> res_serv = ReusableDBResultsConverter.getInstance().getServizi(GestoreDB.getInstance().leggiEntità(GestoreDB.entità.Servizi));
            for (Servizio d : res_serv) {
                optionsServizi.add(d.getId() + " - " + d.getTipo() + " " + d.getPrezzo());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ComboBox idServizio = new ComboBox<>(optionsServizi);
        nodes.add(idServizio);
        idServizio.getSelectionModel().selectLast();


        if (exeAction == actions.MODIFICA || exeAction == actions.RIMUOVI) {
            TextField idApp = new TextField();
            nodes.addFirst(idApp);
            configTextField(idApp, "id", 0, type.INT);
            //todo nel caso va aggiunto hbox con label a sx
        }

        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes, fromView, exeAction, id);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes, exeAction);

        return vbox;

    }

    private VBox setComponentsForClienti(from fromView, actions exeAction, Integer id) {
        String[] labels = {
                "Data appuntamento",
                "Codice fiscale",
                "Dipendente",
                "Servizio"
        };
        return null;
    }


    private enum stato { NEVER_CLICKED, CLICKED, FORM_CORRECT}
    private LinkedList<stato> clicked;
    private Label errors;

    private VBox setComponentsForDipendenti(from fromView, actions exeAction, Integer id) {
        LinkedList<Node> nodes = new LinkedList<>();

        String[] labels = {
                "Nome",
                "Cognome",
                "Ruolo",
                "Salario"
        };

        TextField name = new TextField();
        nodes.add(name);
        configTextField(name, labels[0], 0, type.NAME_LASTNAME);

        TextField lastName = new TextField();
        nodes.add(lastName);
        configTextField(lastName, labels[1], 1, type.NAME_LASTNAME);

        ObservableList<Settings.roles> options = FXCollections.observableArrayList(Settings.roles.values());
        ComboBox role = new ComboBox<>(options);
        nodes.add(role);
        role.getSelectionModel().selectLast();

        TextField salary = new TextField();
        nodes.add(salary);
        configTextField(salary, "Salario", 2, type.FLOAT);

        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes, fromView, exeAction, id);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes, exeAction);

        return vbox;
    }


    private VBox setComponentsForServizi(from fromView, actions exeAction, Integer id) {
        LinkedList<Node> nodes = new LinkedList<>();

        String[] labels = {
                "Servizio",
                "Prezzo",
        };

        TextField nomeServizio = new TextField();
        nodes.add(nomeServizio);
        configTextField(nomeServizio, labels[0], 0, type.LETTERS_NUMBERS_UNDERSCORE);

        TextField prezzo = new TextField();
        nodes.add(prezzo);
        configTextField(prezzo, labels[1], 1, type.FLOAT);

        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes, fromView, exeAction, id);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes, exeAction);

        return vbox;
    }

    private void ifInModificaMode(LinkedList<Node> nodes, from fromView, actions exeAction, int id) {

        if (exeAction == actions.MODIFICA && id > -1) {
            LinkedList res = dbResults(fromView, id);

            if (res.size() != 0) {

                if (res.get(0).getClass().equals(Servizio.class)) {
                    Servizio resServizio = (Servizio) res.get(0);
                    ((TextField) nodes.get(0)).setText(resServizio.getTipo());
                    ((TextField) nodes.get(1)).setText(resServizio.getPrezzo());
                }
                else if (res.get(0).getClass().equals(Dipendente.class)) {
                    Dipendente resDip = (Dipendente) res.get(0);
                    System.out.println("------- " + nodes);
                    ((TextField) nodes.get(0)).setText(resDip.getName());
                    ((TextField) nodes.get(1)).setText(resDip.getLastName());
                    ((ComboBox)  nodes.get(2)).getSelectionModel().select(resDip.getRole());
                    ((TextField) nodes.get(3)).setText(resDip.getSalary());
                }
                else if (res.get(0).getClass().equals(Cliente.class)) {
                    Cliente resCliente = (Cliente) res.get(0);
                    ((TextField) nodes.get(0)).setText(resCliente.getNome());
                    ((TextField) nodes.get(1)).setText(resCliente.getCognome());
                    ((TextField) nodes.get(2)).setText(resCliente.getNumero());
                    ((TextField) nodes.get(3)).setText(resCliente.getEmail());
                } else if (res.get(0).getClass().equals(Appuntamento.class)) {
                    Appuntamento app = (Appuntamento) res.get(0);
                    ((TextField) nodes.get(0)).setText(app.getData());
                    ((TextField) nodes.get(1)).setText(app.getCf());
                    ((TextField) nodes.get(2)).setText(app.getDipendente());
                    ((TextField) nodes.get(3)).setText(app.getServizio());
                    ((TextField) nodes.get(4)).setText(String.valueOf(app.getId()));
                }
            }


        } else if (id < -1) { //se in modifica con codice -1
            System.out.println("Probabile errore nel passaggio dell'id");
        }
    }


    private void setResultConverter(LinkedList<Node> nodes, from fromView, actions exeAction, int id) {
        dialog.setResultConverter((ButtonType bt) -> {

            if (bt == ButtonType.OK) {

                if (exeAction == actions.RIMUOVI){
                    try {
                        switch (fromView) {
                            case APPUNTAMENTI -> {
                                GestoreDB.getInstance().rimozione(GestoreDB.entità.Appuntamenti, String.valueOf(id)); }
                            case CLIENTI -> {
                                GestoreDB.getInstance().rimozione(GestoreDB.entità.Clienti, String.valueOf(id)); }
                            case DIPENDENTI -> {
                                GestoreDB.getInstance().rimozione(GestoreDB.entità.Dipendenti, String.valueOf(id)); }
                            case SERVIZI -> {
                                GestoreDB.getInstance().rimozione(GestoreDB.entità.Servizi, String.valueOf(id)); }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }


                LinkedList<String> res = new LinkedList<>();
                for(Node node : nodes) {
                    if (node.getClass().equals(TextField.class)) {
                        TextField tf = (TextField) node;
                        res.add(tf.getText());
                    } else if (node.getClass().equals(ComboBox.class)) {
                        ComboBox cb = (ComboBox) node;
                        res.add(cb.getSelectionModel().getSelectedItem().toString());
                    }
                }

                if (exeAction == actions.MODIFICA) {
                    try {
                        res.add(String.valueOf(id));
                        switch (fromView) {

                            case APPUNTAMENTI -> {
                                GestoreDB.getInstance().aggiornamento(GestoreDB.entità.Appuntamenti, res.toArray(new String[res.size()]));
                            }
                            case CLIENTI -> {
                                GestoreDB.getInstance().aggiornamento(GestoreDB.entità.Clienti, res.toArray(new String[res.size()]));
                            }
                            case DIPENDENTI -> {
                                GestoreDB.getInstance().aggiornamento(GestoreDB.entità.Dipendenti, res.toArray(new String[res.size()]));
                            }
                            case SERVIZI -> {
                                GestoreDB.getInstance().aggiornamento(GestoreDB.entità.Servizi, res.toArray(new String[res.size()]));
                            }
                        }
                        res.removeLast();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (exeAction == actions.AGGIUNGI) {
                    try {
                        switch (fromView) {
                            case APPUNTAMENTI -> {
                                GestoreDB.getInstance().inserimento(GestoreDB.entità.Appuntamenti, res.toArray(new String[res.size()]));
                            }
                            case CLIENTI -> {
                                GestoreDB.getInstance().inserimento(GestoreDB.entità.Clienti, res.toArray(new String[res.size()]));
                            }
                            case DIPENDENTI -> {
                                res.addFirst(""); //password vuota in posizione 0
                                GestoreDB.getInstance().inserimento(GestoreDB.entità.Dipendenti, res.toArray(new String[res.size()]));
                            }
                            case SERVIZI -> {
                                GestoreDB.getInstance().inserimento(GestoreDB.entità.Servizi, res.toArray(new String[res.size()]));
                            }
                        }
                        res.removeLast();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                FXMLLoader loader = null;
                try {
                    switch (fromView) {
                        case APPUNTAMENTI -> {
                            loader = SceneHandler.getInstance().creaPane("fxml/Appuntamenti");
                        }
                        case CLIENTI -> {
                            loader = SceneHandler.getInstance().creaPane("fxml/Clienti");
                        }
                        case DIPENDENTI -> {
                            loader = SceneHandler.getInstance().creaPane("fxml/Dipendenti");

                        }
                        case SERVIZI -> {
                            loader = SceneHandler.getInstance().creaPane("fxml/Servizi");

                        }
                    }
                } catch (IOException e) {

                }

                Pane pane = null;
                try {
                    pane = (Pane) loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Pane viewPane = SceneHandler.getInstance().getPaneRightContainerContent();
                viewPane.getChildren().clear();

                pane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());

                Pane finalPane = pane;
                viewPane.layoutBoundsProperty().addListener(obs -> {
                    finalPane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());
                });

                Dialog.getInstance().setAnchorPaneFather(anchorPaneFather);
                viewPane.getChildren().add(pane);

                return new DialogResponse(res);
            }


            return null;
        });
    }


    private VBox externalVbox(LinkedList<Node> nodes, actions exeActions) {
        VBox vbox = new VBox(8);

        if (exeActions == actions.RIMUOVI)
            doNotAllowChanges(nodes);

        for (Node node : nodes)
            vbox.getChildren().add(node);
        errors.getStyleClass().add("errors-label");
        vbox.getChildren().add(errors);

        return vbox;
    }

    private LinkedList dbResults(from fromView, Integer id) {

        LinkedList res = null;
        try {

            res = switch (fromView) {
                case APPUNTAMENTI -> null;
                case CLIENTI -> null;
                case DIPENDENTI ->
                        ReusableDBResultsConverter.getInstance().getDipendenti(
                            new ArrayList<>(
                                    Collections.singleton(
                                            GestoreDB.getInstance().cercaRiga(
                                                    GestoreDB.getInstance().getDipendenti(),
                                                    String.valueOf(id))))
                        );
                case SERVIZI ->
                        ReusableDBResultsConverter.getInstance().getServizi(
                                new ArrayList<>(
                                        Collections.singleton(
                                                GestoreDB.getInstance().cercaRiga(
                                                        GestoreDB.getInstance().getServizi(),
                                                        String.valueOf(id))))
                        );
            };

        } catch (SQLException e) {
            throw new RuntimeException(e);
            //todo alert
        }

        return res;
    }


    private void configTextField(TextField l, String promptText, int index, type tipo) {
        l.setPromptText(promptText);
        l.getStyleClass().add("generalField");
        l.textProperty().addListener((observableValue, s, t1) -> {
            okButton.setDisable(!checkField(l, index, t1, tipo));
            System.out.println("------------");
        });


    }


    //todo return boolean per poi inserire nei vari controller se bisogna aggiornare la tabella o meno.
    //prende come parametro l'attuale ancorPane della view
    public Optional<DialogResponse> requestDialog(from fromView, actions exeAction, Integer id, AnchorPane anchorPane)  { //prende come parametri i nomi in minuscolo delle singole opzioni da richedere all'utente

        if (anchorPaneFather == null || anchorPane == null)
            return null;    //se null, non è stato possibile effettuare la richiesta
        //todo aggiungere alert


        Pane rightHomePane = (Pane) anchorPane.getParent();
        if (!hasBeenSetUp) {
            setUpBlurEffect(rightHomePane);
        }

        setDialog(fromView, exeAction, id);

        Optional<DialogResponse> optionalResult = dialog.showAndWait();
        disableBlurEffect(rightHomePane);

        //aggiungo ciò che l'utente ha messo nelle textField nell'ordine in cui vengono passati in params
        return optionalResult;

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



    //per il comboBox deve essere sempre selezionata tramite codice un'opzione di partenza (la più mediamente utilizzata)
    public enum type {FLOAT, NAME_LASTNAME, INT, LETTERS_NUMBERS_UNDERSCORE, DATE}
    private String[] errorMessages = {
            "Inserire un numero intero o decimale valido",
            "Può contenere solo lettere e spazi",
            "Formato data errato",
    };
    private boolean checkField(Node node, int index, String newValue, type tipo) {

        if (clicked.get(index) == stato.NEVER_CLICKED)
            clicked.set(index, stato.CLICKED);

        if (!newValue.toString().equals("")) {
            boolean statoRes = switch (tipo) {
                case FLOAT -> new InputCheck().checkFloat(newValue);
                case NAME_LASTNAME -> new InputCheck().checkNameLastname(newValue);
                case INT -> new InputCheck().checkInt(newValue);
                case LETTERS_NUMBERS_UNDERSCORE -> new InputCheck().checkLettersNumbersUnderscore(newValue);
                case DATE -> new InputCheck().checkDate(newValue);
            };

            if (statoRes)
                clicked.set(index, stato.FORM_CORRECT);
            else
                clicked.set(index, stato.CLICKED);

            StringBuilder e = new StringBuilder(); //todo che farne di sta var / occuparsi della questione errori di inserimento

            int count = 0;

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

    private void doNotAllowChanges(LinkedList<Node> nodes) {
        for(Node node : nodes) {
            if (node.getClass().equals(TextField.class)) {
                TextField textField = (TextField) node;
                textField.setEditable(false);
                continue;
            }

            else if (node.getClass().equals(ComboBox.class)) {
                ComboBox comboBox = (ComboBox) node;
                comboBox.setEditable(false);
            }

            else
                System.out.println("Dialog.doNotAllowChanges() if-else impostato male");
        }
    }

    private void initializeClickedList(LinkedList<Node> nodes, int size) {
        clicked = new LinkedList<>();
        for(int i = 0; i < size; i++)
            if (nodes.get(i).getClass().equals(TextField.class))
                clicked.add(stato.NEVER_CLICKED);
    }


}
