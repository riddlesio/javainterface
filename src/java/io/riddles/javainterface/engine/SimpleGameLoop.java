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

import java.util.logging.Logger;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.processor.SimpleProcessor;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * io.riddles.javainterface.engine.SimpleGameLoop - Created on 2-6-16
 *
 * DO NOT EDIT THIS FILE
 *
 * A GameLoop that the engine uses to play the rounds of the game.
 * This gives the current state to the processor, that should return a new
 * state that represents the state at the start of the next round. (which the
 * processor then gets again, etc.)
 * Stops running when the processor's end-game conditions have been met.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class SimpleGameLoop implements GameLoopInterface<SimpleProcessor<AbstractState, AbstractPlayer>> {

    protected final static Logger LOGGER = Logger.getLogger(SimpleGameLoop.class.getName());

    @Override
    public AbstractState run(AbstractState initialState, SimpleProcessor processor) {

        AbstractState state = initialState;
        int roundNumber = 0;

        while (state != null && !processor.hasGameEnded(state)) {

            roundNumber++;

            state = processor.createNextState(state, roundNumber);

            if (state == null) {
                LOGGER.severe("Processor has returned a null state");
            }
        }

        return state;
    }
}
