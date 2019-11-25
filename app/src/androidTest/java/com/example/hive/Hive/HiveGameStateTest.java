package com.example.hive.Hive;

import org.junit.Test;

import static org.junit.Assert.*;

public class HiveGameStateTest {

    // Done by Samuel Nguyen
    @Test
    public void getTurn() {
        HiveGameState hgs = new HiveGameState();
        int testNum = hgs.getTurn();
        assertEquals(1, testNum);
    }

    // Done by Samuel Nguyen
    @Test
    public void setTurn() {
        HiveGameState hgs = new HiveGameState();
        hgs.setTurn(1);
        int testTurn = 1;
        assertEquals(1, testTurn);
    }

    @Test
    public void canPlace() {
    }

    @Test
    public void checkSurround() {
    }

    //Phuocan Nguyen
    @Test
    public void makeTarget() {
        HiveGameState hgs = new HiveGameState();

        hgs.board[5][5] = HiveGameState.piece.WBEE;

        hgs.makeTarget(5,5);

        //row+1 col
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][5]);
        //row+1 col+1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][6]);
        //row col+1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[5][6]);
        //row col-1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[5][4]);
        //row-1 col
        assertEquals(HiveGameState.piece.TARGET, hgs.board[4][5]);
        //row+1 col-1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][4]);
    }

    //Stephen Nguyen
    @Test
    public void resetTarget() {
        HiveGameState hgs = new HiveGameState();

        HiveGameState.piece[][] board = hgs.getBoard();
        board[0][0] = HiveGameState.piece.WBEE;

        HiveGameState.piece[][] testBoard = hgs.getBoard();
        for (int i = 0; i < testBoard.length; i++)
        {
            for (int j = 0; j < testBoard[i].length; j++)
            {
                testBoard[i][j] = HiveGameState.piece.EMPTY;
            }
        }
        hgs.resetTarget();
        assertArrayEquals(board, testBoard);
    }

    @Test
    public void getBoard() {
    }

    @Test
    public void getBugList() {
    }

    //Stephen Nguyen
    @Test
    public void getPiece() {
        HiveGameState hgs = new HiveGameState();
        HiveGameState.piece[][] board = hgs.getBoard();
        board[0][0] = HiveGameState.piece.WBEE;
        hgs.getPiece(0, 0);
        assertEquals(HiveGameState.piece.WBEE, board[0][0]);
    }

    @Test
    public void setPiece() {
    }
}