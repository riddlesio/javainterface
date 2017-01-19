package io.riddles.javainterface.engine;

import io.riddles.javainterface.exception.TerminalException;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * Created by joost on 1/3/17.
 */
public interface EngineLifecycle<S> {

    /* Setup the app. */
    S willRun() throws TerminalException;

    /* Run the app. */
    S run(S initialState) throws TerminalException, InterruptedException;

    /* Tear down the app. */
    void didRun(S finalState);
}
