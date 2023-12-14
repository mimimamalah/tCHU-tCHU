/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

/**
 *
 * the Deck class represents the draw of the wagon / locomotive cards rather
 * than the draw of the tickets.
 *
 * @param <C>,
 *            represents the type of cards in the pile.
 */

public final class Deck<C extends Comparable<C>> {

    private final List<C> cards;

    public Deck(List<C> cards) {
        this.cards = cards;

    }

    /**
     *
     * @param cards, the cards that need to be shuffled
     * @param rng, the random number generator
     * @return Deck<C>, a bunch of cards with the same cards as the multiset
     *         cards, shuffled using the rng random number generator
     */

    public static <C extends Comparable<C>> Deck<C> of(SortedBag<C> cards, Random rng) {
        List<C> card = cards.toList();
        Collections.shuffle(card, rng);

        return new Deck<C>(card);
    }

    /**
     *
     * @return an integer, the size of the pile, i.e. the number of cards it
     *         contains
     */

    public int size() {
        return cards.size();
    }

    /**
     *
     * @return a boolean, true iff the heap is empty
     */

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     *
     * @throws IllegalArgumentException,
     *             if the heap is empty
     *
     * @return the card on top of the heap
     */

    public C topCard() {
        Preconditions.checkArgument(!cards.isEmpty());
        return cards.get(0);
    }

    /**
     *
     * @throws IllegalArgumentException,
     *             if the heap is empty
     *
     * @return Deck<C>, a heap identical to the receiver (this) but without the
     *         top card
     */

    public Deck<C> withoutTopCard() {
        Preconditions.checkArgument(!cards.isEmpty());
        return this.withoutTopCards(1);
    }

    /**
     *
     * @param count
     * @throws IllegalArgumentException,
     *             if count is not between 0 (inclusive) and heap size
     *             (inclusive)
     * @return SortedBag<C>, a multiset containing the count cards at the top of
     *         the pile
     */

    public SortedBag<C> topCards(int count) {
        Preconditions.checkArgument(count >= 0 && count <= cards.size());

        return SortedBag.of(cards.subList(0, count));
    }

    /**
     *
     * @param count
     * @throws IllegalArgumentException,
     *             if count is not between 0 (inclusive) and the size of the
     *             heap (inclusive)
     * @return Deck<C> , a heap identical to the receiver (this) but without the
     *         top card count
     */
    public Deck<C> withoutTopCards(int count) {
        Preconditions.checkArgument(count >= 0 && count <= cards.size());
        return new Deck<C>(cards.subList(count, cards.size()));
    }

}
