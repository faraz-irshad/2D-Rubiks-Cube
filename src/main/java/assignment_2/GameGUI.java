package assignment_2;

import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {
    private GameController gameController;
    private JButton[][] buttons;
    private int boardSize;
    private Color[] colors;

    public GameGUI() {
        selectBoardSize();
        this.gameController = new GameController(boardSize);
        initializeColors();
        initializeUI();
    }

    private void selectBoardSize() {
        String[] options = {"2x2", "4x4", "6x6"};
        String selected = (String) JOptionPane.showInputDialog(
            null,
            "Select the board size:",
            "Board Size",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            null
        );

        if (selected == null) {
            System.exit(0);
        }

        this.boardSize = Integer.parseInt(selected.split("x")[0]);
    }

    private void initializeColors() {
        colors = new Color[boardSize];
        for (int i = 0; i < boardSize; i++) {
            float hue = (float) i / boardSize; 
            colors[i] = Color.getHSBColor(hue, 0.8f, 0.9f);
        }
    }

    private void initializeUI() {
        setTitle("2D Rubik's Cube Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize, 5, 5)); 
        boardPanel.setBackground(Color.BLACK); 
        buttons = new JButton[boardSize][boardSize];
        updateBoardUI(boardPanel);

        JPanel controlPanel = new JPanel();
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(e -> {
            gameController.resetGame(boardSize);
            refreshUI();
        });

        controlPanel.add(resetButton);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(500, 500);
        setVisible(true);
    }

    private void updateBoardUI(JPanel boardPanel) {
        buttons = new JButton[boardSize][boardSize];
        boardPanel.removeAll();
        int[][] board = gameController.getBoardState();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(colors[board[i][j]]);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

                int row = i, col = j;

                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        String[] options = {"Row", "Column"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Do you want to move the row or column?",
            "Move Option",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) { 
            String[] directions = {"Left", "Right"};
            int direction = JOptionPane.showOptionDialog(
                this,
                "In which direction do you want to move the row?",
                "Direction",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                directions,
                directions[0]
            );

            if (direction == 0) {
                gameController.moveRow(row, false); 
            } else if (direction == 1) {
                gameController.moveRow(row, true);
            }
        } else if (choice == 1) { 
            String[] directions = {"Up", "Down"};
            int direction = JOptionPane.showOptionDialog(
                this,
                "In which direction do you want to move the column?",
                "Direction",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                directions,
                directions[0]
            );

            if (direction == 0) {
                gameController.moveColumn(col, false);
            } else if (direction == 1) {
                gameController.moveColumn(col, true);
            }
        }

        if (gameController.isGameSolved()) {
            showWinningScreen();
        } else {
            refreshUI();
        }
    }

    private void refreshUI() {
        buttons = new JButton[boardSize][boardSize]; 
        getContentPane().removeAll();
        initializeUI();
        revalidate();
        repaint();
    }

    private void showWinningScreen() {
        refreshUI(); 
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            JOptionPane.showMessageDialog(
                this,
                "Congratulations! You solved the puzzle in " + gameController.getStepCount() + " steps.",
                "You Win!",
                JOptionPane.INFORMATION_MESSAGE
            );

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Would you like to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                selectBoardSize();
                initializeColors();
                gameController.resetGame(boardSize);
                refreshUI();
            } else {
                System.exit(0);
            }
        });
    }
}
