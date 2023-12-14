/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;
import java.util.Objects;
import static ch.epfl.tchu.game.Constants.*;
import ch.epfl.tchu.Preconditions;

/**
 * the class PublicCardState represents (part of) the state of the wagon /
 * locomotive cards that are not in the players' hand, namely: the 5 cards
 * placed face up, the deck, the discard pile.
 * 
 */

public class PublicCardState {

    private final List<Card> faceUpCards;
    private final int deckSize;
    private final int discardsSize;

    /**
     * 
     * builds a public state of cards in which the face up cards are those
     * given, the draw pile contains deckSize cards and the discard pile
     * contains discardsSize
     * 
     * @param faceUpCards
     * @param deckSize
     * @param discardsSize
     * 
     * @throws IllegalArgumentException,
     *             if faceUpCards does not contain 5 items, or if the size of
     *             the draw pile or discard pile is negative (<0).
     */

    public PublicCardState(List<Card> faceUpCards, int deckSize,
            int discardsSize) {

        Preconditions.checkArgument(faceUpCards.size() == FACE_UP_CARDS_COUNT);
        Preconditions.checkArgument(deckSize >= 0 && discardsSize >= 0);

        this.faceUpCards = List.copyOf(faceUpCards);
        this.deckSize = deckSize;
        this.discardsSize = discardsSize;

    }

    /**
     * 
     * @return List<Card>, the 5 cards face up
     */

    public List<Card> faceUpCards() {
        return faceUpCards;
    }

    /**
     * 
     * @param slot,
     *            the given index
     * @throws IndexOutOfBoundsException,
     *             if this index is not between 0 (included) and 5 (excluded
     * @return the card face up at the given index
     */

    public Card faceUpCard(int slot) {
        return faceUpCards.get(Objects.checkIndex(slot, FACE_UP_CARDS_COUNT));
    }

    /**
     * 
     * @return the size of the deck
     */

    public int deckSize() {
        return deckSize;
    }

    /**
     * 
     * @return a boolean , true if the deck is empty
     */

    public boolean isDeckEmpty() {
        return deckSize == 0;
    }

    /**
     * 
     * @return the size of the discard pile
     */

    public int discardsSize() {
        return discardsSize;
    }
}
