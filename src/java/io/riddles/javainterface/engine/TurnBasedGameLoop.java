package io.riddles.javainterface.engine;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerBound;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.processor.AbstractProcessor;
import io.riddles.javainterface.game.processor.PlayerResponseProcessor;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.javainterface.io.PlayerResponse;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by joost on 12/5/16.
 */
public class TurnBasedGameLoop implements GameLoop<PlayerResponseProcessor<AbstractPlayer, AbstractState>> {
    protected final static Logger LOGGER = Logger.getLogger(AbstractProcessor.class.getName());
    private PlayerProvider<AbstractPlayer> playerProvider;
    int maxRounds = 15; /* TODO */

    public TurnBasedGameLoop(PlayerProvider playerProvider) {
        this.playerProvider = playerProvider;
    }

    @Override
    public AbstractState run(AbstractState initialState,
                             PlayerResponseProcessor<AbstractPlayer, AbstractState> processor) {

        AbstractState state = initialState;
        int roundNumber = 0;


        while (state != null && !processor.hasGameEnded(state) && roundNumber <= maxRounds) {

            roundNumber++;

            state = playRound(processor, roundNumber, state);

            if (state == null) {
                LOGGER.severe("Processor has returned a null state");
            }
        }

        return state;
    }

    private AbstractState playRound(PlayerResponseProcessor<AbstractPlayer, AbstractState> processor, int roundNumber, AbstractState state) {
        LOGGER.info(String.format("Playing round %d", roundNumber));

        ArrayList<PlayerBound> playerStates = state.getPlayerStates();

        return playerStates.stream()
            .reduce(state,
                    (AbstractState intermediateState, PlayerBound playerState) -> {

                        if (processor.hasGameEnded(intermediateState)) {
                            return intermediateState;
                        }

                        AbstractPlayer player = playerProvider.getPlayerById(playerState.getPlayerId());

                        processor.sendUpdates(intermediateState, player);

                        Enum actionType = processor.getActionRequest(intermediateState, playerState);

                        String response = player.requestMove(actionType);

                        return processor.processInput(intermediateState, roundNumber, new PlayerResponse(response, player.getId()));
                    },
                    (inputState, outputState) -> outputState);
    }

}

