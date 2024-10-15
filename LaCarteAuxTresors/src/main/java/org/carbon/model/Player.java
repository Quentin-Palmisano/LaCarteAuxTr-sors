package org.carbon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.carbon.exception.LineFormatException;

@Builder
@Getter
@AllArgsConstructor
public class Player {

    private String name;
    private int positionWidth;
    private int positionHeight;
    private Orientation orientation;
    private String sequence;
    private int score;
    private int order;

    public int compareTo(Player player) {
        return this.order - player.order;
    }

    public void addScore() {
        this.score++;
    }

    public void moveForward(Map map, Players players) {
        int nextPositionWidth = this.positionWidth;
        int nextPositionHeight = this.positionHeight;
        switch (this.orientation) {
            case NORTH:
                nextPositionHeight = Math.max(0, this.positionHeight - 1);
                break;
            case EAST:
                nextPositionWidth = Math.min(map.getMap().get(0).size() - 1, this.positionWidth + 1);
                break;
            case SOUTH:
                nextPositionHeight = Math.min(map.getMap().size() - 1, this.positionHeight + 1);
                break;
            case WEST:
                nextPositionWidth = Math.max(0, this.positionWidth - 1);
                break;
        }
        if (!map.isMountain(nextPositionWidth, nextPositionHeight) && players.isFreeOfPlayers(nextPositionWidth, nextPositionHeight)) {
            this.positionWidth = nextPositionWidth;
            this.positionHeight = nextPositionHeight;
        }
        map.checkTreasure(this);
    }

    public void nextSequence(Map map, Players players) {
        if (this.sequence.isEmpty()) {
            return;
        }
        char currentChar = this.sequence.charAt(0);
        switch (currentChar) {
            case 'A':
                this.moveForward(map, players);
                break;
            case 'D':
                this.orientation = this.orientation.turnRight();
                break;
            case 'G':
                this.orientation = this.orientation.turnLeft();
                break;
            default:
                throw new LineFormatException("Sequence should only contain 'A', 'D' or 'G'");
        }
        this.sequence = this.sequence.substring(1);
    }

    @Override
    public String toString() {
        StringBuilder player = new StringBuilder();
        player.append("Player P(").append(this.order).append(") ")
                .append(this.name)
                .append(" is at position (")
                .append(this.positionWidth)
                .append(", ")
                .append(this.positionHeight)
                .append(") facing ")
                .append(this.orientation)
                .append(" with a score of ")
                .append(this.score)
                .append(" seq(")
                .append(this.sequence)
                .append(")");

        return player.toString();
    }
}
