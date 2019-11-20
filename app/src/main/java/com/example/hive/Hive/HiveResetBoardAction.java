package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

public class HiveResetBoardAction extends GameAction{
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveResetBoardAction(GamePlayer player) {
        super(player);
    }
}
