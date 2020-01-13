package com.example.hive.Hive;

import java.util.ArrayList;

import com.example.hive.game.GameMainActivity;
import com.example.hive.game.GamePlayer;
import com.example.hive.game.LocalGame;
import com.example.hive.game.gameConfiguration.GameConfig;
import com.example.hive.game.gameConfiguration.GamePlayerType;

/**
 * This is original main activity file used to create game
 * Minor updates included to reflect game, Hive
 *
 * @author Original GameFramework File
 * @version November 7
 */
public class HiveMainActivity extends GameMainActivity {

    // the port number that this game will use when playing over the network
    private static final int PORT_NUMBER = 2278;

    /**
     * Create the default configuration for this game:
     * - one human player vs. one computer player
     * - minimum of 1 player, maximum of 2
     *
     * @return
     * 		the new configuration object, representing the default configuration
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // Hive has two player types:  human and computer
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new HiveHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new HiveComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Smart Computer Player"){
            public GamePlayer createPlayer(String name) {return new HiveSmartComputerPlayer(name);}});


        // Create a game configuration class for Hive:
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Hive", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
        defaultConfig.setRemoteData("Remote Human Player", "", 0);

        return defaultConfig;
    }//createDefaultConfig

    /**
     * create a local game
     *
     * @return
     * 		the local game, a Hive game
     */
    @Override
    public LocalGame createLocalGame() {
        return new HiveLocalGame();
    }

}
