package com.ijse.gdse73.elitedrivingschoolmanagementsystem.util;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ButtonScale {
    public static void buttonScaling(JFXButton button) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(100), button);
        grow.setToX(1.1);
        grow.setToY(1.1);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), button);
        shrink.setToX(1);
        shrink.setToY(1);

        button.setOnMouseEntered(e -> grow.playFromStart());
        button.setOnMouseExited(e -> shrink.playFromStart());
    }

    public static void hboxScaling(HBox hbox) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(100), hbox);
        grow.setToX(1.1);
        grow.setToY(1.1);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), hbox);
        shrink.setToX(1);
        shrink.setToY(1);

        hbox.setOnMouseEntered(e -> grow.playFromStart());
        hbox.setOnMouseExited(e -> shrink.playFromStart());
    }

    public static void labelScaling(Label label) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(100), label);
        grow.setToX(1.1);
        grow.setToY(1.1);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), label);
        shrink.setToX(1);
        shrink.setToY(1);

        label.setOnMouseEntered(e -> grow.playFromStart());
        label.setOnMouseExited(e -> shrink.playFromStart());
    }

    public static void imageScaling(ImageView image) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(100), image);
        grow.setToX(1.1);
        grow.setToY(1.1);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), image);
        shrink.setToX(1);
        shrink.setToY(1);

        image.setOnMouseEntered(e -> grow.playFromStart());
        image.setOnMouseExited(e -> shrink.playFromStart());
    }

}
