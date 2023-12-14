/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import java.util.*;
import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import static ch.epfl.tchu.game.Constants.FACE_UP_CARD_SLOTS;
import static ch.epfl.tchu.game.ChMap.*;

/**
 * This class represents the observable state of a part of tCHu. This is a combined state that includes:
 * the public part of the state of the game and the entire state of a given player.
 */

public class ObservableGameState {

    private PlayerState playerState;
    private PublicGameState gameState;
    private final PlayerId playerId;

    private final IntegerProperty percentageTickets;
    private final IntegerProperty percentageCards;
    private final List<ObjectProperty<Card>> faceUpCards;
    private final Map<Route, ObjectProperty<PlayerId>> mapRoutes;

    private final Map<PlayerId, IntegerProperty> ticketsCount;
    private final Map<PlayerId, IntegerProperty> cardsCount;
    private final Map<PlayerId, IntegerProperty> carsCount;
    private final Map<PlayerId, IntegerProperty> claimPoints;

    private final ObservableList<Ticket> tickets;
    private final Map<Card, IntegerProperty> cards;
    private final Map<Route, BooleanProperty> canClaimRoute;
    private final static int PERCENTAGE_FACTOR = 100;

    private final Map<PlayerId, ObjectProperty<Trail>> longestTrails;
    private final ObservableList<Text> ticketsWithPoints;

    public ObservableGameState(PlayerId playerId) {
        this.playerId = playerId;
        percentageTickets = createPercentageTickets();
        percentageCards = createPercentageCard();
        faceUpCards = createFaceUpCards();
        mapRoutes = createMap();
        ticketsCount = createTicketsCount();
        cardsCount = createCardsCount();
        carsCount = createCarsCount();
        claimPoints = createClaimPoints();
        tickets = createTickets();
        cards = createCards();
        canClaimRoute = createCanClaimRoute();
        longestTrails = createLongestTrails();
        ticketsWithPoints = createTicketWithPoints();

    }

    /**
     *
     * @param newGameState, the new game state
     * @param newPlayerState, the new player state
     *
     * This method updates all of the properties described below based on these two states.
     */

    public void setState(PublicGameState newGameState,
                         PlayerState newPlayerState) {

        gameState = newGameState;
        playerState = newPlayerState;

        percentageTickets.set(newGameState.ticketsCount() * PERCENTAGE_FACTOR / tickets().size());
        percentageCards.set(newGameState.cardState().deckSize() * PERCENTAGE_FACTOR / Constants.TOTAL_CARDS_COUNT);

        for (int slot : FACE_UP_CARD_SLOTS)
            faceUpCards.get(slot).set(newGameState.cardState().faceUpCard(slot));

        for(Route r : gameState.claimedRoutes()){
            for(PlayerId id : PlayerId.ALL){
                if(gameState.playerState(id).routes().contains(r))
                    mapRoutes.get(r).set(id);
            }
        }

        for (PlayerId id : PlayerId.values()) {
            ticketsCount.get(id).set(gameState.playerState(id).ticketCount());
            cardsCount.get(id).set(gameState.playerState(id).cardCount());
            carsCount.get(id).set(gameState.playerState(id).carCount());
            claimPoints.get(id).set(gameState.playerState(id).claimPoints());
        }

        tickets.setAll(newPlayerState.tickets().toList());
        for (Card c : cards.keySet()) cards.get(c).set(newPlayerState.cards().countOf(c));

        for (Route route : canClaimRoute.keySet()) {
            boolean IsRoutePlayerIdNull = mapRoutes.get(route).getValue() == null;
            if (getRouteDouble().containsKey(route)) {
                IsRoutePlayerIdNull = (mapRoutes.get(getRouteDouble().get(route)).getValue() != playerId) && IsRoutePlayerIdNull;
            }
            canClaimRoute.get(route).set(newGameState.currentPlayerId().equals(playerId) && IsRoutePlayerIdNull && newPlayerState.canClaimRoute(route));
        }

        List<Text> list = new ArrayList<>();
        newPlayerState.ticketPoints();
        for(Ticket ticket : newPlayerState.tickets()){
            Text text = new Text();
            text.textProperty().bind((Bindings.format("You have %s points for %s", playerState.getTicketsPoints().get(ticket), ticket)));
            list.add(text);
        }
        ticketsWithPoints.setAll(list);

    }

    private ObservableList<Text> createTicketWithPoints() {
        return FXCollections.observableArrayList();
    }

    /**
     *
     * @return an observableList of the tickets of the player with their current points
     */

    public ObservableList<Text> getTicketsWithPoints(){
        return ticketsWithPoints;
    }

    private static Map<PlayerId, ObjectProperty<Trail>> createLongestTrails() {
        Map<PlayerId, ObjectProperty<Trail>> map = new EnumMap<>(PlayerId.class);
        for (PlayerId id: PlayerId.ALL) map.put(id , new SimpleObjectProperty<>());
        return map;
    }

    /**
     *
     * @param id, the player id
     * @return the longest trail of the player id
     */

    public ReadOnlyObjectProperty<Trail> getLongestTrails(PlayerId id) {
        return longestTrails.get(id);
    }


    private static IntegerProperty createPercentageTickets() {
        return new SimpleIntegerProperty();
    }

    /**
     * @return a property containing the percentage of tickets remaining in the draw pile
     */

    public ReadOnlyIntegerProperty getPercentageTickets() {
        return percentageTickets;
    }

    private static IntegerProperty createPercentageCard() {
        return new SimpleIntegerProperty();
    }

    /**
     * @return a property containing the percentage of cards remaining in the deck
     */

    public ReadOnlyIntegerProperty getPercentageCards() {
        return percentageCards;
    }

    private static List<ObjectProperty<Card>> createFaceUpCards() {
        List<ObjectProperty<Card>> list = new ArrayList<>();
        for (int slot: FACE_UP_CARD_SLOTS) list.add(new SimpleObjectProperty<>());
        return list;
    }

    /**
     * @param slot, the index of the faceUpCard
     * @return a property of the face up card contained in the index slot
     */
    public ReadOnlyObjectProperty<Card> faceUpCard(int slot) {
        return faceUpCards.get(slot);
    }

    private static Map<Route, ObjectProperty<PlayerId>> createMap() {
        Map<Route, ObjectProperty<PlayerId>> map = new HashMap<>();
        for (Route r : routes())  map.put(r, new SimpleObjectProperty<>());
        return map;
    }

    /**
     * @param route, the given route
     * @return a property containing the identity of the player of the given route
     */

    public ReadOnlyObjectProperty<PlayerId> getRouteId(Route route) {
        return mapRoutes.get(route);
    }

    private static Map<PlayerId, IntegerProperty> createTicketsCount() {
        Map<PlayerId, IntegerProperty> map = new EnumMap<>(PlayerId.class);
        for (PlayerId id : PlayerId.values()) map.put(id, new SimpleIntegerProperty());
        return map;
    }

    /**
     * @param playerId, the player id
     * @return a property containing the number of tickets of the given player id
     */

    public ReadOnlyIntegerProperty getTicketCount(PlayerId playerId) {
        return ticketsCount.get(playerId);
    }

    private static Map<PlayerId, IntegerProperty> createCardsCount() {
        Map<PlayerId, IntegerProperty> map = new EnumMap<>(PlayerId.class);
        for (PlayerId id : PlayerId.values()) map.put(id, new SimpleIntegerProperty());
        return map;
    }

    /**
     * @param playerId, the player id
     * @return a property containing the number of cards of the given player id
     */

    public ReadOnlyIntegerProperty getCardsCount(PlayerId playerId) {
        return cardsCount.get(playerId);
    }

    private static Map<PlayerId, IntegerProperty> createCarsCount() {
        Map<PlayerId, IntegerProperty> map = new EnumMap<>(PlayerId.class);
        for (PlayerId id : PlayerId.values()) map.put(id, new SimpleIntegerProperty());
        return map;
    }

    /**
     * @param playerId, the player id
     * @return a property containing the number of cars of the given player id
     */

    public ReadOnlyIntegerProperty getCarsCount(PlayerId playerId) {
        return carsCount.get(playerId);
    }

    private static Map<PlayerId, IntegerProperty> createClaimPoints() {
        Map<PlayerId, IntegerProperty> map = new EnumMap<>(PlayerId.class);
        for (PlayerId id : PlayerId.values()) map.put(id, new SimpleIntegerProperty());
        return map;
    }

    /**
     * @param playerId, the player id
     * @return a property containing the number of claim points of the given player id
     */

    public ReadOnlyIntegerProperty getClaimPoints(PlayerId playerId) {
        return claimPoints.get(playerId);
    }

    private static ObservableList<Ticket> createTickets() {
        return FXCollections.observableArrayList();
    }

    /**
     * @return a property containing the list of player tickets
     */

    public ObservableList<Ticket> getTickets() {
        return FXCollections.unmodifiableObservableList(tickets);
    }

    private static Map<Card, IntegerProperty> createCards() {
        Map<Card, IntegerProperty> map = new EnumMap<>(Card.class);
        for (Card card : Card.ALL) map.put(card, new SimpleIntegerProperty());
        return map;
    }

    /**
     * @param card, the given card
     * @return an integer property containing the number of cards of the given type cards that the player has in hand
     */

    public IntegerProperty getNumberOfCard(Card card) {
        return cards.get(card);
    }

    private static Map<Route, BooleanProperty> createCanClaimRoute() {
        Map<Route, BooleanProperty> map = new HashMap<>();
        for (Route route : routes()) map.put(route, new SimpleBooleanProperty());
        return map;
    }

    /**
     * @param route, the given route
     * @return a boolean property that returns true if the player can claim the given route
     */

    public ReadOnlyBooleanProperty canClaimRoute(Route route) {
        return canClaimRoute.get(route);
    }

    /**
     * @return a boolean property that returns true if the player can draw tickets
     */

    public boolean canDrawTickets() {
        return gameState.canDrawTickets();
    }

    /**
     * @return a boolean property that returns true if the player can draw cards
     */

    public boolean canDrawCards() {
        return gameState.canDrawCards();
    }

    /**
     * @param route, the given route
     * @return the list of all the sets of cards that the player could use to take possession of the given route,
     */

    public List<SortedBag<Card>> possibleClaimCards(Route route) {
        return playerState.possibleClaimCards(route);
    }

    /**
     *
     * @return a map of all routes and if its a double route, the corresponding double route
     */

    private static Map<Route, Route> getRouteDouble() {
        Map<Route, Route> routeDouble = new HashMap<>();
        for (Route r1 : routes()) {
            for (Route r2 : routes()) {
                if (r1 != r2 && r1.station1().equals(r2.station1()) && r1.station2().equals(r2.station2())) {
                    routeDouble.put(r1, r2);
                }
            }
        }
        return routeDouble;
    }

}
