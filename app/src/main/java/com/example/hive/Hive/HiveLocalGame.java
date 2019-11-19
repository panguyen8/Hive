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
                            //Due to how hexagon board is implemented, the
                            //check looks like this, where the piece is the
                            //center space

                            //---  ---  ---
                            //|X|  |*|  |*|
                            //---  ---  ---
                            //---  ---  ---
                            //|*|  |X|  |*|
                            //---  ---  ---
                            //---  ---  ---
                            //|X|  |*|  |*|
                            //---  ---  ---

                            //Where only the spaces with * are checked
                            if (row%2 == 1) {
                                if (board[i][j] == board[row][col] ||
                                        board[i][j] == board[row - 1][col - 1] ||
                                        board[i][j] == board[row + 1][col - 1]) {
                                    // Do nothing
                                } else if (board[i][j] != null) {
                                    occupiedSpaces++;
                                }
                            } else {
                                if (board[i][j] == board[row][col] ||
                                        board[i][j] == board[row - 1][col + 1] ||
                                        board[i][j] == board[row + 1][col + 1]) {
                                    // Do nothing
                                } else if (board[i][j] != null) {
                                    occupiedSpaces++;
                                }
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

            if (hgs.getTurn() == 0) {
                boolean legal = false;

                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ) {
                    return false;
                }

                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = move.endRow - 1; i < move.endRow + 2; i++) {
                    for (int j = move.endCol - 1; j < move.endCol + 2; j++) {

                        if (move.endRow%2 == 1) {
                            //Ignore certain spots
                            if (i == move.endRow + 1 && j == move.endCol - 1) {
                                continue;
                            } else if (i == move.endRow - 1 && j == move.endCol - 1) {
                                continue;
                            } else if (i == move.endRow && j == move.endCol) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        } else {
                            if (i == move.endRow + 1 && j == move.endCol + 1) {
                                continue;
                            } else if (i == move.endRow - 1 && j == move.endCol + 1) {
                                continue;
                            } else if (i == move.endRow && j == move.endCol) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                if (legal)
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }

                hgs.setTurn(1);
            } else {
                boolean legal = false;

                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ) {
                    return false;
                }

                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = move.endRow - 1; i < move.endRow + 2; i++) {
                    for (int j = move.endCol - 1; j < move.endCol + 2; j++) {

                        //Ignore certain spots
                        //Ignore certain spots
                        if (move.endRow%2 == 1) {
                            //Ignore certain spots
                            if (i == move.endRow + 1 && j == move.endCol - 1) {
                                continue;
                            } else if (i == move.endRow - 1 && j == move.endCol - 1) {
                                continue;
                            } else if (i == move.endRow && j == move.endCol) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        } else {
                            if (i == move.endRow + 1 && j == move.endCol + 1) {
                                continue;
                            } else if (i == move.endRow - 1 && j == move.endCol + 1) {
                                continue;
                            } else if (i == move.endRow && j == move.endCol) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                if (legal)
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }

                hgs.setTurn(0);
            }
        }
        else if (action instanceof HivePlacePieceAction) {
            //Declare action
            HivePlacePieceAction placement = (HivePlacePieceAction) action;
            if (hgs.getTurn() == 0) {
                boolean legal = false;

                if (!hgs.bugList.contains(((HivePlacePieceAction) action).piece)){
                    return false;
                }
                hgs.bugList.remove(((HivePlacePieceAction) action).piece);


                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = placement.row - 1; i < placement.row + 2; i++) {
                    for (int j = placement.col - 1; j < placement.col + 2; j++) {

                        if (placement.row%2 == 1) {
                            //Ignore certain spots
                            if (i == placement.row + 1 && j == placement.col - 1) {
                                continue;
                            } else if (i == placement.row - 1 && j == placement.col - 1) {
                                continue;
                            } else if (i == placement.row && j == placement.col) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        } else {
                            if (i == placement.row + 1 && j == placement.col + 1) {
                                continue;
                            } else if (i == placement.row - 1 && j == placement.col + 1) {
                                continue;
                            } else if (i == placement.row && j == placement.col) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                //Sets piece specified in place piece onto the board
                if (hgs.board[placement.row][placement.col] == HiveGameState.piece.EMPTY && legal)
                {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                } else
                {
                    //Print error message?
                    return false;
                }


                hgs.setTurn(1);
            } else {
                boolean legal = false;
                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design

                if (!hgs.bugList.contains(((HivePlacePieceAction) action).piece)){
                    return false;
                }
                hgs.bugList.remove(((HivePlacePieceAction) action).piece);



                for (int i = placement.row - 1; i < placement.row + 2; i++) {
                    for (int j = placement.col - 1; j < placement.col + 2; j++) {

                        //Ignore certain spots
                        if (placement.row%2 == 1) {
                            //Ignore certain spots
                            if (i == placement.row + 1 && j == placement.col - 1) {
                                continue;
                            } else if (i == placement.row - 1 && j == placement.col - 1) {
                                continue;
                            } else if (i == placement.row && j == placement.col) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        } else {
                            if (i == placement.row + 1 && j == placement.col + 1) {
                                continue;
                            } else if (i == placement.row - 1 && j == placement.col + 1) {
                                continue;
                            } else if (i == placement.row && j == placement.col) {
                                continue;
                            }
                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                //Sets piece specified in place piece onto the board
                if (hgs.board[placement.row][placement.col] == HiveGameState.piece.EMPTY && legal)
                {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                } else
                {
                    //Print error message?
                    return false;
                }
                hgs.setTurn(0);
            }
            return true;
        }

        else if(action instanceof HiveSelectedPieceAction) {
            // Declare the type of action
            HiveSelectedPieceAction select = (HiveSelectedPieceAction) action;
            HiveGameState.piece[][] board = hgs.getBoard();

            // Iterate through board to find selected piece's location
            for(int row = 0; row < board.length; row++) {
                for(int col = 0; col < board.length; col++) {


                    if(board[row][col] != HiveGameState.piece.EMPTY) {
                        for(int row2 = 0; row2 < board.length; row2++) {
                            for(int col2 = 0; col2 < board.length; col2++) {

                            }
                        }
                    }
                }
            }
            return true;
        } else if (action instanceof HiveResetBoardAction) {
            for (int i = 0; i < hgs.board.length; i++)
            {
                for (int j = 0; j < hgs.board[i].length; j++)
                {
                    hgs.board[i][j] = HiveGameState.piece.EMPTY;
                }
            }

            hgs.board[5][5] = HiveGameState.piece.WBEE;
            hgs.board[4][6] = HiveGameState.piece.BBEE;

            hgs.bugList.clear();

            //1 BBEE, 2 BSPIDERS, 3 BANT, 3 BGHOPPER, 2,BBEETLE
            //bugList.add(piece.BBEE);
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
            //bugList.add(piece.WBEE);
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

        /*
        // White to move
        if (hgs.getTurn() == 0) {
            hgs.setTurn(1);
        }
        // Black to move
        else if (hgs.getTurn() == 1) {
            hgs.setTurn(0);
        }
         */
        return true;
    }
}
