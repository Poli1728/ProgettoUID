package com.calendly.calendly.View;

import com.calendly.calendly.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CustomButton extends Button {
    public enum prefers {IMAGE_ONLY, BASED_ON_WINDOW_SIZE}

    private prefers preferenza;
    private String buttonText;
    private String imagePath;


    public CustomButton(prefers preferenza, String buttonText, String imagePath) {
        super();
        this.preferenza = preferenza;
        this.buttonText = buttonText;
        this.imagePath = imagePath;

        createButton();
    }


    private void createButton() {
        switch (preferenza) {
            case IMAGE_ONLY -> setButtonImageOnly();
            case BASED_ON_WINDOW_SIZE -> setButtonBasedOnWindowSize();
        }
    }

    private void setButtonBasedOnWindowSize() {

    }

    private void setButtonImageOnly() {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/edit.png")));
            imageView.setImage(image);
            imageView.setFitWidth(10);
            imageView.setFitWidth(10);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        this.setGraphic(imageView);
    }


}
