package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant to selecting a pieces
 */
public class HiveSelectedPieceAction extends GameAction {

    protected int row;
    protected int col;
    protected HiveGameState.piece piece;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveSelectedPieceAction(GamePlayer player, int x, int y) {
        super(player);
        row = x;
        col = y;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
