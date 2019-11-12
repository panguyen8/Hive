package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

public class HiveMoveAction extends GameAction {

    protected int startRow;
    protected int startCol;
    protected int endRow;
    protected int endCol;

    /**
     *  consturctor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveMoveAction(GamePlayer player, int startX, int startY, int endX, int endY) {
        super(player);

        startRow = startX;
        startCol = startY;
        endRow = endX;
        endCol = endY;
    }

    public int getStartX() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndX() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }
}
