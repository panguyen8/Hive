package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

public class HiveMoveAction extends GameAction {

    protected int row;
    protected int col;

    /**
     *  consturctor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveMoveAction(GamePlayer player, int r, int c) {
        super(player);

        row = r;
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
