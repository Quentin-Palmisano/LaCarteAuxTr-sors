package org.carbon.program;

import org.carbon.Interpreter.Interpreter;
import org.carbon.model.Game;

import java.io.FileNotFoundException;

public class Program {

    private static final String INPUT = "src/main/resources/inputs/input.txt";
    private static final String INPUT_WITH_TWO_PLAYER = "src/main/resources/inputs/input_with_two_player.txt";
    private static final String INVALID_INPUT = "src/main/resources/inputs/input_invalid.txt";

    public static void run() throws FileNotFoundException {
        Game game = Interpreter.mapFileToGame(INPUT_WITH_TWO_PLAYER);
        game.play();
        Interpreter.mapGameToFile(game);
    }

    public static void main(String[] args) throws FileNotFoundException {
        run();
    }

}
