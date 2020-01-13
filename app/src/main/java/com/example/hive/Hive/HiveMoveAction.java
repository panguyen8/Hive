package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 *  Holds variables relevant to moving a piece
 *
 *  @author: Phuocan Nguyen
 */
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

    /**
     * gets the starting X coord
     *
     * @return int
     */
    public int getStartX() {
        return startRow;
    }

    /**
     * gets the starting Y coord
     *
     * @return int
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * gets the ending X coordinate
     *
     * @return int
     */
    public int getEndX() {
        return endRow;
    }

    /**
     * gets the ending Y coording
     *
     * @return int
     */
    public int getEndCol() {

        return endCol;
    }


}
