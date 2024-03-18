import javax.swing.JButton;

// Classe représentant le jeu du démineur
public class Minesweeper {
	final int SIZE = 12; // Taille du plateau de jeu
	boolean[][] mines = new boolean[SIZE][SIZE]; // Tableau pour stocker l'emplacement des mines
	boolean[][] revealed = new boolean[SIZE][SIZE]; // Tableau pour suivre les cellules révélées

	// Constructeur de la classe Minesweeper
	public Minesweeper() {
		placeMines(); // Placer les mines de manière aléatoire
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				revealed[i][j] = false; // Initialiser toutes les cellules comme non révélées
			}
		}
	}

	// Méthode privée pour placer les mines aléatoirement
	private void placeMines() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				mines[i][j] = (Math.random() < 0.2); // 20% de chance qu'une cellule contienne une mine
			}
		}
	}

	// Vérifie si une cellule donnée contient une mine
	public boolean isMine(int x, int y) {
		return mines[x][y];
	}

	// Compte le nombre de mines dans les cellules adjacentes
	public int countAdjacentMines(int x, int y) {
		int count = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				int nx = x + dx;
				int ny = y + dy;
				if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && mines[nx][ny]) {
					count++;
				}
			}
		}
		return count;
	}

	// Révèle une cellule et ses voisins si elle ne contient pas de mine
	public void revealCell(int x, int y) {
		if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && !revealed[x][y]) {
			revealed[x][y] = true;
			if (countAdjacentMines(x, y) == 0) {
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						int nx = x + dx;
						int ny = y + dy;
						if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && !isMine(nx, ny)) {
							revealCell(nx, ny);
						}
					}
				}
			}
		}
	}

	// Vérifie si le joueur a gagné
	public boolean hasWon() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (!mines[x][y] && !revealed[x][y]) {
					return false;
				}
			}
		}
		return true;
	}

	// Vérifie si le joueur a perdu en révélant une cellule contenant une mine
	public boolean hasLost(int x, int y) {
		return mines[x][y];
	}

	// Affiche le plateau de jeu avec les cellules révélées ou non
	public void printBoard() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (revealed[x][y]) {
					if (mines[x][y]) {
						System.out.print("M "); // Affiche une mine
					} else {
						System.out.print(countAdjacentMines(x, y) + " "); // Affiche le nombre de mines adjacentes
					}
				} else {
					System.out.print("X "); // Affiche une cellule non révélée
				}
			}
			System.out.println();
		}
	}

	// Méthode pour obtenir la taille du plateau de jeu
	public int getSIZE() {
		return SIZE;
	}

	// Vérifie si le jeu est terminé (le joueur a cliqué sur une mine)
	public boolean gameOver() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (mines[x][y] && revealed[x][y]) {
					return true;
				}
			}
		}
		return false;
	}
}