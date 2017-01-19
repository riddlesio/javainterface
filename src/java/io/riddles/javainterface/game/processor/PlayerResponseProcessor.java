package io.riddles.javainterface.game.processor;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerBound;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.javainterface.io.PlayerResponse;

/**
 * Created by joost on 12/12/16.
 */
public interface PlayerResponseProcessor<P extends AbstractPlayer, S extends AbstractState> extends AbstractProcessor<P, S> {

    /**
     * Play one round of the game. Return the state that will be the state for the next round.
     * So multiple states may be created here if a round constist of multiple states.
     * @param inputState The current state
     * @param roundNumber The current round number
     * @param PlayerResponse input Player response and id.
     * @return The state that will be the start of the next round
     */
    S processInput(S inputState, int roundNumber, PlayerResponse input);
    Enum getActionRequest(S intermediateState, PlayerBound playerState);
    void sendUpdates(S intermediateState, P player);
}
