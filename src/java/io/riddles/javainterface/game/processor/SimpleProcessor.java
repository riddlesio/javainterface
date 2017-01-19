package io.riddles.javainterface.game.processor;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * Created by joost on 12/12/16.
 */
public interface SimpleProcessor<P extends AbstractPlayer, S extends AbstractState> extends AbstractProcessor<P, S> {

    /**
     * Play one round of the game. Return the state that will be the state for the next round.
     * So multiple states may be created here if a round constist of multiple states.
     * @param inputState The current state
     * @param roundNumber The current round number
     * @return The state that will be the start of the next round
     */
    S processInput(S inputState, int roundNumber);
}
