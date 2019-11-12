package com.example.hive.Hive;

import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.actionMessage.GameAction;

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
        return true;
    }

    protected String checkIfGameOver() {
        // Black
        if(hgs.getTurn() == 0) {
            hgs.setTurn(1);
        }
        else if(hgs.getTurn() == 1) {
            hgs.setTurn(0);
        }
        return "";
    }

    protected boolean makeMove(GameAction action) {
        if(hgs.getTurn() == 0) {
            hgs.setTurn(1);
        }
        else if(hgs.getTurn() == 1) {
            hgs.setTurn(0);
        }
        return true;
    }
}
