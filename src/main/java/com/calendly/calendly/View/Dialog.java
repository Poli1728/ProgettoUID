package com.calendly.calendly.View;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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


    public enum from { APPUNTAMENTI, DIPENDENTI, SERVIZI, SIGNUP }
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

        VBox vbox = switch (fromView) {
            case DIPENDENTI -> setComponentsForDipendenti(fromView, exeAction, id);
            case APPUNTAMENTI -> setComponentsForAppuntamenti(fromView, exeAction, id);
            case SERVIZI -> setComponentsForServizi(fromView, exeAction, id);
            case SIGNUP -> null; //todo da togliore o aggiungere
        };

        dialogPane.setContent(vbox);
        dialogPane.getStyleClass().add("external-pane");

        for (String style : Settings.styles)
            dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

        //todo fare prendere da database il giusto tema
        dialogPane.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(Settings.themes[0])).toExternalForm());


    }


    private VBox setComponentsForServizi(actions exeAction) {
        return null;
    }

    private VBox setComponentsForAppuntamenti(from fromView, actions exeAction, Integer id) {
        VBox vbox = new VBox();
        return vbox;
    }

    private enum stato { NEVER_CLICKED, CLICKED, FORM_CORRECT}
    private LinkedList<stato> clicked;
    private Label errors;

    private VBox setComponentsForDipendenti(from fromView, actions exeAction, Integer id) {
        LinkedList<Node> nodes = new LinkedList<>();

        TextField name = new TextField();
        nodes.add(name);
        configTextField(name, "Nome", 0, type.NAME_LASTNAME);

        TextField lastName = new TextField();
        nodes.add(lastName);
        configTextField(lastName, "Cognome", 1, type.NAME_LASTNAME);

        ObservableList<Settings.roles> options = FXCollections.observableArrayList(Settings.roles.values());
        ComboBox role = new ComboBox<>(options);
        nodes.add(role);
        role.getSelectionModel().selectLast();

        TextField salary = new TextField();
        nodes.add(salary);
        configTextField(salary, "Salario", 2, type.FLOAT);

        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes);

        return vbox;
    }


    private VBox setComponentsForServizi(from fromView, actions exeAction, Integer id) {
        LinkedList<Node> nodes = new LinkedList<>();

        TextField nomeServizio = new TextField();
        nodes.add(nomeServizio);
        configTextField(nomeServizio, "Servizio", 0, type.NAME_LASTNAME);

        TextField prezzo = new TextField();
        nodes.add(prezzo);
        configTextField(prezzo, "Prezzo", 1, type.FLOAT);

        initializeClickedList(nodes, nodes.size());
        ifInModificaMode(nodes, fromView, exeAction, id);
        setResultConverter(nodes);

        Label errors = new Label();
        this.errors = errors;

        VBox vbox = externalVbox(nodes);

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
                    ((TextField) nodes.get(0)).setText(resCliente.getCF());
                    ((TextField) nodes.get(1)).setText(resCliente.getNome());
                    ((TextField) nodes.get(2)).setText(resCliente.getCognome());
                    ((TextField) nodes.get(3)).setText(resCliente.getNumero());
                    ((TextField) nodes.get(4)).setText(resCliente.getEmail());
                }
            }


        } else if (id < -1) { //se in modifica con codice -1
            System.out.println("Probabile errore nel passaggio dell'id");
        }
    }


    private void setResultConverter(LinkedList<Node> nodes) {
        dialog.setResultConverter((ButtonType bt) -> {
            if (bt == ButtonType.OK) {
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
                return new DialogResponse(res);
            }
            return null;
        });
    }


    private VBox externalVbox(LinkedList<Node> nodes) {
        VBox vbox = new VBox(8);
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
                case SIGNUP -> null;
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
            System.out.println(clicked.get(0));
            System.out.println(clicked.get(1));
            System.out.println(clicked.get(2));
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
    private boolean checkField(Node node, int index, String newValue, type tipo) {

        if (clicked.get(index) == stato.NEVER_CLICKED)
            clicked.set(index, stato.CLICKED);

        if (!newValue.toString().equals("")) {
            boolean statoRes = switch (tipo) {
                case FLOAT -> new InputCheck().checkFloat(newValue);
                case NAME_LASTNAME -> new InputCheck().checkNameLastname(newValue);
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
