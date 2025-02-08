package Window;

import javax.swing.*;
import Logic.GamePanel;
import java.awt.*;

/**
 * A MainWindow osztály az alkalmazás főablakát valósítja meg.
 * Ez tartalmazza a játéktáblát (GamePanel) és a vezérlő elemeket,
 * például a játék mentésére, betöltésére, új játék indítására szolgáló funkciókat.
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private GamePanel gamePanel; // A játékot kezelő panel
    private String gameMode; // A kiválasztott játékmód (Player vs Player vagy Player vs AI)
    private String boardSize; // A tábla mérete (3x3 vagy 5x5)
    private JLabel winCounterLabel; // A győzelmek számlálóját megjelenítő címke

    /**
     * Konstruktor a MainWindow inicializálásához.
     *
     * @param gameMode  A kiválasztott játékmód (Player vs Player vagy Player vs AI).
     * @param boardSize A kiválasztott tábla mérete (3x3 vagy 5x5).
     */
    public MainWindow(String gameMode, String boardSize) {
        this.gameMode = gameMode;
        this.boardSize = boardSize;

        // Ablak alapbeállításai
        setTitle("Tic-Tac-Toe Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menü inicializálása
        initializeMenu();

        // Főpanel létrehozása, amely középre helyezi a GamePanelt
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // GamePanel inicializálása a kiválasztott mód és méret alapján
        gamePanel = new GamePanel(gameMode, boardSize);
        mainPanel.add(gamePanel);

        // Vezérlőpanel létrehozása a jobb oldalon
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Gombok létrehozása
        JButton saveButton = new JButton("Save Game");
        JButton restartButton = new JButton("Restart Game");
        JButton endButton = new JButton("End Game");

        // Save gomb funkciója: Játék mentése
        saveButton.addActionListener(e -> gamePanel.saveGame());

        // Restart gomb funkciója: Új játék indítása
        restartButton.addActionListener(e -> gamePanel.startNewGame());

        // End gomb funkciója: Alkalmazás bezárása
        endButton.addActionListener(e -> System.exit(0));

        // Gombok hozzáadása és távolság beállítása
        controlPanel.add(saveButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Távolság
        controlPanel.add(restartButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Távolság
        controlPanel.add(endButton);

        // Győzelmek számláló panel létrehozása
        JPanel winCounterPanel = new JPanel();
        winCounterPanel.setLayout(new BorderLayout());
        winCounterLabel = new JLabel("X Wins: 0 | O Wins: 0");
        winCounterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        winCounterPanel.add(winCounterLabel, BorderLayout.CENTER);

        // Panelek hozzáadása a főablakhoz
        add(controlPanel, BorderLayout.EAST);
        add(winCounterPanel, BorderLayout.SOUTH);
    }

    /**
     * A menü inicializálása a főablakhoz.
     * Tartalmaz új játék indítását, játék mentését, betöltését, valamint kilépést.
     */
    private void initializeMenu() {//föablak JMenu-je, nem a nagy főmenű
        JMenuBar menuBar = new JMenuBar(); // Menü sáv létrehozása
        JMenu gameMenu = new JMenu("Game"); // "Game" menü létrehozása

        // "New Game" menüpont: Új játék indítása
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> gamePanel.startNewGame());

        // "Save Game" menüpont: Játék mentése
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        saveGameItem.addActionListener(e -> gamePanel.saveGame());

        // "Load Game" menüpont: Játék betöltése
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        loadGameItem.addActionListener(e -> loadGame());

        // "Exit" menüpont: Alkalmazás bezárása
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        // Menüpontok hozzáadása a menühöz
        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.add(exitItem);

        // Menü hozzáadása a menüsávhoz
        menuBar.add(gameMenu);

        // Menüsáv beállítása az ablakhoz
        setJMenuBar(menuBar);
    }

    /**
     * A győzelmek számlálójának frissítése a játéktól kapott adatok alapján.
     *
     * @param xWins X győzelmeinek száma.
     * @param oWins O győzelmeinek száma.
     */
    public void updateWinCounter(int xWins, int oWins) {
        winCounterLabel.setText("X Wins: " + xWins + " | O Wins: " + oWins);
    }

    /**
     * Játék betöltése fájlból.
     * A GamePanel objektumot frissíti a mentett állapot alapján.
     */
    public void loadGame() {
        gamePanel.loadGame(); // A GamePanel betöltési funkcióját hívja meg
    }

    /**
     * A program belépési pontja. A menüablak megjelenítésével indítja az alkalmazást.
     *
     * @param args Parancssori argumentumok (nem használt).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // A menüablak indítása
            MenuFrame menuFrame = new MenuFrame();
            menuFrame.setVisible(true);
        });
    }
}
