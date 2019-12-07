package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Holds variables relevant for resetting the board
 *
 * @author Phuocan Nguyen
 */
public class HiveResetBoardAction extends GameAction{

    protected boolean deselectTargets;
    /**
     * constructor for GameAction
     *
     * @param player
     *      the player who created the action
     * @param resetTargetsOnly
     *       reset target pieces only
     */
    public HiveResetBoardAction(GamePlayer player, boolean resetTargetsOnly) {
        super(player);
        deselectTargets = resetTargetsOnly;
    }

    /**
     * Returns the deselcted targets
     *
     * @return deselectTargets
     */
    public boolean getSelectTarget(){
        return deselectTargets;
    }
}
