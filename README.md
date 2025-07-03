![CI](https://github.com/Srikantmahapatro0/Tic-Tac-Toe/actions/workflows/ci.yml/badge.svg)

# Tic Tac Toe - OOP Based Console Game

This is a scalable, extensible **Tic Tac Toe** game implemented using Object-Oriented Programming principles in Java. The design supports:

- Variable board sizes (e.g., 3x3, 4x4, etc.)
- Human and bot players with different difficulty levels
- Undo functionality
- Pluggable winning strategies (row, column, diagonal)

---

## 🧱 Architecture

### 🗂 Packages & Classes

```

controller/
└── GameController.java

models/
├── Board.java
├── Bot.java
├── BotDifficultyLevel.java
├── Cell.java
├── CellState.java
├── Game.java
├── GameState.java
├── Move.java
├── Player.java
├── PlayerType.java
└── Symbol.java

strategies/
├── WinningStrategy.java
├── RowWinningStrategy.java
├── ColWinningStrategy.java (expected to be implemented)
├── DiagWinningStrategy.java
├── BotPlayingStrategy.java
├── EasyBotPlayingStrategy.java
├── MediumBotPlayingStrategy.java
├── HardBotPlayingStrategy.java (optional)
└── BotPlayingStrategyFactory.java

exceptions/
├── BotCountMoreThanOneException.java
├── DuplicateSymbolException.java
└── PlayerCountMismatchException.java

```

---

## ⚙️ Features

- ✅ Dynamic board size
- ✅ Supports both human and bot players
- ✅ Bot difficulty support (Easy, Medium, Hard)
- ✅ Undo functionality
- ✅ Multiple winning strategies (row, column, diagonals)
- 🚧 Extensible strategies and enhancements

---

## 🕹️ How to Play

1. Initialize `Game` using `GameController.startGame(...)`.
2. Use `GameController.makeMove(...)` to proceed with turns.
3. Check the current `GameState` using `GameController.checkState(...)`.
4. Print the board using `GameController.printBoard(...)`.
5. Use `GameController.undo(...)` to revert the last move.
6. The winner (if any) can be fetched with `GameController.getWinner(...)`.

---

## 💡 Strategy Examples

### RowWinningStrategy
Keeps track of how many times a symbol has appeared in each row using a `Map<Integer, Map<Symbol, Integer>>`.

### DiagWinningStrategy
Tracks left and right diagonals separately and verifies the count against board size.

---

## 🤖 Bot Strategies

- **EasyBotPlayingStrategy**: Selects the first empty cell found.
- **MediumBotPlayingStrategy**: Placeholder for smarter logic (e.g., blocking opponent).
- **HardBotPlayingStrategy** *(Optional)*: Can implement Minimax or other algorithms.

---

## 📄 License

This project is built for educational purposes and is open for extension and customization.

---
