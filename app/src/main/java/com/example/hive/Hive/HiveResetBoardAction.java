package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

public class HiveResetBoardAction extends GameAction{

    protected boolean deselectTargets;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     */
    public HiveResetBoardAction(GamePlayer player, boolean resetTargetsOnly) {
        super(player);
        deselectTargets = resetTargetsOnly;
    }

    public boolean getSelectTarget(){
        return deselectTargets;
    }
}
