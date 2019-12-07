package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.actionMessage.GameAction;

/**
 *  Holds variables relevant to moving a piece
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

    public int getStartX() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndX() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    /**
     *Moves a piece on the board and sets the turn to that of the other player.
     *Pieces are just being moved to empty spaces (although the beetle can go anywhere);
     *this does not necessarily reflect movement rules.
     *
     * @param id: the id of whose turn it is
     * @param pieceOnBoard: the piece that will be moved
     * @param startX: the piece's starting X position
     * @param startY: the piece's starting Y position
     * @param newX: the piece's new X position
     * @param newY: the piece's new Y position
     * @return true if successful, false otherwise
     */

}
