package org.carbon.model;

import org.junit.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {

    @Test
    public void should_player_score_increment_when_he_is_at_treasure_position() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,2,0)), new ArrayList<>(asList(0,0,0))))).build();
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        int expectedScore = 2;
        int expectedTreasure = 1;

        // When
        map.checkTreasure(player);

        // Then
        assertEquals(expectedScore, player.getScore());
        assertEquals(expectedTreasure, map.getMap().get(1).get(1));
    }

    @Test
    public void should_player_score_not_increment_when_he_is_not_at_treasure_position() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        Player player = Player.builder().name("player").positionWidth(1).positionHeight(1).orientation(Orientation.NORTH).score(1).order(1).build();
        int expectedScore = 1;

        // When
        map.checkTreasure(player);

        // Then
        assertEquals(expectedScore, player.getScore());
    }

    @Test
    public void should_return_true_when_position_is_mountain() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,-1,0)), new ArrayList<>(asList(0,0,0))))).build();
        int x = 1;
        int y = 1;

        // When
        boolean result = map.isMountain(x, y);

        // Then
        assertTrue(result);
    }

    @Test
    public void should_return_false_when_position_is_not_mountain() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0)), new ArrayList<>(asList(0,0,0))))).build();
        int x = 1;
        int y = 1;

        // When
        boolean result = map.isMountain(x, y);

        // Then
        assertFalse(result);
    }

    @Test
    public void should_return_all_mountain_positions() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(0,-1,0)), new ArrayList<>(asList(0,-1,0)), new ArrayList<>(asList(0,0,0))))).build();
        String expectedMountainPositions = "M - 1 - 0\nM - 1 - 1\n";

        // When
        String mountainPositions = map.getAllMountainPositions().toString();

        // Then
        assertEquals(expectedMountainPositions, mountainPositions);
    }

    @Test
    public void should_return_all_treasure_positions() {
        // Given
        Map map = Map.builder().map(new ArrayList<>(asList(new ArrayList<>(asList(1,0,0)), new ArrayList<>(asList(0,2,0)), new ArrayList<>(asList(0,0,0))))).build();
        String expectedTreasurePositions = "T - 0 - 0 - 1\nT - 1 - 1 - 2\n";

        // When
        String treasurePositions = map.getAllTreasurePositions().toString();

        // Then
        assertEquals(expectedTreasurePositions, treasurePositions);
    }
}