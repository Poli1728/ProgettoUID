package com.calendly.calendly;

import com.calendly.calendly.Controller.DipendentiViewController;
import com.calendly.calendly.Controller.LoginController;
import com.calendly.calendly.Controller.WelcomePageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SceneHandler {
    private final boolean FIRST_TIME_OPENED = true;  //TODO da spostare tramite lettura in DB o altro

    private static final SceneHandler instance = new SceneHandler();
    private Stage stage;
    private static Scene scene;
    private FXMLLoader loader;

    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {
    }


    public void init(Stage stage) throws SQLException {
        if (this.stage == null) {
            this.stage = stage;

            stage.setTitle(Settings.INIT_TITLE);
            launchWelcomeFirstOpening();
            stage.setScene(scene);
            loadStyle();

            stage.show();
        }
    }

    private void loadFXML(String resourceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));

            if (scene == null)
                scene = new Scene(fxmlLoader.load());
            else
                scene.setRoot(fxmlLoader.load());

            loader = fxmlLoader;

        } catch (IOException e) {

        }
    }


    public void launchWelcomeFirstOpening() {
        //da eseguire solo alla prima apertura dell'app
        //mostrare un mini welcome
        //creazione dell'account dell'owner

        loadFXML("fxml/WelcomePage.fxml");
        WelcomePageController controller = loader.getController();
        setWindowLoginDimension();
    }

    public void launchCreateAccountOwner() {
        //da utilizzare esclusivamente in contemporanea con launchTutorialFirstOpening()
    }

    public void launchLogin() {
        loadFXML("fxml/LoginView.fxml");
        LoginController controller = loader.getController();
        setWindowLoginDimension();
    }


    public void launchSideBar() {
        //da vedere se passare height and width come params per scegliere se utilizzare la sBar con solo icone o con icone+testo
    }


    public void launchDashboard(){
        setWindowAppDimension();
        loadFXML("fxml/HomeView.fxml");
    }

    public FXMLLoader creaPane(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneHandler.class.getResource(s + "View.fxml"));
        return loader;
    }

    private void setWindowLoginDimension() {
        stage.setMinHeight(Settings.MIN_WINDOW_HEIGHT);
        stage.setMinWidth(Settings.MIN_WINDOW_WIDTH_LOGIN);
        stage.setHeight(Settings.DEFAULT_WINDOW_HEIGHT);
        stage.setWidth(Settings.DEFAULT_WINDOW_WIDTH);
        stage.setResizable(true);
    }

    private void setWindowAppDimension() {
        stage.setMinHeight(Settings.MIN_WINDOW_HEIGHT);
        stage.setMinWidth(Settings.MIN_WINDOW_WIDTH_APP);
        stage.setHeight(Settings.DEFAULT_WINDOW_HEIGHT);
        stage.setWidth(Settings.DEFAULT_WINDOW_WIDTH);
        stage.setResizable(true);
    }

    private void loadStyle() {
        for (String font : Settings.fonts)
            Font.loadFont(Objects.requireNonNull(SceneHandler.class.getResource(font)).toExternalForm(), 10);

        for (String style : Settings.styles)
            scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

        Settings.theme defaultTheme = Settings.theme.DARK; //da db
        setTheme(defaultTheme);
    }



    public void setTheme(Settings.theme theme) {

        String pathTheme;

        pathTheme = switch (theme) {
            case DARK -> Settings.themes[0];
            case LIGHT -> Settings.themes[1];

            default -> Settings.themes[0];
        };


        if (scene.getStylesheets().contains(Objects.requireNonNull(SceneHandler.class.getResource(pathTheme)).toExternalForm())) {
            //Tema già impostato, non fare nulla
            //todo evitare di far selezionare all'utente il tema già scelto
            return;
        }

        //todo altrimenti togli il vecchio (prendi il dato da db) e metti il nuovo
        scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(pathTheme)).toExternalForm());
    }

    public String apriFileChooser(){
        FileChooser fileChooser = new FileChooser();
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("IMAGES FILES", ".jpg", ".png"));
        fileChooser.setTitle("Scegli immagine");
        String name = fileChooser.showOpenDialog(stage).getAbsolutePath();
        return name;
    }

}