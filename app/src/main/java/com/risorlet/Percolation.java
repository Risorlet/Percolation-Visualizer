// @Author risorlet :)
package com.risorlet;
import java.util.Arrays;

public class Percolation {

    private WeightedUF uf; // Union-Find DS for connceting sites and finding connections
    private int[][] grid;
    private int n;
    private int openSiteCount;

    // Introducing virtual top and virtual bottom site for top and bottom row sites
    private int virtualTop,virtualBottom;

    // Creates a (n x n) grid with initially all the sites blocked
    public Percolation(int n) {
        this.n = n;
        // Initializing the grid
        grid = new int[n][n];
        for(int i=0; i<n; i++) {
            Arrays.fill(grid[i],0);
        }
        openSiteCount = 0;

        int siteCount = n * n; // Number of sites in the grid

        // generating a UF with 0 - (n^2 - 1) sites with each being their own root
        // +2 is for the two virtual sites
        uf = new WeightedUF(siteCount + 2);

        // Introducing virtual top and virtual bottom site for top and bottom row sites
        virtualTop = siteCount;
        virtualBottom = siteCount + 1;

        for(int i = 0; i<n; i++) { // connects the top row to the virtual top
            uf.union(i,virtualTop);
        }
        for(int i = n*(n-1); i<siteCount; i++) { // connects the bottom row with the virtual bottom
            uf.union(i,virtualBottom);
        }
    }

    // Converts a blocked site into an open site
    public void open(int row, int col) { // row and col are with 0 based indexing
        if(isOpen(row, col)) return; // if the site is already open then return

        // setting the grid value to 1 to mark the site as open and increasing the openSiteCount
        grid[row][col] = 1;
        openSiteCount++;

        // Connecting the newly opened site to the adjacent open sites
        connectToAdjacentSites(row, col);
    }

    // Connects an open site to its adjacent open sites
    private void connectToAdjacentSites(int row, int col) {
        // the given site index in the UF
        int site = (n * row) + col;

        // all the possible adjacent sites
        int top = (n * (row-1)) + col;
        int bottom = (n * (row+1)) + col;
        int left = (n * row) + (col-1);
        int right = (n * row) + (col+1);

        //  setting up the edges
        boolean inLeftEdge = col==0;
        boolean inRightEdge = col==n-1;
        boolean inTopEdge = row==0;
        boolean inBottomEdge = row==n-1;

        if(inLeftEdge) {
            // sites in the left edge can only connect in the top, bottom and right direction
            // If the adjacent sites are open then connect them
            if(row>0 && isOpen(row-1, col)) // top-left site don't have a top
                uf.union(site, top); 
            if(row<n-1 && isOpen(row+1, col)) // bottom-left site don't have a bottom
                uf.union(site, bottom); 
            if(isOpen(row, col+1)) // If the right site is open then union
                uf.union(site, right);
        }else if(inRightEdge) {
            // sites in the right edge can only connect in the top, bottom and left direction
            // If the adjacent sites are open then connect them
            if(row>0 && isOpen(row-1, col)) 
                uf.union(site, top); // top-right site don't have a top
            if(row<n-1 && isOpen(row+1, col)) 
                uf.union(site, bottom); // bottom-right site don't have a bottom
            if(isOpen(row, col-1)) // If the left site is open then union
                uf.union(site, left);
        }else if(inTopEdge) {
            // sites in the top edge can only connect in the bottom, right and left direction
            // If the adjacent sites are open then connect them
            if(col>0 && isOpen(row, col-1)) // top-left site don't have a left
                uf.union(site, left); 
            if(col<n-1 && isOpen(row, col+1)) // top-right site don't have a right
                uf.union(site, right); 
            if(isOpen(row+1, col)) 
                uf.union(site, bottom);
        }else if(inBottomEdge) {
            // sites in the bottom edge can only connect in the top, right and left direction
            // If the adjacent sites are open then connect them
            if(col>0 && isOpen(row, col-1)) // bottom-left site don't have a left
                uf.union(site, left); 
            if(col<n-1 && isOpen(row, col+1)) // bottom-right site don't have a right
                uf.union(site, right); 
            if(isOpen(row-1, col)) 
                uf.union(site, top);
        }else {
            if(isOpen(row-1, col)) uf.union(site, top);
            if(isOpen(row+1, col)) uf.union(site, bottom);
            if(isOpen(row, col-1)) uf.union(site, left);
            if(isOpen(row, col+1)) uf.union(site, right);
        }
    }

    // Checks if a site is open or closed
    public boolean isOpen (int row, int col) { // row and col are 1 based
        return grid[row][col] == 1;
    }

    // Checks if a site is a full site or not
    public boolean isFull(int row, int col) { // row and col are 1 based
        int site = (n * row) + col;
        return uf.isConnected(site, virtualTop);
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // Checks if the system percolates or not
    public boolean percolates() {
        return uf.isConnected(virtualTop, virtualBottom);
    }

    // Prints the current grid arrangement
    public void printGrid() {
        for(int i=0;i<n;i++) {
            for(int j=0; j<n;j++){
                System.out.print("| " + grid[i][j] + " |");
            }
            System.out.println("\n");
        }
    }

    // Driver code for testing
    // public static void main(String[] args) {
    //     Percolation board = new Percolation(5);
    //     board.open(0, 2);
    //     board.open(0, 3);
    //     board.open(1, 3);
    //     board.open(2, 3);
    //     board.open(4, 3);
    //     board.printGrid();
    //     System.out.println(board.percolates());
    // }
}
