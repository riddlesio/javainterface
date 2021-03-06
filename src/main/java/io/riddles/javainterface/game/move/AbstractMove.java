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

package io.riddles.javainterface.game.move;

/**
 * io.riddles.javainterface.engine.move.AbstractMove - Created on 2-6-16
 *
 * DO NOT EDIT THIS FILE
 *
 * Used to store information about the move a player has made. This class
 * should be extended, and additional information can be stored there.
 * Basic stuff like whether the move was invalid.
 * To which player the move belongs is already implemented in AbstractPlayerState.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public abstract class AbstractMove {

    private Exception invalidException;

    public AbstractMove() {
        this.invalidException = null;
    }

    public AbstractMove(Exception exception) {
        this.invalidException = exception;
    }

    /**
     * @return True if this move is invalid
     */
    public boolean isInvalid() {
        return this.invalidException != null;
    }

    /**
     * Sets the Exception of this move. Only set this if the Move is invalid.
     * @param exception exception
     */
    public void setException(Exception exception) {
        this.invalidException = exception;
    }

    /**
     * @return The exception of this move
     */
    public Exception getException() {
        return this.invalidException;
    }
}
