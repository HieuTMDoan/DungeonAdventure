package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.view.ConsoleView;
import com.tcss.dungeonadventure.view.DungeonGUI;
import javafx.application.Application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;


public class DungeonAdventure implements PropertyChangeListener {


    private Hero myHero;

    private Dungeon myDungeon;

    private Room myCurrentRoom;


    public DungeonAdventure(final boolean theGUIActive) {
        PCS.addPropertyListener(this);


        if (theGUIActive) {
            new ConsoleView();
        } else {
            Application.launch(DungeonGUI.class);
        }


    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (PCS.valueOf(theEvent.getPropertyName())) {
            case START_NEW_GAME -> {
                this.myHero = (Hero) theEvent.getNewValue();
                this.myDungeon = new Dungeon();
                this.myDungeon.loadHeroIntoStartingRoom();

            }
            case LOAD_ROOM -> {
                this.myCurrentRoom = (Room) theEvent.getNewValue();
            }

            case MOVE_PLAYER -> {
                this.myCurrentRoom.movePlayer((Directions.Cardinal) theEvent.getNewValue());
            }

        }

    }

}
