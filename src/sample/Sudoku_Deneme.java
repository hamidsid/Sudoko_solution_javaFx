package sample;

public class Sudoku_Deneme {

    // we define a simple grid to solve. Grid is stored in a 2D Array
    public static int[][] GRID_TO_SOLVE = {
            {0, 0, 8, 0, 0, 9, 0, 6, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 5},
            {1, 0, 2, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 1, 0, 0, 9, 0},
            {0, 5, 0, 0, 0, 0, 6, 0, 0},
            {6, 0, 0, 0, 0, 0, 0, 2, 8},
            {4, 1, 0, 6, 0, 8, 0, 0, 0},
            {8, 6, 0, 0, 3, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 4, 0, 0}
    };

    public static int[][] myGrid = {
            {0, 0, 3, 4},
            {3, 4, 0, 0},
            {0, 0, 4, 3},
            {0, 3, 2, 0}

    };

    private int[][] board;
    public static final int EMPTY = 0; // empty cell
    public static final int SIZE = 9; // size of our Sudoku grids

    public Sudoku_Deneme(int[][] board) {
        this.board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    // we check if a possible number is already in a row
    private boolean isInRow(int row, int number) {
        // row has the unique (row-clash)
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }

    // we check if a possible number is already in a column
    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == number)
                return true;

        return false;
    }

    // we check if a possible number is in its 3x3 box
    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == number)
                    return true;

        return false;
    }


    /*public static boolean isSafe(int[][] board,
                                 int row, int col,
                                 int num) {
        // row has the unique (row-clash)
        for (int d = 0; d < board.length; d++) {
            // if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num) {
                return false;
            }
        }

        // column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++) {
            // if the number we are trying to
            // place is already present in
            // that column, return false;

            if (board[r][col] == num) {
                return false;
            }
        }

        // corresponding square has
        // unique number (box-clash)
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }

        // if there is no clash, it's safe
        return true;
    }

*/

    // combined method to check if a number possible to a row,col or 3x3 box position is ok
    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
    }

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // we search for an empty cell
                if (board[row][col] == EMPTY) {
                    // we try possible numbers
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            // number ok. it respects Sudoku constraints
                            board[row][col] = number;

                            if (solve()) { // we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                board[row][col] = EMPTY;
                            }
                        }
                    }

                    return false; // we return false
                }
            }
        }

        return true; // sudoku solved
    }


    public void display() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }


    public static void main(String[] args) {
        Sudoku_Deneme sudoku = new Sudoku_Deneme(GRID_TO_SOLVE);
        System.out.println("Sudoku grid to solve");
        sudoku.display();

        // we try resolution
        if (sudoku.solve()) {
            System.out.println("Sudoku Grid solved with simple BT");
            sudoku.display();
        } else {
            System.out.println("Unsolvable");
        }
    }


}
