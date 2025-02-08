package Logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamePanelTest {
    private GamePanel gamePanel3x3;
    private GamePanel gamePanel5x5;

    @BeforeEach
    void setUp() {
        gamePanel3x3 = new GamePanel("Player vs Player", "3x3");
        gamePanel5x5 = new GamePanel("Player vs Player", "5x5");
    }

    @Test
    void testHandleButtonClick() {
        gamePanel3x3.startNewGame();
        gamePanel3x3.handleButtonClick(0, 0);
        assertEquals('X', gamePanel3x3.board[0][0]); 
    }

    @Test
    void testCheckWin() {
        gamePanel3x3.startNewGame();
        gamePanel3x3.board[0][0] = 'X';
        gamePanel3x3.board[0][1] = 'X';
        gamePanel3x3.board[0][2] = 'X';
        assertTrue(gamePanel3x3.checkWin());
    }

    @Test
    void testCheckDraw() {
        gamePanel3x3.startNewGame();
        gamePanel3x3.board = new char[][]{
            {'X', 'O', 'X'},
            {'X', 'X', 'O'},
            {'O', 'X', 'O'}
        };
        assertTrue(gamePanel3x3.checkDraw());
    }

    @Test
    void testStartNewGame() {
        gamePanel5x5.startNewGame();
        assertFalse(gamePanel5x5.gameEnded);
        assertEquals('X', gamePanel5x5.currentPlayer);
        for (int row = 0; row < gamePanel5x5.boardSize; row++) {
            for (int col = 0; col < gamePanel5x5.boardSize; col++) {
                assertEquals(0, gamePanel5x5.board[row][col]);
            }
        }
    }
    
    

    @Test
    void testWinningRowCondition() {
        gamePanel5x5.startNewGame();
        gamePanel5x5.board[0][0] = 'X';
        gamePanel5x5.board[0][1] = 'X';
        gamePanel5x5.board[0][2] = 'X';
        gamePanel5x5.board[0][3] = 'X'; 
        assertTrue(gamePanel5x5.checkWin());
    }

    @Test
    void testWinningDiagCondition() {
        gamePanel5x5.startNewGame();
        gamePanel5x5.board[0][0] = 'X';
        gamePanel5x5.board[1][1] = 'X';
        gamePanel5x5.board[2][2] = 'X';
        gamePanel5x5.board[3][3] = 'X'; 
        assertTrue(gamePanel5x5.checkWin());
    }
    
    @Test
    void testInvalidMoveHandling() {
        gamePanel3x3.startNewGame();
        gamePanel3x3.handleButtonClick(0, 0); 
        gamePanel3x3.handleButtonClick(0, 0); 
        assertEquals('X', gamePanel3x3.board[0][0]); 
        assertEquals('O', gamePanel3x3.currentPlayer); 
    }
    
    @Test
    void testResetGameState() {
        gamePanel3x3.board[0][0] = 'X';
        gamePanel3x3.board[0][1] = 'O';
        gamePanel3x3.gameEnded = true;
        gamePanel3x3.startNewGame();

       
        assertFalse(gamePanel3x3.gameEnded);
        for (int row = 0; row < gamePanel3x3.boardSize; row++) {
            for (int col = 0; col < gamePanel3x3.boardSize; col++) {
                assertEquals(0, gamePanel3x3.board[row][col]);
            }
        }
        assertEquals('X', gamePanel3x3.currentPlayer);
    }
}
