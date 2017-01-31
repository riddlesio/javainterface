package io.riddles.javainterface.game.player;


import java.util.ArrayList;

/**
 * Created by joost on 12/12/16.
 */
public class PlayerProvider<Pl extends AbstractPlayer> {
    protected ArrayList<Pl> players;


    public PlayerProvider() {
        this.players = new ArrayList<Pl>();

    }


    public Pl getPlayerById(int playerId) {
        for (Pl player : this.players) {
            if (player.getId() == playerId) return player;
        }
        return null;
    }

    public ArrayList<Pl> getPlayers() {
        return this.players;
    }

    public void add(Pl player) {
        this.players.add(player);
    }


    /**
     * When this.players's size == 2, it will find the other player than 'p'.
     * If this.player's size != 2, it will return null.
     *
     * @param AbstractPlayer The player to find the opponent for.
     * @return The AbstractPlayer of the opponent player, or null if no opponent can be determined.
     */
    private Pl getOpponentPlayer(Pl p) {
        if (this.players.size() == 2) {
            for (Pl player : this.players) {
                if (player.getId() != p.getId()) return player;
            }
        }
        return null;
    }
}
