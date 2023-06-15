package com.calendly.calendly.Controller;
import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.MyInfo;
import com.calendly.calendly.View.MyTransition;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WelcomePageController {
    @FXML
    private Pane pane;
    @FXML
    private Label labelTitle;
    @FXML
    private Button continueButton;
    @FXML
    private StackPane stackPane;
    @FXML
    void actionContinueButton() {
        if (imgTransition.getStatus() == Animation.Status.RUNNING)
            imgTransition.stop();
        SceneHandler.getInstance().launchLogin();
    }


    private ImageView imageView = new ImageView();
    private List<Image> images = new LinkedList<>();

    private Stage stage;

    /*private void impostaTemi() throws IOException {
        if(MyInfo.getInstance().getFont().equals("Dyslexie")){
            labelTitle.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),labelTitle.getFont().getSize()-2));
            continueButton.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(), continueButton.getFont().getSize()-3));
        }else{
            labelTitle.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),labelTitle.getFont().getSize()));
            continueButton.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(), continueButton.getFont().getSize()));
        }
    }*/

    public void initialize() throws IOException {
        continueButton.setDisable(true);
        labelTitle.setText(Settings.INIT_TITLE);
        labelTitle.setTextAlignment(TextAlignment.CENTER);
        configStackPane();
        setImages();
        configContinueButton();
        //impostaTemi();
    }

    private void configStackPane() {
        Platform.runLater(() -> {
            this.setLayoutStackPane();
        });

        pane.layoutBoundsProperty().addListener((observable -> {
            this.setLayoutStackPane();
        }));

        stackPane.setAlignment(Pos.CENTER);
    }

    private void setLayoutStackPane() {
        double width  = stackPane.getWidth();
        double height = stackPane.getHeight();

        double x = (pane.getWidth() - width) / 2;
        double y = (pane.getHeight() - height) / 2;

        stackPane.resizeRelocate(x, y, width, height);
    }


    private void configContinueButton() {
        continueButton.setText("Start");
        continueButton.prefWidthProperty().bind(stackPane.widthProperty().divide(3).multiply(2.5));
        continueButton.prefHeightProperty().bind(stackPane.heightProperty().divide(5).multiply(0.3));
    }



    private SequentialTransition imgTransition = MyTransition.getInstance().imgFadeTransition(images, imageView);

    private void setImages() {
        int N_IMAGES = 4;

        for (int i = 1; i <= N_IMAGES; i++) {
            try {
                Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/welcome" + i + ".png")));
                images.add(image);
                if (i == 4)
                    imageView.setImage(image);
            } catch (Exception e) {
                SceneHandler.getInstance().generaAlert("Qualcosa è andato storto!", false);
            }
        }

        imageView.maxWidth(stackPane.getMaxWidth());
        imageView.maxHeight(210);

        stackPane.layoutBoundsProperty().addListener(observable -> {
            setLayoutImageView();
        });

        setLayoutImageView();
        imgTransition.setCycleCount(SequentialTransition.INDEFINITE);
        imgTransition.play();

        continueButton.setDisable(false);
    }

    private void setLayoutImageView() {
        imageView.setFitWidth(stackPane.getWidth());
        imageView.setFitHeight(210);

        imageView.setPreserveRatio(true);

        imageView.setLayoutX((stackPane.getWidth() - imageView.getFitWidth())/2);
        imageView.setLayoutY(0);

        if (!stackPane.getChildren().contains(imageView)) {
            stackPane.getChildren().add(imageView);
        }
    }



}
