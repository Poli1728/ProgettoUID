package com.calendly.calendly;

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

    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {
    }

    public void init(Stage stage) {
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle(this.INIT_TITLE);

            this.launchTutorialFirstOpening();

            this.stage.setScene(scene);

            for (String font : Settings.fonts)
                Font.loadFont(Objects.requireNonNull(SceneHandler.class.getResource(font)).toExternalForm(), 10);
            for (String style : Settings.styles)
                scene.getStylesheets().add(Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm());

            this.stage.show();
        }
    }

    private void loadFXML(String resourceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("fxml/hello-view.fxml"));
            if (scene == null)
                scene = new Scene(fxmlLoader.load());
            else
                scene.setRoot(fxmlLoader.load());

        } catch (IOException e) { }
    }


    public void launchTutorialFirstOpening() {
        //da eseguire solo alla prima apertura dell'app
        //mostrare un mini welcome
        //creazione dell'account dell'owner

        loadFXML("fxml/hello-view.fxml");
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setWidth(300);
        stage.setHeight(200);
        stage.setResizable(false);

    }

    public void launchCreateAccountOwner() {
        //da utilizzare esclusivamente in contemporanea con launchTutorialFirstOpening()
    }

    public void launchLogin() {
        loadFXML("fxml/hello-view.fxml");
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setWidth(300);
        stage.setHeight(200);
        stage.setResizable(false);
    }


    public void launchSideBar() {
        //da vedere se passare height and width come params per scegliere se utilizzare la sBar con solo icone o con icone+testo
    }

    public void launchDashboard() {
    }

}