package AI;

import java.util.ArrayList;
import java.util.List;

/**
 * A TicTacToeAI osztály 3x3-as Tic-Tac-Toe játék AI-ját valósítja meg generikus listák használatával.
 */
public class TicTacToeAI extends AIParent {

    /**
     * Konstruktor a 3x3-as játék AI inicializálására.
     *
     * @param board       A játék tábla.
     * @param aiPlayer    Az AI karaktere.
     * @param humanPlayer Az emberi játékos karaktere.
     */
    public TicTacToeAI(char[][] board, char aiPlayer, char humanPlayer) {
        super(board, aiPlayer, humanPlayer, 3); // 3 a győzelem feltétele 3x3 táblán
    }

    @Override
    public int[] getBestMove() {
        // Van e nyerő lépés az AI-nak
        int[] winningMove = findWinningMove(aiPlayer);
        if (winningMove != null) {
            return winningMove; // Ha van, térjen vissza ezzel
        }

        // Ellenőrzi blokkolható-e az ember
        int[] blockingMove = findWinningMove(humanPlayer);
        if (blockingMove != null) {
            return blockingMove; // Ha van, térjen vissza ezzel
        }

        // Minimax folytatása
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};// mindent 0-ra initel vagy érvénytelenre 

        for (int[] move : getAvailableMoves()) { // Generikus lista használata az üres mezőkön
            int row = move[0], col = move[1];
            board[row][col] = aiPlayer;
            int score = minimax(board, false);
            board[row][col] = 0;

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove; // Legjobb lépés visszaadása
    }

    @Override
    protected int[] findWinningMove(char player) {
        for (int[] move : getAvailableMoves()) { // Generikus lista használata az üres mezőkön
            int row = move[0], col = move[1];
            board[row][col] = player;
            if (checkWin(player)) {
                board[row][col] = 0; // Állítsuk vissza az állapotot
                return new int[]{row, col}; // Visszatérünk a győztes lépéssel
            }
            board[row][col] = 0; // Állapot visszaállítása, ha nem nyerő lépés
        }
        return null; // Nincs győztes lépés
    }

    @Override
    protected boolean checkWin(char player) {
        // Sorok és oszlopok ellenőrzése
        for (int i = 0; i < 3; i++) {
            if (checkLine(player, i, 0, 0, 1) || checkLine(player, 0, i, 1, 0)) {
                return true;
            }
        }
        // Átlók ellenőrzése
        return checkLine(player, 0, 0, 1, 1) || checkLine(player, 0, 2, 1, -1);
    }

    /**
     * A Minimax algoritmus a legjobb lépés kiválasztására.
     *
     * @param board       A jelenlegi tábla állapota.
     * @param isMaximizing Az AI játssza-e a lépést (maximalizálás), vagy az ember (minimalizálás).
     * @return A tábla állapotának pontszáma.
     */
    int minimax(char[][] board, boolean isMaximizing) {
        if (checkWin(aiPlayer)) return 10; // AI nyerése
        if (checkWin(humanPlayer)) return -10; // Ember nyerése
        if (checkDraw()) return 0; // Döntetlen

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int[] move : getAvailableMoves()) { // Generikus lista használata az üres mezőkön
            int row = move[0], col = move[1];
            board[row][col] = isMaximizing ? aiPlayer : humanPlayer;
            int score = minimax(board, !isMaximizing); // Rekurzió
            board[row][col] = 0; // Visszaállítjuk az állapotot

            bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
        }
        return bestScore;
    }

    /**
     * Üres mezők lekérdezése.
     *
     * @return Egy generikus lista, amely tartalmazza az összes elérhető mező sor- és oszlopindexeit.
     */
    private List<int[]> getAvailableMoves() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 0) {
                    availableMoves.add(new int[]{row, col});
                }
            }
        }
        return availableMoves;
    }

    @Override
    protected int[] pickRandomMove() {
        List<int[]> moves = getAvailableMoves(); // Generikus lista használata
        if (moves.isEmpty()) {
            return new int[]{-1, -1};
        }
        return moves.get((int) (Math.random() * moves.size())); // Véletlenszerű lépés
    }

    protected boolean checkDraw() {
        return getAvailableMoves().isEmpty(); // Döntetlen, ha nincs több üres mező
    }
}
