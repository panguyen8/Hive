/**
 *@author Marc Hilderbrand
 *@author Erik Liu
 *@author Phuocan Nguyen
 *@author Samuel Nguyen
 *@author Stephen Nguyen
 *
 *@version 10/22/19
 */

package com.example.hive.Hive;

//Comment by Stephen
//I assume this is used in network play, delete if it's unnecessary
import android.net.wifi.p2p.WifiP2pGroup;

import com.example.hive.game.infoMessage.GameState;

import java.util.ArrayList;

public class HiveGameState extends GameState {
    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;

    //the declaration of the board
    public piece[][] board = new piece[20][20];

    //int variable to tell whose turn it is
    //If 1, white moves, if 0, black moves
    //Removed static for now, not sure if it's needed
    //Let me (Stephen) know if it is
    private int turn = 1;  // Edit by Samuel Nguyen

    //Represents how many total pieces each player has
    private int player0Pieces;
    private int player1Pieces;

    public ArrayList<piece> bugList = new ArrayList<>();
    enum piece {
        BBEE, BSPIDER, BANT, BGHOPPER, BBEETLE, WBEE, WSPIDER, WANT, WGHOPPER, WBEETLE, EMPTY, TARGET;
    }

    //Basic constructor
    public HiveGameState() {

        //1 BBEE, 2 BSPIDERS, 3 BANT, 3 BGHOPPER, 2,BBEETLE
        bugList.add(piece.BBEE);
        bugList.add(piece.BSPIDER);
        bugList.add(piece.BSPIDER);
        bugList.add(piece.BANT);
        bugList.add(piece.BANT);
        bugList.add(piece.BANT);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BBEETLE);
        bugList.add(piece.BBEETLE);

        //1 BBEE, 2 WSPIDERS, 3 WANT, 3 WGHOPPER, 2,WBEETLE
        bugList.add(piece.WBEE);
        bugList.add(piece.WSPIDER);
        bugList.add(piece.WSPIDER);
        bugList.add(piece.WANT);
        bugList.add(piece.WANT);
        bugList.add(piece.WANT);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WBEETLE);
        bugList.add(piece.WBEETLE);

        this.turn = WHITE_TURN; // White goes first?
        this.player0Pieces = 11;
        this.player1Pieces = 11;

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                board[i][j] = HiveGameState.piece.EMPTY;
            }
        }

        //board[5][5] = HiveGameState.piece.WBEE;
        //board[4][6] = HiveGameState.piece.BBEE;
        /* this is a full board for the ai to test movements since it places all the pieces first
        board[3][4] = HiveGameState.piece.BANT;
        board[4][4] = HiveGameState.piece.BANT;
        board[5][6] = HiveGameState.piece.BANT;
        board[5][2] = HiveGameState.piece.BSPIDER;
        board[5][1] = HiveGameState.piece.BSPIDER;
        board[3][3] = HiveGameState.piece.BGHOPPER;
        board[4][5] = HiveGameState.piece.BGHOPPER;
        board[4][2] = HiveGameState.piece.BGHOPPER;
        board[6][5] = HiveGameState.piece.BBEETLE;
        board[6][6] = HiveGameState.piece.BBEETLE;

         */
    }

    //Copy constructor (Stephen)
    // Some edits by Samuel Nguyen
    public HiveGameState(HiveGameState hgs) {
        this.turn = hgs.turn;

        //Copies each board index/cell
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                this.board[i][j] = hgs.board[i][j];
            }
        }
        this.bugList = hgs.bugList;
    }

    /**
     * Gets the id of whoever's move it is
     * @return id of player to move
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Sets the player to move
     * @param id: id of player that is being set to move
     */
    public void setTurn(int id) {
        this.turn = id;
    }

    /**
     * Checks to see if a piece can be placed at a certain spot
     * A piece beyond the first must be placed next to one already on the board
     *
     * @param row: the desired spot's row
     * @param col: the desired spot's column
     * @return true if able, false otherwise
     */
    public boolean canPlace(int row, int col) {
        int count = 0;
        if (col % 2 == 0) {
            if (board[row + 1][col] == piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col + 1] == piece.EMPTY) {
                count++;
            }
            if (board[row][col + 1] == piece.EMPTY) {
                count++;
            }
            if (board[row][col - 1] == piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col] == piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col - 1] == piece.EMPTY) {
                count++;
            }
        } else {
            if (board[row + 1][col] == piece.EMPTY) {
                count++;
            }
            if (board[row + 1][col + 1] == piece.EMPTY) {
                count++;
            }
            if (board[row][col + 1] == piece.EMPTY) {
                count++;
            }
            if (board[row][col - 1] == piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col] == piece.EMPTY) {
                count++;
            }
            if (board[row + 1][col - 1] == piece.EMPTY) {
                count++;
            }
        }
        if (count == 6) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks to see if a spot is surrounded
     *
     * @param row: the spot's row
     * @param col: the spot's col
     * @return true if so, false otherwise
     */
    public boolean checkSurround(int row, int col) {
        int count = 0;
        if (col % 2 == 0) {
            if (board[row + 1][col] != piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col + 1] != piece.EMPTY) {
                count++;
            }
            if (board[row][col + 1] != piece.EMPTY) {
                count++;
            }
            if (board[row][col - 1] != piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col] != piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col - 1] != piece.EMPTY) {
                count++;
            }
        } else {
            if (board[row + 1][col] != piece.EMPTY) {
                count++;
            }
            if (board[row + 1][col + 1] != piece.EMPTY) {
                count++;
            }
            if (board[row][col + 1] != piece.EMPTY) {
                count++;
            }
            if (board[row][col - 1] != piece.EMPTY) {
                count++;
            }
            if (board[row - 1][col] != piece.EMPTY) {
                count++;
            }
            if (board[row + 1][col - 1] != piece.EMPTY) {
                count++;
            }
        }
        if (count == 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Marks spots around a given spot as potential targets
     *
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void makeTarget(int row, int col) {
        if (board[row][col] != piece.EMPTY && board[row][col] != piece.TARGET) {
            if (col % 2 == 0) {
                if (board[row + 1][col] == piece.EMPTY) {
                    board[row + 1][col] = piece.TARGET;
                }
                if (board[row - 1][col + 1] == piece.EMPTY) {
                    board[row - 1][col + 1] = piece.TARGET;
                }
                if (board[row][col + 1] == piece.EMPTY) {
                    board[row][col + 1] = piece.TARGET;
                }
                if (board[row][col - 1] == piece.EMPTY) {
                    board[row][col - 1] = piece.TARGET;
                }
                if (board[row - 1][col] == piece.EMPTY) {
                    board[row - 1][col] = piece.TARGET;
                }
                if (board[row - 1][col - 1] == piece.EMPTY) {
                    board[row - 1][col - 1] = piece.TARGET;
                }
            } else {
                if (board[row + 1][col] == piece.EMPTY) {
                    board[row + 1][col] = piece.TARGET;
                }
                if (board[row + 1][col + 1] == piece.EMPTY) {
                    board[row + 1][col + 1] = piece.TARGET;
                }
                if (board[row][col + 1] == piece.EMPTY) {
                    board[row][col + 1] = piece.TARGET;
                }
                if (board[row][col - 1] == piece.EMPTY) {
                    board[row][col - 1] = piece.TARGET;
                }
                if (board[row - 1][col] == piece.EMPTY) {
                    board[row - 1][col] = piece.TARGET;
                }
                if (board[row + 1][col - 1] == piece.EMPTY) {
                    board[row + 1][col - 1] = piece.TARGET;
                }
            }
        }
    }

    public void resetTarget() {
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 11; x++) {
                if (board[x][y] == piece.TARGET) {
                    board[x][y] = piece.EMPTY;
                }
            }
        }
    }


    public piece[][] getBoard() {
        return this.board;
    }

    public ArrayList<piece> getBugList() { return bugList;}

    public HiveGameState.piece getPiece(int x, int y) {
        return board[x][y];
    }

    public void setPiece(int x, int y, HiveGameState.piece piece) {
        board[x][y] = piece;
    }
}
