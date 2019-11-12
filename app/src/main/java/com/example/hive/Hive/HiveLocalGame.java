package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.actionMessage.GameAction;
// Started by Samuel Nguyen, add your name if you edit this
public class HiveLocalGame extends LocalGame {
    private HiveGameState hgs;

    public HiveLocalGame() {
        hgs = new HiveGameState();
    }

    protected void sendUpdatedStateTo(GamePlayer p) {
        HiveGameState copy = new HiveGameState(hgs);
        p.sendInfo(copy);
    }

    protected boolean canMove(int playerIdx) {
        if(playerIdx == hgs.getTurn()) {
            return true;
        }
        return false;
    }

    protected String checkIfGameOver() {
        HiveGameState.piece[][] board = hgs.getBoard();
        // Check black bee
        if(hgs.getTurn() == 0) {
            if (checkBee(0))
            {
                return "Player 0 wins.";
            }
        }

        else
        {
            if (checkBee(1))
            {
                return "Player 1 wins.";
            }
        }

        return null;
    }




    private boolean checkBee(int player)
    {
        HiveGameState.piece[][] board = hgs.getBoard();
        HiveGameState.piece beeToCheck;

        if (player == 0)
        {
            beeToCheck = HiveGameState.piece.BBEE;
        }

        else
        {
            beeToCheck = HiveGameState.piece.WBEE;
        }

        int occupiedSpaces = 0;
        int pieceX = 0;
        int pieceY = 0;

        for(int row = 0; row < board.length; row++) {
            for(int col =0; col < board[row].length; col++) {
                if(board[row][col] == beeToCheck) {
                    pieceX = row;
                    pieceY = col;

                    // Check adjacent squares
                    for(int i = row - 1; i < row + 1; i++) {
                        for(int j = col - 1; j < col + 1; j++) {
                            //Error checking on array bounds
                            if(i < 0 || j < 0 || i > board.length || j > board.length) {
                                continue; // Goes to next iteration
                            }

                            if(board[i][j] == board[row][col]) {
                                // Do nothing
                            }

                            else if(board[i][j] != null) {
                                occupiedSpaces++;
                            }
                        }
                    }
                }
            }
        }

        // Fix if necessary
        if(pieceX == 0 || pieceX == board.length - 1 || pieceY == 0 ||
                pieceY == board.length - 1) {
            if(occupiedSpaces == 3) {
                return true;
            }

            if((pieceX > 0 && pieceX < board.length - 1) ||
                    (pieceY > 0 && pieceY < board.length - 1)) {
                if(occupiedSpaces == 5) {
                    return true;
                }
            }
        }
        else {
            if(occupiedSpaces == 8) {
                return true;
            }
        }

        return false;
    }




    protected boolean makeMove(GameAction action) {
        // White to move
        if(hgs.getTurn() == 0) {
            hgs.setTurn(1);
        }
        // Black to move
        else if(hgs.getTurn() == 1) {
            hgs.setTurn(0);
        }
        return true;
    }
}
