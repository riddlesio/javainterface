package io.riddles.javainterface.engine

import io.riddles.javainterface.configuration.CheckedConfiguration
import io.riddles.javainterface.configuration.Configuration
import io.riddles.javainterface.game.TestProcessor
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.player.TestPlayer
import io.riddles.javainterface.game.state.TestState
import io.riddles.javainterface.io.BotIO
import io.riddles.javainterface.io.FileIOHandler

/**
 * Created by joost.
 */
public class TestEngine extends AbstractEngine<TestProcessor, TestPlayer, TestState> {

    TestEngine(PlayerProvider<TestPlayer> playerProvider, String wrapperInput) {
        super(playerProvider, null);
        this.ioHandler = new FileIOHandler(wrapperInput);
    }

    @Override
    protected void sendConfigurationToPlayer(TestPlayer player, Configuration configuration) {

    }


    @Override
    protected CheckedConfiguration getConfiguration() {
        return new CheckedConfiguration();
    }

    @Override
    protected TestState getInitialState(Configuration configuration) {
        return new TestState();
    }

    @Override
    protected TestPlayer createPlayer(int id, BotIO ioHandler) {
        return new TestPlayer();
    }

    @Override
    protected TestProcessor createProcessor() {
        return new TestProcessor();
    }

    @Override
    protected GameLoop createGameLoop() {
        return new SimpleGameLoop();
    }

    @Override
    protected String getPlayedGame(TestState initialState) {
        return "";
    }
}