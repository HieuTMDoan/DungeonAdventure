package com.tcss.dungeonadventure.model;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a timeline of events, using conditionals and delays.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class TimedSequence {

    /**
     * The list of all stored events.
     */
    private final List<Pair<Pair<Integer, TimedEvent>, Conditional>> myQueuedEvents
            = new ArrayList<>();

    public TimedSequence() {

    }


    /**
     * Adds a new event to the timeline that will trigger AFTER the specified delay.
     *
     * @param theSecondsDelay The delay before the event is triggered.
     * @param theEvent The event to run.
     * @return itself.
     */
    public TimedSequence afterDo(final int theSecondsDelay, final TimedEvent theEvent) {
        return afterDoIf(theSecondsDelay, null, theEvent);
    }

    /**
     * Adds a new event to the timeline that will only trigger
     * IF the conditional evaluates to true.
     *
     * @param theSecondsDelay The delay before the event is triggered.
     * @param theEvent The event to run.
     * @param theCondition The condition for the event to run.
     * @return itself.
     */
    public TimedSequence afterDoIf(final int theSecondsDelay,
                                   final Conditional theCondition,
                                   final TimedEvent theEvent) {

        myQueuedEvents.add(new Pair<>(new Pair<>(theSecondsDelay, theEvent), theCondition));
        return this;

    }

    /**
     * Starts the sequence.
     */
    public void start() {
        if (myQueuedEvents.isEmpty()) {
            return;
        }
        doAction(0);

    }

    /**
     * Recursive method to run each event in the sequence.
     *
     * @param theIndex The index of the event to run.
     */
    private void doAction(final int theIndex) {
        try {
            final Pair<Pair<Integer, TimedEvent>, Conditional>
                    sequence = myQueuedEvents.get(theIndex);
            if (sequence.getValue() == null || sequence.getValue().shouldRun()) {
                final Pair<Integer, TimedEvent> action = sequence.getKey();

                final PauseTransition pause =
                        new PauseTransition(Duration.seconds(action.getKey()));
                pause.setOnFinished(e -> {
                    if (action.getValue().run()) {
                        doAction(theIndex + 1);
                    }

                });
                pause.playFromStart();
            } else {
                doAction(theIndex + 1);

            }

        } catch (final IndexOutOfBoundsException ignored) {
        }
    }



    @FunctionalInterface
    public interface TimedEvent {
        /**
         * The event to run. If the return value is false, it will stop the execution of the sequence.
         * @return True if the sequence should continue, false otherwise.
         */
        boolean run();
    }

    @FunctionalInterface
    public interface Conditional {

        /**
         * @return If the corresponding event should run.
         */
        boolean shouldRun();
    }





}
