package org.carbon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
@AllArgsConstructor
public class Players {

    private ArrayList<Player> players;

    public boolean isFreeOfPlayers(int x, int y) {
        return this.players.stream().noneMatch(player -> player.getPositionWidth() == x && player.getPositionHeight() == y);
    }

    public StringBuilder getPlayersPositions() {
        StringBuilder playersPositions = new StringBuilder();
        for (Player player : this.players) {
            playersPositions.append("A - ").append(player.getName()).append(" - ").append(player.getPositionWidth()).append(" - ").append(player.getPositionHeight()).append(" - ").append(player.getOrientation()).append(" - ").append(player.getScore()).append("\n");
        }
        return playersPositions;
    }

    @Override
    public String toString() {
        StringBuilder players = new StringBuilder();
        for (Player player : this.players) {
            players.append(player.toString()).append("\n");
        }
        return players.toString();
    }
}
