/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;

/**
 * The listed type Card represents the different types of cards in the game such as the eight Cars and a locomotive
 */

public enum Card {

    BLACK(Color.BLACK), VIOLET(Color.VIOLET), BLUE(Color.BLUE), GREEN(
            Color.GREEN), YELLOW(Color.YELLOW), ORANGE(Color.ORANGE), RED(
                    Color.RED), WHITE(Color.WHITE), LOCOMOTIVE(null);

    private final Color color;

    /**
     * Builds a card with a given color
     * @param color, the color of the card.
     */
    Card(Color color) {
        this.color = color;
    }

    /**
     * The attribute CARS is a list of Cards containing only the cars in their order of definition.
     * 
     * The attribute ALL is a list containing all the cards (either the cars or the locomotive).
     * 
     * The attribute COUNT represents the size of the List ALL.
     */
    
    public final static List<Card> CARS = List.of(BLACK, VIOLET, BLUE, GREEN,
            YELLOW, ORANGE, RED, WHITE);
    public final static List<Card> ALL = List.of(Card.values());
    public final static int COUNT = ALL.size();

    /**
     *  @param color the color of the cars
     *  @return returns the type of card corresponding to each color.
     *  
     */
    
    public static Card of(Color color) {
        switch (color) {
        case BLACK:
            return Card.BLACK;
        case VIOLET:
            return Card.VIOLET;
        case BLUE:
            return Card.BLUE;
        case GREEN:
            return Card.GREEN;
        case YELLOW:
            return Card.YELLOW;
        case ORANGE:
            return Card.ORANGE;
        case RED:
            return Card.RED;
        case WHITE:
            return Card.WHITE;
        default:
            return null;
        }

    }

    /**
     * @return returns the color of the card when the method is applied if it is a Car, or null if it is a locomotive.
     */
    
    public Color color() {
        return color;
    }

}
