package com.mcvly.bowling.simulator;

import java.util.LinkedHashMap;

/**
 * @author mcvly
 * @since 24.12.13
 */
public interface BowlingSimulator {

    Frame nextFrame();

    void throwBall(int score);

    LinkedHashMap<String, Integer> score();

}
