package com.tcss.dungeonadventure.controller;

import com.tcss.dungeonadventure.controller.MementoGameState;

import java.util.ArrayList;
import java.util.List;

public class StateCaretaker {
    private List<MementoGameState> mementos = new ArrayList<>();

    public void addMemento(MementoGameState memento) {
        mementos.add(memento);
    }

    public MementoGameState getMemento(int index) {
        if (index >= 0 && index < mementos.size()) {
            return mementos.get(index);
        }
        return null;
    }
}
