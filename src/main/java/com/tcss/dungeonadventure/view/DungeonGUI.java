package com.tcss.dungeonadventure.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class DungeonGUI extends Application {

    private static final double WINDOW_WIDTH = 900;
    private static final double WINDOW_HEIGHT = 600;
    private static final String HOME_FXML_PATH = "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-home-screen.fxml";
    private static final String ADVENTURE_FXML_PATH = "./src/main/resources/com/tcss/dungeonadventure/fxml/dungeon-adventure.fxml";
    private static final String WINDOW_TITLE = "Dungeon Adventure";


    private Scene myScene;
    private GridPane myGridPane;
    private final Text[][] myRoomChars = new Text[10][10];

    @Override
    public void start(final Stage theStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(new File(ADVENTURE_FXML_PATH).toURI().toURL());

        myScene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        theStage.setTitle(WINDOW_TITLE);
        theStage.setScene(myScene);
        theStage.show();

        locateNodes();
        createGUI();
    }

    Node lookup(final String theNodeID) {
        return this.myScene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    private void locateNodes() {
        myGridPane = (GridPane) this.lookup("roomGrid");
    }

    private void createGUI() {
        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                final HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);

                final Text text = new Text(" ");
                myRoomChars[row][col] = text;
                text.setBoundsType(TextBoundsType.VISUAL);
                text.setStyle("-fx-font-size: 60; -fx-fill: rgb(255, 255, 255)");

                final int finalRow = row;
                final int finalCol = col;
                hbox.setOnMouseClicked(e -> {
                    setCharAt(finalRow, finalCol, '*');
                });

                hbox.setOnMouseEntered(e -> {
                    onMouseOver(finalRow, finalCol);
                });
                hbox.getChildren().add(text);



                this.myGridPane.add(hbox, col, row);
            }

        }
    }

    private void setCharAt(final int theRowIndex, final int theColIndex, final char theChar) {
        myRoomChars[theRowIndex][theColIndex].setText(String.valueOf(theChar));
    }

    private void onMouseOver(final int theRowIndex, final int theColIndex) {
        final String c = myRoomChars[theRowIndex][theColIndex].getText();



    }




}