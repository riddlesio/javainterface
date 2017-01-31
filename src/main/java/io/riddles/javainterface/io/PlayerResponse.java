package io.riddles.javainterface.io;

import io.riddles.javainterface.game.player.PlayerBound;

/**
 * Wrap a player response and playerId together in one Object for the Processor to eat.
 * Created by joost on 12/12/16.
 */
public class PlayerResponse implements PlayerBound {
    String value;
    int playerId;

    public PlayerResponse(String value, int playerId) {
        this.value = value;
        this.playerId = playerId;
    }

    public String getValue() { return this.value; }
    public int getPlayerId() { return this.playerId; }
}
