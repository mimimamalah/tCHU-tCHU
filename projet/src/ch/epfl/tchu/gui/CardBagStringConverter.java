/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.Card;
import javafx.util.StringConverter;

/**
 * This class defines concrete versions of String Converter two abstract methods.
 */

public class CardBagStringConverter extends StringConverter<SortedBag<Card>> {
    /**
     * This method transforms the multiset of cards into strings.
     * @param cards, the multiset of cards
     * @return the String transformed
     */
    @Override
    public String toString(SortedBag<Card> cards) {
        return Info.cards(cards);
    }

    /**
     *
     * @param string, the string
     * @return the multiset of cards transformed from a string
     * @throws UnsupportedOperationException, because never used
     */
    @Override
    public SortedBag<Card> fromString(String string) {
        throw new UnsupportedOperationException();
    }
}
