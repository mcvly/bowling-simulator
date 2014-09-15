package com.mcvly.bowling.simulator.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import com.mcvly.bowling.simulator.BowlingSimulator;
import com.mcvly.bowling.simulator.Frame;

/**
 * @author mcvly
 * @since 24.12.13
 */
public class BowlingSimulatorImpl implements BowlingSimulator {

    private List<String> players;
    private Frame[] frames;
    int currentFrame;

    public BowlingSimulatorImpl(LinkedHashSet<String> players) {
        this.players = new ArrayList<String>(players);
        frames = new Frame[10 * players.size()];
        currentFrame = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < players.size(); j++) {
                frames[players.size() * i + j] = new Frame(i, this.players.get(j));
            }
        }
    }

    @Override
    public Frame nextFrame() {
        if (currentFrame >= frames.length) {
            return null;
        }
        else {
            return frames[currentFrame];
        }
    }

    @Override
    public void throwBall(int score) {

        if (currentFrame >= frames.length) {
            throw new IllegalStateException();
        }

        Frame frame = frames[currentFrame];
        switch (frame.getState()) {
            case CLOSED:
                if (score == 10) {
                    setStrike(frame);
                } else {
                    setFirstAttempt(frame, score);
                }
                break;

            case ONE_ATTEMPT:
                if (frame.getFirstAttempt() + score == 10) {
                    setSpare(frame);
                } else {
                    setSecondAttempt(frame, score);
                }
                break;

            case STRIKE: // last frame 2nd throw
                frame.setSecondAttempt(score);
                frame.setState(Frame.State.STRIKE_LAST);
                break;

            case STRIKE_LAST: // last frame last throw, after strike
                frame.setThirdAttempt(score);
                currentFrame += 1;
                break;

            case SPARE: // last frame, last throw
                frame.setThirdAttempt(score);
                currentFrame += 1;
                break;
        }
    }

    private void setStrike(Frame frame) {
        frame.setState(Frame.State.STRIKE);
        frame.setFirstAttempt(10);
        if (!isLastUsersFrame(currentFrame)) {
            currentFrame += 1;
        }
    }

    private void setSpare(Frame frame) {
        frame.setSecondAttempt(10 - frame.getFirstAttempt());
        frame.setState(Frame.State.SPARE);
        if (!isLastUsersFrame(currentFrame)) {
            currentFrame += 1;
        }
    }

    private void setFirstAttempt(Frame frame, int score) {
        frame.setFirstAttempt(score);
        frame.setState(Frame.State.ONE_ATTEMPT);
    }

    private void setSecondAttempt(Frame frame, int score) {
        frame.setSecondAttempt(score);
        frame.setState(Frame.State.OPEN);
        currentFrame += 1;
    }

    private boolean isLastUsersFrame(int frameNumber) {
        return frameNumber >= frames.length - players.size();
    }

    @Override
    public LinkedHashMap<String, Integer> score() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>(players.size());

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < players.size(); j++) {
                Frame currentFrame = frames[players.size() * i + j];
                int sum = 0;
                switch (currentFrame.getState()) {
                    case OPEN:
                        sum += currentFrame.getFirstAttempt() + currentFrame.getSecondAttempt();
                        break;
                    case SPARE:
                        sum += (10 + frames[players.size() * (i+1) + j].getFirstAttempt());
                        break;
                    case STRIKE:
                        sum += getPointsForStrike(i, j);
                        break;
                }
                Integer prevSum = result.get(players.get(j));
                result.put(players.get(j), sum + (prevSum == null ? 0 : prevSum.intValue()));
            }
        }

        for (int j = 0; j < players.size(); j++) {
            Frame currentFrame = frames[players.size() * 9 + j];
            int sum = (currentFrame.getFirstAttempt() + currentFrame.getSecondAttempt() + currentFrame.getThirdAttempt());
            int prevSum = result.get(players.get(j));
            result.put(players.get(j), prevSum + sum);
        }

        return result;
    }

    private int getPointsForStrike(int frame, int player) {
        int result = 10;
        Frame nextFrame = frames[players.size() * (frame + 1) + player];
        if (Frame.State.STRIKE != nextFrame.getState()) {
            result += nextFrame.getFirstAttempt() + nextFrame.getSecondAttempt();
        } else {
            result += 10;
            if (frame == 8) {
                result += nextFrame.getSecondAttempt();
            } else {
                nextFrame = frames[players.size() * (frame + 2) + player];
                result += nextFrame.getFirstAttempt();
            }
        }

        return result;
    }
}
