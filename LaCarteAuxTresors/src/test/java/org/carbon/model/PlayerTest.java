package org.carbon.model;

import org.carbon.exception.LineFormatException;
import org.junit.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void should_return_negative_int_when_current_player_is_before_other_player() {
        // Given
        Player currentPlayer = Player.builder().name("currentPlayer").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        Player otherPlayer = Player.builder().name("otherPlayer").positionWidth(2).positionHeight(2).orientation(Orientation.EAST).score(2).order(2).build();

        // When
        int result = currentPlayer.compareTo(otherPlayer);

        // Then
        assertTrue(result < 0);
    }

    @Test
    public void should_return_positive_int_when_current_player_is_after_other_player() {
        // Given
        Player currentPlayer = Player.builder().name("currentPlayer").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(2).build();
        Player otherPlayer = Player.builder().name("otherPlayer").positionWidth(2).positionHeight(2).orientation(Orientation.EAST).score(2).order(1).build();

        // When
        int result = currentPlayer.compareTo(otherPlayer);

        // Then
        assertTrue(result > 0);
    }

    @Test
    public void should_increment_score() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        int expectedScore = 2;

        // When
        player.addScore();

        // Then
        assertEquals(expectedScore, player.getScore());
    }

    @Test
    public void should_move_forward_when_next_position_is_free() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();
        int expectedPositionWidth = 1;
        int expectedPositionHeight = 0;

        // When
        player.moveForward(map, players);

        // Then
        assertEquals(expectedPositionWidth, player.getPositionWidth());
        assertEquals(expectedPositionHeight, player.getPositionHeight());
    }

    @Test
    public void should_not_move_forward_when_next_position_is_mountain() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,-1,0)), new ArrayList<>(asList(0,-0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>(asList(player))).build();
        int expectedPositionWidth = 1;
        int expectedPositionHeight = 1;

        // When
        player.moveForward(map, players);

        // Then
        assertEquals(expectedPositionWidth, player.getPositionWidth());
        assertEquals(expectedPositionHeight, player.getPositionHeight());
    }

    @Test
    public void should_not_move_forward_when_next_position_is_occupied_by_another_player() {
        // Given
        Player player1 = Player.builder().name("player1").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        Player player2 = Player.builder().name("player2").positionWidth(1).positionHeight(0).orientation(Orientation.NORTH).score(1).order(2).build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>(asList(player1, player2))).build();
        int expectedPositionWidth = 1;
        int expectedPositionHeight = 1;

        // When
        player1.moveForward(map, players);

        // Then
        assertEquals(expectedPositionWidth, player1.getPositionWidth());
        assertEquals(expectedPositionHeight, player1.getPositionHeight());
    }

    @Test
    public void should_not_move_forward_when_next_position_is_out_of_map() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(0).orientation(Orientation.NORTH).score(1).order(1).build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();
        int expectedPositionWidth = 1;
        int expectedPositionHeight = 0;

        // When
        player.moveForward(map, players);

        // Then
        assertEquals(expectedPositionWidth, player.getPositionWidth());
        assertEquals(expectedPositionHeight, player.getPositionHeight());
    }

    @Test
    public void should_moveForward_when_sequence_start_with_A() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).sequence("AendOfSequence").build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();
        int expectedPositionWidth = 1;
        int expectedPositionHeight = 0;

        // When
        player.nextSequence(map, players);

        // Then
        assertEquals(expectedPositionWidth, player.getPositionWidth());
        assertEquals(expectedPositionHeight, player.getPositionHeight());
        assertEquals("endOfSequence", player.getSequence());
    }

    @Test
    public void should_turnRight_when_sequence_start_with_D() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).sequence("DGGG").build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();
        Orientation expectedOrientation = Orientation.EAST;

        // When
        player.nextSequence(map, players);

        // Then
        assertEquals(expectedOrientation, player.getOrientation());
        assertEquals("GGG", player.getSequence());
    }

    @Test
    public void should_turnLeft_when_sequence_start_with_G() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).sequence("GDDD").build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();
        Orientation expectedOrientation = Orientation.WEST;

        // When
        player.nextSequence(map, players);

        // Then
        assertEquals(expectedOrientation, player.getOrientation());
        assertEquals("DDD", player.getSequence());
    }

    @Test
    public void should_throw_exception_when_sequence_start_with_unknown_char() {
        // Given
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).sequence("Z").build();
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Players players = Players.builder().players(new ArrayList<>()).build();

        // When
        // Then
        assertThrows(LineFormatException.class, () -> player.nextSequence(map, players));
    }

}