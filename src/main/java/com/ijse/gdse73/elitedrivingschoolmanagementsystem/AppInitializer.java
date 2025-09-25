package com.ijse.gdse73.elitedrivingschoolmanagementsystem;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(
                new Scene(
                        new FXMLLoader(getClass().getResource(
                                "/view/UserLogin/LoadingScreen.fxml"
                        )).load()
                )
        );
        primaryStage.show();

        Task<Scene> loadingTask = new Task() {
            @Override
            protected Scene call() throws Exception {
                Parent parent = FXMLLoader.load(
                        getClass().getResource("/view/UserLogin/LoginPage.fxml")
                );
                return new Scene(parent);
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            primaryStage.setTitle("ELITE Driving School");
            primaryStage.setScene(value);
        });

        loadingTask.setOnFailed(event -> {
            System.out.println("Failed to load application");
            primaryStage.close();
        });

        new Thread(loadingTask).start();
    }

}
