import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import assignment_2.*;


public class WhiteBoxTest {
    private GameController gameController;

    @Test
    public void testMoveRowLogic() {
        gameController = new GameController(4);
        gameController.setBoardState(new int[][] {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        });

        gameController.moveRow(1, true); 

        int[][] expectedBoard = {
            {1, 2, 3, 4},
            {8, 5, 6, 7},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };

        assertArrayEquals(expectedBoard, gameController.getBoardState());
    }

    @Test
    public void testMoveColumnLogic() {
        gameController = new GameController(4);
        gameController.setBoardState(new int[][] {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        });

        gameController.moveColumn(1, true); 

        int[][] expectedBoard = {
            {1, 14, 3, 4},
            {5, 2, 7, 8},
            {9, 6, 11, 12},
            {13, 10, 15, 16}
        };

        assertArrayEquals(expectedBoard, gameController.getBoardState());
    }

    @Test
    public void testStepCountIncrement() {
        gameController = new GameController(4);
        int initialSteps = gameController.getStepCount();
        gameController.moveRow(0, true);
        assertEquals(initialSteps + 1, gameController.getStepCount());
    }

    @Test
    public void testIsGameSolvedLogic() {
        gameController = new GameController(4);
        gameController.setBoardState(new int[][] {
            {1, 1, 1, 1},
            {2, 2, 2, 2},
            {3, 3, 3, 3},
            {4, 4, 4, 4}
        });
        assertTrue(gameController.isGameSolved());
        
        gameController.setBoardState(new int[][] {
            {1, 2, 1, 1},
            {2, 3, 2, 2},
            {3, 4, 3, 3},
            {4, 1, 4, 4}
        });
        assertFalse(gameController.isGameSolved());
    }
}
