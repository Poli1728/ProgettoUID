package com.calendly.calendly.View;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    private void setDialog(from fromView, actions exeAction, String id) {
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

    private VBox setComponentsForAppuntamenti(from fromView, actions exeAction, String id) {
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

        System.out.println("set 1");

        ObservableList<String> optionsClienti = FXCollections.observableArrayList();
        LinkedList<Cliente> res_clienti = ReusableDBResultsConverter.getInstance().getClienti((ArrayList<String>) GestoreDbThreaded.getInstance().runQuery(1, GestoreDB.entità.Clienti, null));
        for (Cliente d : res_clienti) {
            optionsClienti.add(d.getCF() + " - " + d.getNome() + " " + d.getCognome());
        }
        ComboBox idClienti = new ComboBox<>(optionsClienti);
        nodes.add(idClienti);
        idClienti.setPromptText("Scegli un cliente");

        System.out.println("set 2");

        ObservableList<String> optionsDip = FXCollections.observableArrayList();
        LinkedList<Dipendente> res_dip = ReusableDBResultsConverter.getInstance().getDipendenti((ArrayList<String>) GestoreDbThreaded.getInstance().runQuery(1, GestoreDB.entità.Dipendenti, null));
        for (Dipendente d : res_dip) {
            optionsDip.add(d.getId() + " - " + d.getName() + " " + d.getLastName());
        }
        ComboBox idDip = new ComboBox<>(optionsDip);
        nodes.add(idDip);
        idDip.getSelectionModel().selectLast();

        System.out.println("set 3");


        ObservableList<String> optionsServizi = FXCollections.observableArrayList();
        LinkedList<Servizio> res_serv = ReusableDBResultsConverter.getInstance().getServizi((ArrayList<String>) GestoreDbThreaded.getInstance().runQuery(1, GestoreDB.entità.Servizi, null));
        for (Servizio d : res_serv) {
            optionsServizi.add(d.getId() + " - " + d.getTipo() + " " + d.getPrezzo());
        }
        ComboBox idServizio = new ComboBox<>(optionsServizi);
        nodes.add(idServizio);
        idServizio.getSelectionModel().selectLast();

        System.out.println("set 4");


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

    private VBox setComponentsForClienti(from fromView, actions exeAction, String id) {
        LinkedList<Node> nodes = new LinkedList<>();

        String[] labels = {
                "Codice Fiscale",
                "Nome",
                "Cognome",
                "Numero",
                "Email"
        };


        TextField cf = new TextField();
        nodes.add(cf);
        configTextField(cf, labels[0], 0, type.NAME_LASTNAME);

        TextField nome = new TextField();
        nodes.add(nome);
        configTextField(nome, labels[1], 1, type.NAME_LASTNAME);

        TextField cognome = new TextField();
        nodes.add(cognome);
        configTextField(cognome, labels[2], 2, type.NAME_LASTNAME);

        TextField numero = new TextField();
        nodes.add(numero);
        configTextField(numero, labels[3], 3, type.INT);

        TextField email = new TextField();
        nodes.add(email);
        configTextField(email, labels[4], 4, type.EMAIL);


        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes, fromView, exeAction, id);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes, exeAction);

        return vbox;
    }


    private enum stato { NEVER_CLICKED, CLICKED, FORM_CORRECT}
    private LinkedList<stato> clicked;
    private Label errors;

    private VBox setComponentsForDipendenti(from fromView, actions exeAction, String id) {
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


    private VBox setComponentsForServizi(from fromView, actions exeAction, String id) {
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

    private void ifInModificaMode(LinkedList<Node> nodes, from fromView, actions exeAction, String id) {

        if (exeAction == actions.MODIFICA ) {
            LinkedList res = dbResults(fromView, id);

            if (res.size() != 0) {
                if (res.get(0).getClass().equals(Servizio.class)) {
                    Servizio resServizio = (Servizio) res.get(0);
                    ((TextField) nodes.get(0)).setText(resServizio.getTipo());
                    ((TextField) nodes.get(1)).setText(resServizio.getPrezzo());
                }
                else if (res.get(0).getClass().equals(Dipendente.class)) {
                    Dipendente resDip = (Dipendente) res.get(0);
                    ((TextField) nodes.get(0)).setText(resDip.getName());
                    ((TextField) nodes.get(1)).setText(resDip.getLastName());
                    ((ComboBox)  nodes.get(2)).getSelectionModel().select(resDip.getRole());
                    ((TextField) nodes.get(3)).setText(resDip.getSalary());
                }
                else if (res.get(0).getClass().equals(Cliente.class)) {
                    Cliente resCliente = (Cliente) res.get(0);
                    ((TextField) nodes.get(0)).setText(resCliente.getCF());
                    ((TextField) nodes.get(1)).setText(resCliente.getNome());
                    ((TextField) nodes.get(2)).setText(resCliente.getCognome());
                    ((TextField) nodes.get(3)).setText(resCliente.getNumero());
                    ((TextField) nodes.get(4)).setText(resCliente.getEmail());
                } else if (res.get(0).getClass().equals(Appuntamento.class)) {
                    Appuntamento app = (Appuntamento) res.get(0);
                    ((DatePicker) nodes.get(0)).setValue(LocalDate.parse(app.getData()));
                    ((TextField) nodes.get(1)).setText(app.getCf());
                    ((TextField) nodes.get(2)).setText(app.getDipendente());
                    ((TextField) nodes.get(3)).setText(app.getServizio());
                    ((TextField) nodes.get(4)).setText(String.valueOf(app.getId()));
                }
            }


        }
    }


    private void setResultConverter(LinkedList<Node> nodes, from fromView, actions exeAction, String id) {
        dialog.setResultConverter((ButtonType bt) -> {

            if (bt == ButtonType.OK) {

                if (exeAction == actions.RIMUOVI){
                    String[] parametri = {String.valueOf(id)};
                    switch (fromView) {
                        case APPUNTAMENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(4, GestoreDB.entità.Appuntamenti, parametri);
                        }
                        case CLIENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(4, GestoreDB.entità.Clienti, parametri);
                        }
                        case DIPENDENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(4, GestoreDB.entità.Dipendenti, parametri);
                        }
                        case SERVIZI -> {
                            GestoreDbThreaded.getInstance().runQuery(4, GestoreDB.entità.Servizi, parametri);
                        }
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
                    } else if (node.getClass().equals(DatePicker.class)) {
                        DatePicker dp = (DatePicker) node;
                        res.add(dp.getValue().format(DateTimeFormatter.ofPattern("dd/mm/YYYY hh:mm")));
                        System.out.println(dp.getValue().format(DateTimeFormatter.ofPattern("dd/mm/YYYY hh:mm")));
                    }
                }

                if (exeAction == actions.MODIFICA) {
                    res.add(String.valueOf(id));
                    switch (fromView) {

                        case APPUNTAMENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(3, GestoreDB.entità.Appuntamenti, res.toArray(new String[res.size()]));
                        }
                        case CLIENTI -> {
                            for (String s: res)
                                System.out.println("Sono qui:" + s);
                            GestoreDbThreaded.getInstance().runQuery(3, GestoreDB.entità.Clienti, res.toArray(new String[res.size()]));
                        }
                        case DIPENDENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(3, GestoreDB.entità.Dipendenti, res.toArray(new String[res.size()]));
                        }
                        case SERVIZI -> {
                            GestoreDbThreaded.getInstance().runQuery(3, GestoreDB.entità.Servizi, res.toArray(new String[res.size()]));
                        }
                    }
                    res.removeLast();

                }

                if (exeAction == actions.AGGIUNGI) {

                    switch (fromView) {
                        case APPUNTAMENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(2, GestoreDB.entità.Appuntamenti, res.toArray(new String[res.size()]));
                        }
                        case CLIENTI -> {
                            GestoreDbThreaded.getInstance().runQuery(2, GestoreDB.entità.Clienti, res.toArray(new String[res.size()]));
                        }
                        case DIPENDENTI -> {
                            res.addFirst(""); //password vuota in posizione 0
                            GestoreDbThreaded.getInstance().runQuery(2, GestoreDB.entità.Dipendenti, res.toArray(new String[res.size()]));
                        }
                        case SERVIZI -> {
                            GestoreDbThreaded.getInstance().runQuery(2, GestoreDB.entità.Servizi, res.toArray(new String[res.size()]));
                        }
                    }
                    res.removeLast();

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

    private LinkedList dbResults(from fromView, String id) {

        LinkedList res = null;
        String [] parametri = {String.valueOf(id)};
        res = switch (fromView) {
            case APPUNTAMENTI ->
                ReusableDBResultsConverter.getInstance().getAppuntamenti(
                    new ArrayList<>(Collections.singleton((String)GestoreDbThreaded.getInstance().runQuery(7, GestoreDB.entità.Appuntamenti, parametri)))
                );
            case CLIENTI ->
                ReusableDBResultsConverter.getInstance().getClienti(
                    new ArrayList<>(Collections.singleton((String)GestoreDbThreaded.getInstance().runQuery(7, GestoreDB.entità.Clienti, parametri)))
                );
            case DIPENDENTI ->
                ReusableDBResultsConverter.getInstance().getDipendenti(
                    new ArrayList<>(Collections.singleton((String)GestoreDbThreaded.getInstance().runQuery(7, GestoreDB.entità.Dipendenti, parametri)))
                );
            case SERVIZI ->
                ReusableDBResultsConverter.getInstance().getServizi(
                    new ArrayList<>(Collections.singleton((String)GestoreDbThreaded.getInstance().runQuery(7, GestoreDB.entità.Servizi, parametri)))
                );
        };

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
    public Optional<DialogResponse> requestDialog(from fromView, actions exeAction, String id, AnchorPane anchorPane)  { //prende come parametri i nomi in minuscolo delle singole opzioni da richedere all'utente

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
    public enum type {FLOAT, NAME_LASTNAME, INT, LETTERS_NUMBERS_UNDERSCORE, EMAIL, DATE}
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
                case EMAIL -> new InputCheck().checkEmail(newValue);
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
