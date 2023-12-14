/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

/**
 * This class represents a route.
 */
public final class Route {

    /**
     * The listed type Level represents the two levels at which a road can be
     * found
     */

    public enum Level {

        OVERGROUND, UNDERGROUND

    }

    private final String id;
    private final Station station1, station2;
    private final int length;
    private final Level level;
    private final Color color;
    private final List<Station> stationList;

    /**
     * Builds a route with the given id, stations, length, level and color
     * 
     * @param id, the identity of the route.
     * @param station1, the first station
     * @param station2, the second station
     * @param length, the length of the route.
     * @param level, the level where the route is.
     * @param color, its color.
     * 
     * @throws IllegalArgumentException,
     *             if the stations are the same.
     * @throws IllegalArgumentException,
     *             if the length is not within the acceptable limits
     * @throws NullPointerException,
     *             if the id is null
     * @throws NullPointerException,
     *             if the station1 or station2 is null
     * @throws NullPointerException,
     *             if the level is null
     */

    public Route(String id, Station station1, Station station2, int length,
            Level level, Color color) {

        Preconditions.checkArgument(!station1.equals(station2));
        Preconditions.checkArgument(Constants.MIN_ROUTE_LENGTH <= length
                && length <= Constants.MAX_ROUTE_LENGTH);

        this.id = Objects.requireNonNull(id);
        this.station1 = Objects.requireNonNull(station1);
        this.station2 = Objects.requireNonNull(station2);
        this.length = length;
        this.level = Objects.requireNonNull(level);
        this.color = color;
        this.stationList = List.of(station1, station2);

    }

    /**
     * @return the identity of the route
     */

    public String id() {
        return id;
    }

    /**
     * @return the first station
     */

    public Station station1() {
        return station1;
    }

    /**
     * @return the seconde station
     */

    public Station station2() {
        return station2;
    }

    /**
     * @return the length of the route
     */

    public int length() {
        return length;
    }

    /**
     * @return the level of the route
     */

    public Level level() {
        return level;
    }

    /**
     * @return the color of the route or null if the route is neutral
     */

    public Color color() {
        return color;
    }

    /**
     * @return the list of the two stations on the route, in the order in which
     *         they were passed on the constructor.
     */

    public List<Station> stations() {
        return stationList;
    }

    /**
     * @return the station of the route which is not the one given
     * 
     * @param station, the station which we have to fond its opposite.
     * 
     * @throws IllegalArgumentException,
     *             if the station is neither of the stations passed on the
     *             constructor.
     */

    public Station stationOpposite(Station station) {
        if (station.equals(station1)) {
            return station2;
        } else if (station.equals(station2)) {
            return station1;
        }

        throw new IllegalArgumentException();

    }

    /**
     * @return the list of all the sets of cards that could be played to take
     *         the route
     */

    public List<SortedBag<Card>> possibleClaimCards() {

        List<SortedBag<Card>> cardsList = new ArrayList<>();

        if (level.equals(Level.UNDERGROUND)) {
            for (int j = 0; j < length; ++j){
                for (Card s : Card.CARS)
                    if (color == null || s.equals(Card.of(color)))
                        cardsList.add(SortedBag.of(length - j, s, j, Card.LOCOMOTIVE));
            }
            cardsList.add(SortedBag.of(length, Card.LOCOMOTIVE));

        } else {
            for (Card s : Card.CARS)
                if (color == null || s.equals(Card.of(color))){
                    cardsList.add(SortedBag.of(length, s));
                }
        }
        return cardsList;
    }

    /**
     * @return an integer, the number of additional cards to be played to seize
     *         the road
     * 
     * @param claimCards represents the cards initially played
     * @param drawnCards represents the three cards drawn from the top of
     *            the deck
     * 
     * @throws IllegalArgumentException
     *             if the route is not a tunnel, which means its level is
     *             OVERGROUND.
     * @throws IllegalArgumentException
     *             if the size of the list drawnCards does not contain exactly
     *             three cards.
     */

    public int additionalClaimCardsCount(SortedBag<Card> claimCards,
            SortedBag<Card> drawnCards) {

        Preconditions.checkArgument(level.equals(Level.UNDERGROUND));
        Preconditions.checkArgument(
                drawnCards.size() == Constants.ADDITIONAL_TUNNEL_CARDS);

        boolean allClaimCardsAreLocomotive = true;
        int additionalCards = 0;
        Color initialColor = null;

        for (Card s : claimCards)
            if (!s.equals(Card.LOCOMOTIVE)) {
                allClaimCardsAreLocomotive = false;
                initialColor = s.color();
            }

        for (Card c : drawnCards)

            if (c.equals(Card.LOCOMOTIVE) || !allClaimCardsAreLocomotive
                    && c.color().equals(initialColor))
                ++additionalCards;

        return additionalCards;

    }

    /**
     * @return an integer, the number of construction points a player gets when
     *         they seize the road
     */

    public int claimPoints() {
        return Constants.ROUTE_CLAIM_POINTS.get(length);
    }

}
