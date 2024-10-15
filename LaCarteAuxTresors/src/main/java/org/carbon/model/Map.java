package org.carbon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class Map {
    ArrayList<ArrayList<Integer>> map;

    public void checkTreasure(Player player) {
        int actualCase = map.get(player.getPositionHeight()).get(player.getPositionWidth());
        if(actualCase > 0) {
            System.out.println("Player P(" + player.getOrder() + ") " + player.getName() + " found a treasure at position " + player.getPositionWidth() + " " + player.getPositionHeight());
            player.addScore();
            map.get(player.getPositionHeight()).set(player.getPositionWidth(), actualCase - 1);
        }
    }

    public boolean isMountain(int x, int y) {
        return this.map.get(y).get(x) == -1;
    }

    public StringBuilder getAllMountainPositions() {
        StringBuilder mountainPositions = new StringBuilder();
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                if (this.map.get(i).get(j) == -1) {
                    mountainPositions.append("M").append(" - ").append(j).append(" - ").append(i).append("\n");
                }
            }
        }
        return mountainPositions;
    }

    public StringBuilder getAllTreasurePositions() {
        StringBuilder treasurePositions = new StringBuilder();
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                if (this.map.get(i).get(j) >= 1) {
                    treasurePositions.append("T").append(" - ").append(j).append(" - ").append(i).append(" - ").append(this.map.get(i).get(j)).append("\n");
                }
            }
        }
        return treasurePositions;
    }

    public String toString(Players players) {
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                int finalI = i;
                int finalJ = j;
                Optional<Player> player = players.getPlayers().stream().filter(p -> p.getPositionHeight() == finalI && p.getPositionWidth() == finalJ).findFirst();
                if (player.isPresent()) {
                    map.append("P").append(player.get().getOrder()).append(" ");
                } else {
                    String currentCase = this.map.get(i).get(j).toString();
                    if(currentCase.length() == 1) {
                        map.append(currentCase).append("  ");
                    } else {
                        map.append(currentCase).append(" ");
                    }
                }
            }
            map.append("\n");
        }
        return map.toString();
    }
}
