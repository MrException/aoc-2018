package com.mrexception.aoc2018;

import com.mrexception.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day13 {
    private Logger log = LoggerFactory.getLogger("Day13");
    private String inputFile = "com/mrexception/aoc2018/day13.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[]{
                "/->-\\",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "\\------/"
        };

        assertThat(new Logic(testData).partOne()).isEqualTo("7,3");
        assertThat(new Logic(processFile(inputFile, false)).partOne()).isEqualTo("80,100");
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[]{
                "/>-<\\",
                "|   |",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/",
        };
        assertThat(new Logic(testData).partTwo()).isEqualTo("6,4");
        assertThat(new Logic(processFile(inputFile, false)).partTwo()).isEqualTo("16,99");
    }

    class Logic {
        private int[][] track;
        private List<Cart> carts = new ArrayList<>();

        private final int nothing = 0;
        private final int horiz = 1; //"-"
        private final int vert = 2; //"|"
        private final int cross = 3; //"+"
        private final int curveL = 4; //"\"
        private final int curveR = 5; //"/"

        Logic(String[] data) {
            // need longest line to initialize matrix
            int width = 0;
            for (String line : data) {
                if (line.length() > width) {
                    width = line.length();
                }
            }

            track = new int[data.length][width];
            Cart c;
            for (int y = 0; y < data.length; y++) {
                char[] chars = data[y].toCharArray();
                for (int x = 0; x < chars.length; x++) {
                    switch (chars[x]) {
                        case ' ':
                            track[y][x] = nothing;
                            break;
                        case '-':
                            track[y][x] = horiz;
                            break;
                        case '|':
                            track[y][x] = vert;
                            break;
                        case '+':
                            track[y][x] = cross;
                            break;
                        case '\\':
                            track[y][x] = curveL;
                            break;
                        case '/':
                            track[y][x] = curveR;
                            break;
                        case '^':
                            track[y][x] = vert;
                            c = new Cart(x, y, Point.UP);
                            carts.add(c);
                            break;
                        case 'v':
                            track[y][x] = vert;
                            c = new Cart(x, y, Point.DOWN);
                            carts.add(c);
                            break;
                        case '<':
                            track[y][x] = horiz;
                            c = new Cart(x, y, Point.LEFT);
                            carts.add(c);
                            break;
                        case '>':
                            track[y][x] = horiz;
                            c = new Cart(x, y, Point.RIGHT);
                            carts.add(c);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        String partOne() {
            return run(false);
        }

        String run(boolean partTwo) {
            String loc = "";
            int step = 1;
            boolean done = false;
            while (!done) {
                log.info("step {}", step);
                for (Cart cart : carts) {
                    if (cart.crashed) {
                        continue;
                    }
                    cart.move();
                    switch (track[cart.p.y][cart.p.x]) {
                        case cross:
                            cart.turn();
                            break;
                        case curveL:
                            cart.curveL();
                            break;
                        case curveR:
                            cart.curveR();
                            break;
                        default:
                            break;
                    }

                    for (Cart other : carts) {
                        if (cart == other || other.crashed) {
                            continue;
                        }
                        if (cart.p.equals(other.p)) {
                            if (partTwo) {
                                cart.crashed = true;
                                other.crashed = true;
                                List<Cart> active = activeCarts();
                                if (active.size() == 1) {
                                    done = true;
                                }
                            } else {
                                loc = cart.p.toString();
                                done = true;
                            }
                        }
                    }
                }
                step++;
            }
            if (partTwo) {
                loc = activeCarts().get(0).p.toString();
            }
            return loc;
        }

        List<Cart> activeCarts() {
            List<Cart> active = new ArrayList<>();
            for (Cart cart : carts) {
                if (!cart.crashed) {
                    active.add(cart);
                }
            }
            return active;
        }

        String partTwo() {
            return run(true);
        }
    }

    class Cart {
        Point p;
        int oX;
        int oY;
        boolean crashed = false;

        String nextTurn = "L";

        public Cart(int x, int y, Point dir) {
            this.oX = x;
            this.oY = y;
            p = new Point(x, y);
            p.setVelocity(dir);
        }

        void turn() {
            switch (nextTurn) {
                case "L":
                    p.turnLeft();
                    nextTurn = "S";
                    break;
                case "S":
                    nextTurn = "R";
                    break;
                case "R":
                    p.turnRight();
                    nextTurn = "L";
                    break;
            }
        }

        void curveL() {
            if (p.velocity.equals(Point.UP)) {
                p.turnLeft();
            } else if (p.velocity.equals(Point.DOWN)) {
                p.turnLeft();
            } else if (p.velocity.equals(Point.LEFT)) {
                p.turnRight();
            } else if (p.velocity.equals(Point.RIGHT)) {
                p.turnRight();
            }
        }

        void curveR() {
            if (p.velocity.equals(Point.UP)) {
                p.turnRight();
            } else if (p.velocity.equals(Point.DOWN)) {
                p.turnRight();
            } else if (p.velocity.equals(Point.LEFT)) {
                p.turnLeft();
            } else if (p.velocity.equals(Point.RIGHT)) {
                p.turnLeft();
            }
        }

        void move() {
            p.move();
        }
    }
}