package Logic;
import javax.swing.*;

import AI.AIParent;
import AI.TicTacToeAI;
import AI.TicTacToeAI5x5;
import Window.MainWindow;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * A GamePanel osztály a játék fő logikáját és a grafikus felületét valósítja meg.
 * Kezeli a játék állapotát, a felhasználói interakciókat és az AI-t.
 */
public class GamePanel extends JPanel implements Serializable {
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons; // A tábla gombjai
    char[][] board; // A játék tábla állapota
    char currentPlayer; // Az aktuális játékos (X vagy O)
    boolean gameEnded; // Jelzi, hogy vége van-e a játéknak
    private String gameMode; // Játékmód (PvP vagy PvAI)
    private int xWins; // X győzelmeinek száma
    private int oWins; // O győzelmeinek száma
    int boardSize; // Tábla mérete (3 vagy 5)
    private int winCondition; // Győzelemhez szükséges szimbólumok száma

    
    /**
     * Konstruktor a GamePanel inicializálásához.
     *
     * @param gameMode  A kiválasztott játékmód (Player vs Player vagy Player vs AI).
     * @param boardSize A kiválasztott tábla mérete (3x3 vagy 5x5).
     */
    public GamePanel(String gameMode, String boardSize) {
        this.gameMode = gameMode;
        this.boardSize = boardSize.equals("5x5") ? 5 : 3; // Set board size based on selection
        this.winCondition = boardSize.equals("5x5") ? 4 : 3; // Set win condition based on board size
        setPreferredSize(new Dimension(300, 300));
        setLayout(new GridLayout(this.boardSize, this.boardSize));
        // Tábla inicializálása
        board = new char[this.boardSize][this.boardSize];
        buttons = new JButton[this.boardSize][this.boardSize];
        currentPlayer = 'X';
        gameEnded = false;
        xWins = 0;
        oWins = 0;

     // Gombok létrehozása és hozzáadása
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

                int finalRow = row;
                int finalCol = col;
                buttons[row][col].addActionListener(e -> handleButtonClick(finalRow, finalCol));

                add(buttons[row][col]);
            }
        }

        setBackground(Color.CYAN);
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
    }

    
    /**
     * Egy gomb megnyomását kezeli.
     *
     * @param row A gomb sorindexe.
     * @param col A gomb oszlopindexe.
     */
    void handleButtonClick(int row, int col) {
    	// Ha a játék véget ért vagy a mező foglalt, nem csinálunk semmit
        if (gameEnded || board[row][col] != 0) return;

     // Az aktuális játékos karakterének hozzáadása a táblához és a gombhoz
        board[row][col] = currentPlayer;
        buttons[row][col].setText(String.valueOf(currentPlayer));
        buttons[row][col].setForeground(currentPlayer == 'X' ? Color.BLACK : Color.RED); // Szín beállítás

        
     // Ellenőrizzük, hogy az aktuális játékos nyert-e
        if (checkWin()) {
            gameEnded = true;
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            if (currentPlayer == 'X') {
                xWins++;
            } else {
                oWins++;
            }
            // WinCounter frissítés Mainwindown keresztül
            ((MainWindow) SwingUtilities.getWindowAncestor(this)).updateWinCounter(xWins, oWins);
        } else if (checkDraw()) {
        	//ha döntetlen lenne, vagyis nem nyert senki, de vége vagyis betelt a tábla
            gameEnded = true;
            JOptionPane.showMessageDialog(this, "It's a draw!");
        } else {
        	// Váltunk a következő játékosra
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

            // Ha AI ellen játszunk, az AI lép
            if (gameMode.equals("Player vs AI") && currentPlayer == 'O') {//mindig az AI van az 'o'-val
                performAIMove();
            }
        }
    }

    /**
     * Ellenőrzi, hogy a jelenlegi játékos nyert-e.
     *
     * @return Igaz, ha nyert a játékos, különben hamis.
     */
    boolean checkWin() {
    	// Sorok és oszlopok ellenőrzése
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize - winCondition + 1; j++) {
                if (checkLine(i, j, 0, 1) || checkLine(j, i, 1, 0)) {
                    return true; //ha talált win-t
                }
            }
        }
     // Átlók ellenőrzése
        for (int i = 0; i < boardSize - winCondition + 1; i++) {
            for (int j = 0; j < boardSize - winCondition + 1; j++) {
                if (checkLine(i, j, 1, 1) || checkLine(i, boardSize - j - 1, 1, -1)) {
                    return true; 
                }
            }
        }
        return false;
    }


    /**
     * Egy sor, oszlop vagy átló  ellenőrzése.
     *
     * @param startRow  Kezdősor.
     * @param startCol  Kezdőoszlop.
     * @param deltaRow  Sor irányának változása.
     * @param deltaCol  Oszlop irányának változása.
     * @return Igaz, ha győzelem van az adott vonalon.
     */
    private boolean checkLine(int startRow, int startCol, int deltaRow, int deltaCol) {
        int count = 0;
        for (int k = 0; k < winCondition; k++) {
            if (board[startRow + k * deltaRow][startCol + k * deltaCol] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }
        return count == winCondition;
    }

    /**
     * Ellenőrzi, hogy döntetlen van-e (a tábla teljesen tele van-e).
     *
     * @return Igaz, ha döntetlen van, különben hamis.
     */
    boolean checkDraw() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    return false; // Van üres cella
                }
            }
        }
        return true; //Nincs üres cella
    }

    /**
     * Új játék indítása.
     */
    public void startNewGame() {
        board = new char[boardSize][boardSize]; // Tábla törlése
        currentPlayer = 'X'; //Váltás vissza X-re
        gameEnded = false; //Státusz újraállítása

        // Minden gomb üresbe
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                buttons[row][col].setText(""); //Gpmb tisztítása
                buttons[row][col].setForeground(Color.BLACK); //Szín reset
            }
        }
    }

    /**
     * Játék mentése fájlba.
     */
    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tictactoe_save.dat"))) {
            oos.writeObject(this);
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game: " + e.getMessage());
        }
    }

    /**
     * Játék betöltése fájlból.
     */
    public void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tictactoe_save.dat"))) {
            GamePanel loadedPanel = (GamePanel) ois.readObject();
            this.board = loadedPanel.board;
            this.currentPlayer = loadedPanel.currentPlayer;
            this.gameEnded = loadedPanel.gameEnded;
            this.xWins = loadedPanel.xWins;
            this.oWins = loadedPanel.oWins;
            this.gameMode = loadedPanel.gameMode;
            this.boardSize = loadedPanel.boardSize;
            this.winCondition = loadedPanel.winCondition;
    
            // Tábla méretezése
            this.buttons = new JButton[boardSize][boardSize];
            removeAll(); // Gombok tisztítása
            setLayout(new GridLayout(boardSize, boardSize)); // Méret beállítás
    
            // UI elemek frissítése
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    buttons[row][col] = new JButton(board[row][col] == 0 ? "" : String.valueOf(board[row][col]));
                    buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                    buttons[row][col].setBackground(Color.LIGHT_GRAY);
                    buttons[row][col].setFocusPainted(false);
                    buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                    buttons[row][col].setForeground(board[row][col] == 'X' ? Color.BLACK : Color.RED);
    
                    int finalRow = row;
                    int finalCol = col;
                    buttons[row][col].addActionListener(e -> handleButtonClick(finalRow, finalCol));
    
                    add(buttons[row][col]); // Gomb panelhez adása
                }
            }
    
            repaint();
            revalidate(); // Frissítés
            ((MainWindow) SwingUtilities.getWindowAncestor(this)).updateWinCounter(xWins, oWins);
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading game: " + e.getMessage());
        }
    }

    
    /**
     * Az AI által végrehajtott lépést kezeli.
     */
    private void performAIMove() {
        AIParent ai;
        if (boardSize == 5) {
            ai = new TicTacToeAI5x5(board, 'O', 'X'); 
        } else {
            ai = new TicTacToeAI(board, 'O', 'X'); 
        }
        int[] bestMove = ai.getBestMove(); 
        if (bestMove[0] != -1 && bestMove[1] != -1) {
            handleButtonClick(bestMove[0], bestMove[1]);
        }
    }
    

}
