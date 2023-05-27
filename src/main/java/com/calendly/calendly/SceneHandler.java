package com.calendly.calendly;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    private static final SceneHandler instance = new SceneHandler();
    private Stage stage;
    private static Scene scene;

    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {}

    public void init(Stage stage) {
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setTitle("Calendly");
            createBrowserView();
            this.stage.setScene(scene);
            this.stage.show();
        }
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource(resourceName));
        return fxmlLoader.load();
    }
    public void createBrowserView() {
        try {
            if(scene == null)
                scene = new Scene(loadRootFromFXML("LoginView.fxml"));
            else
                scene.setRoot(loadRootFromFXML("LoginView.fxml"));
            stage.setMinWidth(900);
            stage.setMinHeight(700);
            stage.setWidth(900);
            stage.setHeight(700);
        } catch (IOException ignored) {
        }
    }

}
