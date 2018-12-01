package com.mrexception.aoc2017;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RunWith( JUnit4.class)
public class Day2 {
    private Logger log = LoggerFactory.getLogger( Day2.class.getName() );

    private Resource dataFile;

    @Before
    public void setup() {
        dataFile = new ClassPathResource( "Day2.txt" );
    }

    @Test
    public void testData() throws Exception {
        InputStream resource = dataFile.getInputStream();
        String data;
        try ( BufferedReader reader = new BufferedReader(
          new InputStreamReader(resource)) ) {
            data = reader.lines()
              .collect( Collectors.joining( "\n" ) );
        }
        log.info(data);
        assertThat(data.length()).isGreaterThan( 0 );
    }
}
