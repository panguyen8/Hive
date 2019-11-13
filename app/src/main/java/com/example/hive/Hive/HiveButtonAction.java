package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

public class HiveButtonAction extends GameAction {
    private final int QUEEN = 1;
    private final int GRASSHOPPER = 2;
    private final int SPIDER = 3;
    private final int BEETLE = 4;
    private final int ANT = 5;

    HiveGameState.piece gamePiece;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveButtonAction(GamePlayer player, int piece) {
        super(player);
        if(piece == QUEEN) {
            gamePiece = HiveGameState.piece.WBEE;

        } else if (piece == GRASSHOPPER) {
            gamePiece = HiveGameState.piece.WGHOPPER;
        } else if (piece == SPIDER) {
            gamePiece = HiveGameState.piece.WSPIDER;
        } else if (piece == BEETLE) {
            gamePiece = HiveGameState.piece.WBEETLE;
        } else if (piece == ANT) {
            gamePiece = HiveGameState.piece.WANT;
        }
    }

}
