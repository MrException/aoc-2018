package com.mrexception.aoc2017;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrexception.Point;

@RunWith( JUnit4.class )
public class Day3 {
    private Logger log = LoggerFactory.getLogger( Day3.class.getName() );

    @Test
    public void testPartOne() {
        assertThat( new Logic( 1 ).partOne() ).isEqualTo( 0 );
        assertThat( new Logic( 12 ).partOne() ).isEqualTo( 3 );
        assertThat( new Logic( 23 ).partOne() ).isEqualTo( 2 );
        assertThat( new Logic( 1024 ).partOne() ).isEqualTo( 31 );

        assertThat( new Logic( 277678 ).partOne() ).isEqualTo( 475 );
    }

    @Test
    public void testPartTwo() {
        assertThat( new Logic( 1 ).partTwo() ).isEqualTo( 2 );
        assertThat( new Logic( 6 ).partTwo() ).isEqualTo( 10 );
        assertThat( new Logic( 27 ).partTwo() ).isEqualTo( 54 );
        assertThat( new Logic( 130 ).partTwo() ).isEqualTo( 133 );
        assertThat( new Logic( 750 ).partTwo() ).isEqualTo( 806 );

        assertThat( new Logic( 277678 ).partTwo() ).isEqualTo( 279138);
    }

    class Logic {
        private final int data;

        Logic( int data ) {
            this.data = data;
        }

        List<Point> getPoints(int length) {
            var points = new ArrayList<Point>();
            int edgeLen = 3;
            Point p = new Point( 0, 0 );
            points.add( p );
            if( length == 1 ) {
                return points;
            }
            p = p.right();
            points.add( p );
            int i = 2;
            while( true ) {
                if( i == length ) {
                    return points;
                }
                for( int j = 0; j < edgeLen - 2; j++ ) {
                    p = p.up();
                    i++;
                    points.add( p );
                    if( i == length ) {
                        return points;
                    }
                }
                for( int j = 0; j < edgeLen - 1; j++ ) {
                    p = p.left();
                    i++;
                    points.add( p );
                    if( i == length ) {
                        return points;
                    }
                }
                for( int j = 0; j < edgeLen - 1; j++ ) {
                    p = p.down();
                    i++;
                    points.add( p );
                    if( i == length ) {
                        return points;
                    }
                }
                for( int j = 0; j < edgeLen; j++ ) {
                    p = p.right();
                    i++;
                    points.add( p );
                    if( i == length ) {
                        return points;
                    }
                }
                edgeLen += 2;
            }
        }

        int partOne() {
            List<Point> points = getPoints(data);
            Point point = points.get( points.size() - 1 );
            return point.manhattanDistance();
        }

        int partTwo() {
            List<Point> points = getPoints(1000);
            Map<Point, Integer> map = new HashMap<>();
            for( Point p : points ) {
                if( p.equals( Point.ORIGIN ) ) {
                    map.put( p, 1 );
                    continue;
                }
                int value = 0;
                for( Point n : p.neighbour8() ) {
                    if( map.containsKey( n ) ) {
                        value += map.get( n );
                    }
                }
                if( value > data ) {
                    return value;
                }
                map.put( p, value );
            }
            return -1;
        }
    }
}
