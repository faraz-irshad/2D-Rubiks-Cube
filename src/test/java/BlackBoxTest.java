import assignment_2.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlackBoxTest {
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        gameController = new GameController(4); 
    }

    @Test
    public void testBoardInitialization() {
        int[][] board = gameController.getBoardState();
        assertEquals(4, board.length);
        for (int[] row : board) {
            assertEquals(4, row.length);
        }
    }

    @Test
    public void testGameCompletionCheck() {
        gameController.setBoardState(new int[][] {
            {1, 1, 1, 1},
            {2, 2, 2, 2},
            {3, 3, 3, 3},
            {4, 4, 4, 4}
        });
        assertTrue(gameController.isGameSolved());
    }

    @Test
    public void testResetGame() {
        int[][] initialBoardState = gameController.getBoardState();
        gameController.resetGame(4);
        int[][] boardAfterReset = gameController.getBoardState();
        assertNotNull(boardAfterReset);
        assertNotEquals(initialBoardState, boardAfterReset);
    }
}
