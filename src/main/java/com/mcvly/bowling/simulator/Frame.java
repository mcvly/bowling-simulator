package com.mcvly.bowling.simulator;

/**
 * @author <a href="mailto:RMalyona@luxoft.com">Ruslan Malyona</a>
 * @since 24.12.13
 */
public class Frame {

    public static enum State {
        STRIKE,
        SPARE,
        OPEN,
        ONE_ATTEMPT,
        CLOSED,
        STRIKE_LAST
    }

    private int firstAttempt, secondAttempt, thirdAttempt;
    private State state;
    private String player;
    private int number;

    public Frame(int number, String player) {
        this.number = number;
        this.player = player;
        state = State.CLOSED;
    }

    public void setFirstAttempt(int firstAttempt) {
        this.firstAttempt = firstAttempt;
    }

    public void setSecondAttempt(int secondAttempt) {
        this.secondAttempt = secondAttempt;
    }

    public void setThirdAttempt(int thirdAttempt) {
        this.thirdAttempt = thirdAttempt;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getFirstAttempt() {
        return firstAttempt;
    }

    public int getSecondAttempt() {
        return secondAttempt;
    }

    public int getThirdAttempt() {
        return thirdAttempt;
    }

    public String getPlayer() {
        return player;
    }

    public int getNumber() {
        return number;
    }
}
