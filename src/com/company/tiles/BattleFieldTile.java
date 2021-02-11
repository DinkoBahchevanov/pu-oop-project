package com.company.tiles;

import java.awt.*;

public class BattleFieldTile extends Tile {

    private boolean Barrier;

    public BattleFieldTile(Color color, int x, int y) {
        super(color, x, y);
    }

    public boolean isBarrier() {
        return Barrier;
    }

    public void setBarrier(boolean barrier) {
        Barrier = barrier;
    }
}
