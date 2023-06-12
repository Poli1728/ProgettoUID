package com.calendly.calendly;

import com.calendly.calendly.Controller.LoginController;
import com.calendly.calendly.View.MyFont;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static java.lang.System.exit;

public class SceneHandler {
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
            MyFont.getInstance().prendiDati();
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
        loader.getController();
        setWindowLoginDimension();
    }

    public void launchCreateAccountOwner() {
        //da utilizzare esclusivamente in contemporanea con launchTutorialFirstOpening()
    }

    public void launchLogin() {
        loadFXML("fxml/LoginView.fxml");
        LoginController controller = loader.getController();
        controller.init();
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

        setTheme();
    }



    public void setTheme() {

        String pathTheme;

        pathTheme = switch (MyFont.getInstance().getTema()) {
            case "DARK" -> Settings.themes[0];
            case "LIGHT" -> Settings.themes[1];
            default -> {
                exit(11);
                yield Settings.themes[0];
            }
        };

        System.out.println("tema preso da db = " + pathTheme);


        if (scene.getStylesheets().contains(Objects.requireNonNull(SceneHandler.class.getResource(pathTheme)).toExternalForm())) {
            return;
        }

        //todo altrimenti togli il vecchio (prendi il dato da db) e metti il nuovo
        for (Settings.theme t : Settings.theme.values()) {
            try {
                scene.getStylesheets().add(Objects.requireNonNull((SceneHandler.class.getResource(t.toString())).toExternalForm()));
            } catch (Exception ignore) {

            }
        }
        scene.getStylesheets().remove("css/style.css");
        scene.getStylesheets().add("css/style.css");
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