package com.tcss.dungeonadventure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(final Stage theStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        final Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        theStage.setTitle("Hello!");
        theStage.setScene(scene);
        theStage.show();
    }

    public static void main(final String[] theArgs) {
        launch();
    }
}