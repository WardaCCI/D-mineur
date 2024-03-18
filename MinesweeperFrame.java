import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Classe représentant la fenêtre du jeu démineur
public class MinesweeperFrame extends JFrame {
	private final Minesweeper minesweeper;
	JButton[][] buttons; // Tableau de boutons pour l'interface utilisateur

	// Constructeur de la classe MinesweeperFrame
	public MinesweeperFrame() {
		this.minesweeper = new Minesweeper();
		this.buttons = create();
		this.setTitle("Minesweeper"); // Crée une nouvelle fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configure la fenêtre pour qu'elle se ferme lorsque l'utilisateur clique sur la croix
		setLayout(new GridLayout(minesweeper.getSIZE(), minesweeper.getSIZE())); // Définit la disposition de la grille
		add();
		setSize(800, 800);
		setVisible(true); // Rend la fenêtre visible
	}

	// Crée un tableau de boutons pour l'interface utilisateur
	JButton[][] create() {
		JButton[][] buttons = new JButton[minesweeper.SIZE][minesweeper.SIZE];
		for (int i = 0; i < minesweeper.getSIZE(); i++)
			for (int j = 0; j < minesweeper.getSIZE(); j++)
				buttons[i][j] = create(i, j);
		return buttons;
	}

	// Crée un bouton pour une cellule spécifique
	JButton create(int i, int j) {
		JButton button = new JButton(); // Crée un nouveau bouton pour chaque cellule
		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				minesweeper.revealCell(i, j);
				update();
				if (minesweeper.hasWon()) {
					JOptionPane.showMessageDialog(MinesweeperFrame.this,
							"Congratulations! You revealed all safe cells. You won!");
					dispose(); // Ferme la fenêtre actuelle
					new MinesweeperFrame(); // Crée une nouvelle partie
				}
				if (minesweeper.gameOver()) {
					JOptionPane.showMessageDialog(MinesweeperFrame.this, "Boom! You hit a mine. Game over.");
					dispose(); // Ferme la fenêtre actuelle
					new MinesweeperFrame(); // Crée une nouvelle partie
				}
			}
		});
		return button;
	}

	// Met à jour l'affichage des boutons
	void update() {
		for (int i = 0; i < minesweeper.getSIZE(); i++)
			for (int j = 0; j < minesweeper.getSIZE(); j++)
				update(i, j);
	}

	// Met à jour un bouton spécifique
	void update(int i, int j) {
		if (!minesweeper.revealed[i][j])
			return;
		JButton button = buttons[i][j];
		if (minesweeper.isMine(i, j)) {
			button.setText("M"); // Si la cellule contient une mine, affiche "M"
			button.setBackground(Color.RED); // Change la couleur de fond en rouge
			button.setEnabled(false); // Désactive le bouton
			return;
		}
		button.setText(String.valueOf(minesweeper.countAdjacentMines(i, j)));
		button.setBackground(Color.GREEN); // Change la couleur de fond en vert
		button.setEnabled(false);
	}

	// Ajoute les boutons à la fenêtre
	void add() {
		for (int i = 0; i < minesweeper.getSIZE(); i++)
			for (int j = 0; j < minesweeper.getSIZE(); j++)
				add(buttons[i][j]);
	}

	// Propage la révélation des cellules déjà révélées
	public void propagateRevealtedCells() {
		for (int i = 0; i < minesweeper.getSIZE(); i++)
			for (int j = 0; j < minesweeper.getSIZE(); j++)
				if (minesweeper.revealed[i][j])
					buttons[i][j].doClick();
	}
}