/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

import javafx.scene.media.*;

import static ch.epfl.tchu.game.Constants.*;

/**
 *
 * This class represents the state of part of tCHu.
 *
 */

/**
 * @author malaklahlou
 *
 */
public final class GameState extends PublicGameState {

    private final Deck<Ticket> ticketsDeck;
    private final Map<PlayerId, PlayerState> playerState;
    private final CardState cardState;

    private GameState(Deck<Ticket> ticketsDeck, CardState cardState,
                      PlayerId currentPlayerId, Map<PlayerId, PlayerState> playerState,
                      PlayerId lastPlayer) {
        super(ticketsDeck.size(), cardState, currentPlayerId,
                Map.copyOf(playerState), lastPlayer);
        this.ticketsDeck = ticketsDeck;
        this.playerState = playerState;
        this.cardState = cardState;
    }

    /**
     *
     * @param tickets, the given tickets
     * @param rng, the given random generator
     * @return returns the initial state of a game of tCHu in which the bank of
     *         tickets contains the tickets given and the deck of cards is
     *         distributed to the players; these decks are shuffled as well as
     *         the identity of the first player.
     */

    public static GameState initial(SortedBag<Ticket> tickets, Random rng) {

        Deck<Ticket> ticketsDeck = Deck.of(tickets, rng);
        Deck<Card> cardsDeck = Deck.of(ALL_CARDS, rng);

        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        for (PlayerId id : PlayerId.ALL) {
            playerState2.put(id, new PlayerState(SortedBag.of(),
                    cardsDeck.topCards(INITIAL_CARDS_COUNT), List.of()));
            cardsDeck = cardsDeck.withoutTopCards(INITIAL_CARDS_COUNT);
        }

        return new GameState(ticketsDeck, CardState.of(cardsDeck),
                PlayerId.ALL.get(rng.nextInt(PlayerId.COUNT)), playerState2,
                null);

    }

    /**
     *
     * @Override the same name method of PublicGameState
     * @return the full state of the given identity player and not just its
     *         public part
     */

    @Override
    public PlayerState playerState(PlayerId playerId) {
        return playerState.get(playerId);

    }

    /**
     *
     * @Override the same name method of PublicGameState
     * @return the full state of the current player and not just its public part
     */

    @Override
    public PlayerState currentPlayerState() {
        return playerState.get(currentPlayerId());

    }

    /**
     *
     * @throws IllegalArgumentException,
     *             if count is not between 0 and the size of the deck (included)
     * @param count, the index
     * @return SortedBag<Ticket> , the count tickets from the top of the deck
     */

    public SortedBag<Ticket> topTickets(int count) {
        Preconditions.checkArgument(count >= 0 && count <= ticketsCount());

        return ticketsDeck.topCards(count);

    }

    /**
     * @throws IllegalArgumentException,
     *             if count is not between 0 and the size of the deck (included)
     * @param count, the index
     * @return GameState, an identical state to the receiver, but without the
     *         tickets count on top of the deck
     */

    public GameState withoutTopTickets(int count) {
        Preconditions.checkArgument(count >= 0 && count <= ticketsCount());

        return new GameState(ticketsDeck.withoutTopCards(count), cardState,
                currentPlayerId(), playerState, lastPlayer());

    }

    /**
     * @throws IllegalArgumentException,
     *             if the deck is empty
     * @return Card, the top deck Card
     */

    public Card topCard() {
        Preconditions.checkArgument(!cardState.isDeckEmpty());

        return cardState.topDeckCard();
    }

    /**
     * @throws IllegalArgumentException,
     *             if the deck is empty
     * @return GameState, an identical state to the receiver, but without the
     *         top Deck Card
     */

    public GameState withoutTopCard() {
        Preconditions.checkArgument(!cardState.isDeckEmpty());

        return new GameState(ticketsDeck, cardState.withoutTopDeckCard(),
                currentPlayerId(), playerState, lastPlayer());
    }

    /**
     *
     * @param discardedCards, the added cards
     * @return GameState , an identical state to the receiver but with the given
     *         cards added to the discard pile
     */

    public GameState withMoreDiscardedCards(SortedBag<Card> discardedCards) {
        return new GameState(ticketsDeck,
                cardState.withMoreDiscardedCards(discardedCards),
                currentPlayerId(), playerState, lastPlayer());
    }

    /**
     *
     * @param rng, the random generator
     * @return GameState, an identical state to the receiver unless the deck of
     *         cards is empty, in which case it is recreated from the discard
     *         pile, shuffled using the given random generator.
     */

    public GameState withCardsDeckRecreatedIfNeeded(Random rng) {

        return !cardState.isDeckEmpty() ? this
                : new GameState(ticketsDeck,
                cardState.withDeckRecreatedFromDiscards(rng),
                currentPlayerId(), playerState, lastPlayer());
    }

    /**
     *
     * @param playerId, the given player
     * @param chosenTickets, the chosen Tickets.
     * @throws IllegalArgumentException, if the player already has at least one ticket
     * @return GameState. an identical state to the receiver but in which the
     *         given tickets have been added to the given player's hand
     */

    public GameState withInitiallyChosenTickets(PlayerId playerId,
                                                SortedBag<Ticket> chosenTickets) {
        Preconditions.checkArgument(playerState(playerId).ticketCount() == 0);

        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        playerState2.putAll(playerState);
        playerState2.replace(playerId,
                playerState(playerId).withAddedTickets(chosenTickets));

        return new GameState(ticketsDeck, cardState, currentPlayerId(),
                playerState2, lastPlayer());

    }

    /**
     *
     * @param drawnTickets, the drawn tickets
     * @param chosenTickets, the chosen tickets
     * @throws IllegalArgumentException, if all the tickets chosen are not included in the drawn tickets
     * @return GameState, an identical state to the receiver, but in which the
     *         current player has drawn the drawnTickets from the top of the
     *         draw pile, and chosen to keep those contained in chosenTicket
     */

    public GameState withChosenAdditionalTickets(SortedBag<Ticket> drawnTickets,
                                                 SortedBag<Ticket> chosenTickets) {
        Preconditions.checkArgument(drawnTickets.contains(chosenTickets));

        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        playerState2.putAll(playerState);
        playerState2.replace(currentPlayerId(),
                playerState(currentPlayerId()).withAddedTickets(chosenTickets));

        return new GameState(ticketsDeck.withoutTopCards(drawnTickets.size()),
                cardState, currentPlayerId(), playerState2, lastPlayer());

    }

    /**
     *
     * @param slot, the index of the Drawn Face Up Card
     * @throws IllegalArgumentException, if it is not possible to draw cards
     * @return GameState, an identical state to the receiver except that the
     *         face-up card at the given index has been placed in the current
     *         player's hand, and replaced by the one at the top of the draw
     *         deck
     */

    public GameState withDrawnFaceUpCard(int slot) {

        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        playerState2.putAll(playerState);
        playerState2.replace(currentPlayerId(), playerState(currentPlayerId())
                .withAddedCard(cardState.faceUpCard(slot)));

        return new GameState(ticketsDeck, cardState.withDrawnFaceUpCard(slot),
                currentPlayerId(), playerState2, lastPlayer());
    }

    /**
     * @throws IllegalArgumentException, if it is not possible to draw cards
     * @return GameState, an identical state to the receiver except that the top
     *         card of the draw pile has been placed in the current player's
     *         hand
     */

    public GameState withBlindlyDrawnCard() {

        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        playerState2.putAll(playerState);
        playerState2.replace(currentPlayerId(),
                currentPlayerState().withAddedCard(cardState.topDeckCard()));

        return new GameState(ticketsDeck, cardState.withoutTopDeckCard(),
                currentPlayerId(), playerState2, lastPlayer());

    }

    /**
     *
     * @param route, the claimed route
     * @param cards the given cards
     * @return GameState, n identical state to the receiver but in which the
     *         current player has seized the given route by means of the given
     *         cards
     */

    public GameState withClaimedRoute(Route route, SortedBag<Card> cards) {
        Map<PlayerId, PlayerState> playerState2 = new EnumMap<>(PlayerId.class);
        playerState2.putAll(playerState);
        playerState2.replace(currentPlayerId(),
                currentPlayerState().withClaimedRoute(route, cards));

        return new GameState(ticketsDeck,
                cardState.withMoreDiscardedCards(cards), currentPlayerId(),
                playerState2, lastPlayer());
    }

    /**
     * This method should only be called at the end of a player's turn,
     *
     * @return boolean,true iff the last round begins, ie if the identity of the
     *         last player is currently unknown but the current player has only
     *         two cars or less left
     */

    public boolean lastTurnBegins() {
        return (lastPlayer() == null && (currentPlayerState().carCount() <= 2));
    }

    /**
     *
     * @return GameState, an identical state to the receiver except that the
     *         current player is the one following the current current player;
     *         furthermore, if lastTurnBegins returns true, the current current
     *         player becomes the last player
     */

    public GameState forNextTurn() {
        return new GameState(ticketsDeck, cardState, currentPlayerId().next(),
                playerState, lastTurnBegins() ? currentPlayerId() : lastPlayer());

    }

}
