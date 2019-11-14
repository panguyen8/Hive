package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.actionMessage.GameAction;

/**
 * Represents a local game, which is responsible for enforcing rules
 */
public class HiveLocalGame extends LocalGame {
    private HiveGameState hgs;

    public HiveLocalGame() {
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
        if (playerIdx == hgs.getTurn()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the game is over,
     * which is when a bee is surrounded
     * by other pieces
     *
     * @return: A message that states who wins,
     * which is blank if it is not game over
     */
    protected String checkIfGameOver() {
        // Check black bee
        if (hgs.getTurn() == 0) {
            if (checkBee(0)) {
                return "Player 0 wins.";
            }
        } else {
            if (checkBee(1)) {
                return "Player 1 wins.";
            }
        }

        return null;
    }

    /**
     * Checks if a player's bee is surrounded
     * (i.e. if all adjacent spots are full)
     *
     * @param player: ID of player whose bee is being checked
     * @return: True of surrounded, false otherwise
     */
    private boolean checkBee(int player) {
        HiveGameState.piece[][] board = hgs.getBoard();
        HiveGameState.piece beeToCheck;

        if (player == 0) {
            beeToCheck = HiveGameState.piece.BBEE;
        } else {
            beeToCheck = HiveGameState.piece.WBEE;
        }

        int occupiedSpaces = 0;
        int pieceX = 0;
        int pieceY = 0;

        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (board[row][col] == beeToCheck) {
                    pieceX = row;
                    pieceY = col;

                    // Check adjacent squares
                    for (int i = row - 1; i < row + 1; i++) {
                        for (int j = col - 1; j < col + 1; j++) {

                            //Error checking on array bounds
                            //This is currently unneeded as the board iteration
                            //prevents out of bounds errors
//                            if(i < 0 || j < 0 || i > board.length || j > board.length) {
//                                continue; // Goes to next iteration
//                            }

                            //Check if current iteration is the piece
                            //DUe to how hexagon board is implemented, the
                            //check looks like this


                            //---  ---  ---
                            //|X|  |*|  |*|
                            //---  ---  ---
                            //---  ---  ---
                            //|*|  |*|  |*|
                            //---  ---  ---
                            //---  ---  ---
                            //|X|  |*|  |*|
                            //---  ---  ---

                            //Where only the spaces with * are checked

                            if (board[i][j] == board[row][col] ||
                                    board[i][j] == board[row - 1][col - 1] ||
                                    board[i][j] == board[row + 1][col - 1]) {
                                // Do nothing
                            } else if (board[i][j] != null) {
                                occupiedSpaces++;
                            }
                        }
                    }
                }
            }
        }

        //Since squares have different amounts of adjacent spots,
        //it is necessary to check where queen bee is, then
        //check what the maximum number of adjacent squares is
        //If # of full spots is equal to that maximum, return true

        //Checks the corners, which have 3 adjacent spots
        if ((pieceX == 0 && pieceY == 0) ||
                (pieceX == board.length - 1 && pieceY == 0) ||
                (pieceX == board.length - 1 && pieceY == board.length - 1) ||
                (pieceY == board.length - 1 && pieceX == 0)) {
            if (occupiedSpaces == 3) {
                return true;
            }
        }

        //Make sure this edge checking is done
        //There are 5 adjacent spots for edge squares
        else if ((pieceX > 0 && pieceX < board.length - 1) ||
                (pieceY > 0 && pieceY < board.length - 1)
        ) {
            if (occupiedSpaces == 5) {
                return true;
            }
        }

        //Anywhere else has 8 adjacent spots
        else {
            if (occupiedSpaces == 8) {
                return true;
            }
        }

        return false;
    }

    /**
     * Makes a move based on whose turn it is
     *
     * @param action The move that the player has sent to the game
     * @return
     */
    protected boolean makeMove(GameAction action) {
        // Checks which type of action is being taken
        if (action instanceof HiveMoveAction) {
            HiveMoveAction move = (HiveMoveAction) action;

            HiveGameState.piece[][] board = hgs.getBoard();

            //A piece can only be moved to a spot with at least
            //one full adjacent space
            //Assume false
            boolean legal = false;

            //Iterate through surrounding spots, ignoring the piece
            //and 2 spots due to the board design
            for (int i = move.endRow - 1; i < move.endRow + 2; i++) {
                for (int j = move.endCol - 1; j < move.endCol + 2; j++) {

                    //Ignore certain spots
                    if (board[i][j] == board[move.endRow][move.endCol] ||
                            board[i][j] == board[move.endRow - 1][move.endCol - 1] ||
                            board[i][j] == board[move.endRow + 1][move.endCol - 1]) {
                        // Do nothing
                    }

                    else if (board[i][j] != null) {
                        legal = true;
                    }
                }
            }

            if (legal)
            {
                board[move.endRow][move.endCol] = board[move.startRow][move.startCol];
                board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
            }

        }
        else if (action instanceof HivePlacePieceAction) {
            //Declare action
            HivePlacePieceAction placement = (HivePlacePieceAction) action;

            HiveGameState.piece[][] board = hgs.getBoard();
            boolean legal = false;

            //Iterate through surrounding spots, ignoring the piece
            //and 2 spots due to the board design
            for (int i = placement.row - 1; i < placement.col + 2; i++) {
                for (int j = placement.col - 1; j < placement.col + 2; j++) {

                    //Ignore certain spots
                    if (board[i][j] == board[placement.row][placement.col] ||
                            board[i][j] == board[placement.row - 1][placement.col - 1] ||
                            board[i][j] == board[placement.row + 1][placement.col - 1]) {
                        // Do nothing
                    }

                    else if (board[i][j] != null) {
                        legal = true;
                    }
                }
            }

            //Sets piece specified in place piece onto the board
            if (board[placement.row][placement.col] == null && legal)
            {
                board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
            }

            else
            {
                //Print error message?
                return false;
            }
            hgs.bugList.remove(((HivePlacePieceAction) action).piece);
        }
        
        else if(action instanceof HiveSelectedPieceAction) {
            // Declare the type of action
            HiveSelectedPieceAction select = (HiveSelectedPieceAction) action;
            HiveGameState.piece[][] board = hgs.getBoard();

            // Iterate through board to find selected piece's location
            for(int row = 0; row < board.length; row++) {
                for(int col = 0; col < board.length; col++) {
                    if(board[row][col] == select.piece) {
                        // Insert highlight code here

                    }
                }
            }
        }

        // White to move
        if (hgs.getTurn() == 0) {
            hgs.setTurn(1);
        }
        // Black to move
        else if (hgs.getTurn() == 1) {
            hgs.setTurn(0);
        }
        return true;
    }
}
