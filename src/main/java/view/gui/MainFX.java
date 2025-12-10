package view.gui;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class MainFX extends Application {

    private static List<Controller> controllers;
    private static List<String> descriptions;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/ProgramSelector.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Kiddo Interpreter - Program Selector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
