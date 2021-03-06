package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant to selecting a pieces
 *
 * @author Phuocan Nguyen
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

    /**
     * Gets the row of the selectedPiece
     *
     * @return int
     */
    public int getRow(){
        return row;
    }

    /**
     * Gets the Col of the selectedPiece
     *
     * @return int
     */
    public int getCol(){
        return col;
    }
}
