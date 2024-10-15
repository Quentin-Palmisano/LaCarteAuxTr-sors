package org.carbon.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class OrientationTest {

    private static Stream<Arguments> getValidOrientations() {
        return Stream.of(Arguments.of("N", Orientation.NORTH), Arguments.of("E", Orientation.EAST), Arguments.of("S", Orientation.SOUTH), Arguments.of("O", Orientation.WEST), Arguments.of("anything", null));
    }

    private static Stream<Arguments> getTurnRightOrientations() {
        return Stream.of(Arguments.of(Orientation.NORTH, Orientation.EAST), Arguments.of(Orientation.EAST, Orientation.SOUTH), Arguments.of(Orientation.SOUTH, Orientation.WEST), Arguments.of(Orientation.WEST, Orientation.NORTH));
    }

    private static Stream<Arguments> getTurnLeftOrientations() {
        return Stream.of(Arguments.of(Orientation.NORTH, Orientation.WEST), Arguments.of(Orientation.EAST, Orientation.NORTH), Arguments.of(Orientation.SOUTH, Orientation.EAST), Arguments.of(Orientation.WEST, Orientation.SOUTH));
    }

    @ParameterizedTest
    @MethodSource("getValidOrientations")
    public void should_map_orientation(String orientation, Orientation expected) {
        Assertions.assertEquals(Orientation.mapOrientation(orientation), expected);
    }

    @ParameterizedTest
    @MethodSource("getTurnRightOrientations")
    public void should_turn_right(Orientation orientation, Orientation expected) {
        Assertions.assertEquals(orientation.turnRight(), expected);
    }

    @ParameterizedTest
    @MethodSource("getTurnLeftOrientations")
    public void should_turn_left(Orientation orientation, Orientation expected) {
        Assertions.assertEquals(orientation.turnLeft(), expected);
    }

}