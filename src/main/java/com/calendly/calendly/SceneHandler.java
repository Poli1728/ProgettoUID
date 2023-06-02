package com.calendly.calendly;

import com.calendly.calendly.Controller.HomeController;
import com.calendly.calendly.Controller.LoginController;
import com.calendly.calendly.Controller.WelcomePageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneHandler {

    private final String INIT_TITLE = "Calendly";
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


    public void init(Stage stage) {
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle(this.INIT_TITLE);
            launchWelcomeFirstOpening();
            this.stage.setScene(scene);
            loadStyle();
            this.stage.show();
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
        setWindowDefaultDimension();
        controller.init(stage);
    }

    public void launchCreateAccountOwner() {
        //da utilizzare esclusivamente in contemporanea con launchTutorialFirstOpening()
    }

    public void launchLogin() {
        loadFXML("fxml/LoginView.fxml");
        LoginController controller = loader.getController();
        controller.init(stage);

    }


    public void launchSideBar() {
        //da vedere se passare height and width come params per scegliere se utilizzare la sBar con solo icone o con icone+testo
    }

    public void launchDashboard() {
    }

    public void launchHome(){
        loadFXML("fxml/HomeView.fxml");
    }

    public Pane creaPane(String s) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneHandler.class.getResource(s+"View.fxml"));
        Pane cerca= (Pane) loader.load();
        return cerca;
    }

    private void setWindowDefaultDimension()  {
        stage.setMinHeight(Settings.DEFAULT_MIN_PAGE_HEIGHT);
        stage.setMinWidth(Settings.DEFAULT_MIN_PAGE_WIDTH);
        stage.setHeight(Settings.DEFAULT_PAGE_HEIGHT);
        stage.setWidth(Settings.DEFAULT_PAGE_WIDTH);
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

        scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(pathTheme)).toExternalForm());
    }



}