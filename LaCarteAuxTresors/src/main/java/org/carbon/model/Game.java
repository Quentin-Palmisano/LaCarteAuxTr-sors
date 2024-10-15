package org.carbon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.carbon.exception.ForbidenRuleException;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class Game {

    private int lapCounter = 0;
    private List<String> originalLines;

    private MapSize mapSize;
    private Players players;
    private Map map;

    public void initializeMap() {
        this.map = Map.builder().map(new ArrayList<>()).build();
        for (int i = 0; i < this.mapSize.getHeight(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < this.mapSize.getWidth(); j++) {
                row.add(0);
            }
            this.map.getMap().add(row);
        }
    }

    public void checkPlayersInitialPosition() {
        for (Player player : this.players.getPlayers()) {
            if (player.getPositionWidth() >= this.mapSize.getWidth() || player.getPositionHeight() >= this.mapSize.getHeight()) {
                throw new ForbidenRuleException("Player " + player.getName() + " is out of the map");
            }
            if (map.getMap().get(player.getPositionHeight()).get(player.getPositionWidth()) == -1) {
                throw new ForbidenRuleException("Player line does not respect the format 'A - name - x - y - direction - sequence', player cannot be placed on a mountain");
            }
        }
    }

    private void executeOneLap() {
        this.players.getPlayers().stream().sorted(Player::compareTo)
                .forEach(player -> player.nextSequence(this.map, this.players));
    }

    private boolean isOver() {
        return this.players.getPlayers().stream().allMatch(player -> player.getSequence().isEmpty());
    }

    public void play() {
        this.originalLines.forEach(System.out::println);
        System.out.println("\n");
        System.out.println("GAME STARTED\n");
        System.out.println(map.toString(players));
        while (!this.isOver()) {
            System.out.println(this);
            this.executeOneLap();
            lapCounter++;
        }
        System.out.println("GAME FINISHED\n");
    }

    @Override
    public String toString() {
        StringBuilder game = new StringBuilder();
        game.append("LAP ").append(lapCounter).append("\n").append(players).append("\n").append(map.toString(players));
        return game.toString();
    }

}
