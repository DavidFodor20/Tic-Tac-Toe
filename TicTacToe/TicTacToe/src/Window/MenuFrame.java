package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A MenuFrame osztály a játék menüablakát valósítja meg.
 * Ez az ablak biztosítja a játékmód és a tábla méretének kiválasztását,
 * valamint a játék indítását vagy egy mentett állapot betöltését.
 */
public class MenuFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> modeComboBox; // A játékmód választó legördülő menü
    private JComboBox<String> sizeComboBox; // A tábla méretének választó legördülő menü
    private JButton startButton; // Gomb a játék indítására
    private JButton loadButton; // Gomb egy mentett játék betöltésére

    /**
     * Konstruktor a MenuFrame osztályhoz.
     * Beállítja az ablak alapvető tulajdonságait és inicializálja a GUI komponenseket.
     */
    public MenuFrame() {
        // Ablak beállításai
        setTitle("Tic-Tac-Toe Menu"); // Az ablak címe
        setSize(400, 250); // Az ablak mérete
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Bezárási művelet
        setLocationRelativeTo(null); // Az ablakot középre helyezi
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Vertikális elrendezés

        // Játékmódok és tábla méretek inicializálása
        String[] gameModes = {"Player vs Player", "Player vs AI"};
        String[] boardSizes = {"3x3", "5x5"};

        // GUI elemek létrehozása
        modeComboBox = new JComboBox<>(gameModes); // Játékmód választó
        sizeComboBox = new JComboBox<>(boardSizes); // Tábla méretének választó
        startButton = new JButton("Start Game!"); // Játék indítása
        loadButton = new JButton("Load Saved Game"); // Mentett játék betöltése

        // GUI elemek testreszabása
        modeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        sizeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Gombok eseménykezelőinek beállítása
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // A kiválasztott játékmód és tábla méretének lekérése
                String selectedMode = (String) modeComboBox.getSelectedItem();
                String selectedSize = (String) sizeComboBox.getSelectedItem();

                dispose(); // Menüablak bezárása
                MainWindow mainWindow = new MainWindow(selectedMode, selectedSize); // Új ablak létrehozása a kiválasztott beállításokkal
                mainWindow.setVisible(true); // Főablak megjelenítése
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Menüablak bezárása
                MainWindow mainWindow = new MainWindow("Player vs Player", "3x3"); // Alapértelmezett játék indítása
                mainWindow.setVisible(true);
                mainWindow.loadGame(); // Mentett állapot betöltése
            }
        });

        // Komponensek középre igazítása
        modeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Komponensek hozzáadása az ablakhoz, megfelelő távolságokkal
        add(Box.createVerticalGlue());
        add(new JLabel("Select Game Mode:") {
            private static final long serialVersionUID = 1L;

            {
                setFont(new Font("Arial", Font.BOLD, 18)); // Címke betűtípusának beállítása
                setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítás
            }
        });
        add(Box.createVerticalStrut(10)); // Függőleges távolság
        add(modeComboBox);
        add(Box.createVerticalStrut(10));
        add(new JLabel("Select Board Size:") {
            private static final long serialVersionUID = 1L;

            {
                setFont(new Font("Arial", Font.BOLD, 18)); // Címke betűtípusának beállítása
                setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítás
            }
        });
        add(Box.createVerticalStrut(10));
        add(sizeComboBox);
        add(Box.createVerticalStrut(20));
        add(startButton);
        add(Box.createVerticalStrut(10));
        add(loadButton);
        add(Box.createVerticalGlue());
    }
}
