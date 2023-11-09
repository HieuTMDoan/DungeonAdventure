package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.PCS;
import com.tcss.dungeonadventure.model.Room;
import com.tcss.dungeonadventure.objects.Directions;

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
        startup();
    }

    public void startup() {
        System.out.println("---- Dungeon Adventure ----");

        switch (validInputChecker(
                "Start a new game (N), or load an existing one (L)?: ",
                "N", "L")) {
            case "N" -> newGameStartup();
            case "L" -> System.out.println("Not yet implemented");
            default -> {
            }
        }


    }


    private void newGameStartup() {
        final String playerName = validInputChecker("Enter player name: ");


        final String playerClass = switch (validInputChecker(
                "Warrior (W), Priestess (P), Thief (T)"
                        + "\nChoose your class: ", "W", "P", "T")) {

            case "W" -> "Warrior";
            case "P" -> "Priestess";
            case "T" -> "Thief";
            default -> null;
        };


        System.out.println("Name: " + playerName + " | Class: " + playerClass);
    }


    /**
     * This prompts the user for input based on a question.
     * If the user enters an invalid choice, it will continue to prompt
     * until a valid input is received.
     * If no valid choices are provided, it will return the first input received.
     *
     * @param thePrompt The prompt to display.
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

        System.out.println(this.myCurrentRoom);
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

        }
    }
}
