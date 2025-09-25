package com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.userLogin;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class LoadingScreenController implements Initializable {
    public ImageView imgL;

    public void applyPulseAnimation(ImageView imageView, double scaleFactor, double duration) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), imageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyPulseAnimation(imgL, 1.2, 300);
    }
}
