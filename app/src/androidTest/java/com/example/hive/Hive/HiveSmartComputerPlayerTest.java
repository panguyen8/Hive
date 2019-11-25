package com.example.hive.Hive;

import org.junit.Test;

import static org.junit.Assert.*;
public class HiveSmartComputerPlayerTest {

    //unit test done by Erik Liu
    @Test
    public int getPieceY(HiveGameState game, HiveGameState.piece bug, int boardNum){
        HiveGameState test = new HiveGameState();
        test.board[6][5] = HiveGameState.piece.BBEE;
        test.board[3][4] = HiveGameState.piece.WANT;
        test.board[9][3] = HiveGameState.piece.WBEE;

        int testX = getPieceY(test, HiveGameState.piece.BBEE, 1);

        assertEquals(6, testX);
        return 0;
    }

    @Test
    public int getPieceX(HiveGameState game, HiveGameState.piece bug, int boardNum){
        HiveGameState test = new HiveGameState();
        test.board[6][5] = HiveGameState.piece.BBEE;
        test.board[3][4] = HiveGameState.piece.WANT;
        test.board[9][3] = HiveGameState.piece.WBEE;

        int testY = getPieceY(test, HiveGameState.piece.BBEE, 1);

        assertEquals(5, testY);
        return 0;
    }

}
