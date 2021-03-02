/* *****************************************************************************
 *  Name:    Aabhas Sao
 *  NetID:   aabhas
 *  Precept: P00
 *
 *  Description:  monte carlo simulation
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites;
    private int gridSize;
    private int virtualTopIndex;
    private int virtualBottomIndex;

    private WeightedQuickUnionUF wuf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");
        gridSize = n;
        int gridSquared = gridSize * gridSize;
        grid = new boolean[gridSize][gridSize];
        virtualTopIndex = gridSquared;
        virtualBottomIndex = gridSquared + 1;
        openSites = 0;

        wuf = new WeightedQuickUnionUF(gridSquared + 2); //including virtual top and bottom
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validatesite(row, col);

        // if site is already open return
        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        int flatIndex = flattenGrid(row, col);

        // union first row with virtual top
        if (row == 0) {
            wuf.union(flatIndex, virtualTopIndex);
        }

        // union last row with virtual bottom
        if (row == gridSize - 1) {
            wuf.union(flatIndex, virtualBottomIndex);
        }

        // check if adjacent members are open and if open union them.
        if (row != gridSize - 1 && isOpen(row + 1, col)) {
            wuf.union(flatIndex, flattenGrid(row + 1, col));
        }
        if (row != 0 && isOpen(row - 1, col)) {
            wuf.union(flatIndex, flattenGrid(row - 1, col));
        }
        if (col != gridSize - 1 && isOpen(row, col + 1)) {
            wuf.union(flatIndex, flattenGrid(row, col + 1));
        }
        if (col != 0 && isOpen(row, col - 1)) {
            wuf.union(flatIndex, flattenGrid(row, col - 1));
        }

        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validatesite(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wuf.connected(flattenGrid(row, col), virtualTopIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wuf.connected(virtualTopIndex, virtualBottomIndex);
    }

    public void validatesite(int row, int col) {
        if (row >= gridSize || row < 0 || col >= gridSize || col < 0) {
            throw new IllegalArgumentException("row and col should be between 0 and gridSize");
        }
    }

    public int flattenGrid(int row, int col) {
        return row * gridSize + col;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println(p.percolates());
    }
}
