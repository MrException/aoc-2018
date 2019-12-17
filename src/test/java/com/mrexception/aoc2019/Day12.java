package com.mrexception.aoc2019;

import com.mrexception.Point3D;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day12 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    private Point3D[] moons = new Point3D[]{
            new Point3D(15, -2, -6),
            new Point3D(-5, -4, -11),
            new Point3D(0, -6, 0),
            new Point3D(5, 9, 6)
    };

    @Test
    public void testPartOne() {
        Point3D[] testIn1 = new Point3D[]{
                new Point3D(-1, 0, 2),
                new Point3D(2, -10, -7),
                new Point3D(4, -8, 8),
                new Point3D(3, 5, -1)
        };

        assertThat(new Logic(testIn1, 10).partOne()).isEqualTo(179);

        Point3D[] testIn2 = new Point3D[]{
                new Point3D(-8, -10, 0),
                new Point3D(5, 5, 10),
                new Point3D(2, -7, 3),
                new Point3D(9, -8, -3)
        };

        assertThat(new Logic(testIn2, 100).partOne()).isEqualTo(1940);

        assertThat(new Logic(moons, 1000).partOne()).isEqualTo(6735);
    }

    @Test
    public void testPartTwo() {
        Point3D[] testIn1 = new Point3D[]{
                new Point3D(-1, 0, 2),
                new Point3D(2, -10, -7),
                new Point3D(4, -8, 8),
                new Point3D(3, 5, -1)
        };

        assertThat(new Logic(testIn1, 100000).partTwo()).isEqualTo(2772);

        Point3D[] testIn2 = new Point3D[]{
                new Point3D(-8, -10, 0),
                new Point3D(5, 5, 10),
                new Point3D(2, -7, 3),
                new Point3D(9, -8, -3)
        };
//        assertThat(new Logic(testIn2, 500000000).partTwo()).isEqualTo(2772);

        assertThat(new Logic(moons, 250000000).partTwo()).isEqualTo(2772);
    }

    class Logic {

        private final Point3D[] moons;
        private final long iterations;
        //        private final SetMultimap<Integer, String> hashes;
        private final Set<Integer> energies;

        @SuppressWarnings("UnstableApiUsage")
        public Logic(Point3D[] moons, int iterations) {
            this.moons = moons;
            this.iterations = iterations;
//            hashes = MultimapBuilder.hashKeys(iterations / 2).hashSetValues(1).build();
            energies = new HashSet<>(iterations / 2);
        }

        private void updateVal(Point3D a, Point3D b) {
            if (a.x != b.x) {
                if (a.x > b.x) {
                    a.velocity.x -= 1;
                    b.velocity.x += 1;
                } else {
                    a.velocity.x += 1;
                    b.velocity.x -= 1;
                }
            }

            if (a.y != b.y) {
                if (a.y > b.y) {
                    a.velocity.y -= 1;
                    b.velocity.y += 1;
                } else {
                    a.velocity.y += 1;
                    b.velocity.y -= 1;
                }
            }

            if (a.z != b.z) {
                if (a.z > b.z) {
                    a.velocity.z -= 1;
                    b.velocity.z += 1;
                } else {
                    a.velocity.z += 1;
                    b.velocity.z -= 1;
                }
            }
        }

        int calcEnergy() {
            int e = 0;

            e += (Math.abs(moons[0].x) + Math.abs(moons[0].y) + Math.abs(moons[0].z)) * (Math.abs(moons[0].velocity.x) + Math.abs(moons[0].velocity.y) +
                    Math.abs(moons[0].velocity.z));
            e += (Math.abs(moons[1].x) + Math.abs(moons[1].y) + Math.abs(moons[1].z)) * (Math.abs(moons[1].velocity.x) + Math.abs(moons[1].velocity.y) +
                    Math.abs(moons[1].velocity.z));
            e += (Math.abs(moons[2].x) + Math.abs(moons[2].y) + Math.abs(moons[2].z)) * (Math.abs(moons[2].velocity.x) + Math.abs(moons[2].velocity.y) +
                    Math.abs(moons[2].velocity.z));
            e += (Math.abs(moons[3].x) + Math.abs(moons[3].y) + Math.abs(moons[3].z)) * (Math.abs(moons[3].velocity.x) + Math.abs(moons[3].velocity.y) +
                    Math.abs(moons[3].velocity.z));
            return e;
        }

        int checksumi(int c, int v) {
            c += v;
            c = c << 3 | c >> (32 - 3); // rotate a little
            c ^= 0xFFFFFFFF; // invert just for fun
            return c;
        }

        int checksum() {
            int c = 0;
            for (int i = 0; i < 4; i++) {
                c = checksumi(c, moons[i].x);
                c = checksumi(c, moons[i].y);
                c = checksumi(c, moons[i].z);
                c = checksumi(c, moons[i].velocity.x);
                c = checksumi(c, moons[i].velocity.y);
                c = checksumi(c, moons[i].velocity.z);
            }
            return c;
        }

        void print(int i) {
            log.info("After {} steps:", i);
            log.info(moons[0].toString());
            log.info(moons[1].toString());
            log.info(moons[2].toString());
            log.info(moons[3].toString());
//            log.info("Hash - {}", hashes[i]);
            log.info("\n");
        }

        String hash() {
            String m0 = String.format("%s-%s-%s-%s-%s-%s", moons[0].x, moons[0].y, moons[0].z, moons[0].velocity.x, moons[0].velocity.y, moons[0].velocity.z);
            String m1 = String.format("%s-%s-%s-%s-%s-%s", moons[1].x, moons[1].y, moons[1].z, moons[1].velocity.x, moons[1].velocity.y, moons[1].velocity.z);
            String m2 = String.format("%s-%s-%s-%s-%s-%s", moons[2].x, moons[2].y, moons[2].z, moons[2].velocity.x, moons[2].velocity.y, moons[2].velocity.z);
            String m3 = String.format("%s-%s-%s-%s-%s-%s", moons[3].x, moons[3].y, moons[3].z, moons[3].velocity.x, moons[3].velocity.y, moons[3].velocity.z);
            return String.format("%s-%s-%s-%s", m0, m1, m2, m3);
        }

        boolean seen(int energy) {
            return energies.contains(energy);
//            return hashes.containsEntry(energy, hash);
        }

        private void step() {
            updateVal(moons[0], moons[1]);
            updateVal(moons[0], moons[2]);
            updateVal(moons[0], moons[3]);

            updateVal(moons[1], moons[2]);
            updateVal(moons[1], moons[3]);

            updateVal(moons[2], moons[3]);

            moons[0].move();
            moons[1].move();
            moons[2].move();
            moons[3].move();
        }

        int partOne() {
//            print(0);
            for (int i = 0; i < iterations; i++) {
                step();

//                if (i > 0 && i % 10 == 0) {
//                print(i + 1);
//                }
            }
            return calcEnergy();
        }

        int partTwo() {
            for (int i = 0; i < iterations; i++) {
                step();

                int checksum = checksum();
//                String hash = hash();
                if (seen(checksum)) {
                    return i;
                } else {
                    energies.add(checksum);
                }

                if (i > 0 && i % 100000 == 0) {
                    print(i);
                }
            }
            return 0;
        }
    }
}
