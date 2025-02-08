package AI;

/**
 * Az AIParent osztály egy absztrakt osztály, amely általános funkciókat biztosít 
 * Tic-Tac-Toe mesterséges intelligencia (AI) számára.
 */
public abstract class AIParent {
    protected char[][] board; // A játék tábla 
    protected char aiPlayer; // Az AI által használt karakter (X vagy O)
    protected char humanPlayer; // Az ember által használt karakter (X vagy O)
    protected int winCondition; // A győzelemhez szükségesek száma

    /**
     * Konstruktor a tábla és a játékosok inicializálására.
     *
     * @param board        A játék tábla.
     * @param aiPlayer     Az AI karaktere.
     * @param humanPlayer  Az ember karaktere.
     * @param winCondition A győzelem feltétele (pl. 3 egy sorban).
     */
    public AIParent(char[][] board, char aiPlayer, char humanPlayer, int winCondition) {
        this.board = board;
        this.aiPlayer = aiPlayer;
        this.humanPlayer = humanPlayer;
        this.winCondition = winCondition;
    }

    /**
     * Meghatározza az AI számára a legjobb lépést.
     *
     * @return Az optimális lépés koordinátái {sor, oszlop}.
     */
    public abstract int[] getBestMove();

    /**
     * Megkeresi a győztes lépést a megadott játékos számára, ha van ilyen.
     *
     * @param player A játékos karaktere, akinek a győztes lépést keressük.
     * @return A győztes lépés koordinátái {sor, oszlop}, vagy null, ha nincs ilyen lépés.
     */
    protected abstract int[] findWinningMove(char player);

    /**
     * Ellenőrzi, hogy a megadott játékos nyert-e.
     *
     * @param player A játékos karaktere.
     * @return Igaz, ha a játékos nyert; hamis, ha nem.
     */
    protected abstract boolean checkWin(char player);

    /**
     * Véletlenszerű lépést választ az elérhető helyek közül.
     *
     * @return A véletlenszerű lépés koordinátái {sor, oszlop}.
     */
    protected abstract int[] pickRandomMove();

    /**
     * Ellenőrzi, hogy egy sorozat (sor, oszlop vagy átló) teljesül-e egy adott játékos számára.
     *
     * @param player    A játékos karaktere.
     * @param startRow  A kezdősor indexe.
     * @param startCol  A kezdőoszlop indexe.
     * @param deltaRow  A sor irányának változása.
     * @param deltaCol  Az oszlop irányának változása.
     * @return Igaz, ha a sorozat teljesül; hamis, ha nem.
     */
    protected boolean checkLine(char player, int startRow, int startCol, int deltaRow, int deltaCol) {
        for (int i = 0; i < winCondition; i++) {
            // Ellenőrizzük, hogy minden pozíció a játékos karakterét tartalmazza-e.
            if (board[startRow + i * deltaRow][startCol + i * deltaCol] != player) {
                return false;
            }
        }
        return true;
    }
}
