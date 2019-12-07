package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant to placing a piece on the board
 *
 * @author Phuocan Nguyen
 */
public class HivePlacePieceAction extends GameAction {

    protected int row;
    protected int col;
    protected  HiveGameState.piece piece;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HivePlacePieceAction(GamePlayer player, int x, int y, HiveGameState.piece piece)
    {
        super(player);

        row = x;
        col = y;
        this.piece = piece;
    }

    /**
     * Gets the row of the placedpiece
     * @return row
     */
    public int getRow(){
        return row;
    }

    /**
     * Gets the col of placedpiece
     * @return col
     */
    public int getCol(){
        return col;
    }
}
