package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.Helper;
import com.tcss.dungeonadventure.model.*;
import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.heroes.Priestess;
import com.tcss.dungeonadventure.objects.heroes.Thief;
import com.tcss.dungeonadventure.objects.heroes.Warrior;
import com.tcss.dungeonadventure.objects.tiles.Tile;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements PropertyChangeListener {

    /**
     * The scanner to take input.
     */
    private final Scanner myScanner = new Scanner(System.in);

    /**
     * The current room.
     */
    private Room myCurrentRoom;


    public ConsoleView() {
        PCS.addPropertyListener(this);
        startup();
    }

    public void startup() {
        System.out.println("---- Dungeon Adventure ----");

        switch (
                validInputChecker(
                        "Start a new game (N), or load an existing one (L)?: ",
                        "N", "L"
                )) {
            case "N" -> newGameStartup();
            case "L" -> System.out.println("Not yet implemented");
            default -> {
            }
        }


    }


    private void newGameStartup() {
        final String playerName = validInputChecker("Enter player name: ");


        final Hero playerClass = switch (validInputChecker(
                "Warrior (W), Priestess (P), Thief (T)"
                        + "\nChoose your class: ", "W", "P", "T")) {

            case "W" -> (Warrior) SQLiteDB.getCharacterByName(Helper.Characters.WARRIOR);
            case "P" -> (Priestess) SQLiteDB.getCharacterByName(Helper.Characters.PRIESTESS);
            case "T" -> (Thief) SQLiteDB.getCharacterByName(Helper.Characters.THIEF);
            default -> null;
        };

        PCS.firePropertyChanged(PCS.START_NEW_GAME, new Object[]{playerName, playerClass});

        System.out.println("Name: " + playerName + " | Class: " + playerClass.getClass().getSimpleName());

    }


    /**
     * This prompts the user for input based on a question.
     * If the user enters an invalid choice, it will continue to prompt
     * until a valid input is received.
     * If no valid choices are provided, it will return the first input received.
     *
     * @param thePrompt       The prompt to display.
     * @param theValidChoices The possible valid choices.
     * @return The valid choice.
     */
    private String validInputChecker(final String thePrompt,
                                     final String... theValidChoices) {
        System.out.print(thePrompt);

        if (theValidChoices.length == 0) {
            return myScanner.nextLine();
        }

        final List<String> list = Arrays.asList(theValidChoices);

        while (true) {
            final String line = myScanner.nextLine();
            if (list.contains(line)) {
                return line;
            } else {
                final StringBuilder sb = new StringBuilder("Invalid choice. Please enter ");
                for (int i = 0; i < theValidChoices.length; i++) {
                    final String s = theValidChoices[i];
                    if (i == theValidChoices.length - 2) {
                        sb.append(s).append(", or ");
                    } else {
                        sb.append(s).append(", ");
                    }
                }
                System.out.print(sb.toString().trim() + ": ");
            }

        }

    }

    private void loadRoom(final Room theRoom) {
        this.myCurrentRoom = theRoom;

        printRoomWithPlayer();


    }

    private void printRoomWithPlayer() {
        System.out.println(this.myCurrentRoom);

        switch (validInputChecker("Enter WASD: ", "W", "A", "S", "D")) {
            case "W" -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.NORTH);
            case "S" -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.SOUTH);
            case "A" -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.WEST);
            case "D" -> PCS.firePropertyChanged(PCS.MOVE_PLAYER, Directions.Cardinal.EAST);

            default -> {
            }
        }


    }

    private void movePlayer(final Directions.Cardinal theDirection) {
        if (theDirection == null) {
            return;
        }


    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case LOAD_ROOM -> loadRoom((Room) theEvent.getNewValue());
            case MOVE_PLAYER -> movePlayer((Directions.Cardinal) theEvent.getNewValue());
            case UPDATED_PLAYER_LOCATION -> {
                System.out.println("Player is at: " + theEvent.getNewValue());
                printRoomWithPlayer();


            }
        }
    }
}
