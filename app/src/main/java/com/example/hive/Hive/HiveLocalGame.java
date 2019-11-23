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
     * @return a message that states who wins,
     * or nothing if a win condition has not been met
     */
    protected String checkIfGameOver() {
        if(checkBee(0)) {
            return "Game! This game's winner is... " + playerNames[1] + "!";
        }

        else if(checkBee(1)) {
            return "Game! This game's winner is... " + playerNames[0] + "!";
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
        HiveGameState.piece beeToCheck;

        //Get bee to check
        if (player == 0) {
            beeToCheck = HiveGameState.piece.WBEE;
        }
        else {
            beeToCheck = HiveGameState.piece.BBEE;
        }

        int occupiedSpaces = 0;
        int pieceX = 0;
        int pieceY = 0;

        for (int row = 1; row < hgs.board.length - 1; row++) {
            for (int col = 1; col < hgs.board[row].length - 1; col++) {
                if (hgs.board[row][col] == beeToCheck) {
                    pieceX = row;
                    pieceY = col;

                    // Check adjacent squares
                    for (int i = pieceX - 1; i < pieceX + 2; i++) {
                        for (int j = pieceY - 1; j < pieceY + 2; j++) {
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

                            if (pieceX % 2 == 1) {
                                if (hgs.board[i][j] == hgs.board[pieceX][pieceY] ||
                                        hgs.board[i][j] == hgs.board[pieceX - 1][pieceY - 1] ||
                                        hgs.board[i][j] == hgs.board[pieceX + 1][pieceY - 1]) {
                                    // Do nothing

                                }
                                else if (hgs.board[i][j] != HiveGameState.piece.TARGET) {
                                    occupiedSpaces++;
                                }
                            }
                            else {
                                if (hgs.board[i][j] == hgs.board[pieceX][pieceY] ||
                                        hgs.board[i][j] == hgs.board[pieceX - 1][pieceY + 1] ||
                                        hgs.board[i][j] == hgs.board[pieceX + 1][pieceY + 1]) {
                                    // Do nothing
                                }
                                else if (hgs.board[i][j] != HiveGameState.piece.TARGET) {
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
                (pieceX == hgs.board.length - 1 && pieceY == 0) ||
                (pieceX == hgs.board.length - 1 && pieceY == hgs.board.length - 1) ||
                (pieceY == hgs.board.length - 1 && pieceX == 0)) {
            if (occupiedSpaces == 3) {
                return true;
            }
        }

//        // Edges have 3-4 spots
//        else if ((pieceX > 0 && pieceX < board.length - 1) ||
//                (pieceY > 0 && pieceY < board.length - 1)) {
//            if (occupiedSpaces == 4) {
//                return true;
//            }
//        }

        //Anywhere else has 6 adjacent spots
        else {
            if (occupiedSpaces == 6) {
                return true;
            }
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

            if (hgs.getTurn() == 0) {
                // Cannot move to occupied space or move opponent's pieces
                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BBEE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BANT ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BBEETLE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BGHOPPER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.BSPIDER) {
                    return false;
                }

                boolean legal = false;
                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = move.endRow - 1; i < move.endRow + 2; i++) {
                    for (int j = move.endCol - 1; j < move.endCol + 2; j++) {

                        if (move.endRow % 2 == 1) {
                            //Ignore certain spots
                            if ((i == move.endRow + 1 && j == move.endCol - 1) ||
                                    (i == move.endRow - 1 && j == move.endCol - 1) ||
                                    (i == move.endRow && j == move.endCol)) {
                                continue;
                            }

                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                        else {
                            if ((i == move.endRow + 1 && j == move.endCol + 1) ||
                                    (i == move.endRow - 1 && j == move.endCol + 1) ||
                                    (i == move.endRow && j == move.endCol)) {
                                continue;
                            }

                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                // Moves piece to new spot and sets original spot to empty
                if (legal)
                {
                    hgs.setPiece(move.endRow, move.endCol, hgs.board[move.startRow][move.startCol]);
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }
                hgs.setTurn(1);
            }
            else {
                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WBEE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WANT ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WBEETLE ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WGHOPPER ||
                        hgs.board[move.startRow][move.startCol] == HiveGameState.piece.WSPIDER) {
                    return false;
                }

                boolean legal = false;
                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = move.endRow - 1; i < move.endRow + 2; i++) {
                    for (int j = move.endCol - 1; j < move.endCol + 2; j++) {

                        //Ignore certain spots
                        if (move.endRow % 2 == 1) {
                            if ((i == move.endRow + 1 && j == move.endCol - 1) ||
                                    (i == move.endRow - 1 && j == move.endCol - 1) ||
                                    (i == move.endRow && j == move.endCol)) {
                                continue;
                            }

                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                        else {
                            if ((i == move.endRow + 1 && j == move.endCol + 1) ||
                                    (i == move.endRow - 1 && j == move.endCol + 1) ||
                                    (i == move.endRow && j == move.endCol)) {
                                continue;
                            }

                            if (hgs.board[i][j] != HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                // Moves piece to new spot and sets original spot to empty
                if (legal)
                {
                    hgs.setPiece(move.endRow, move.endCol, hgs.board[move.startRow][move.startCol]);
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }
                hgs.setTurn(0);
            }
        }
        // Placing piece
        else if (action instanceof HivePlacePieceAction) {
            HivePlacePieceAction placement = (HivePlacePieceAction) action;
            if (hgs.getTurn() == 0) {
                boolean legal = false;

                // Cannot place piece if it is not in hand
                if (!hgs.bugList.contains(((HivePlacePieceAction) action).piece)){
                    return false;
                }

                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design
                for (int i = placement.row - 1; i < placement.row + 2; i++) {
                    for (int j = placement.col - 1; j < placement.col + 2; j++) {

                        if (placement.row % 2 == 1) {
                            //Ignore certain spots
                            if ((i == placement.row + 1 && j == placement.col - 1) ||
                                    (i == placement.row - 1 && j == placement.col - 1) ||
                                    (i == placement.row && j == placement.col)) {
                                continue;
                            }

                            if (hgs.board[i][j] == HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                        else {
                            if ((i == placement.row + 1 && j == placement.col + 1) ||
                                    (i == placement.row - 1 && j == placement.col + 1) ||
                                    (i == placement.row && j == placement.col)) {
                                continue;
                            }

                            if (hgs.board[i][j] == HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                //Sets piece specified in place piece onto the board
                if (hgs.board[placement.row][placement.col] == HiveGameState.piece.EMPTY && legal)
                {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                }
                else
                {
                    //Print error message?
                    return false;
                }
                hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                hgs.setTurn(1);
            }
            else {
                boolean legal = false;
                //Iterate through surrounding spots, ignoring the piece
                //and 2 spots due to the board design

                if (!hgs.bugList.contains(((HivePlacePieceAction) action).piece)){
                    return false;
                }

                for (int i = placement.row - 1; i < placement.row + 2; i++) {
                    for (int j = placement.col - 1; j < placement.col + 2; j++) {
                        if(i < 0 || j < 0 || i > 10 || j > 10){
                            break;
                        }
                        //Ignore certain spots
                        if (placement.row%2 == 1) {
                            if ((i == placement.row + 1 && j == placement.col - 1) ||
                                    (i == placement.row - 1 && j == placement.col - 1) ||
                                    (i == placement.row && j == placement.col)) {
                                continue;
                            }

                            if (hgs.board[i][j] == HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        } else {
                            if ((i == placement.row + 1 && j == placement.col + 1) ||
                                    (i == placement.row - 1 && j == placement.col + 1)||
                                    (i == placement.row && j == placement.col)) {
                                continue;
                            }

                            if (hgs.board[i][j] == HiveGameState.piece.EMPTY) {
                                legal = true;
                            }
                        }
                    }
                }

                //Sets piece specified in place piece onto the board
                if (hgs.board[placement.row][placement.col] == HiveGameState.piece.EMPTY && legal)
                {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                    hgs.setTurn(0);
                    return true;
                }
                else
                {
                    //Print error message?
                    return false;
                }
            }
            return false;
        }
        // Clicking a button
        else if(action instanceof HiveButtonAction) {
            // Iterate through board to find selected piece's location
            for(int row = 1; row < hgs.board.length - 1; row++) {
                for (int col = 1; col < hgs.board[row].length - 1; col++) {
                    if (col % 2 == 0) {
                        if (hgs.board[row][col] != HiveGameState.piece.EMPTY) {
                            hgs.board[row + 1][col] = HiveGameState.piece.TARGET;
                            hgs.board[row - 1][col + 1] = HiveGameState.piece.TARGET;
                            hgs.board[row][col + 1] = HiveGameState.piece.TARGET;
                            hgs.board[row][col - 1] = HiveGameState.piece.TARGET;
                            hgs.board[row - 1][col] = HiveGameState.piece.TARGET;
                            hgs.board[row - 1][col - 1] = HiveGameState.piece.TARGET;
                            return true;
                        }
                    } else {
                        if (hgs.board[row][col] != HiveGameState.piece.EMPTY) {
                            hgs.board[row + 1][col] = HiveGameState.piece.TARGET;
                            hgs.board[row + 1][col + 1] = HiveGameState.piece.TARGET;
                            hgs.board[row][col + 1] = HiveGameState.piece.TARGET;
                            hgs.board[row][col - 1] = HiveGameState.piece.TARGET;
                            hgs.board[row - 1][col] = HiveGameState.piece.TARGET;
                            hgs.board[row + 1][col - 1] = HiveGameState.piece.TARGET;
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        // Resetting the board
        else if (action instanceof HiveResetBoardAction) {
            for (int i = 0; i < hgs.board.length; i++)
            {
                for (int j = 0; j < hgs.board[i].length; j++)
                {
                    hgs.board[i][j] = HiveGameState.piece.EMPTY;
                }
            }

            hgs.board[5][5] = HiveGameState.piece.WBEE;
            hgs.board[4][6] = HiveGameState.piece.BBEE;

            // Resets players' hands
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
        return true;
    }
}
