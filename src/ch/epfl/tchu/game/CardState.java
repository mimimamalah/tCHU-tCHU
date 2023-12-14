/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import static ch.epfl.tchu.game.Constants.*;
import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

/**
 *
 * the class CardState represents the state of the wagon / locomotive cards that
 * are not in the players' hand.
 *
 */

public final class CardState extends PublicCardState {

    private final Deck<Card> cardDeck;
    private final SortedBag<Card> discardsCards;

    private CardState(List<Card> faceUpCards, Deck<Card> cardDeck,
                      SortedBag<Card> discardsCards) {
        super(faceUpCards, cardDeck.size(), discardsCards.size());
        this.cardDeck = cardDeck;
        this.discardsCards = discardsCards;
    }

    /**
     *
     * @param deck,
     *            the draw with the cards
     * @throws IllegalArgumentException,
     *             if the given pile contains less than five cards
     * @return a state in which the 5 cards placed face up are the first 5 of
     *         the given pile, the draw pile consists of the remaining cards of
     *         the pile, and the discard pile is empty
     */

    public static CardState of(Deck<Card> deck) {

        Preconditions.checkArgument(deck.size() >= Constants.FACE_UP_CARDS_COUNT);

        List<Card> faceUpCards2 = new ArrayList<Card>();
        Deck<Card> deck2 = deck;
        for (int slot : Constants.FACE_UP_CARD_SLOTS) {
            faceUpCards2.add(deck2.topCard());
            deck2 = deck2.withoutTopCard();
        }

        return new CardState(faceUpCards2, deck2, SortedBag.of());

    }

    /**
     *
     * @param slot,
     *            an integer
     * @throws IllegalArgumentException,
     *             if the given index is not between 0 (inclusive) and 5
     *             (excluded), or IllegalArgumentException if the draw is empty
     * @return CardState, a set of cards identical to the receiver (this),
     *         except that the face-up index slot card has been replaced by the
     *         one at the top of the draw pile, which is at the same time
     *         removed from it
     */

    public CardState withDrawnFaceUpCard(int slot) {
        Objects.checkIndex(slot, FACE_UP_CARDS_COUNT);
        Preconditions.checkArgument(!isDeckEmpty());
        Card removedCard = topDeckCard();
        List<Card> faceUpCards2 = new ArrayList<>();
        for (int i: FACE_UP_CARD_SLOTS)
            faceUpCards2.add(faceUpCards().get(i));

        faceUpCards2.set(slot, removedCard);

        return new CardState(faceUpCards2, cardDeck.withoutTopCard(),
                discardsCards);
    }

    /**
     * @throws IllegalArgumentException,
     *             if the draw is empty
     * @return the card at the top of the deck
     */

    public Card topDeckCard() {
        Preconditions.checkArgument(!isDeckEmpty());

        return cardDeck.topCard();
    }

    /**
     * @throws IllegalArgumentException,
     *             if the draw is empty
     * @return a CardState, a set of cards identical to the receiver (this), but
     *         without the card at the top of the deck
     */

    public CardState withoutTopDeckCard() {
        Preconditions.checkArgument(!isDeckEmpty());

        return new CardState(faceUpCards(), cardDeck.withoutTopCard(),
                discardsCards);
    }

    /**
     *
     * @param rng,
     *            the random number generator
     * @throws IllegalArgumentException,
     *             if the receiver's deck is not empty
     * @return a CardState, a set of cards identical to the receiver (this),
     *         except that the cards from the discard pile have been shuffled
     *         using the given random generator to form the new draw pile
     */

    public CardState withDeckRecreatedFromDiscards(Random rng) {
        Preconditions.checkArgument(isDeckEmpty());

        return new CardState(faceUpCards(), Deck.of(SortedBag.of(discardsCards), rng), SortedBag.of());
    }

    /**
     *
     * @param additionalDiscards,
     *            SortedBag<Card>
     * @return a CardState,a set of identical cards to the receiver (this), but
     *         with the given cards added to the discard pile.
     */

    public CardState withMoreDiscardedCards(
            SortedBag<Card> additionalDiscards) {
        return new CardState(faceUpCards(), cardDeck,
                discardsCards.union(additionalDiscards));
    }

}
