package strategies;

import models.Board;
import models.Move;

public interface WinningStrategy {

    public boolean checkWinner(Board board, Move move);

    public void handleUndo(Board board, Move move);
}
