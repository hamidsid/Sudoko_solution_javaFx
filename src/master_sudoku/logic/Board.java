package master_sudoku.logic;

public class Board {

	int[][] board;
	private boolean[][] empty;
	private boolean solutionFound;
	private boolean unsolvableByUser;

	static final int EMPTY = 0;

	/**
	 * Created an empty Sudoku board.
	 */
	public Board() {
		board = new int[9][9];
		empty = new boolean[9][9];
		solutionFound = false;
		unsolvableByUser = false;
	}

	/**
	 * Puts input value into the specified board coordinates.
	 * 
	 * @param x     - specifies which row to add the input to.
	 * @param y     - specifies which column to add the input to.
	 * @param input - the value getting added to the specified board element.
	 */
	public void put(int x, int y, String input) {
		board[x][y] = Integer.parseInt(input);
	}

	/**
	 * Returns the element on the specified board coordinate.
	 * 
	 * @param x - specifies which row to get from.
	 * @param y - specifies which column to get from.
	 * @return the element on the specified board coordinate.
	 */
	public String get(int x, int y) {
		return board[x][y] == 0 ? "" : Integer.toString(board[x][y]);
	}

	/**
	 * Resets and clears the board, setting all elements to EMPTY.
	 */
	public void reset() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = EMPTY;
			}
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				empty[i][j] = false;
			}
		}
	}

	/**
	 * Attempts to solve the Sudoku.
	 * 
	 * @return true if the sudoku was solved, otherwise returns false.
	 */
	public boolean solve() {
		solutionFound = false;
		unsolvableByUser = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) {
					empty[i][j] = true;
				} else {
					empty[i][j] = false;
				}
			}
		}
		return solve(0);
	}

	private boolean solve(int square) {
		if (unsolvableByUser) {
			return false;
		}
		if (square == 81) {
			solutionFound = true;
			print();
			return true;
		}
		int x = square % board.length;
		int y = square / board.length;

		if (empty[x][y]) {
			boolean numbers[] = new boolean[9];
			for (int i = 0; i < numbers.length; i++) {
				int value = i + 1;
				if (validate(x, y, value) && !solutionFound) {
					board[x][y] = value;
					numbers[i] = solve(square + 1);
				}
			}
			for (int i = 0; i < numbers.length; i++) {
				if (numbers[i]) {
					return true;
				}
			}
			board[x][y] = EMPTY;
			return false;
		} else {
			if (validate(x, y, board[x][y]) && !solutionFound) {
				return solve(square + 1);
			} else {

				return false;
			}
		}
	}

	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private boolean validate(int x, int y, int value) {
		// checks row X elements on all columns
		for (int i = 0; i < 9; i++) {
			if (board[x][i] == EMPTY || i == y)
				continue;
			if (board[x][i] == value) {
				if (!empty[x][i] && !empty[x][y]) {
					unsolvableByUser = true;
				}
				return false;
			}
		}

		// checks col Y elements on all rows
		for (int i = 0; i < 9; i++) {
			if (board[i][y] == EMPTY || x == i)
				continue;
			if (board[i][y] == value) {
				if (!empty[i][y] && !empty[x][y]) {
					unsolvableByUser = true;
				}
				return false;
			}
		}

		// checks corresponding region
		int regX = (x / 3) * 3 + 1;
		int regY = (y / 3) * 3 + 1;
		for (int i = regX - 1; i <= regX + 1; i++) {
			for (int j = regY - 1; j <= regY + 1; j++) {
				if (board[i][j] == EMPTY || (x == i && y == j))
					continue;
				if (board[i][j] == value) {
					if (!empty[i][j] && !empty[x][y]) {
						unsolvableByUser = true;
					}
					return false;
				}
			}
		}

		// no duplicates found
		return true;
	}
}
