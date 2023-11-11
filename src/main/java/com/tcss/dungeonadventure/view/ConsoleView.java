package com.tcss.dungeonadventure.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ConsoleView implements PropertyChangeListener {

    // Create a list to keep track of the console history
    private List<String> consoleHistory;

    public ConsoleView() {
        consoleHistory = new ArrayList<>();
    }

    // Nested Memento class
    public class Memento {
        private List<String> historySnapshot;

        private Memento(List<String> historySnapshot) {
            this.historySnapshot = new ArrayList<>(historySnapshot);
        }

        public List<String> getHistorySnapshot() {
            return historySnapshot;
        }
    }

    // Method to capture the current state of the ConsoleView
    public Memento createMemento() {
        return new Memento(consoleHistory);
    }

    // Method to restore the state of the ConsoleView from a Memento
    public void setMemento(Memento memento) {
        consoleHistory = new ArrayList<>(memento.getHistorySnapshot());
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        // Handle property changes and update the console history
        if ("message".equals(theEvent.getPropertyName())) {
            String message = (String) theEvent.getNewValue();
            consoleHistory.add(message);
            // Perform any other actions based on the property change
        }
    }

    // Additional methods for interacting with the console
    public List<String> getConsoleHistory() {
        return consoleHistory;
    }

    public void clearConsoleHistory() {
        consoleHistory.clear();
    }
}
