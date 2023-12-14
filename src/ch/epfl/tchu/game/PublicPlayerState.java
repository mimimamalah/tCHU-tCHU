/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;

import ch.epfl.tchu.Preconditions;

/**
 * This class represents represents the public part of a player's state, namely
 * the number of tickets, cards and wagons he owns, the roads he has seized, and
 * the number of build points he has thus obtained.
 *
 */

public class PublicPlayerState {

    private final int ticketCount, cardCount, carCount, claimPoints;
    private final List<Route> routes;

    /**
     * builds the state of a player who owns the number of given tickets and
     * cards, and has seized the given routes
     * 
     * @param ticketCount
     * @param cardCount
     * @param routes
     * @throws IllegalArgumentException,
     *             if the number of tickets or the number of cards is strictly
     *             negative (<0)
     * 
     */
    public PublicPlayerState(int ticketCount, int cardCount,
            List<Route> routes) {

        Preconditions.checkArgument(ticketCount >= 0 && cardCount >= 0);

        this.ticketCount = ticketCount;
        this.cardCount = cardCount;
        this.routes = List.copyOf(routes);

        int routesLength = 0;
        int claimPoints2 = 0;
        for (Route r : routes){
            routesLength += r.length();
            claimPoints2 += r.claimPoints();
        }
        carCount = Constants.INITIAL_CAR_COUNT - routesLength;
        claimPoints = claimPoints2;

    }

    /**
     * 
     * @return an integer, the number of tickets the player has
     */

    public int ticketCount() {
        return ticketCount;
    }

    /**
     * 
     * @return an integer, the number of cards the player has
     */

    public int cardCount() {
        return cardCount;
    }

    /**
     * 
     * @return List<Route>, the roads that the player has seized
     */

    public List<Route> routes() {
        return routes;
    }

    /**
     * 
     * @return an integer, the number of cars the player has
     */

    public int carCount() {
        return carCount;
    }

    /**
     * 
     * @return an integer, the number of construction points obtained by the
     *         player
     */

    public int claimPoints() {
        return claimPoints;
    }

}
