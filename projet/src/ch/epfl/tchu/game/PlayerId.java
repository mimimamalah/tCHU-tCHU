/**
 *  @author:      Malak Lahlou Nabil (329571)
 *   @author:     Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;

/**
 * 
 * The listed type PlayerId represents the identity of a player;
 *
 */

public enum PlayerId {

    PLAYER_1, PLAYER_2, PLAYER_3;

    /**
     * 
     * The attribute ALL is a list containing the identities of the two player.
     * 
     * The attribute COUNT represents the size of the List ALL.
     */

    public final static List<PlayerId> ALL = List.of(PlayerId.values());
    public final static int COUNT = ALL.size();

    /**
     * 
     * @return PlayerId, the identity of the player following the one to whom it
     *         is applied
     */
    public PlayerId next() {
        switch (this) {
            case PLAYER_1:
                return PLAYER_2;
            case PLAYER_2:
                return PLAYER_3;
            case PLAYER_3:
                return PLAYER_1;
            default:
                throw new Error();
        }
    }

}
