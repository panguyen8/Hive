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

/**
 * Represents a GameState, which has a "current" state of a game and movement rules
 */
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

    //Represents a player's "hand" (what pieces are not on the board)
    public ArrayList<piece> bugList = new ArrayList<>();

    //Represents the pieces, as well as targets (for highlighting) and empty
    //(drawing all of the hexagons on startup was found to be too slow)
    enum piece {
        BBEE, BSPIDER, BANT, BGHOPPER, BBEETLE, WBEE, WSPIDER, WANT, WGHOPPER, WBEETLE, EMPTY, TARGET;
    }

    //Basic constructor
    public HiveGameState() {

        addPieces(this, bugList);

        this.turn = BLACK_TURN; // White goes first?
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
        this.board = new piece[12][12];
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
        }

        else {
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
            highlightSurrounding(board, row, col);
        }
    }

    /**
     * Checks to see if a piece can be placed at a given spot
     *
     * @param row: the desired spot's row
     * @param col: the desired spot's col
     * @param piece: the piece to move
     * @return true if possible, false otherwise
     */
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

    /**
     * Checks to see whether the move being made is possible
     *
     * @param row: the desired spot's row
     * @param col: the desired spot's col
     * @param piece: the piece to move
     * @return true if possible, false otherwise
     */
    public boolean makeMove(int row, int col, HiveGameState.piece piece) {
        if (board[row][col] == piece.TARGET) {
            return true;
        }
        return false;
    }

    /**
     * Removes all targets from the board, as targets are a piece enum
     */
    public void resetTarget() {
        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 11; x++) {
                if (board[x][y] == piece.TARGET) {
                    board[x][y] = piece.EMPTY;
                }
            }
        }
    }

    /**
     * Gets the current board
     *
     * @return the current board
     */
    public piece[][] getBoard() {
        return this.board;
    }

    /**
     * Gets the list of bugs not on the board
     *
     * @return the list of bugs
     */
    public ArrayList<piece> getBugList() { return bugList;}

    /**
     * Gets a piece at a certain spot
     *
     * @param x: the spot's row
     * @param y: the spot's col
     * @return the piece at the desired spot
     */
    public HiveGameState.piece getPiece(int x, int y) {
        return board[x][y];
    }

    /* Helper methods below */

    /**
     * Highlights legal spots for the bee
     * The bee moves one square in any direction
     * as long as it is adjacent to another piece
     *
     * @param board: the board
     * @param row: the new spot's row
     * @param col: the new spot's col
     */
    public void highlightBee(piece[][] board, int row, int col) {
        highlightSurrounding(board, row, col);
    }

    /**
     * Highlights legal spots for the beetle
     * Moves like the bee, but can also stack on top of others (not implemented yet)
     *
     * @param board: the board
     * @param row: the new spot's row
     * @param col: the new spot's col
     */
    public void highlightBeetle(piece[][] board, int row, int col) {
        highlightSurrounding(board, row, col);
    }

    /**
     * Highlights legal spots for the ant
     * For now, it moves to any spot adjacent to another piece
     *
     * @param board: the board
     * @param row: the new spot's row
     * @param col: the new spot's col
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
    public void highlightGHopper(piece[][] board, int row, int col, int direction) {
        //checks for out of bounds
        if(row-1 < 0 || col-1 < 0 || row+1 > board.length - 1 || col+1 > board[col].length -1){
            return;
        }
        //using the direction, goes in one direction until there is an opening, if theres an opening,
        //swtich it from empty to target
        //mod opperators are for the offset of the board
        switch(direction){
            case 0:
                if(col%2 == 0) {
                    if (board[row - 1][col - 1] == HiveGameState.piece.EMPTY) {
                        board[row - 1][col - 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board,row - 1, col - 1, 0);
                        return;
                    }
                }
                else{
                    if (board[row][col-1] == HiveGameState.piece.EMPTY) {
                        board[row][col-1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row, col-1, 0);
                        return;
                    }
                }

            case 1:
                if(col%2 == 0) {
                    if (board[row][col-1] == HiveGameState.piece.EMPTY) {
                        board[row][col-1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row, col-1, 1);
                        return;
                    }
                }
                else{
                    if (board[row + 1][col - 1] == HiveGameState.piece.EMPTY) {
                        board[row + 1][col - 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row + 1, col - 1, 1);
                        return;
                    }
                }

            case 2:
                if(board[row+1][col] == HiveGameState.piece.EMPTY){
                    board[row+1][col] = HiveGameState.piece.TARGET;
                    return;
                }
                else{
                    highlightGHopper(board, row+1, col, 2);
                    return;
                }

            case 3:
                if(col%2 == 0) {
                    if (board[row][col + 1] == HiveGameState.piece.EMPTY) {
                        board[row][col + 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row, col+1, 3);
                        return;
                    }
                }
                else{
                    if (board[row+1][col + 1] == HiveGameState.piece.EMPTY) {
                        board[row+1][col + 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row+1, col + 1, 3);
                        return;
                    }
                }

            case 4:
                if(col%2 == 0) {
                    if (board[row - 1][col + 1] == HiveGameState.piece.EMPTY) {
                        board[row - 1][col + 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row - 1, col + 1, 4);
                        return;
                    }
                }
                else{
                    if (board[row][col+1] == HiveGameState.piece.EMPTY) {
                        board[row][col+1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        highlightGHopper(board, row, col+1, 4);
                        return;
                    }
                }

            case 5:
                if(board[row-1][col] == HiveGameState.piece.EMPTY){
                    board[row-1][col] = HiveGameState.piece.TARGET;
                    return;
                }
                else{
                    highlightGHopper(board, row-1, col, 5);
                    return;
                }
        }
    }

    public void highlightGHopperCleanUp(piece[][] board, int row, int col){
        if(col%2 == 0){
            checkTargetToEmpty(row-1, col-1);//up left
            checkTargetToEmpty(row-1, col);//left
            checkTargetToEmpty(row, col+1);//down right
            checkTargetToEmpty(row+1, col);//right
            checkTargetToEmpty(row-1, col+1);//down left
            checkTargetToEmpty(row, col-1);//up right
        }
        else{
            checkTargetToEmpty(row+1, col-1);//up right
            checkTargetToEmpty(row-1, col);//left
            checkTargetToEmpty(row, col+1);//down left
            checkTargetToEmpty(row+1, col);//right
            checkTargetToEmpty(row+1, col+1);//down right
            checkTargetToEmpty(row, col-1);//up left
        }
    }

    /**
     * Makes spots labeled as targets empty
     * This is used for grasshopper movement
     *
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void checkTargetToEmpty(int row, int col){
        if(board[row][col] == HiveGameState.piece.TARGET){
            board[row][col] = HiveGameState.piece.EMPTY;
        }
    }

    /**
     * Highlights legal spots for the spider
     * For now, it moves like the grasshopper, except that the
     * new row or col must differ from the starting one by exactly 3
     *
     * @param board: the board
     * @param row: the spot's row
     * @param col: the spot's col
     */
    public void highlightSpider(piece[][] board, int row, int col) {
        // The ant algorithm is used to highlight all empty spots next to any piece
        highlightAnt(board, row, col);

        // Unmarks targets not in the same row and col and not 3
        //spots away
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
     * Highlights all empty surrounding spots of a piece
     * @param board: the board
     * @param row: row of piece
     * @param col: col of piece
     */
    public void highlightSurrounding(piece[][] board, int row, int col)
    {
        //Due to hexagon board implementation, the procedure
        //to check adjacent spots varies based on column
        //(if it's an odd or even index)
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

    /**
     * Get what turn it is (not whose turn it is,
     * but how many turns have been played)
     * @return number of turns played
     */
    public int getTurnCount() {
        return turnCount;
    }

    //Since this method is only one line, we should probably
    //just replace the instances of it with its code

    /**
     * Add 1 to the number of turns played
     */
    public void addTurnToCount() {
        turnCount = turnCount + 1;
    }

    //This is similar to above method (one line methods are
    //rather unnecessary)
    /**
     * Set the number of turns played to 0
     */
    public void resetTurnCount() {
        turnCount = 0;
    }

    /**
     * Method that makes sure that the piece selected is white
     * @param row: the piece's row
     * @param col: the piece's col
     * @return true if so, false otherwise
     */
    public boolean checkIfWhite(int row, int col) {
        if (board[row][col] == piece.WBEE) {
            return true;
        }

        else if (board[row][col] == piece.WGHOPPER) {
            return true;
        }

        else if (board[row][col] == piece.WSPIDER) {
            return true;
        }

        else if (board[row][col] == piece.WANT) {
            return true;
        }

        else if (board[row][col] == piece.WBEETLE) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Method that makes sure that the piece selected is black
     * @param row: the piece's row
     * @param col: the piece's col
     * @return true if so, false otherwise
     */
    public boolean checkIfBlack(int row, int col) {
        if (board[row][col] == piece.BBEE) {
            return true;
        }

        else if (board[row][col] == piece.BGHOPPER) {
            return true;
        }

        else if (board[row][col] == piece.BSPIDER) {
            return true;
        }

        else if (board[row][col] == piece.BANT) {
            return true;
        }

        else if (board[row][col] == piece.BBEETLE) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Adds all pieces to their respective owner's
     * hands
     * @param hgs: HiveGameState to act on
     * @param list: List to add pieces to
     */
    public void addPieces(HiveGameState hgs, ArrayList<piece> list)
    {
        //1 BEE, 2 SPIDERs, 3 ANTs, 3 GHOPPERs, 2 BEETLEs
        //for each player
        list.add(HiveGameState.piece.BBEE);
        list.add(HiveGameState.piece.WBEE);

        for (int i = 0; i < 2; i++)
        {
            list.add(HiveGameState.piece.BSPIDER);
            list.add(HiveGameState.piece.BBEETLE);
            list.add(HiveGameState.piece.WSPIDER);
            list.add(HiveGameState.piece.WBEETLE);
        }

        for (int i = 0; i < 3; i++)
        {
            list.add(HiveGameState.piece.BANT);
            list.add(HiveGameState.piece.BGHOPPER);
            list.add(HiveGameState.piece.WANT);
            list.add(HiveGameState.piece.WGHOPPER);
        }
    }

    /**
     * counts specified bug pieces in bugList (unplaced bugs)
     * @param bug the bug to look for
     * @return    number of unplaced pieces in bugList
     */
    public int checkNumPieces(HiveGameState.piece bug){
        int num = 0;
        if(bugList.isEmpty()){
            return 0;
        }
        for(int i = 0; i < bugList.size(); i++){
            if(bugList.get(i) == bug){
                num++;
            }
        }
        return num;
    }

    /**
     *  Check Islands method which ensures that islands will not be made upon moving a piece
     *
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @return
     */
    public boolean checkIslands(int startRow, int startCol, int endRow, int endCol) {
        int numIslands = 1;
        HiveGameState check = new HiveGameState(this);
        check.resetTarget();

        int[][] islandArray = new int[board.length][board.length];
        for (int gridRow = 0; gridRow < board.length; gridRow++) {
            for (int gridCol = 0; gridCol < board.length; gridCol++) {
                islandArray[gridRow][gridCol] = 0;
            }
        }

        check.board[endRow][endCol] = check.board[startRow][startCol];
        check.board[startRow][startCol] = piece.EMPTY;

        for (int gridRow = 1; gridRow < board.length - 1; gridRow++) {
            for (int gridCol = 1; gridCol < board.length - 1; gridCol++) {
                if(check.board[gridRow][gridCol] != piece.EMPTY && islandArray[gridRow][gridCol] == 0) {
                    islandArray[gridRow][gridCol] = numIslands;
                    check.checkIslandsRecursive(gridRow, gridCol, numIslands, islandArray);
                    numIslands++;
                }
            }
        }


        if (numIslands > 2) {
            return false;
        }
        return true;
    }

    /**
     * Method which is called recursively to mark the array to make sure
     * island won't be called
     *
     * @param row
     * @param col
     * @param islandCount
     * @param islandArray
     */
    public void checkIslandsRecursive(int row, int col, int islandCount, int[][] islandArray) {
        if (col % 2 == 0) {
            if (!(board[row + 1][col] == piece.EMPTY)
                    && islandArray[row+1][col] == 0) {
                islandArray[row+1][col] = islandCount;
                checkIslandsRecursive(row+1,col,islandCount,islandArray);
            }
            if (!(board[row - 1][col + 1] == piece.EMPTY)
                    && islandArray[row - 1][col + 1] == 0) {
                islandArray[row - 1][col + 1] = islandCount;
                checkIslandsRecursive(row - 1,col + 1,islandCount,islandArray);
            }
            if (!(board[row][col + 1] == piece.EMPTY)
                    && islandArray[row][col + 1] == 0) {
                islandArray[row][col+1] = islandCount;
                checkIslandsRecursive(row,col+1,islandCount,islandArray);
            }
            if (!(board[row][col-1] == piece.EMPTY)
                    && islandArray[row][col-1] == 0) {
                islandArray[row][col-1] = islandCount;
                checkIslandsRecursive(row,col-1,islandCount,islandArray);
            }
            if (!(board[row-1][col] == piece.EMPTY)
                    && islandArray[row-1][col] == 0) {
                islandArray[row-1][col] = islandCount;
                checkIslandsRecursive(row-1,col,islandCount,islandArray);
            }
            if (!(board[row - 1][col - 1] == piece.EMPTY)
                    && islandArray[row-1][col-1] == 0) {
                islandArray[row-1][col-1] = islandCount;
                checkIslandsRecursive(row-1,col-1,islandCount,islandArray);
            }
        } else {
            if (!(board[row + 1][col] == piece.EMPTY)
                    && islandArray[row+1][col] == 0) {
                islandArray[row+1][col] = islandCount;
                checkIslandsRecursive(row+1,col,islandCount,islandArray);
            }
            if (!(board[row + 1][col + 1] == piece.EMPTY)
                    && islandArray[row+1][col+1] == 0) {
                islandArray[row+1][col+1] = islandCount;
                checkIslandsRecursive(row+1,col+1,islandCount,islandArray);
            }
            if (!(board[row][col + 1] == piece.EMPTY)
                    && islandArray[row][col+1] == 0) {
                islandArray[row][col+1] = islandCount;
                checkIslandsRecursive(row,col+1,islandCount,islandArray);
            }
            if (!(board[row][col - 1] == piece.EMPTY)
                    && islandArray[row][col-1] == 0) {
                islandArray[row][col-1] = islandCount;
                checkIslandsRecursive(row,col-1,islandCount,islandArray);
            }
            if (!(board[row - 1][col] == piece.EMPTY)
                    && islandArray[row-1][col] == 0) {
                islandArray[row-1][col] = islandCount;
                checkIslandsRecursive(row-1,col,islandCount,islandArray);
            }
            if (!(board[row + 1][col - 1] == piece.EMPTY)
                    && islandArray[row+1][col-1] == 0) {
                islandArray[row+1][col-1] = islandCount;
                checkIslandsRecursive(row+1,col-1,islandCount,islandArray);
            }
        }
    }
}
