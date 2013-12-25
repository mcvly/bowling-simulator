package com.mcvly.bowling.simulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.junit.Test;

import com.mcvly.bowling.simulator.impl.BowlingSimulatorImpl;

/**
 * @author <a href="mailto:RMalyona@luxoft.com">Ruslan Malyona</a>
 * @since 24.12.13
 */
public class BowlingSimulatorTest {

    @Test(expected = IllegalStateException.class)
    public void testZeroPlayers() {
        BowlingSimulator simulator = new BowlingSimulatorImpl(new LinkedHashSet<String>());
        assertNull(simulator.nextFrame());
        assertEquals(new LinkedHashMap<String, Integer>(), simulator.score());
        simulator.throwBall(0);
    }

    @Test
    public void testEdgeCases() {
        LinkedHashSet<String> john = new LinkedHashSet<String>(Arrays.asList("John"));
        BowlingSimulator simulator = new BowlingSimulatorImpl(john);
        for (int i : arrayOf(20, 0)) {
            simulator.throwBall(i);
        }
        assertEquals(new Integer(0), simulator.score().get("John"));

        simulator = new BowlingSimulatorImpl(john);
        for (int i : arrayOf(20, 1)) {
            simulator.throwBall(i);
        }
        assertEquals(new Integer(20), simulator.score().get("John"));

        simulator = new BowlingSimulatorImpl(john);
        for (int i : arrayOf(21, 5)) {
            simulator.throwBall(i);
        }
        assertEquals(new Integer(150), simulator.score().get("John"));

        simulator = new BowlingSimulatorImpl(john);
        for (int i : arrayOf(12, 10)) {
            simulator.throwBall(i);
        }
        assertEquals(new Integer(300), simulator.score().get("John"));
    }

    @Test
    public void testMixed() {
        BowlingSimulator simulator = new BowlingSimulatorImpl(new LinkedHashSet<String>(Arrays.asList("John")));
        for (int i : getMixed()) {
            simulator.throwBall(i);
        }
        assertEquals(new Integer(161), simulator.score().get("John"));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalCount() {
        BowlingSimulator simulator = new BowlingSimulatorImpl(new LinkedHashSet<String>(Arrays.asList("John")));
        for (int i : arrayOf(21, 1)) {
            simulator.throwBall(i);
        }
    }

    @Test
    public void testMultiplePlayers() {
        LinkedHashSet<String> players = new LinkedHashSet<String>(Arrays.asList("John", "Bern", "Anna"));
        BowlingSimulator simulator = new BowlingSimulatorImpl(players);
        int[] johns = arrayOf(20, 1);
        int i = 0;
        int[] berns = getMixed();
        int j = 0;
        int[] annas = arrayOf(12, 10);
        int k = 0;
        while (simulator.nextFrame() != null) {
            if ("John".equals(simulator.nextFrame().getPlayer())) {
                simulator.throwBall(johns[i++]);
            } else if ("Bern".equals(simulator.nextFrame().getPlayer())) {
                simulator.throwBall(berns[j++]);
            } else {
                simulator.throwBall(annas[k++]);
            }
        }

        assertEquals(new Integer(20), simulator.score().get("John"));
        assertEquals(new Integer(161), simulator.score().get("Bern"));
        assertEquals(new Integer(300), simulator.score().get("Anna"));
    }

    private int[] arrayOf(int n, int z) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = z;
        }

        return array;
    }

    private int[] getMixed() {
        return new int[] {10, 5, 5, 7, 2, 9, 0, 1, 9, 10, 7, 3, 8, 0, 10, 1, 9, 10};
    }

}
