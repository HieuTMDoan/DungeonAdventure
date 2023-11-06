package com.tcss.dungeonadventure.model;



import java.util.Scanner;


public class DungeonAdventure {



    public DungeonAdventure(final boolean theGUIActive) {
//        From main, choose between gui mode or not based on command line arguments
//            Something like --nogui or something idk

        startup();

    }

    private void startup() {
        System.out.println("---- Dungeon Adventure ----");

        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter player name: ");

        String playerName;
        while (true) {
            playerName = scanner.nextLine();
            if (playerName.isBlank()) {
                System.out.print("Invalid player name. Please re-enter the player name: ");
                continue;
            }
            break;
        }

        System.out.println("Warrior (W), Priestess (P), Thief (T)");
        System.out.print("Choose your class: ");

        String playerClass;
        while (true) {
            playerClass = switch (scanner.nextLine()) {
                case "W" -> "Warrior";
                case "P" -> "Priestess";
                case "T" -> "Thief";
                default -> null;
            };

            if (playerClass == null) {
                System.out.print("Invalid class; please enter W, P or T: ");
                continue;
            }
            break;

        }

        System.out.println("Name: " + playerName + " | Class: " + playerClass);


    }



}
