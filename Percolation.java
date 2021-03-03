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
    private boolean grid[][];
    private final int gridSize;
    private final int verticalTop;
    private final int vertcalBottom;
    private WeightedQuickUnionUF wuf;
    private WeightedQuickUnionUF wufFull;
    private int noOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N > 0");
        }
        gridSize = n;
        grid = new boolean[n][n];
        int gridSquared = n * n;
        verticalTop = gridSquared;
        vertcalBottom = gridSquared + 1;
        noOfOpenSites = 0;
        wuf = new WeightedQuickUnionUF(gridSquared + 2);
        wufFull = new WeightedQuickUnionUF(gridSquared + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        int flatIndex = flattenGrid(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[shiftRow][shiftCol] = true;
        noOfOpenSites++;

        // top row
        if (row == 1) {
            wuf.union(verticalTop, flatIndex);
            wufFull.union(verticalTop, flatIndex);
        }
        // bottom row
        if (row == gridSize) {
            wuf.union(vertcalBottom, flatIndex);
        }

        // check left and open
        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            wuf.union(flatIndex, flattenGrid(row, col - 1));
            wufFull.union(flatIndex, flattenGrid(row, col - 1));
        }
        // check right and open
        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            wuf.union(flatIndex, flattenGrid(row, col + 1));
            wufFull.union(flatIndex, flattenGrid(row, col + 1));
        }
        // check top and open
        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            wuf.union(flatIndex, flattenGrid(row - 1, col));
            wufFull.union(flatIndex, flattenGrid(row - 1, col));
        }
        //check bottom and open
        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            wuf.union(flatIndex, flattenGrid(row + 1, col));
            wufFull.union(flatIndex, flattenGrid(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wufFull.find(verticalTop) == wufFull.find(flattenGrid(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wuf.find(verticalTop) == wuf.find(vertcalBottom);
    }

    private void validateSite(int row, int col) {
        if (row > gridSize || row <= 0 || col > gridSize || col <= 0) {
            throw new IllegalArgumentException("row and col not bw 0 and gridsize + 1");
        }
    }

    private int flattenGrid(int row, int col) {
        return ((row - 1) * gridSize + col) - 1;
    }

    private boolean isOnGrid(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
            return false;
        }
        return true;
    }
}
