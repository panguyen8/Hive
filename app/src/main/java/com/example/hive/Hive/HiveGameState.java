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
    public piece[][] board = new piece[12][12];

    //int variable to tell whose turn it is
    //If 1, white moves, if 0, black moves
    //Removed static for now, not sure if it's needed
    //Let me (Stephen) know if it is
    private int turn;  // Edit by Samuel Nguyen
    private int turnCount;

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

        this.turnCount = 0;

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                board[i][j] = HiveGameState.piece.EMPTY;
            }
        }

    }

    //Copy constructor (Stephen)
    // Some edits by Samuel Nguyen
    public HiveGameState(HiveGameState hgs) {
        this.turn = hgs.turn;
        this.turnCount = hgs.turnCount;

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
            if(row + 1 > board.length || row - 1 < 0 ||
                    col + 1 > board.length || col - 1 < 0) {
                return false;
            }

            if (board[row + 1][col] != piece.EMPTY && board[row + 1][col] != piece.TARGET) {
                count++;
            }
            if (board[row - 1][col + 1] != piece.EMPTY && board[row - 1][col + 1] != piece.TARGET) {
                count++;
            }
            if (board[row][col + 1] != piece.EMPTY && board[row][col + 1] != piece.TARGET) {
                count++;
            }
            if (board[row][col - 1] != piece.EMPTY && board[row][col - 1] != piece.TARGET) {
                count++;
            }
            if (board[row - 1][col] != piece.EMPTY && board[row - 1][col] != piece.TARGET) {
                count++;
            }
            if (board[row - 1][col - 1] != piece.EMPTY && board[row - 1][col - 1]!= piece.TARGET) {
                count++;
            }
        } else {
            if(row + 1 > board.length || row - 1 < 0 ||
                    col + 1 > board.length || col - 1 < 0) {
                return false;
            }

            if (board[row + 1][col] != piece.EMPTY && board[row + 1][col] != piece.TARGET) {
                count++;
            }
            if (board[row + 1][col + 1] != piece.EMPTY && board[row + 1][col + 1] != piece.TARGET) {
                count++;
            }
            if (board[row][col + 1] != piece.EMPTY && board[row][col + 1] != piece.TARGET) {
                count++;
            }
            if (board[row][col - 1] != piece.EMPTY && board[row][col - 1] != piece.TARGET) {
                count++;
            }
            if (board[row - 1][col] != piece.EMPTY && board[row - 1][col] != piece.TARGET) {
                count++;
            }
            if (board[row + 1][col - 1] != piece.EMPTY && board[row + 1][col - 1] != piece.TARGET) {
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

    public boolean makePlace(int row, int col, HiveGameState.piece piece) {
        if (!bugList.contains(piece)){
            return false;
        }
        if (turnCount == 0) {
            return true;
        }
        if (board[row][col] == piece.TARGET) {
            return true;
        }
        return false;
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

    /* Helper methods below */

    /**
     * Highlights legal spots for the bee
     * The bee moves one square in any direction
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightBee(piece[][] board, int row, int col) {
        highlightSurrounding(board, row, col);
    }

    /**
     * Highlights legal spots for the beetle
     * Moves like the bee, but can also stack on top of others (not implemented yet)
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightBeetle(piece[][] board, int row, int col) {
        highlightSurrounding(board, row, col);
    }

    /**
     * Highlights legal spots for the ant
     * For now, it moves to any spot adjacent to another piece
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightAnt(piece[][] board, int row, int col) {
        for(int i = 1; i < board.length; i++) {
            for(int j = 1; j < board[i].length; j++) {
                if (board[i][j] != board[row][col])
                {
                    makeTarget(i, j);
                }
            }
        }
    }

    /**
     * Highlights legal spots for the spider
     * For now, it moves to any spot adjacent to another piece
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightSpider(piece[][] board, int row, int col) {
        highlightAnt(board, row, col);

        // Unmarks pieces not in the same row and col
        for(int i = 1; i < board.length; i++) {
            for(int j = 1; j < board[i].length; j++) {
                if (i != row + 3 && i != row - 3 && j != col - 3 &&
                        j != col - 3 && board[i][j] == piece.TARGET)
                {
                    board[i][j] = piece.EMPTY;
                }
            }
        }
    }

    /**
     * Highlights legal spots for the grasshopper
     * Moves in a straight or diagonal line
     *
     * First, all spots next to a piece are marked as targets
     * Then, if each spot's row and col are not the same as the grasshopper's current spot,
     * it is unmarked.
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightGHopper(piece[][] board, int row, int col) {
        // The ant highlights mark all spots next to any piece as a target
        highlightAnt(board, row, col);

        // Unmarks pieces not in the same row and col
        for(int i = 1; i < board.length; i++) {
            for(int j = 1; j < board[i].length; j++) {
                if (i != row && j != col && board[i][j] == piece.TARGET)
                {
                    board[i][j] = piece.EMPTY;
                }
            }
        }
    }

    public void highlightSurrounding(piece[][] board, int row, int col)
    {
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
        }
        else {
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

    public int getTurnCount() {
        return turnCount;
    }

    public void addTurnToCount() {
        turnCount = turnCount + 1;
    }

    public void resetTurnCount() {
        turnCount = 0;
    }
}
