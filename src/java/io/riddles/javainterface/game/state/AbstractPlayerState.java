package io.riddles.javainterface.game.state;

import io.riddles.javainterface.game.player.PlayerBound;

import java.util.ArrayList;

/**
 * Created by joost on 6-12-16.
 */
public abstract class AbstractPlayerState<M> implements PlayerBound {
    private ArrayList<M> moves;
    private int playerId;

    public AbstractPlayerState() {
        this.moves = new ArrayList<>();
    }

    public AbstractPlayerState(ArrayList<M> moves) {
        this.moves = moves;
    }

    public AbstractPlayerState(M move) {
        this.moves = new ArrayList<>();
        this.moves.add(move);
    }

    public void addMove(M move) { this.moves.add(move); }
    public void setMove(M move) {
        this.moves = new ArrayList<>();
        this.moves.add(move);
    }

    public ArrayList<M> getMoves() { return this.moves; }
    public int getPlayerId() { return this.playerId; }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

}
