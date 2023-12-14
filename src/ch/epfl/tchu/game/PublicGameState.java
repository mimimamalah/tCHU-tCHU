/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.tchu.Preconditions;

/**
 * 
 * Represents the public state of a tCHu game.
 *
 */

public class PublicGameState {

    private final int ticketsCount;
    private final PublicCardState cardState;
    private final PlayerId currentPlayerId;
    private final Map<PlayerId, PublicPlayerState> playerState;
    private final PlayerId lastPlayer;

    /**
     * 
     * @param ticketsCount
     *            The number of tickets
     * @param cardState
     *            The public state of the cards (Cars and locomotives)
     * @param currentPlayerId
     *            The current player
     * @param playerState
     *            The public state of the player
     * @param lastPlayer
     *            The identity of the player who plays the last round.
     * @Throws IllegalArgumentException if the number of tickets is negative or
     *         the size of the playerState is different than 2.
     * 
     *         Builds a the public part of the game's state.
     */

    public PublicGameState(int ticketsCount, PublicCardState cardState,
            PlayerId currentPlayerId,
            Map<PlayerId, PublicPlayerState> playerState, PlayerId lastPlayer) {

        Preconditions
                .checkArgument(ticketsCount >= 0 && playerState.size() == PlayerId.COUNT);
        this.ticketsCount = ticketsCount;
        this.cardState = Objects.requireNonNull(cardState);
        this.currentPlayerId = Objects.requireNonNull(currentPlayerId);
        this.playerState = Map.copyOf(playerState);
        this.lastPlayer = lastPlayer;

    }

    /**
     * 
     * @return the number of tickets.
     */

    public int ticketsCount() {
        return ticketsCount;
    }

    /**
     * 
     * @return true if the number of tickets is strictly bigger than 0 which
     *         means that the player can draw tickets.
     */

    public boolean canDrawTickets() {
        return ticketsCount > 0;
    }

    /**
     * 
     * @return the card's public state.
     */

    public PublicCardState cardState() {
        return cardState;
    }

    /**
     * 
     * @return true if the deck's size added to the discard's size is bigger or
     *         equal to 5.
     */

    public boolean canDrawCards() {

        return cardState.deckSize() + cardState.discardsSize() >= 5;
    }

    /**
     * 
     * @return the current player's identity.
     */
    public PlayerId currentPlayerId() {
        return currentPlayerId;
    }

    /**
     * 
     * @param playerId
     *            the player's identity
     * @return the public state of the given player.
     */

    public PublicPlayerState playerState(PlayerId playerId) {
        return playerState.get(playerId);
    }

    /**
     * 
     * @return the current player's public state
     */

    public PublicPlayerState currentPlayerState() {
        return playerState.get(currentPlayerId);
    }

    /**
     * 
     * @return the total number of roads which had been claimed by one of the
     *         players.
     */

    public List<Route> claimedRoutes() {
        List<Route> claimedRoutes = new ArrayList<Route>();
        for (PlayerId id: PlayerId.values()) {
            claimedRoutes.addAll(playerState(id).routes());
        }

        return claimedRoutes;
    }

    /**
     * 
     * @return the last player's identity.
     */

    public PlayerId lastPlayer() {
        return lastPlayer;
    }

}
