package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.actionMessage.GameAction;
import com.example.hive.game.utilities.Logger;

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
        if(checkBee(0)) {
            return "Game! This game's winner is... " + playerNames[0] + "!";
        }

        if(checkBee(1)) {
            return "Game! This game's winner is... " + playerNames[1] + "!";
        }
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
                    // Move below into methods above?
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

                    // Move below into methods above?
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
            if (hgs.getTurn() == 0) {
                if (hgs.getTurnCount() == 0) {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);

                    hgs.addTurnToCount();
                    hgs.setTurn(1);
                    //Logger.log("turnCount", Integer.toString(hgs.getTurnCount()));

                    return true;
                }
            } else {
                if (hgs.getTurnCount() == 0) {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);

                    hgs.addTurnToCount();
                    hgs.setTurn(0);
                    //Logger.log("turnCount", Integer.toString(hgs.getTurnCount()));

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
            //A piece can only be selected if it is in the ArrayList
            //of unplayed pieces
            if (hgs.getTurn() != getPlayerIdx(action.getPlayer())) {
                Logger.log("MakeMove", "Not Your Turn " + hgs.getTurn());
                return false;
            }
            if (!hgs.bugList.contains(((HiveButtonAction) action).gamePiece)) {
                return false;
            }

            for (int row = 1; row < hgs.board.length - 1; row++)
            {
                for (int col = 1; col < hgs.board[col].length - 1; col++)
                {
                    hgs.makeTarget(row, col);
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

                for (int row = 1; row < hgs.board.length - 1; row++) {
                    for (int col = 1; col < hgs.board[col].length - 1; col++) {
                        if (row == move.row && col == move.col) {
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
                                    hgs.highlightGHopper(hgs.board, row, col);
                                }

                            }
                        } else {
                            // DO nothing
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

            //hgs.board[5][5] = HiveGameState.piece.WBEE;
            //hgs.board[4][6] = HiveGameState.piece.BBEE;

            //Reset turn count to 0
            hgs.resetTurnCount();

            //To make adding all bugs to the player
            //hands easier, clear it, then add the
            //appropriate quantity of each piece
            hgs.bugList.clear();

            //1 BBEE, 2 BSPIDERS, 3 BANT, 3 BGHOPPER, 2,BBEETLE
            hgs.bugList.add(HiveGameState.piece.BBEE);
            hgs.bugList.add(HiveGameState.piece.BSPIDER);
            hgs.bugList.add(HiveGameState.piece.BSPIDER);
            hgs.bugList.add(HiveGameState.piece.BANT);
            hgs.bugList.add(HiveGameState.piece.BANT);
            hgs.bugList.add(HiveGameState.piece.BANT);
            hgs.bugList.add(HiveGameState.piece.BGHOPPER);
            hgs.bugList.add(HiveGameState.piece.BGHOPPER);
            hgs.bugList.add(HiveGameState.piece.BGHOPPER);
            hgs.bugList.add(HiveGameState.piece.BBEETLE);
            hgs.bugList.add(HiveGameState.piece.BBEETLE);

            //1 BBEE, 2 WSPIDERS, 3 WANT, 3 WGHOPPER, 2,WBEETLE
            hgs.bugList.add(HiveGameState.piece.WBEE);
            hgs.bugList.add(HiveGameState.piece.WSPIDER);
            hgs.bugList.add(HiveGameState.piece.WSPIDER);
            hgs.bugList.add(HiveGameState.piece.WANT);
            hgs.bugList.add(HiveGameState.piece.WANT);
            hgs.bugList.add(HiveGameState.piece.WANT);
            hgs.bugList.add(HiveGameState.piece.WGHOPPER);
            hgs.bugList.add(HiveGameState.piece.WGHOPPER);
            hgs.bugList.add(HiveGameState.piece.WGHOPPER);
            hgs.bugList.add(HiveGameState.piece.WBEETLE);
            hgs.bugList.add(HiveGameState.piece.WBEETLE);
        }
        return true;
    }

    // Below methods are helpers that dictate movement of each piece

    /**
     * Checks the spots that are legal for a bee to move
     */
    public void moveBee(HiveGameState.piece[][] board, HiveGameState.piece piece,
                        int startX, int startY, int endX, int endY) {
//        if(hgs.canPlace(endX, endY)) {
//
//        }
    }

    /**
     * Checks spots for beetle
     */
    public void moveBeetle(HiveGameState.piece[][] board, HiveGameState.piece piece,
                           int startX, int startY, int endX, int endY) {

    }

    /**
     * Checks spots for ants
     */
    public void moveAnt(HiveGameState.piece[][] board, HiveGameState.piece piece,
                        int startX, int startY, int endX, int endY) {

    }

    /**
     * Checks spots for spiders
     */
    public void moveSpider(HiveGameState.piece[][] board, HiveGameState.piece piece,
                           int startX, int startY, int endX, int endY) {

    }

    /**
     * Checks spots for grasshoppers
     */
    public void moveGHopper(HiveGameState.piece[][] board, HiveGameState.piece piece,
                            int startX, int startY, int endX, int endY) {

    }
}
