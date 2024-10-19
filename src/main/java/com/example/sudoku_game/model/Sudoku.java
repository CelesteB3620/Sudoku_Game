package com.example.sudoku_game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Laura Celeste Berrio Parra - 2322101
 * The Sudoku class represents a 6x6 Sudoku board and contains methods to solve, validate,
 * and automatically fill some cells in the board.
 */
public class Sudoku {

    private final ArrayList<ArrayList<Integer>> sudoku; // Represents the current Sudoku board.
    private final ArrayList<ArrayList<Integer>> sudokuSolved; // Represents the full solution of the Sudoku.
    private final int SUDOKU_SIZE = 6; // Size of the Sudoku board (6x6).
    private final Random random = new Random(); // Random number generator.
    private int helps;
    /**
     * Constructor that initializes an empty board and generates a solution for the Sudoku.
     */
    public Sudoku() {
        sudoku = new ArrayList<>();
        sudokuSolved = new ArrayList<>();

        // Initializes the Sudoku boards with zeros (empty cells).
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            sudoku.add(new ArrayList<>(Collections.nCopies(SUDOKU_SIZE, 0)));
            sudokuSolved.add(new ArrayList<>(Collections.nCopies(SUDOKU_SIZE, 0)));
        }

        // Generates a valid solution.
        solve(0, 0);
    }

    /**
     * Gets the value of the cell at the given position.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The value of the cell at the given row and column.
     */
    public int getCellValue(int row, int col) {
        return sudoku.get(row).get(col);
    }

    /**
     * Sets a value in the cell at the given position.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param value The value to be set in the cell.
     */
    public void setCellValue(int row, int col, int value) {
        sudoku.get(row).set(col, value);
    }

    /**
     * Fills two empty cells within a 2x3 block with values from the solution.
     * @param blockRow The block row index (2 rows per block).
     * @param blockCol The block column index (3 columns per block).
     */
    public void fillTwoCellsInBlock(int blockRow, int blockCol) {
        int count = 0;

        // Fill two random empty cells within the specified block.
        while (count < 2) {
            int row = blockRow * 2 + random.nextInt(2); // Calculate the row within the block.
            int col = blockCol * 3 + random.nextInt(3); // Calculate the column within the block.

            if (sudoku.get(row).get(col) == 0) {
                sudoku.get(row).set(col, sudokuSolved.get(row).get(col)); // Set the solution value.
                count++;
            }
        }
    }

    /**
     * Checks if a value is valid in a given cell according to Sudoku rules.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param value The value to check.
     * @param sudoku The Sudoku board on which to check the value.
     * @return true if the value is valid in the row, column, and block, false otherwise.
     */
    public boolean checkValidValue(int row, int col, int value, ArrayList<ArrayList<Integer>> sudoku) {

        // Check if the value is already in the cell.
        if (sudoku.get(row).get(col) == value) {
            return true;
        }

        // Check if the value is in the row.
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (sudoku.get(row).get(i) == value) {
                return false;
            }
        }

        // Check if the value is in the column.
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (sudoku.get(i).get(col) == value) {
                return false;
            }
        }

        // Check if the value is in the 2x3 block.
        int blockFirstRow = (row / 2) * 2;
        int blockFirstCol = (col / 3) * 3;

        for (int i = blockFirstRow; i < (blockFirstRow + 2); i++) {
            for (int j = blockFirstCol; j < (blockFirstCol + 3); j++) {
                if (sudoku.get(i).get(j) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the Sudoku is fully solved and valid.
     * @return true if the Sudoku is completely solved, false otherwise.
     */
    public boolean isSudokuSolved() {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                int value = sudoku.get(row).get(col);

                // If any cell is empty, the Sudoku is not solved.
                if (value == 0) {
                    return false;
                }

                // Check if the value in the cell is valid.
                if (!checkValidValue(row, col, value, sudoku)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the Sudoku using backtracking.
     * @param row The current row.
     * @param col The current column.
     * @return true if the Sudoku is solved, false if backtracking is needed.
     */
    private boolean solve(int row, int col) {
        if (row == 6) {
            return true;
        }

        if (sudokuSolved.get(row).get(col) == 0) {
            for (int num = 1; num <= SUDOKU_SIZE; num++) {
                if (checkValidValue(row, col, num, sudokuSolved)) {
                    sudokuSolved.get(row).set(col, num);
                    if (solve(nextRow(row, col), nextCol(col))) {
                        return true;
                    }
                    sudokuSolved.get(row).set(col, 0); // Backtracking
                }
            }
            return false;
        } else {
            return solve(nextRow(row, col), nextCol(col));
        }
    }

    /**
     * Calculates the next column in the row.
     * @param col The current column.
     * @return The index of the next column.
     */
    public int nextCol(int col) {
        if (col == 5) {
            return 0;
        } else {
            return col + 1;
        }
    }

    /**
     * Calculates the next row when switching columns.
     * @param row The current row.
     * @param col The current column.
     * @return The index of the next row.
     */
    public int nextRow(int row, int col) {
        if (col == 5) {
            return row + 1;
        } else {
            return row;
        }
    }

    /**
     * Returns the solved Sudoku board.
     * @return The solved Sudoku board.
     */
    public ArrayList<ArrayList<Integer>> getSudokuSolved() {
        return sudokuSolved;
    }

    /**
     * Returns the current Sudoku board.
     * @return The current Sudoku board.
     */
    public ArrayList<ArrayList<Integer>> getSudoku() {
        return sudoku;
    }

    /**
     * Returns the size of the Sudoku (6x6).
     * @return The size of the Sudoku.
     */
    public int getSudokuSize() {
        return SUDOKU_SIZE;
    }

    public int getHelps() {
        return helps;
    }

    public void setHelps(int helps) {
        this.helps = helps;
    }
}
