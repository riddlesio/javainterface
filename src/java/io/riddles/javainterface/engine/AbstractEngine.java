/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.javainterface.engine;

import io.riddles.javainterface.configuration.Configuration;
import io.riddles.javainterface.exception.TerminalException;

import io.riddles.javainterface.game.player.PlayerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.processor.AbstractProcessor;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.javainterface.io.IOInterface;

/**
 * io.riddles.javainterface.engine.AbstractEngine - Created on 2-6-16
 *
 * DO NOT EDIT THIS FILE
 *
 * The engine in the main project should extend this abstract class.
 * This class handles everything the game engine needs to do to start, run and finish.
 * Quite a lot of methods have already been implemented, but some need to
 * be Overridden in the Subclass. An object of the Subclass of AbstractEngine should
 * be created in the Main method of the project and then the engine is started with
 * engine.run()
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public abstract class AbstractEngine<Pr extends AbstractProcessor,
        Pl extends AbstractPlayer, S extends AbstractState> {

    protected final static Logger LOGGER = Logger.getLogger(AbstractEngine.class.getName());

    public static Configuration configuration;

    protected IOInterface ioHandler;
    protected PlayerProvider<Pl> playerProvider;
    protected Pr processor;
    protected GameLoopInterface gameLoop;

    protected AbstractEngine(PlayerProvider<Pl> playerProvider, IOInterface ioHandler) throws TerminalException {
        this.playerProvider = playerProvider;
        this.gameLoop = createGameLoop();

        this.ioHandler = ioHandler;
    }

    /**
     * Does everything needed before the game can start, such as
     * getting the amount of players, setting the processor and sending
     * the game settings to the bots.
     */
    public S willRun() throws TerminalException {
        LOGGER.info("Setting up engine. Waiting for initialize...");

        this.ioHandler.waitForMessage("initialize");
        this.ioHandler.sendMessage("ok");

        LOGGER.info("Got initialize. Parsing settings...");
        configuration = getDefaultConfiguration();
        parseInputUntilStart();

        this.processor = createProcessor();

        LOGGER.info("Got start. Sending game settings to bots...");

        sendSettingsToPlayers(this.playerProvider.getPlayers());
        LOGGER.info("Settings sent. Setting up engine done...");

        return getInitialState();

    }

    /**
     * This method starts the engine.
     */
    public S run(S initialState) throws TerminalException, InterruptedException {
        LOGGER.info("Running Engine...");

        if (this.processor == null) {
            throw new TerminalException("Processor has not been set");
        }

        LOGGER.info("Starting game loop...");

        this.gameLoop.run(initialState, this.processor);
        return initialState;
    }

    /**
     * Handles everything that needs to happen after the game is done
     * @param finalState Last state of the game
     */
    public void didRun(S finalState) {
        // let the wrapper know the game has ended
        this.ioHandler.sendMessage("end");

        // send game details
        this.ioHandler.waitForMessage("details");

        String details = getDetails(finalState);
        this.ioHandler.sendMessage(details);

        // send the game file
        this.ioHandler.waitForMessage("game");

        String playedGame = getPlayedGame(finalState);
        this.ioHandler.sendMessage(playedGame);
    }

    protected void parseInputUntilStart() throws TerminalException {
        InputType type = InputType.EMPTY;

        try {
            while (type != InputType.START) {
                String line = this.ioHandler.getNextMessage();
                String[] split = line.split(" ");
                type = InputType.fromString(split[0]);

                switch (type) {
                    case BOT_IDS:
                        String[] ids = split[1].split(",");
                        for (String idString : ids) {
                            int id = Integer.parseInt(idString);

                            Pl player = createPlayer(id);
                            this.playerProvider.add(player);
                        }
                        break;
                    case CONFIGURATION:
                        JSONObject config = new JSONObject(split[1].trim());
                        Iterator<String> keys = config.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject configValue = config.getJSONObject(key);
                            configuration.put(key, configValue);
                        }
                        break;
                }
            }
        } catch (IOException ex) {
            throw new TerminalException("Failed to parse configuration");
        }
    }

    private String getDetails(S finalState) {
        Integer winner = this.processor.getWinnerId(finalState);
        String winnerId = "null";

        if (winner != null) {
            winnerId = String.valueOf(winner);
        }

        JSONObject details = new JSONObject();
        details.put("winner", winnerId);
        details.put("score", this.processor.getScore(finalState));

        return details.toString();
    }

    /**
     * Send the settings to the player (bot) that are specific to this game
     * @param players Players to send the settings to
     */
    private void sendSettingsToPlayers(List<Pl> players) {
        players.forEach(this::sendSettingsToPlayer);
    }

    /**
     * Implement this to create a CheckedConfiguration for the app.
     * @return The CheckedConfiguration of the game
     */
    protected abstract Configuration getDefaultConfiguration();

    /**
     * Implement this to return the processor for the game.
     * @return Object that is Subclass of AbstractProcessor
     */
    protected abstract Pr createProcessor();

    /**
     * Implement this to return the GameLoop for the game.
     * @return Object that is Subclass of GameLoop
     */
    protected abstract GameLoopInterface createGameLoop();

    /**
     * Implement this to return a player in the game.
     * @param id Id of the player
     * @return Object that is Subclass of AbstractPlayer
     */
    protected abstract Pl createPlayer(int id);

    /**
     * Send the settings to the player (bot) that are specific to this game
     * @param player Player to send the game settings to
     */
    protected abstract void sendSettingsToPlayer(Pl player);

    /**
     * Implement this to return the initial (mostly empty) game state.
     * @return The initial state of the game, should be Subclass of AbstractState
     */
    protected abstract S getInitialState();

    /**
     * Return the string representation of the entire game to use in
     * the visualizer
     * @param initialState The initial state of the game (can be used to go the next game states).
     * @return String representation of the entire game
     */
    protected abstract String getPlayedGame(S initialState);

    /**
     * @return The players for the game
     */
    public PlayerProvider getPlayerProvider() {
        return this.playerProvider;
    }

    /**
     * @return The processor for the game
     */
    public Pr getProcessor() {
        return this.processor;
    }
}


