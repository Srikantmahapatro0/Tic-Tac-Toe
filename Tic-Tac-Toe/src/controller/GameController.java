package controller;

import java.util.List;

import exceptions.BotCountMoreThanOneException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountMismatchException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.WinningStrategy;

public class GameController {

    public Game startGame(List<Player> players,
                          int boardDimensions,
                          List<WinningStrategy> winningStrategies) throws BotCountMoreThanOneException, DuplicateSymbolException, PlayerCountMismatchException {
        return Game.getBuilder()
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .setSize(boardDimensions)
                .build();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public GameState checkState(Game game) {
        return game.getGameState();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public void undo(Game game) {
        game.undo();
    }
}