package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day9 {
    @Test
    public void testPartOne() {
        assertThat(new Logic(9, 25).play()).isEqualTo(32);
        assertThat(new Logic(10, 1618).play()).isEqualTo(8317);
        assertThat(new Logic(13, 7999).play()).isEqualTo(146373);
        assertThat(new Logic(17, 1104).play()).isEqualTo(2764);
        assertThat(new Logic(21, 6111).play()).isEqualTo(54718);
        assertThat(new Logic(30, 5807).play()).isEqualTo(37305);

        assertThat(new Logic(439, 71307).play()).isEqualTo(410375);
    }

    @Test
    public void testPartTwo() {
        assertThat(new Logic(439, 7130700).play()).isEqualTo(3314195047L);
    }

    class Logic {
        private final int players;
        private final long lastMarble;
        private final Map<Integer, Long> scores;

        Logic(int players, long lastMarble) {
            this.players = players;
            this.lastMarble = lastMarble;
            scores = new HashMap<>();
            for (int i = 1; i <= players; i++) {
                scores.put(i, 0L);
            }
        }

        long play() {
            Marble currentMarble = new Marble(0, null, null);
            currentMarble.next = currentMarble;
            currentMarble.prev = currentMarble;
            Circle circle = new Circle();
            int currentPlayer = 1;
            long turn = 1;
            while (turn <= lastMarble) {
                if (turn % 23 == 0) {
                    long currentScore = scores.get(currentPlayer);
                    currentScore += turn;
                    Marble toRemove = circle.backwardSeven(currentMarble);
                    currentScore += toRemove.value;
                    currentMarble = circle.forwardOne(toRemove);
                    circle.removeMarble(toRemove);
                    scores.put(currentPlayer, currentScore);
                } else {
                    currentMarble = circle.placeMarble(currentMarble, turn);
                }

                turn++;
                currentPlayer++;
                if (currentPlayer > players) {
                    currentPlayer = 1;
                }
            }

            long best = Long.MIN_VALUE;
            for (long value : scores.values()) {
                if (value > best) {
                    best = value;
                }
            }
            return best;
        }
    }

    class Circle {
        Marble placeMarble(Marble cur, long value) {
            Marble prev = cur.next;
            Marble next = prev.next;
            Marble newMarble = new Marble(value, prev, next);
            next.prev = newMarble;
            prev.next = newMarble;
            return newMarble;
        }

        void removeMarble(Marble toRemove) {
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }

        Marble forwardOne(Marble cur) {
            return cur.next;
        }

        Marble backwardSeven(Marble cur) {
            for (int i = 0; i < 7; i++) {
                cur = cur.prev;
            }
            return cur;
        }
    }

    class Marble {
        long value;
        Marble prev;
        Marble next;

        Marble(long value, Marble prev, Marble next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}