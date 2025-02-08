package AI;

import java.util.ArrayList;
import java.util.List;

/**
 * A TicTacToeAI5x5 osztály 5x5-ös Tic-Tac-Toe játék AI-ját valósítja meg.
 * A győzelem feltétele 4 egy sorban.
 */
public class TicTacToeAI5x5 extends AIParent {

    /**
     * Konstruktor az 5x5-ös játék AI inicializálására.
     *
     * @param board       A játék tábla.
     * @param aiPlayer    Az AI karaktere.
     * @param humanPlayer Az emberi játékos karaktere.
     */
    public TicTacToeAI5x5(char[][] board, char aiPlayer, char humanPlayer) {
        super(board, aiPlayer, humanPlayer, 4); // Győzelem feltétele 4 a sorban
    }

    
    /**
     * Meghatározza az AI által választandó legjobb lépést.
     * Ha van győztes lépés az AI számára, azt választja. Ha nincs, akkor blokkolja az ember nyerő lépését.
     * Ha egyik sem, akkor a középső mezőt, vagy egy véletlenszerű lépést választ.
     *
     * @return Az AI által választott lépés (sor és oszlop indexek).
     */
    @Override
    public int[] getBestMove() {
        // Megkeressük az AI győztes lépését
        int[] winningMove = findWinningMove(aiPlayer);
        if (winningMove != null) {
            return winningMove;
        }

        // Megkeressük az emberi játékost blokkoló lépését, vagyis az ember nyerő lépését
        int[] blockingMove = findWinningMove(humanPlayer);
        if (blockingMove != null) {
            return blockingMove;
        }

        // Ha a középső mező üres, akkor azt választjuk
        if (board[2][2] == 0) {
            return new int[]{2, 2};
        }

        // Véletlenszerű lépés választása, ha nincs jobb
        return pickRandomMove();
    }

    
    /**
     * Megkeresi az adott játékos számára elérhető győztes lépést.
     *
     * @param player A játékos karaktere, akinek a győztes lépését keressük.
     * @return A győztes lépés koordinátái (sor és oszlop), vagy null, ha nincs ilyen lépés.
     */
    @Override
    protected int[] findWinningMove(char player) {
        // Iterálunk minden mezőn a győztes lépés kereséséhez
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board[row][col] == 0) {
                    board[row][col] = player;
                    if (checkWin(player)) {
                        board[row][col] = 0; // Visszaállítjuk az állapotot
                        return new int[]{row, col}; // Győztes lépés
                    }
                    board[row][col] = 0;
                }
            }
        }
        return null; // Nincs győztes lépés
    }

    
    
    /**
     * Ellenőrzi, hogy az adott játékos elérte-e a győzelmi feltételt.
     *
     * @param player A játékos karaktere, akinek győzelmét ellenőrizzük.
     * @return Igaz, ha a játékos győzött, különben hamis.
     */
    
    @Override
    protected boolean checkWin(char player) {
        int size = board.length;

        // Sorok és oszlopok ellenőrzése
        for (int i = 0; i < size; i++) {
            if (checkLine(player, i, 0, 0, 1) || checkLine(player, 0, i, 1, 0)) {
                return true;
            }
        }

        // Átlók ellenőrzése (balról-jobbra és jobbról-balra)
        for (int start = 0; start <= size - winCondition; start++) {
            if (checkLine(player, start, 0, 1, 1) || checkLine(player, 0, start, 1, 1)) { // Balról-jobbra átló lefele
                return true;
            }
            if (checkLine(player, start, size - 1, 1, -1) || checkLine(player, 0, size - 1 - start, 1, -1)) { // Jobbról-balra átló lefele
                return true;
            }
        }

        return false; // Ha nincs győzelem
    }
    
    
    
    /**
     * Ellenőrzi, hogy egy adott sorozatban azonos karakterek vannak-e a győzelemhez szükséges számban.
     *
     * @param player    A játékos karaktere, akit ellenőrzünk.
     * @param startRow  A kezdő sor indexe.
     * @param startCol  A kezdő oszlop indexe.
     * @param rowDelta  Az irány sor irányú változása.
     * @param colDelta  Az irány oszlop irányú változása.
     * @return Igaz, ha a játékos teljesítette a győzelmi feltételt az adott irányban, különben hamis.
     */
    @Override
	protected boolean checkLine(char player, int startRow, int startCol, int rowDelta, int colDelta) {
        int count = 0;

        // Végigmegyünk az adott irányon
        for (int i = 0; i < board.length; i++) {
            int row = startRow + i * rowDelta;
            int col = startCol + i * colDelta;

            // Ha kívül esik a táblán, megszakítjuk a ciklust
            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
                break;
            }

            // Ha a játékos karakterét találjuk, növeljük a számlálót
            if (board[row][col] == player) {
                count++;
                if (count == winCondition) { // Győzelem, ha elérjük a `winCondition`-t
                    return true;
                }
            } else {
                count = 0; // Ha nem egyezik, nullázzuk a számlálót
            }
        }
        return false;
    }

    
    
    /**
     * Kiválaszt egy véletlenszerű lépést az elérhető üres mezők közül.
     *
     * @return Egy véletlenszerű lépés koordinátái (sor és oszlop), vagy (-1, -1), ha nincs elérhető lépés.
     */
    @Override
    protected int[] pickRandomMove() {
        List<int[]> availableMoves = new ArrayList<>();

        // Üres mezők keresése
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board[row][col] == 0) {
                    availableMoves.add(new int[]{row, col});
                }
            }
        }

        // Ha nincs elérhető lépés, akkor hibás érték (-1, -1) visszaadása
        if (availableMoves.isEmpty()) {
            return new int[]{-1, -1};
        }

        // Véletlenszerűen választunk egy üres mezőt
        return availableMoves.get((int) (Math.random() * availableMoves.size()));
    }
}
