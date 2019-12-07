package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant to placing a piece on the board
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

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
