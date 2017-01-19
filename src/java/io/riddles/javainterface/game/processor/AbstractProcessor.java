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

package io.riddles.javainterface.game.processor;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerBound;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * io.riddles.javainterface.game.processor.AbstractProcessor - Created on 6-6-16
 *
 * DO NOT EDIT THIS FILE
 *
 * The processor handles the main game logic. This class should be subclassed and
 * the subclass should implement these methods. Implement anything else here needed
 * to process the game states.
 *
 * This abstract class only stores the players on it's own.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public interface AbstractProcessor<P extends AbstractPlayer, S extends AbstractState> {

    //protected final static Logger LOGGER = Logger.getLogger(AbstractProcessor.class.getName());

    /**
     * The stopping condition for this game.
     * @param state The current state
     * @return True if the game is over, false otherwise
     */
    boolean hasGameEnded(S state);

    /**
     * Returns the winner playerId of the game
     * @return Null if there is no winner, a playerId otherwise
     */
     Integer getWinnerId(S state);

    /**
     * @return The current game score
     */
    double getScore(S state);


}
