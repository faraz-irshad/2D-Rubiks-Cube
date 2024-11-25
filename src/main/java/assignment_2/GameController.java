package assignment_2;

import java.util.Arrays;

public class GameController {
    private int[][] board;
    private int boardSize;
    private int stepCount;

    public GameController(int boardSize) {
        this.boardSize = boardSize;
        resetGame(boardSize);
    }

    public void resetGame(int size) {
        stepCount = 0;
        boardSize = size;
        board = new int[boardSize][boardSize];
        initializeBoard();        
    }

    public void moveRow(int row, boolean toRight) {
        if (toRight) {
            int last = board[row][boardSize - 1];
            System.arraycopy(board[row], 0, board[row], 1, boardSize - 1);
            board[row][0] = last;
        } else {
            int first = board[row][0];
            System.arraycopy(board[row], 1, board[row], 0, boardSize - 1);
            board[row][boardSize - 1] = first;
        }
        stepCount++;
    }
    
    public void moveColumn(int col, boolean toDown) {
        if (toDown) {
            int last = board[boardSize - 1][col];
            for (int i = boardSize - 1; i > 0; i--) {
                board[i][col] = board[i - 1][col];
            }
            board[0][col] = last;
        } else {
            int first = board[0][col];
            for (int i = 0; i < boardSize - 1; i++) {
                board[i][col] = board[i + 1][col];
            }
            board[boardSize - 1][col] = first;
        }
        stepCount++;
    }


    public boolean isGameSolved() {
        for (int i = 0; i < boardSize; i++) {
            boolean rowHomogeneous = true;
            boolean colHomogeneous = true;

            for (int j = 1; j < boardSize; j++) {
                if (board[i][j] != board[i][0]) rowHomogeneous = false;
                if (board[j][i] != board[0][i]) colHomogeneous = false;
            }

            if (!rowHomogeneous && !colHomogeneous) return false;
        }
        return true;
    }

    private void initializeBoard() {
        if (boardSize == 2) {
            board[0][0] = 0; board[0][1] = 1;
            board[1][0] = 1; board[1][1] = 0;
        } else {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    board[i][j] = i;
                }
            }
            shuffleBoard();
        }
    }

    private void shuffleBoard() {
        for (int i = 0; i < boardSize; i++) {
            moveRow(i, Math.random() < 0.5);
            moveColumn(i, Math.random() < 0.5);
        }
    }

    
    public int getStepCount() {
        return stepCount;
    }


    public int[][] getBoardState() {
        return board;
    }

    public void setBoardState(int[][] input) {
        board = input;
    }
    
}
