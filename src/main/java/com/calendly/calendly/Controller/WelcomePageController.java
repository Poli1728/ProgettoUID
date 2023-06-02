package com.calendly.calendly.Controller;
import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WelcomePageController {

    @FXML
    private Pane pane;

    @FXML
    private Button continueButton;

    @FXML
    private StackPane stackPane;

    @FXML
    void actionContinueButton(ActionEvent event) {
        if (timeline != null)
            timeline.stop();
        SceneHandler.getInstance().launchLogin();

    }


    private ImageView imageView = new ImageView();
    private List<Image> images = new LinkedList<>();

    //--------------------------
    private Stage stage;

    public void init(Stage stage) {
        if (stage == null)
            return;
        this.stage = stage;

        configPane();
        configContinueButton();
        configStackPane();
        setImages();
    }

    private void configPane() { }

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
        continueButton.prefWidthProperty().bind(stackPane.widthProperty().divide(3).multiply(2.5));
        continueButton.prefHeightProperty().bind(stackPane.heightProperty().divide(5).multiply(0.3));
    }



    private Timeline timeline;
    private int indexImage = 0;


    private void setImages() {
        int N_IMAGES = 4;

        for (int i = 1; i <= N_IMAGES; i++) {
            try {
                Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/welcome" + i + ".png")));
                images.add(image);
            } catch (Exception e) {
                System.out.println(i + " " + e.toString());
            }
        }

        int DEFAULT_IMAGE = 2;

        imageView.maxWidth(stackPane.getMaxWidth());
        imageView.maxHeight(210);



        Platform.runLater(() -> {
            timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> setLayoutImageView()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });


        stackPane.layoutBoundsProperty().addListener(observable -> {
            setLayoutImageView();
        });



    }

    private void setLayoutImageView() {

        imageView.setImage(images.get(indexImage));


        imageView.setFitWidth(images.get(indexImage).getWidth() * 0.5);
        imageView.setFitHeight(images.get(indexImage).getHeight() * 0.4);

        imageView.setLayoutX(stackPane.getWidth()/2 - imageView.getFitWidth()/2);
        imageView.setLayoutY(0);


        if (!stackPane.getChildren().contains(imageView)) {
            stackPane.getChildren().add(imageView);
        }

        indexImage++;

        if (indexImage > images.size()-1)
            indexImage = 0;

    }



}
