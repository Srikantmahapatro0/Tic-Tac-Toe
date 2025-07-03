import exceptions.BotCountMoreThanOneException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountMismatchException;
import jdk.jshell.Diag;
import models.*;
import strategies.ColWinningStrategy;
import strategies.DiagWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws BotCountMoreThanOneException, DuplicateSymbolException, PlayerCountMismatchException {
        controller.GameController gameController = new controller.GameController();
        Scanner scanner = new Scanner(System.in);
        int dimension = 3;
        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L, "Surya", new Symbol('X'), PlayerType.HUMAN)
        );

        players.add(
                new Bot(2L, "Sharath", new Symbol('O'), BotDifficultyLevel.EASY)
        );

        List<WinningStrategy> winningStrategies = List.of(
                new RowWinningStrategy(),
                new ColWinningStrategy(),
                new DiagWinningStrategy()
        );
//test//
        Game game = gameController.startGame(
                players, dimension, winningStrategies
        );

        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)) {
            gameController.printBoard(game);

            System.out.println("Does anyone want to undo? (y/n)");
            String undoAnswer = scanner.next();

            if(undoAnswer.equalsIgnoreCase("y")) {
                gameController.undo(game);
                continue;
            }

            gameController.makeMove(game);
        }

        System.out.println("Game is finished");
        GameState gameState = gameController.checkState(game);

        if(gameState == GameState.DRAW) {
            System.out.println("Game has drawn");
        } else if(gameState == GameState.WIN) {
            System.out.println(gameController.getWinner(game).getName() + " won the game.");
        }
    }
}
