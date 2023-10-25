package com.tcss.dungeonadventure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;

    private static final String HOME_FXML_PATH = "fxml/dungeon-home-screen.fxml";
    private static final String WINDOW_TITLE = "Dungeon Adventure";


    @Override
    public void start(final Stage theStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource(HOME_FXML_PATH));

        final Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        theStage.setTitle(WINDOW_TITLE);
        theStage.setScene(scene);
        theStage.show();
    }

    public static void main(final String[] theArgs) {
        launch();
    }
}