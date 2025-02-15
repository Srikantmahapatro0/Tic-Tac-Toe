package models;

import exceptions.BotCountMoreThanOneException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountMismatchException;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(List<Player> players,
                int dimensions,
                List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.board = new Board(dimensions);
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    public static class Builder {
        private List<Player> players;
        private int size;
        private List<WinningStrategy> winningStrategies;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.add(player);
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder addWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        // TODO: Move the validation logic to another class
        public void validatePlayersCount() throws PlayerCountMismatchException {
            if(players.size() != size - 1) {
                throw new PlayerCountMismatchException();
            }
        }

        public void validateUniqueSymbolForPlayer() throws DuplicateSymbolException {
            Map<Character, Integer> count = new HashMap<>();
            for(Player player: players) {
                Character symbol = player.getSymbol().getaChar();

                count.put(symbol, count.getOrDefault(symbol, 0) + 1);
                if(count.get(symbol) > 1) {
                    throw new DuplicateSymbolException();
                }
            }
        }

        public void validateBotCount() throws BotCountMoreThanOneException {
            int botCount = 0;
            for(Player player: players) {
                if(player.getPlayerType() == PlayerType.BOT) {
                    botCount += 1;
                }
            }

            if(botCount > 1) {
                 throw new BotCountMoreThanOneException();
            }
        }

        // TODO: Add more exceptions to check nulls, etc

        public void validate() throws BotCountMoreThanOneException, PlayerCountMismatchException, DuplicateSymbolException {
            validateBotCount();
            validatePlayersCount();
            validateUniqueSymbolForPlayer();
        }

        public Game build() throws BotCountMoreThanOneException, DuplicateSymbolException, PlayerCountMismatchException {
            validate();
            return new Game(players, size, winningStrategies);
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public void makeMove() {
        Player currentMovePlayer = players.get(nextMovePlayerIndex);

        System.out.println("It is " + currentMovePlayer.getName() +
                " turn. Please make your move");

        Move move = currentMovePlayer.makeMove(board);

        if(!validateMove(move)) {
            System.out.println("Invalid move. Please try again.");
            return;
        }

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToUpdate = board.getBoard().get(row).get(col);
        cellToUpdate.setCellState(CellState.FILLED);
        cellToUpdate.setPlayer(currentMovePlayer);

        Move finalMove = new Move(cellToUpdate, currentMovePlayer);
        moves.add(finalMove);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        if(checkWinner(move)) {
            gameState = GameState.WIN;
            winner = currentMovePlayer;
        } else if(moves.size() == board.getSize() * board.getSize()) {
            gameState = GameState.DRAW;
        }
    }

    private boolean checkWinner(Move move) {
        for(WinningStrategy winningStrategy: winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }

        return false;
    }

    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row >= board.getSize()) {
            return false;
        }

        if(col >= board.getSize()) {
            return false;
        }

        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)) {
            return true;
        }

        return false;
    }

    public void printBoard() {
        board.printBoard();
    }

    public void undo() {
        if(moves.size() == 0) {
            System.out.println("Board is empty. Cannot undo.");
            return;
        }

        Move lastMove = moves.get(moves.size() - 1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        nextMovePlayerIndex -= 1;
        nextMovePlayerIndex = (nextMovePlayerIndex + players.size()) % players.size();
    }
}