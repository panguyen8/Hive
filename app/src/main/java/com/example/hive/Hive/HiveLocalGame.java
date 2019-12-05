package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.actionMessage.GameAction;
import com.example.hive.game.utilities.Logger;

import java.lang.annotation.Target;

/**
 * Represents a local game, which is responsible for enforcing rules,
 * moving pieces, etc.
 * @author Stephen Nguyen
 * @author Samuel Nguyen
 * @author Phuocan Nguyen
 */
public class HiveLocalGame extends LocalGame {
    //Create instance of current game state
    private HiveGameState hgs;

    //After a player's fourth turn,
    //the bee must be played if it already
    //hasn't been

    /**
     * Constructor of local game
     */
    public HiveLocalGame() {
        super();
        hgs = new HiveGameState();
    }

    /**
     * Sends new GameState to the appropriate player
     *
     * @param p: the player receiving the info
     */
    protected void sendUpdatedStateTo(GamePlayer p) {
        HiveGameState copy = new HiveGameState(hgs);
        p.sendInfo(copy);
    }

    /**
     * Checks if a player can move
     *
     * @param playerIdx: the player's player-number (ID)
     * @return true if their id matches that of who is to move, false otherwise
     */
    protected boolean canMove(int playerIdx) {
        if (hgs.getTurn() == playerIdx) {
            return true;
        }
        return true;
    }

    /**
     * Checks if the game is over,
     * which is when a bee is surrounded
     * by other pieces
     *
     * @return a message that states who wins,
     * or nothing if a win condition has not been met
     */
    protected String checkIfGameOver() {
        //If player 0's bee is surrounded, but player 1's is not,
        //player 0 wins and vice versa
        if(checkBee(0) && (!checkBee(1))) {
            return "Game! This game's winner is... " + playerNames[0] + "!";
        }

        if(checkBee(1) && (!checkBee(0))) {
            return "Game! This game's winner is... " + playerNames[1] + "!";
        }

        //If both bees are surrounded, result in a draw.
        if (checkBee(0) && checkBee(1))
        {
            return "Game! This game resulted in... a draw.";
        }

        //Return nothing if game isn't over.
        return null;
    }

    /**
     * Checks if a player's bee is surrounded
     * (i.e. if all adjacent spots are full)
     *
     * @param player: ID of player whose bee is being checked
     * @return true if surrounded, false otherwise
     */
    private boolean checkBee(int player) {
        HiveGameState.piece[][] board = hgs.getBoard();
        HiveGameState.piece beeToCheck;

        //Get bee to check
        if (player == 0) {
            beeToCheck = HiveGameState.piece.BBEE;
        }
        else {
            beeToCheck = HiveGameState.piece.WBEE;
        }

        // Set default x and y values
        int pieceX = 0;
        int pieceY = 0;

        // Iterate through board and update x and y values at bee's spot
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (board[row][col] == beeToCheck) {
                    pieceX = row;
                    pieceY = col;
                }
            }
        }

        // Returns status of whether or not surrounding spots are empty
        if (hgs.checkSurround(pieceX, pieceY)) {
            return true;
        }
        return false;
    }


    /**
     * Makes a move based on whose turn it is
     *
     * @param action The move that the player has sent to the game
     * @return true if successful, false otherwise
     */
    protected boolean makeMove(GameAction action) {
        // Moving a piece
        if (action instanceof HiveMoveAction) {
            HiveMoveAction move = (HiveMoveAction) action;

            //To prevent moving opponent's pieces, make sure only player's
            //own pieces are being checked, return false if an opponent's
            //piece was selected
            if (hgs.getTurn() == 1) {
                if(hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WBEE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WBEETLE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WANT ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WSPIDER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WGHOPPER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.EMPTY) {
                    return false;
                }

                //Moving can only be done on legal spaces
                //Moving rules will be done here as well
                if (hgs.board[move.endRow][move.endCol] == HiveGameState.piece.EMPTY)//changed target to empty
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                } else {
                    return false;
                }

                //Set the turn to the next player
                hgs.setTurn(0);
            }
            else {
                //This functions like the code above, just with regards to
                //the other player
                if(hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BBEE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BBEETLE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BANT ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BSPIDER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BGHOPPER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.EMPTY) {
                    return false;
                }
                if (hgs.board[move.endRow][move.endCol] == HiveGameState.piece.TARGET)
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                } else {
                    return false;
                }
                hgs.setTurn(1);
            }

            hgs.resetTarget();
            //increment turnCount
            hgs.addTurnToCount();
            return true;
        }

        // Placing piece
        else if (action instanceof HivePlacePieceAction) {

            HivePlacePieceAction placement = (HivePlacePieceAction) action;
            //At turn 0, the piece can be placed anywhere, so there is no need to check
            //illegal placements

            //Checks if current player is player 0
            if (hgs.getTurn() == 0) {
                if (hgs.getTurnCount() == 0) {
                    //Set piece to wherever it was tapped on the board and remove it from
                    //player's hand
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);

                    //Since all of the board spots become a target, reset them
                    hgs.resetTarget();

                    //Increment turn count and change the turn
                    hgs.addTurnToCount();
                    hgs.setTurn(1);
                    return true;
                }
            }

            //Same as above, but with player 1
            else {
                if (hgs.getTurnCount() == 0) {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                    hgs.resetTarget();
                    hgs.addTurnToCount();
                    hgs.setTurn(0);
                    return true;
                }
            }

            if (hgs.getTurn() == 0) {
                if(hgs.board[placement.row][placement.col] == HiveGameState.piece.TARGET) {
                    //makes sure that the bee is placed
                    if(hgs.getTurnCount() > 7 && hgs.bugList.contains(HiveGameState.piece.WBEE)) {
                        hgs.board[placement.row][placement.col] = HiveGameState.piece.WBEE;
                        hgs.bugList.remove(HiveGameState.piece.WBEE);
                    }
                    else {
                        hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                        hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                    }
                }
                else {
                    return false;
                }

                //Set next turn
                hgs.setTurn(1);
            }
            //Same as above, just with other player's pieces
            else {
                //makes sure that the bee is placed
                if(hgs.getTurnCount() > 7 && hgs.bugList.contains(HiveGameState.piece.BBEE)) {
                    hgs.board[placement.row][placement.col] = HiveGameState.piece.BBEE;
                    hgs.bugList.remove(HiveGameState.piece.BBEE);
                } else if(hgs.board[placement.row][placement.col] == HiveGameState.piece.EMPTY) {//changed target to empty // always true
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                }

                else {
                    return false;
                }
                hgs.setTurn(0);
            }

            hgs.resetTarget();

            //increment turnCount
            hgs.addTurnToCount();
            //Logger.log("turnCount", Integer.toString(hgs.getTurnCount()));
            return true;
        }

        // Selecting a piece
        else if(action instanceof HiveButtonAction) {
            if (hgs.getTurn() != getPlayerIdx(action.getPlayer())) {
                Logger.log("MakeMove", "Not Your Turn " + hgs.getTurn());
                return false;
            }

            //Can't place pieces that are not in the player's hand (ArrayList)
            if (!hgs.bugList.contains(((HiveButtonAction) action).gamePiece)) {
                return false;
            }

            boolean isBoardEmpty = true;

            for (int i = 0; i < hgs.board.length; i++)
            {
                for (int j = 0; j < hgs.board[i].length; j++)
                {
                    if (hgs.board[i][j] != HiveGameState.piece.EMPTY)
                    {
                        isBoardEmpty = false;
                    }
                }
            }

            if (isBoardEmpty)
            {
                for (int i = 0; i < hgs.board.length; i++)
                {
                    for (int j = 0; j < hgs.board[i].length; j++)
                    {
                        hgs.board[i][j] = HiveGameState.piece.TARGET;
                    }
                }
            }

            else
            {
                //Placing pieces can be done anywhere adjacent to another
                for (int row = 1; row < hgs.board.length - 1; row++)
                {
                    for (int col = 1; col < hgs.board[col].length - 1; col++)
                    {
                        hgs.makeTarget(row, col);
                    }
                }
            }

            return true;
        }
        //selected piece targets
        else if (action instanceof HiveSelectedPieceAction) {

            HiveSelectedPieceAction move = (HiveSelectedPieceAction) action;

            if (hgs.getTurn() == getPlayerIdx(action.getPlayer())) {
                //An empty spot can't be selected (there is no piece)
                if (hgs.board[move.row][move.col] == HiveGameState.piece.EMPTY) {
                    return false;
                }

                // Iterates through the board (except edges)
                for (int row = 1; row < hgs.board.length - 1; row++) {
                    for (int col = 1; col < hgs.board[col].length - 1; col++) {
                        if (row == move.row && col == move.col) {

                            // If current iteration is the selected piece,
                            // highlight potential legal spots
                            if(hgs.board[row][col] == HiveGameState.piece.WBEE ||
                                    hgs.board[row][col] == HiveGameState.piece.BBEE) {
                                hgs.highlightBee(hgs.board, row, col);
                            }
                            else if(hgs.board[row][col] == HiveGameState.piece.WBEETLE ||
                                    hgs.board[row][col] == HiveGameState.piece.BBEETLE) {
                                hgs.highlightBeetle(hgs.board, row, col);
                            }
                            else if(hgs.board[row][col] == HiveGameState.piece.WANT ||
                                    hgs.board[row][col] == HiveGameState.piece.BANT) {
                                hgs.highlightAnt(hgs.board, row, col);
                            }
                            else if(hgs.board[row][col] == HiveGameState.piece.WSPIDER ||
                                    hgs.board[row][col] == HiveGameState.piece.BSPIDER) {
                                hgs.highlightSpider(hgs.board, row, col);
                            }
                            else if(hgs.board[row][col] == HiveGameState.piece.WGHOPPER ||
                                    hgs.board[row][col] == HiveGameState.piece.BGHOPPER) {
                                hgs.highlightGHopper(hgs.board, row, col, 0);
                                hgs.highlightGHopper(hgs.board, row, col, 1);
                                hgs.highlightGHopper(hgs.board, row, col, 2);
                                hgs.highlightGHopper(hgs.board, row, col, 3);
                                hgs.highlightGHopper(hgs.board, row, col, 4);
                                hgs.highlightGHopper(hgs.board, row, col, 5);
                                hgs.highlightGHopperCleanUp(hgs.board, row, col);
                            }
                        }
                        else {
                            // Do nothing
                        }
                    }
                }
                return true;
            }
            return false;
        }
        // Resetting the board
        else if (action instanceof HiveResetBoardAction) {
            HiveResetBoardAction reset = (HiveResetBoardAction) action;
            //remove targets only
            if(reset.deselectTargets){
                hgs.resetTarget();
                return true;
            }
            //Iterate through board and make each spot empty
            for (int i = 0; i < hgs.board.length; i++)
            {
                for (int j = 0; j < hgs.board[i].length; j++)
                {
                    hgs.board[i][j] = HiveGameState.piece.EMPTY;
                }
            }

            //Reset turn count to 0
            hgs.resetTurnCount();

            //To make adding all bugs to the player
            //hands easier, clear it, then add the
            //appropriate quantity of each piece
            hgs.bugList.clear();

            hgs.addPieces(hgs, hgs.bugList);
        }
        return true;
    }
}
