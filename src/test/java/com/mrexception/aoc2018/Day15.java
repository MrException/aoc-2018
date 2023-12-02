package com.mrexception.aoc2018;

import com.mrexception.Map2D;
import com.mrexception.MapNode;
import com.mrexception.MapParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

public class Day15 {
    private Logger log = LoggerFactory.getLogger("Day15");
    private String inputFile = "com/mrexception/aoc2018/day15.txt";

    private Map2D map;
    private List<Actor> goblins;
    private List<Actor> elves;

    @BeforeEach
    public void reset() {
        map = null;
        goblins = new ArrayList<>();
        elves = new ArrayList<>();
    }

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    // @Test
    // Was never able to finish this one!
    public void testPartOne() {
        String[] testData = new String[]{
                "#########",
                "#G......#",
                "#.E.#...#",
                "#..##..G#",
                "#...##..#",
                "#...#...#",
                "#.G...G.#",
                "#.....G.#",
                "#########"
        };

        map = MapParser.parse(testData, mapNodeCreator);

        assertThat(run()).isEqualTo(18740);
//        assertThat(new Logic(processFile(inputFile, false)).partOne()).isEqualTo("80,100");
    }

    Function<Character, MapNode> mapNodeCreator = c -> {
        MapNode n = null;
        switch (c) {
            case '#':
                n = new MapNode(1);
                break;
            case '.':
                n = new MapNode(0);
                break;
            case 'G':
                n = new MapNode(1);
                goblins.add(new Actor(n));
                break;
            case 'E':
                n = new MapNode(1);
                elves.add(new Actor(n));
                break;
        }
        return n;
    };

//    @Test
//    public void testPartTwo() throws Exception {
//    }

    private class Actor {
        MapNode location;
        int life;
        int attackPower;

        public Actor(MapNode location) {
            this.location = location;
            this.life = 200;
            this.attackPower = 3;
        }
    }

    private int run() {
        MapNode[][] grid = map.grid;
        return 0;
    }

    private boolean canAttack(Actor a, boolean isElf) {
        Set<MapNode> neighbours = map.getNeighbours(a.location);
        List<Actor> actors = isElf ? elves : goblins;
        for (MapNode neighbour : neighbours) {
            if (isActor(neighbour, actors)) {
                return true;
            }
        }
        return false;
    }

    private boolean isActor(MapNode loc, List<Actor> actors) {
        for (Actor actor : actors) {
            if (actor.location.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    private Comparator<MapNode> readingOrderComparator = Comparator.comparing(MapNode::getY).thenComparing(MapNode::getX);
}
