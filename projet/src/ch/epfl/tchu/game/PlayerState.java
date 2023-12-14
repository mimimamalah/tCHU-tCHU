/**
 *  @Author:      Malak Lahlou Nabil (329571)
 *               Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.awt.*;
import java.util.*;
import java.util.List;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.StationPartition.Builder;

import static ch.epfl.tchu.game.Constants.*;

/**
 * 
 * This class represents the complete state of the player.
 *
 */

public final class PlayerState extends PublicPlayerState {

    private final SortedBag<Ticket> tickets;
    private final SortedBag<Card> cards;
    private Map<Ticket, Integer> ticketsPoints;

    /**
     * 
     * @param tickets
     * @param cards
     * @param routes
     *
     * The constructor builds the state of a player with the
     *            given tickets, maps and routes.
     */
    public PlayerState(SortedBag<Ticket> tickets, SortedBag<Card> cards,
            List<Route> routes) {

        super(tickets.size(), cards.size(), routes);
        this.tickets = tickets;
        this.cards = cards;
        ticketsPoints = new HashMap<>();
        for(Ticket ticket : tickets)
          ticketsPoints.put(ticket, 0);
    }

    /**
     * 
     * @param initialCards
     * @throws IllegalArgumentException, if the number of initial cards is not equal to 4
     * @return PlayerState, the initial state of a player to which the given
     *         initial cards were dealt
     */

    public static PlayerState initial(SortedBag<Card> initialCards) {

        Preconditions.checkArgument(initialCards.size() == INITIAL_CARDS_COUNT);

        return new PlayerState(SortedBag.of(), initialCards, List.of());
    }

    /**
     * @return SortedBag<Ticket>, player tickets
     */

    public SortedBag<Ticket> tickets() {
        return tickets;
    }

    /**
     * 
     * @param newTickets, the given tickets
     * @return an identical state to the receiver, except that the player also has the given tickets.
     */

    public PlayerState withAddedTickets(SortedBag<Ticket> newTickets) {
        return new PlayerState(tickets.union(newTickets), cards, routes());
    }

    /**
     * @return SortedBag<Card>, the player's wagon / locomotive cards
     */

    public SortedBag<Card> cards() {
        return cards;
    }

    /**
     * 
     * @param card,
     *            the added card
     * @return PlayerState, an identical state to the receiver, except that the
     *         player also has the given card
     */

    public PlayerState withAddedCard(Card card) {
        return new PlayerState(tickets, cards.union(SortedBag.of(card)),
                routes());
    }


    /**
     * 
     * @param route,
     * @return boolean, true iff the player can seize the given route, i.e. if
     *         he has enough cars left and if he has the necessary cards,
     */

    public boolean canClaimRoute(Route route) {

        return (carCount() >= route.length())
                && !possibleClaimCards(route).isEmpty();

    }

    /**
     * 
     * @param route
     * @throws IllegalArgumentException,
     *             if he has enough cars left
     * @return List<SortedBag<Card>>, the list of all the sets of cards that the
     *         player could use to take possession of the given route,
     */

    public List<SortedBag<Card>> possibleClaimCards(Route route) {

        Preconditions.checkArgument(carCount() >= route.length());
        List<SortedBag<Card>> possibleClaimCards = new ArrayList<SortedBag<Card>>();
        List<Card> lc = new ArrayList<>();

        for (SortedBag<Card> pcc : route.possibleClaimCards()) {

            boolean hasEnoughCards = true;
            lc.addAll(cards.toList());

            for (Card p : pcc) {

                if (!lc.contains(p)) {
                    hasEnoughCards = false;
                } else {
                    lc.remove(p);
                }
            }

            if (hasEnoughCards)
                possibleClaimCards.add(pcc);

            lc.clear();
        }
        return possibleClaimCards;
    }

    /**
     * 
     * @param additionalCardsCount
     * @param initialCards
     * @throws IllegalArgumentException,
     *             if the number of additional cards is not between 1 and 3
     *             (inclusive)
     * @throws IllegalArgumentException,
     *             if the set of initial cards is empty or contains more than 2
     *             different types of cards
     * @throws IllegalArgumentException,
     *             if the set of cards drawn does not exactly contain 3 cards
     *             
     * @return the list of all the sets of cards that the player could use to
     *         seize a tunnel, sorted in ascending order of the number of
     *         locomotive cards,
     */

    public List<SortedBag<Card>> possibleAdditionalCards(
            int additionalCardsCount, SortedBag<Card> initialCards) {

        Preconditions.checkArgument(
                additionalCardsCount >= 1 && additionalCardsCount <= ADDITIONAL_TUNNEL_CARDS);

        Preconditions.checkArgument(!initialCards.isEmpty());

        Set<Card> s = initialCards.toSet();
        Preconditions.checkArgument(s.size() <= 2);

        SortedBag<Card> remainingCards = cards.difference(initialCards);
        SortedBag<Card> usableCards = SortedBag.of();

        boolean sameAsInitial = false;
        for (Card rc : remainingCards) {
            sameAsInitial = false;

            for (Card ic : initialCards) {
                if (rc.equals(ic) || rc.equals(Card.LOCOMOTIVE)) {
                    sameAsInitial = true;
                }
            }

            if (sameAsInitial)
                usableCards = usableCards.union(SortedBag.of(rc));
        }

        if (additionalCardsCount <= usableCards.size()) {

            Set<SortedBag<Card>> mySet = usableCards
                    .subsetsOfSize(additionalCardsCount);
            List<SortedBag<Card>> options = new ArrayList<>(mySet);
            options.sort(
                    Comparator.comparingInt(cs -> cs.countOf(Card.LOCOMOTIVE)));
            return options;

        }

        return List.of();
    }

    /**
     * 
     * @param route
     * @param claimCards
     * @return an identical state to the receiver, except that the player has
     *         also seized the given route by means of the given cards,
     */

    public PlayerState withClaimedRoute(Route route,
            SortedBag<Card> claimCards) {

        List<Route> route2 = new ArrayList<>(routes());
        route2.add(route);
        return new PlayerState(tickets, cards.difference(claimCards), route2);
    }

    /**
     * 
     * @return an integer, the number of points — possibly negative — obtained
     *         by the player thanks to his tickets
     */
    public int ticketPoints() {

        int maxId = -1;
        int maxIdStation = 0;
        for (Route r : routes()) {
            maxIdStation = Math.max(r.station1().id(), r.station2().id());
            maxId = Math.max(maxId, maxIdStation);
        }

        Builder builder = new Builder(maxId + 1);
        for (Route r : routes()) {
            builder.connect(r.station1(), r.station2());
        }

        StationPartition partition = builder.build();
        int points = 0;
        for (Ticket t : tickets) {
            points += t.points(partition);
            ticketsPoints.put(t, t.points(partition));
        }

        return points;
    }

    /**
     * 
     * @return an integer, returns all the points obtained by the player at the
     *         end of the game, namely the sum of the points returned by the
     *         claimPoints and ticketPoints methods
     */

    public int finalPoints() {
        return claimPoints() + ticketPoints();
    }

    public Map<Ticket, Integer> getTicketsPoints(){
        return ticketsPoints;
    }

}
