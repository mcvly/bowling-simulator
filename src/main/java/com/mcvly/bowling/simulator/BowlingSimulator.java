package com.mcvly.bowling.simulator;

import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:RMalyona@luxoft.com">Ruslan Malyona</a>
 * @since 24.12.13
 */
public interface BowlingSimulator {

    Frame nextFrame();

    void throwBall(int score);

    LinkedHashMap<String, Integer> score();

}
