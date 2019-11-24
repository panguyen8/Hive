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
        /*
        //Determine if one or both players have bee surrounded
        boolean whiteWins = checkBee(0);
        boolean blackWins = checkBee(1);

        //Draw if both are somehow surrounded at the same time.
        if (whiteWins && blackWins)
        {
            return "Draw.";
        }

        //Standard cases
        else if (!whiteWins)
        {
            return "Game! This game's winner is... " + players[0] + "!";
        }

        else if (!blackWins)
        {
            return "Game! This game's winner is... " + players[1] + "!";
        }

         */
        return null;
    }

    /**
     * Checks if a player's bee is surrounded
     * (i.e. if all adjacent spots are full)
     *
     * @param player: ID of player whose bee is being checked
     * @return true of surrounded, false otherwise
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

        int occupiedSpaces = 0;
        int pieceX = 0;
        int pieceY = 0;

        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[row].length - 1; col++) {
                if (board[row][col] == beeToCheck) {
                    pieceX = row;
                    pieceY = col;
                }
            }
        }

       if (hgs.canPlace(pieceX, pieceY) == false) {
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

            if (hgs.getTurn() == 0) {
                boolean legal = false;

                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ) {
                    return false;
                }

                if (hgs.canPlace(move.endRow, move.endCol))
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }

                hgs.setTurn(1);
            }
            else {
                boolean legal = false;

                if(hgs.board[move.endRow][move.endCol] != HiveGameState.piece.EMPTY ) {
                    return false;
                }

                if (hgs.canPlace(move.endRow, move.endCol))
                {
                    hgs.board[move.endRow][move.endCol] = hgs.board[move.startRow][move.startCol];
                    hgs.board[move.startRow][move.startCol] = HiveGameState.piece.EMPTY;
                }
                hgs.setTurn(0);
            }
        }
        // Placing piece
        else if (action instanceof HivePlacePieceAction) {
            HivePlacePieceAction placement = (HivePlacePieceAction) action;

            if (!hgs.bugList.contains(((HivePlacePieceAction) action).piece)){
                return false;
            }


            if (hgs.getTurn() == 0) {
                if(hgs.canPlace(placement.row, placement.col)) {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);

                } else {
                    return false;
                }
                hgs.setTurn(1);
                return true;
            }
            else {
                if(hgs.canPlace(placement.row, placement.col)) {
                    hgs.board[placement.row][placement.col] = ((HivePlacePieceAction) action).piece;
                    hgs.bugList.remove(((HivePlacePieceAction) action).piece);
                } else {
                    return false;
                }
                hgs.setTurn(0);
                return true;
            }
        }
        // Selecting a piece
        else if(action instanceof HiveButtonAction) {
            return false;
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
