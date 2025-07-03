package models;

import java.util.Scanner;

public class Player {
     private Symbol symbol;
    private String name;
    private Long id;
    private PlayerType playerType;
    private Scanner scanner;

    // We Can also generate auto increament id
    public Player(Long id, String name, Symbol symbol, PlayerType type) {
        this.id = id;
        this.playerType = type;
        this.name = name;
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Move makeMove(Board board) {
        System.out.println("Please give the row where you want to " +
                "make the move (0 based index)");

        int row = scanner.nextInt();

        System.out.println("Please give the col where you want to " +
                "make the move (0 based index)");

        int col = scanner.nextInt();

        return new Move(new Cell(row, col), this);
    }
}
