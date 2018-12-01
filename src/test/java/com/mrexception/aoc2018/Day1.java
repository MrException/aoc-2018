package com.mrexception.aoc2018;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mrexception.aoc2017.Day2;

@RunWith( JUnit4.class )
public class Day1 {
    private Logger log = LoggerFactory.getLogger( Day2.class.getName() );

    private Resource dataFile;

    @Before
    public void setup() {
        dataFile = new ClassPathResource( "com/mrexception/aoc2018/day1.txt" );
    }

    @Test
    public void testData() throws Exception {
        InputStream resource = dataFile.getInputStream();
        String data;
        try( BufferedReader reader = new BufferedReader(
          new InputStreamReader( resource ) ) ) {
            data = reader.lines()
              .collect( Collectors.joining( "\n" ) );
        }
        assertThat( data.length() ).isGreaterThan( 0 );
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat( new Logic( Stream.of( "+1", "+1", "+1" ) ).partOne() ).isEqualTo( 3 );
        assertThat( new Logic( Stream.of( "+1", "+1", "-2" ) ).partOne() ).isEqualTo( 0 );
        assertThat( new Logic( Stream.of( "-1", "-2", "-3" ) ).partOne() ).isEqualTo( -6 );

        InputStream resource = dataFile.getInputStream();
        try( BufferedReader reader = new BufferedReader(
          new InputStreamReader( resource ) ) ) {
            assertThat( new Logic( reader.lines() ).partOne() ).isEqualTo( 569 );
        }
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat( new Logic( Stream.of( "+1", "-1" ) ).partTwo() ).isEqualTo( 0 );
        assertThat( new Logic( Stream.of( "+3", "+3", "+4", "-2", "-4" ) ).partTwo() ).isEqualTo( 10 );
        assertThat( new Logic( Stream.of( "-6", "+3", "+8", "+5", "-6" ) ).partTwo() ).isEqualTo( 5 );
        assertThat( new Logic( Stream.of( "+7", "+7", "-2", "-7", "-4" ) ).partTwo() ).isEqualTo( 14 );

        InputStream resource = dataFile.getInputStream();
        try( BufferedReader reader = new BufferedReader(
          new InputStreamReader( resource ) ) ) {
            assertThat( new Logic( reader.lines() ).partTwo() ).isEqualTo( 77666 );
        }
    }

    class Logic {
        private final Stream<String> data;

        Logic( Stream<String> data ) {
            this.data = data;
        }

        int partOne() {
            return data
              .map( Integer::parseInt )
              .reduce( Integer::sum )
              .get();
        }

        int partTwo() {
            List<Integer> list = data
              .map( Integer::parseInt )
              .collect( Collectors.toList() );
            HashSet<Integer> found = new HashSet<>();
            found.add(0);
            return partTwoInner( list, found, 0 );
        }

        int partTwoInner( List<Integer> list, Set<Integer> found, Integer result ) {
            for( Integer cur : list ) {
                result += cur;
                if( found.contains( result ) ) {
                    return result;
                }
                found.add( result );
            }
            return partTwoInner( list, found, result );
        }
    }
}