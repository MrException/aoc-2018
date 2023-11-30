package com.mrexception.aoc2018;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrexception.Point;

public class Day10 {
    private Logger log = LoggerFactory.getLogger( getClass().getName() );
    private String inputFile = "com/mrexception/aoc2018/day10.txt";

    private String[] testData = new String[] {
      "position=< 9,  1> velocity=< 0,  2>",
      "position=< 7,  0> velocity=<-1,  0>",
      "position=< 3, -2> velocity=<-1,  1>",
      "position=< 6, 10> velocity=<-2, -1>",
      "position=< 2, -4> velocity=< 2,  2>",
      "position=<-6, 10> velocity=< 2, -2>",
      "position=< 1,  8> velocity=< 1, -1>",
      "position=< 1,  7> velocity=< 1,  0>",
      "position=<-3, 11> velocity=< 1, -2>",
      "position=< 7,  6> velocity=<-1, -1>",
      "position=<-2,  3> velocity=< 1,  0>",
      "position=<-4,  3> velocity=< 2,  0>",
      "position=<10, -3> velocity=<-1,  1>",
      "position=< 5, 11> velocity=< 1, -2>",
      "position=< 4,  7> velocity=< 0, -1>",
      "position=< 8, -2> velocity=< 0,  1>",
      "position=<15,  0> velocity=<-2,  0>",
      "position=< 1,  6> velocity=< 1,  0>",
      "position=< 8,  9> velocity=< 0, -1>",
      "position=< 3,  3> velocity=<-1,  1>",
      "position=< 0,  5> velocity=< 0, -1>",
      "position=<-2,  2> velocity=< 2,  0>",
      "position=< 5, -2> velocity=< 1,  2>",
      "position=< 1,  4> velocity=< 2,  1>",
      "position=<-2,  7> velocity=< 2, -2>",
      "position=< 3,  6> velocity=<-1, -1>",
      "position=< 5,  0> velocity=< 1,  0>",
      "position=<-6,  0> velocity=< 2,  0>",
      "position=< 5,  9> velocity=< 1, -2>",
      "position=<14,  7> velocity=<-2,  0>",
      "position=<-3,  6> velocity=< 2, -1>"
    };

    @Test
    public void testData() throws Exception {
        assertThat( processFile( inputFile ).length ).isGreaterThan( 0 );
    }

    @Test
    public void testPartOne() throws Exception {
        String hi = "#...#..###\n#...#...#.\n#...#...#.\n#####...#.\n#...#...#.\n#...#...#.\n#...#...#.\n#...#..###\n";
        assertThat( new Logic( testData ).run( false ) ).isEqualTo( hi.length() );

        assertThat( new Logic( processFile( inputFile ) ).run( false ) ).isEqualTo( 630 );
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat( new Logic( testData ).run( true ) ).isEqualTo( 3 );
        assertThat( new Logic( processFile( inputFile ) ).run( true ) ).isEqualTo( 0 );
    }

    class Logic {
        int minX;
        int maxX;
        int minY;
        int maxY;
        Point[] points;

        Logic( String[] data ) {
            points = new Point[ data.length ];
            for( int i = 0; i < data.length; i++ ) {
                String line = data[ i ];
                String[] split = splitLine( line, ">" );
                int[] pos = toInts( splitLine( split[ 0 ].substring( 10 ), "," ) );
                int[] vel = toInts( splitLine( split[ 1 ].substring( 10 ), "," ) );
                Point p = new Point( pos[ 0 ], pos[ 1 ] );
                p.setVelocity( vel[ 0 ], vel[ 1 ] );
                points[ i ] = p;
            }
        }

        int run( boolean partTwo ) {
            for( int i = 0; i < 100000; i++ ) {
                for( Point point : points ) {
                    point.move();
                }
                if( heuristic() ) {
                    if( partTwo ) {
                        return i + 1;
                    }
                    break;
                }
            }
            return print().length();
        }

        boolean heuristic() {
            // a heuristic test to see if we are done
            minMax();
            int height = maxY - minY;
            int width = maxX - minX;
            if( height < 20 && width < 100 ) {
                Set<Point> set = new HashSet<>( Arrays.asList( points ) );
                // 8 or more in one of the first 5 columns?
                for( int x = minX; x < minX + 6; x++ ) {
                    int col = 0;
                    for( int y = 0; y < minY + 20; y++ ) {
                        if( set.contains( new Point( x, y ) ) ) {
                            col++;
                        }
                    }
                    if( col >= 8 ) {
                        return true;
                    }
                }
            }
            return false;
        }

        String print() {
            minMax();
            Set<Point> set = new HashSet<>( Arrays.asList( points ) );
            StringBuilder total = new StringBuilder();
            for( int y = minY; y <= maxY; y++ ) {
                StringBuilder line = new StringBuilder( maxY - minY );
                for( int x = minX; x <= maxX; x++ ) {
                    if( set.contains( new Point( x, y ) ) ) {
                        line.append( '#' );
                    } else {
                        line.append( '.' );
                    }
                }
                log.info( line.toString() );
                total.append( line.toString() ).append( "\n" );
            }
            return total.toString();
        }

        void minMax() {
            minX = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            minY = Integer.MAX_VALUE;
            maxY = Integer.MIN_VALUE;
            for( Point p : points ) {
                if( p.x < minX ) {
                    minX = p.x;
                }
                if( p.x > maxX ) {
                    maxX = p.x;
                }
                if( p.y < minY ) {
                    minY = p.y;
                }
                if( p.y > maxY ) {
                    maxY = p.y;
                }
            }
        }
    }
}