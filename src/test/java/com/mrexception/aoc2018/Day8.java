package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day8 {
    private String inputFile = "com/mrexception/aoc2018/day8.txt";

    private String testData = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(testData).partOne()).isEqualTo(138);

        assertThat(new Logic(processFile(inputFile)[0]).partOne()).isEqualTo(38722);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(testData).partTwo()).isEqualTo(66);

        assertThat(new Logic(processFile(inputFile)[0]).partTwo()).isEqualTo(13935);
    }

    class Logic {
        private final int[] data;

        Logic(String data) {
            this.data = toInts(splitLine(data));
        }

        private int pointer;

        int partOne() {
            pointer = 0;
            Node head = nextNode();
            return head.partOneSum();
        }

        int partTwo() {
            pointer = 0;
            Node head = nextNode();
            return head.partTwoSum();
        }

        private Node nextNode() {
            int childCount = data[pointer];
            int metadataCount = data[pointer + 1];
            Node node = new Node(childCount, metadataCount);
            pointer += 2;
            for (int i = 0; i < childCount; i++) {
                node.children[i] = nextNode();
            }
            for (int i = 0; i < metadataCount; i++) {
                node.metadata[i] = data[pointer];
                pointer++;
            }
            return node;
        }
    }

    class Node {
        Node[] children;
        int[] metadata;

        Node(int childCount, int metadataCount) {
            children = new Node[childCount];
            metadata = new int[metadataCount];
        }

        int partOneSum() {
            int sum = 0;
            for (int i : metadata) {
                sum += i;
            }
            for (Node child : children) {
                sum += child.partOneSum();
            }
            return sum;
        }

        int partTwoSum() {
            int sum = 0;

            if (children.length == 0) {
                for (int i : metadata) {
                    sum += i;
                }
            } else {
                for (int i : metadata) {
                    if (i <= children.length) {
                        sum += children[i - 1].partTwoSum();
                    }
                }
            }
            return sum;
        }
    }
}