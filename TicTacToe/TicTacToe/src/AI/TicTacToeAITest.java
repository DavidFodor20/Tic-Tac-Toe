package AI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeAITest {
    private TicTacToeAI ai;

    @BeforeEach
    void setUp() {
        char[][] board = {
            {'X', 'O', 0},
            {'O', 'X', 0},
            {0, 0, 0}
        };
        ai = new TicTacToeAI(board, 'O', 'X');
    }

    @Test
    void testGetBestMove() {
        int[] move = ai.getBestMove();
        assertNotNull(move);
        assertTrue(move[0] >= 0 && move[1] >= 0);
    }

    @Test
    void testMinimax() {
        int score = ai.minimax(ai.board, false);
        assertTrue(score <= 10 && score >= -10);
    }
    
    
    @Test
    void testPreventOpponentWin() {
        ai.board[0][0] = 'X';
        ai.board[0][1] = 'X';
        int[] move = ai.getBestMove();
        assertArrayEquals(new int[]{0, 2}, move); // Várjuk, hogy az ai blokkol (0, 2)
    }

    @Test
    void testEmptyBoardMove() {
        char[][] emptyBoard = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        ai = new TicTacToeAI(emptyBoard, 'O', 'X');
        int[] move = ai.getBestMove();
        assertNotNull(move); 
    }
    
    @Test
    void testAIWin() {
        ai.board[1][0] = 'O';
        ai.board[1][1] = 'O';
        int[] move = ai.getBestMove();
        assertArrayEquals(new int[]{1, 2}, move); // Várjuk, hogy az ai nyer (1, 2)
    }

}
