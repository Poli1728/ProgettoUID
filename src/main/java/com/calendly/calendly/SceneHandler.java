package com.calendly.calendly;

import com.calendly.calendly.Controller.WelcomePageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        setWindowDimension();
        controller.init();
    }

    public void launchCreateAccountOwner() {
        //da utilizzare esclusivamente in contemporanea con launchTutorialFirstOpening()
    }

    public void launchLogin() {
        loadFXML("fxml/LoginView.fxml");
        setWindowDimension();
    }


    public void launchSideBar() {
        //da vedere se passare height and width come params per scegliere se utilizzare la sBar con solo icone o con icone+testo
    }

    public void launchDashboard() {
    }







    private void setWindowDimension(boolean ... isResizable)  {
        //Default value for isResizable => true
        stage.setMinHeight(Settings.DEFAULT_MIN_PAGE_HEIGHT);
        stage.setMinWidth(Settings.DEFAULT_MIN_PAGE_WIDTH);
        stage.setHeight(Settings.DEFAULT_PAGE_HEIGHT);
        stage.setWidth(Settings.DEFAULT_PAGE_WIDTH);

        if (isResizable.length == 0)
            stage.setResizable(true);
        else
            stage.setResizable(isResizable[0]);
    }

    private void loadStyle() {
        for (String font : Settings.fonts)
            Font.loadFont(Objects.requireNonNull(SceneHandler.class.getResource(font)).toExternalForm(), 10);
        for (String style : Settings.styles)
            scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());
    }

}