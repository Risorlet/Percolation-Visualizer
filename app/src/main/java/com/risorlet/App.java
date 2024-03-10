package com.risorlet;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;

public class App{

    private JFrame frame;
    private GridPanel board;
    private int gridSize;

    private void init(int n) {
        this.gridSize = n;

        frame = new JFrame("Percolation Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 600, 400);
        frame.setResizable(false);
        frame.setLayout(null);

        board = new GridPanel(gridSize,300,300);
        frame.add(board);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        App percolationVisualizer = new App();
        percolationVisualizer.init(12);
        percolationVisualizer.board.timer.start();
    }
}
