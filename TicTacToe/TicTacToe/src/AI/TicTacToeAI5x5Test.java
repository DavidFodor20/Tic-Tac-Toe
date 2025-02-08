package AI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeAI5x5Test {
    private TicTacToeAI5x5 ai5x5;

    @BeforeEach
    void setUp() {
        char[][] board = new char[5][5];
        ai5x5 = new TicTacToeAI5x5(board, 'O', 'X');
    }

    @Test
    void testGetBestMove() {
        int[] move = ai5x5.getBestMove();
        assertNotNull(move);
        assertTrue(move[0] >= 0 && move[1] >= 0);
    }

    @Test
    void testFindWinningMove() {
        ai5x5.board[0][0] = 'O';
        ai5x5.board[0][1] = 'O';
        ai5x5.board[0][2] = 'O';
        int[] move = ai5x5.findWinningMove('O');
        assertArrayEquals(new int[]{0, 3}, move); // Expecting winning move at (0, 3)
    }

    @Test
    void testPickRandomMove() {
        ai5x5.board[0][0] = 'X';
        ai5x5.board[0][1] = 'O';
        ai5x5.board[0][2] = 'X';
        int[] move = ai5x5.pickRandomMove();
        assertNotNull(move);
        assertTrue(move[0] >= 0 && move[1] >= 0);
    }

    @Test
    void testCheckWin() {
        ai5x5.board[0][0] = 'X';
        ai5x5.board[0][1] = 'X';
        ai5x5.board[0][2] = 'X';
        ai5x5.board[0][3] = 'X';
        assertTrue(ai5x5.checkWin('X'));
    }
    
    @Test
    void testNoAvailableMove() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ai5x5.board[i][j] = 'X'; // Tábla töltése
            }
        }
        int[] move = ai5x5.getBestMove();
        assertArrayEquals(new int[]{-1, -1}, move); //Nincs lehetséges lépés
    }

    @Test
    void testBlockOpponentWin() {
        ai5x5.board[2][0] = 'X';
        ai5x5.board[2][1] = 'X';//
        ai5x5.board[2][2] = 'X';
        int[] move = ai5x5.getBestMove();
        assertArrayEquals(new int[]{2, 3}, move); // Várjuk, hogy az AI blokkol (2, 3)
    }

    @Test
    void testWinningMoveDiagonal() {
        ai5x5.board[0][0] = 'O';
        ai5x5.board[1][1] = 'O';
        ai5x5.board[2][2] = 'O';
        int[] move = ai5x5.findWinningMove('O');
        assertArrayEquals(new int[]{3, 3}, move); // Győztes lépés (3, 3)
    }
    
   
}
