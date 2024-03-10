package com.risorlet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GridPanel extends JPanel implements ActionListener{

    Site[][] sites;
    Timer timer;
    Percolation perc;
    int bound;
    int gridSize;
    int row,col;
    
    public GridPanel(int gridSize, int height, int width) {
        this.gridSize = gridSize;
        perc = new Percolation(gridSize);
        this.bound = gridSize * gridSize;
        setLayout(new GridLayout(gridSize,gridSize,1,1));
        setBorder(BorderFactory.createLineBorder(Color.WHITE));;
        setBounds(50, 25, width, height);

        sites = new Site[gridSize][gridSize];
        timer = new Timer(200, this);
 
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Site cell = new Site();
                sites[i][j] = cell;
                this.add(cell);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        generateSite();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(perc.percolates()){
            timer.stop();
            // perc.printGrid();
        }
        changeColor();
        repaint();
    }

    public void generateSite() {
        Random random = new Random();

        int site = random.nextInt(bound);
        row = site/gridSize;
        col = site%gridSize;
        
        while(perc.isOpen(row, col)) {
            site = random.nextInt(64);
            row = site/gridSize;
            col = site%gridSize;
        }
        perc.open(row, col);
        Site panel = sites[row][col];

        Color color = Color.WHITE;
        if(perc.isFull(row, col)){
            color = Color.CYAN;
        }
        
        Circle dot = new Circle(panel);
        panel.setDrawable(dot,color);
    }

    public void changeColor() {
        // This function is supposed to scan the whole grid for full sites and turn them
        // into blue color everytime a new site is opened

        // Approach 1: Brute-Force : scan the whole grid O(n^2)
        

        for(int row = 0; row<gridSize; row++) {
            for(int col =0; col<gridSize; col++) {
                if(perc.isFull(row, col) && perc.isOpen(row, col)) {
                    // Circle dot = (Circle) sites[row][col].getDrawable();
                    // if(dot != null) {
                    //     dot.draw(getGraphics(), Color.CYAN);
                    // }
                    Site panel = sites[row][col];

                    Color color = Color.CYAN;
                    
                    Circle dot = new Circle(panel);
                    panel.setDrawable(dot,color);
                }
            }
        }
    }
}
