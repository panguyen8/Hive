package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant to clicking a button
 *
 * @author Phuocan Nguyen
 */
public class HiveButtonAction extends GameAction {

    HiveGameState.piece gamePiece;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveButtonAction(GamePlayer player, HiveGameState.piece piece) {
        super(player);
        gamePiece = piece;
    }
}
