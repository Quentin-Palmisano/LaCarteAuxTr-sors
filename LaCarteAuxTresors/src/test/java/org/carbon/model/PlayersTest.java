package org.carbon.model;

import org.junit.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayersTest {

    @Test
    public void should_return_players_positions() {
        // Given
        Player player1 = Player.builder().name("player1").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).build();
        Player player2 = Player.builder().name("player2").positionWidth(2).positionHeight(2).orientation(Orientation.EAST).score(2).build();
        Player player3 = Player.builder().name("player3").positionWidth(3).positionHeight(3).orientation(Orientation.SOUTH).score(3).build();
        Player player4 = Player.builder().name("player4").positionWidth(4).positionHeight(4).orientation(Orientation.WEST).score(4).build();
        Players players = Players.builder().players(new ArrayList<>(asList(player1, player2, player3, player4))).build();
        StringBuilder expected = new StringBuilder();
        expected.append("A - player1 - 1 - 1 - N - 1\n");
        expected.append("A - player2 - 2 - 2 - E - 2\n");
        expected.append("A - player3 - 3 - 3 - S - 3\n");
        expected.append("A - player4 - 4 - 4 - O - 4\n");

        // When
        String result = players.getPlayersPositions().toString();

        // Then
        assertEquals(expected.toString(), result);
    }

    @Test
    public void should_return_empty_string_given_empty_players() {
        // Given
        Players players = Players.builder().players(new ArrayList<>()).build();

        // When
        String result = players.getPlayersPositions().toString();

        // Then
        assertEquals("", result);
    }

    @Test
    public void should_return_true_given_free_position() {
        // Given
        Player player1 = Player.builder().name("player1").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).build();
        Player player2 = Player.builder().name("player2").positionWidth(2).positionHeight(2).orientation(Orientation.EAST).score(2).build();
        Player player3 = Player.builder().name("player3").positionWidth(3).positionHeight(3).orientation(Orientation.SOUTH).score(3).build();
        Player player4 = Player.builder().name("player4").positionWidth(4).positionHeight(4).orientation(Orientation.WEST).score(4).build();
        Players players = Players.builder().players(new ArrayList<>(asList(player1, player2, player3, player4))).build();

        // When
        boolean result = players.isFreeOfPlayers(5, 5);

        // Then
        assertTrue(result);
    }

    @Test
    public void should_return_false_given_occupied_position() {
        // Given
        Player player1 = Player.builder().name("player1").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).build();
        Player player2 = Player.builder().name("player2").positionWidth(2).positionHeight(2).orientation(Orientation.EAST).score(2).build();
        Player player3 = Player.builder().name("player3").positionWidth(3).positionHeight(3).orientation(Orientation.SOUTH).score(3).build();
        Player player4 = Player.builder().name("player4").positionWidth(4).positionHeight(4).orientation(Orientation.WEST).score(4).build();
        Players players = Players.builder().players(new ArrayList<>(asList(player1, player2, player3, player4))).build();

        // When
        boolean result = players.isFreeOfPlayers(1, 1);

        // Then
        assertFalse(result);
    }
}