package org.carbon.Interpreter;

import org.carbon.exception.FileFormatException;
import org.carbon.exception.LineFormatException;
import org.carbon.model.Game;
import org.carbon.model.MapSize;
import org.carbon.model.Orientation;
import org.carbon.model.Player;
import org.carbon.model.Players;
import org.carbon.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Interpreter {

    public static final String OUTPUT_FILE_NAME = "src/main/resources/output/output.txt";

    public static Game mapFileToGame(String filePath) throws FileNotFoundException {
        Game.GameBuilder gameBuilder = Game.builder();

        System.out.println("Reading file " + filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        List<String> lines = reader.lines().toList();
        gameBuilder.originalLines(lines);

        lines = lines.stream().filter(line -> !StringUtils.isComment(line)).map(StringUtils::removeWhitespace).toList();

        checkLinesFormat(lines);

        List<String> mapLines = lines.stream().filter(StringUtils::isMapLine).toList();
        List<String> playersLines = lines.stream().filter(StringUtils::isPlayerLine).toList();
        List<String> mountainLines = lines.stream().filter(StringUtils::isMountainLine).toList();
        List<String> treasureLines = lines.stream().filter(StringUtils::isTreasureLine).toList();

        gameBuilder.mapSize(createMap(mapLines));
        gameBuilder.players(Players.builder().players(createPlayers(playersLines)).build());

        Game game = gameBuilder.build();
        game.initializeMap();
        addMountainsToMap(mountainLines, game.getMap().getMap(), game.getMapSize().getWidth(), game.getMapSize().getHeight());
        addTreasuresToMap(treasureLines, game.getMap().getMap(), game.getMapSize().getWidth(), game.getMapSize().getHeight());

        game.checkPlayersInitialPosition();

        return game;

    }

    public static void mapGameToFile(Game game) {
        StringBuilder output = new StringBuilder();
        output.append("C - ").append(game.getMapSize().getWidth()).append(" - ").append(game.getMapSize().getHeight()).append("\n")
                .append(game.getMap().getAllMountainPositions())
                .append(game.getMap().getAllTreasurePositions())
                .append(game.getPlayers().getPlayersPositions());
        try {
            FileWriter myWriter = new FileWriter(OUTPUT_FILE_NAME);
            myWriter.write(output.toString());
            myWriter.close();
            System.out.println("Output file created successfully. (" + OUTPUT_FILE_NAME + ")");
            System.out.println(output);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating output file (" + OUTPUT_FILE_NAME + ") :\n", e);
        }
    }

    public static void checkLinesFormat (List<String> lines) {
        if (lines == null || lines.size() < 2) {
            throw new FileFormatException("There should be at least two lines in the file. One for the map and one for the player");
        }
        if (lines.stream().anyMatch((line -> !StringUtils.isAcceptedLine(line)))) {
            throw new FileFormatException("A line should start with 'C', 'M', 'T' or 'A'");
        }
    }

    private static MapSize createMap(List<String> mapLines) {
        if (mapLines == null) {
            throw new FileFormatException("There should be a line which starts with 'C'");
        }
        if (mapLines.size() != 1) {
            throw new FileFormatException("There should be only one line which starts with 'C'");
        }
        String[] mapLine = mapLines.get(0).split("-");
        if (mapLine.length != 3) {
            throw new LineFormatException("Map line does not respect the format 'C - width - height'");
        }
        try {
            int width = Integer.parseInt(mapLine[1]);
            int height = Integer.parseInt(mapLine[2]);
            if (width < 0 || height < 0) {
                throw new LineFormatException("Map line does not respect the format 'C - width - height', width and height should be positive integers");
            }
            return MapSize.builder().width(width).height(height).build();
        } catch (NumberFormatException e) {
            throw new LineFormatException("Map line does not respect the format 'C - width - height', width and height should be positive integers");
        }
    }

    private static void addMountainsToMap(List<String> mountainLines, ArrayList<ArrayList<Integer>> map, int width, int height) {
        if (mountainLines == null) {
            return;
        }
        mountainLines.forEach(mountainLine -> {
            String[] mountainLineArray = mountainLine.split("-");
            if (mountainLineArray.length != 3) {
                throw new LineFormatException("Mountain line does not respect the format 'M - x - y'");
            }
            int x;
            int y;
            try {
                x = Integer.parseInt(mountainLineArray[1]);
                y = Integer.parseInt(mountainLineArray[2]);
                if (x < 0 || y < 0) {
                    throw new LineFormatException("Mountain line does not respect the format 'M - x - y', x and y should be positive integers");
                }
            } catch (NumberFormatException e) {
                throw new LineFormatException("Mountain line does not respect the format 'M - x - y', x and y should be positive integers");
            }
            if (x >= width || y >= height) {
                throw new LineFormatException("Mountain line does not respect the format 'M - x - y', x and y should be less than the width and height of the map");
            }
            map.get(y).set(x, -1);
        });
    }

    private static void addTreasuresToMap(List<String> treasureLines, ArrayList<ArrayList<Integer>> map, int width, int height) {
        if (treasureLines == null) {
            return;
        }
        treasureLines.forEach(treasureLine -> {
            String[] treasureLineArray = treasureLine.split("-");
            if (treasureLineArray.length != 4) {
                throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure'");
            }
            int x;
            int y;
            int nbTreasure;
            try {
                x = Integer.parseInt(treasureLineArray[1]);
                y = Integer.parseInt(treasureLineArray[2]);
                nbTreasure = Integer.parseInt(treasureLineArray[3]);
                if (x < 0 || y < 0) {
                    throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure', x and y should be positive integers");
                }
                if (nbTreasure <= 0) {
                    throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure', nbTreasure should be a positive integer gretaer than 0");
                }
            } catch (NumberFormatException e) {
                throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure', x, y and nbTreasures should be positive integers");
            }
            if (x >= width || y >= height) {
                throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure', x and y should be less than the width and height of the map");
            }
            if (map.get(y).get(x) == -1 || map.get(y).get(x) > 0) {
                throw new LineFormatException("Treasure line does not respect the format 'T - x - y - nbTreasure', there is already a mountain or treasure at this position");
            }
            map.get(y).set(x, nbTreasure);
        });
    }

    private static ArrayList<Player> createPlayers(List<String> playersLines) {
        if (playersLines == null) {
            throw new FileFormatException("There should be two lines which start with 'A'");
        }
        ArrayList<Player> players = new ArrayList<>();
        AtomicInteger order = new AtomicInteger();
        playersLines.forEach(playerLine -> {
            String[] playerLineArray = playerLine.split("-");
            if (playerLineArray.length != 6) {
                throw new LineFormatException("Player line does not respect the format 'A - name - x - y - direction - sequence'");
            }
            if (!List.of("N", "E", "S", "O").contains(playerLineArray[4])) {
                throw new LineFormatException("Player line does not respect the format 'A - name - x - y - direction - sequence', direction should be one of 'N', 'E', 'S', 'W'");
            }
            String playerName = playerLineArray[1];
            int width;
            int height;
            Orientation orientation = Orientation.mapOrientation(playerLineArray[4]);
            String sequence = playerLineArray[5];
            try {
                width = Integer.parseInt(playerLineArray[2]);
                height = Integer.parseInt(playerLineArray[3]);
                if (width < 0 || height < 0) {
                    throw new LineFormatException("Player line does not respect the format 'A - name - x - y - direction - sequence', x and y should be positive integers");
                }
            } catch (NumberFormatException e) {
                throw new LineFormatException("Player line does not respect the format 'A - name - x - y - direction - sequence', x and y should be positive integers");
            }
            Player player = Player.builder().name(playerName).positionWidth(width).positionHeight(height).orientation(orientation).sequence(sequence).order(order.getAndIncrement()).build();
            players.add(player);
        });
        return players;
    }
}
