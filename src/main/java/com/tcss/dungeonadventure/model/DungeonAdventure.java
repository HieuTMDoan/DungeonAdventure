package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.view.ConsoleView;
import com.tcss.dungeonadventure.view.DungeonGUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;


public class DungeonAdventure implements PropertyChangeListener {

    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(this);


    public DungeonAdventure(final boolean theGUIActive) {
//        From main, choose between gui mode or not based on command line arguments
//            Something like --nogui or something idk
        PCS.addPropertyListener(this);



        if (theGUIActive) {
            new ConsoleView();
        } else {
            new DungeonGUI();
        }


    }



    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

    }

}
