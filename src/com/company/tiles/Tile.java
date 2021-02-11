package com.company.tiles;

import java.awt.*;

public abstract class Tile {

    private Color color;
    private final int row = getRow();
    private final int col = getCol();

    private int x;
    private int y;

    public Tile() {

    }

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Graphics g, int x, int y) {
        g.drawRect(x, y, 100, 100);
        g.setColor(getColor());
        g.fillRect(x+1,y+1,98,98);

//        g.setColor(new Color(238, 5, 5));
//        g.drawRect(1000, 100, 300, 200);
//        g.fillRect(1000, 100, 300, 200);
//
//        g.setColor(new Color(255,255,255));
//        g.drawRect(1005,104,290,190);
//        g.fillRect(1005,104,290,190);
    }

    private int getRow() {
        return (this.y - 30) / 100;
    }
    private int getCol() {
        return x / 100;
    }
}
