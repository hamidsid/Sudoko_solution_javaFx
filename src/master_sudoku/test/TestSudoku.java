package master_sudoku.test;

import static org.junit.jupiter.api.Assertions.*;

import master_sudoku.logic.Board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudoku {
    Board board;

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void emptySudoku() {
        assertTrue(board.solve());
    }

    //Example from the text
    @Test
    void semifilledSudoku() {
        board.put(0, 2, "8");
        board.put(0, 5, "9");
        board.put(0, 7, "6");
        board.put(0, 8, "2");
        board.put(1, 8, "5");
        board.put(2, 0, "1");
        board.put(2, 2, "2");
        board.put(2, 3, "5");
        board.put(3, 3, "2");
        board.put(3, 4, "1");
        board.put(3, 7, "9");
        board.put(4, 1, "5");
        board.put(4, 6, "6");
        board.put(5, 0, "6");
        board.put(5, 7, "2");
        board.put(5, 8, "8");
        board.put(6, 0, "4");
        board.put(6, 1, "1");
        board.put(6, 3, "6");
        board.put(6, 5, "8");
        board.put(7, 0, "8");
        board.put(7, 1, "6");
        board.put(7, 4, "3");
        board.put(7, 6, "1");
        board.put(8, 6, "4");
        assertTrue(board.solve());

    }

    @Test
    void unsolvableSudoku() {
        board.put(8, 8, "1");
        board.put(8, 7, "1");
        assertFalse(board.solve());
        board.reset();
    }

}
