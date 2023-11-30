package com.mrexception.aoc2017;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day4 {
    private Logger log = LoggerFactory.getLogger( Day4.class.getName() );
    private String inputFile = "com/mrexception/aoc2017/day4.txt";

    @Test
    public void testData() throws Exception {
        assertThat( processFile( inputFile ).length ).isGreaterThan( 0 );
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[] {
          "aa bb cc dd ee",
          "aa bb cc dd aa",
          "aa bb cc dd aaa"
        };

        assertThat( new Logic( testData ).partOne() ).isEqualTo( 2 );

        assertThat( new Logic( processFile( inputFile ) ).partOne() ).isEqualTo( 451 );
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[] {
          "abcde fghij",
          "abcde xyz ecdab",
          "a ab abc abd abf abj",
          "iiii oiii ooii oooi oooo",
          "oiii ioii iioi iiio"
        };
        assertThat( new Logic( testData ).partTwo() ).isEqualTo( 3 );

        assertThat( new Logic( processFile( inputFile ) ).partTwo() ).isEqualTo( 223 );
    }

    class Logic {
        private final String[] data;

        Logic( String[] data ) {
            this.data = data;
        }

        int partOne() {
            int good = 0;
            for( String line : data ) {
                boolean valid = true;
                Set<String> seen = new HashSet<>();
                for( String pass : splitLine( line ) ) {
                    if( seen.contains( pass ) ) {
                        valid = false;
                        break;
                    }
                    seen.add( pass );
                }
                if( valid ) {
                    good++;
                }
            }
            return good;
        }

        int partTwo() {
            int good = 0;
            for( String line : data ) {
                boolean valid = true;
                Set<String> seen = new HashSet<>();
                for( String pass : splitLine( line ) ) {
                    pass = sortLetters( pass );
                    if( seen.contains( pass ) ) {
                        valid = false;
                        break;
                    }
                    seen.add( pass );
                }
                if( valid ) {
                    good++;
                }
            }
            return good;
        }

        private String sortLetters( String pass ) {
            char[] chars = pass.toCharArray();
            Arrays.sort( chars );
            return new String( chars );
        }
    }
}
