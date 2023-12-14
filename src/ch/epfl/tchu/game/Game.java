/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.*;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.Player.TurnKind;
import ch.epfl.tchu.game.Route.Level;
import ch.epfl.tchu.gui.Info;
import ch.epfl.tchu.gui.Language;

import static ch.epfl.tchu.game.Constants.*;
import static ch.epfl.tchu.game.PlayerId.*;

/**
 *
 * The public final class Game, represents the progress of a tCHu game. From the
 * beginning to the end.
 */
public final class Game {

    public static Language languageGame;
    private static boolean isFinished = false;

    private Game(){

    }

    /**
     * The method play runs the game, turn by turn, until one of the players win
     * the game and ends it.
     *
     * @param players
     *            The map of the players
     * @param playerNames
     *            The map of the players' names
     * @param tickets
     *            The game's available tickets
     * @param rng
     *            The random generator used to create the initial state of the
     *            game.
     * @Throws IllegalArgumentException if there aren't 2 players and 2 names.
     */

    public static void play(Map<PlayerId, Player> players,
                            Map<PlayerId, String> playerNames, SortedBag<Ticket> tickets,
                            Random rng,Language language) {
        Preconditions.checkArgument(players.size() == PlayerId.COUNT);
        Preconditions.checkArgument(playerNames.size() == PlayerId.COUNT);
        languageGame = language;
        GameState gameState = startGame(players, playerNames, tickets, rng);
        gameState = middleGame(players, playerNames, gameState, rng);
        endGame(players, playerNames, gameState);

    }

    /**
     * This method starts the game with its initial state, the players and the
     * start's tickets available.
     *
     * @param players
     *            The map of the players
     * @param playerNames
     *            The map of the players' names
     * @param tickets
     *            The game's available tickets
     * @param rng
     *            The random generator used to create the initial state of the
     *            game.
     * @return the initial game state.
     */

    private static GameState startGame(Map<PlayerId, Player> players,
                                       Map<PlayerId, String> playerNames, SortedBag<Ticket> tickets,
                                       Random rng) {
        players.forEach((k, v) -> v.initPlayers(k, playerNames, languageGame));

        GameState gameState = GameState.initial(tickets, rng);
        Info info1 = new Info(playerNames.get(gameState.currentPlayerId()));
        playersReceiveInfo(players, info1.willPlayFirst());

        for (Player p : players.values()) {
            p.setInitialTicketChoice(
                    gameState.topTickets(INITIAL_TICKETS_COUNT));
            gameState = gameState.withoutTopTickets(INITIAL_TICKETS_COUNT);
        }

        playersUpdateState(players, gameState);

        for (PlayerId Id : players.keySet())
            gameState = gameState.withInitiallyChosenTickets(Id,
                    players.get(Id).chooseInitialTickets());

        for (PlayerId Id : players.keySet()) {
            Info info2 = new Info(playerNames.get(Id));
            playersReceiveInfo(players,
                    info2.keptTickets(gameState.playerState(Id).ticketCount()));
        }
        return gameState;
    }

    /**
     *
     * @param players
     *            The map of the players
     * @param playerNames
     *            The map of the players' names
     * @param gameState
     *            The game's state
     * @param rng
     *            The random generator used to create the initial state of the
     *            game.
     * @return the game's state after a player has played its turn whether he
     *         chooses to draw a ticket, a card, or to claim a road.
     */

    private static GameState middleGame(Map<PlayerId, Player> players,
                                        Map<PlayerId, String> playerNames, GameState gameState,
                                        Random rng) {

        int lastTurns = PlayerId.COUNT;
        boolean lastTurnBegin = false;

        while (lastTurns >= 0) {

            Player currentPlayer = players.get(gameState.currentPlayerId());
            String currentPlayerName = playerNames
                    .get(gameState.currentPlayerId());
            Info info = new Info(currentPlayerName);
            playersReceiveInfo(players, info.canPlay());
            playersUpdateState(players, gameState);

            gameState = playerNextTurn(gameState, currentPlayer,
                    currentPlayerName, info, players, rng);

            if (gameState.lastTurnBegins())
                lastTurnBegin = true;

            if (lastTurnBegin) {
                if (lastTurns == PlayerId.COUNT) {
                    int c = gameState.playerState(gameState.currentPlayerId())
                            .carCount();
                    playersReceiveInfo(players, info.lastTurnBegins(c));
                }
                --lastTurns;
            }
            gameState = gameState.forNextTurn();

        }
        return gameState;

    }

    /**
     * This method ends the game and gives the necesessary informations to the
     * players. (Who won and who had the longest trail bonus).
     *
     * @param players
     *            The map of the players
     * @param playerNames
     *            The map of the players' names
     * @param gameState
     *            The previous game's state, just before the game ends, which
     *            means the game's state when the last turn was played..
     */

    private static void endGame(Map<PlayerId, Player> players,
                                Map<PlayerId, String> playerNames, GameState gameState) {

        playersUpdateState(players, gameState);

        Map<PlayerId, Integer> playerPoints  = new EnumMap<>(PlayerId.class);
        playerPoints.forEach((playerId, integer)-> integer = gameState.playerState(playerId).finalPoints());

        Info info1 = new Info(playerNames.get(PLAYER_1));
        Info info2 = new Info(playerNames.get(PLAYER_2));
        Info info3 = new Info(playerNames.get(PLAYER_3));

        Trail trail1 = Trail.longest(routes(gameState, PLAYER_1));
        Trail trail2 = Trail.longest(routes(gameState, PLAYER_2));
        Trail trail3 = Trail.longest(routes(gameState, PLAYER_3));

        playerPoints = compareTrail(trail1, trail2, trail3, playerPoints, info1, info2, info3, players);
        isFinished = true;
        List<String>  list = whoWon(playerNames, playerPoints, info1, info2, info3, players);
        players.forEach((k, v) -> v.showPodium(list.get(0), list.get(1), list.get(2)));

    }


    /**
     * Method used to give the progress of the player's next turn, whether he
     * decides to draw a card, a ticket, or claim a road.
     *
     * @param gameState
     *            the previous game's state. Which means the game's state before
     *            the player plays his turn.
     * @param currentPlayer
     *            the current player who will play the turn.
     * @param currentPlayerName
     *            the current player's name.
     * @param info
     *            The information that will be given to all the players.
     * @param players
     *            The map of the players
     * @param rng
     *            The random generator used to create the initial state of the
     *            game.
     * @return the game's state after the player has finished his turn.
     */

    private static GameState playerNextTurn(GameState gameState,
                                            Player currentPlayer, String currentPlayerName, Info info,
                                            Map<PlayerId, Player> players, Random rng) {
        TurnKind nextTurn = currentPlayer.nextTurn();

        switch (nextTurn) {

            case DRAW_TICKETS:

                playersReceiveInfo(players,
                        info.drewTickets(Constants.IN_GAME_TICKETS_COUNT));
                SortedBag<Ticket> drawnTickets = gameState
                        .topTickets(Constants.IN_GAME_TICKETS_COUNT);
                SortedBag<Ticket> tickets = currentPlayer
                        .chooseTickets(drawnTickets);
                playersReceiveInfo(players, info.keptTickets(tickets.size()));
                gameState = gameState.withChosenAdditionalTickets(drawnTickets,
                        tickets);

                break;

            case DRAW_CARDS:
                gameState = drawCards(gameState, currentPlayer.drawSlot(),
                        currentPlayerName, players, rng);
                playersUpdateState(players, gameState);
                gameState = drawCards(gameState, currentPlayer.drawSlot(),
                        currentPlayerName, players, rng);
                break;

            case CLAIM_ROUTE:
                gameState = claimRoute(gameState, currentPlayerName, players,
                        currentPlayer, rng);
                break;
        }

        return gameState;
    }

    /**
     *
     * Method used to draw a card for the player depending on the value of the
     * Integer i.
     *
     * @param gameState
     *            the current game's state, when the player decides to draw a
     *            card.
     * @param i
     *            An integer. If i equals -1, the player will draw blindly from
     *            the deck. If i is between 0 and 5 excluded, the player will
     *            draw between the 5 face up cards. He will get the i'th face up
     *            card.
     * @param currentPlayerName
     *            the current player's name.
     * @param players
     *            the map of the players.
     * @param rng
     *            The random generator used to generate the current state.
     * @return the game's state after the player drew a card.
     */

    private static GameState drawCards(GameState gameState, int i,
                                       String currentPlayerName, Map<PlayerId, Player> players,
                                       Random rng) {
        Info info = new Info(currentPlayerName);
        gameState = gameState.withCardsDeckRecreatedIfNeeded(rng);

        if (i == DECK_SLOT) {
            playersReceiveInfo(players, info.drewBlindCard());
            gameState = gameState.withBlindlyDrawnCard();
        } else {
            playersReceiveInfo(players,
                    info.drewVisibleCard(gameState.cardState().faceUpCard(i)));
            gameState = gameState.withDrawnFaceUpCard(i);
        }

        return gameState;
    }

    /**
     *
     * Method used to claim or not the road required by the player depending on
     * if he wants to claim it or not and if he has the cards required.
     *
     * @param gameState
     *            the current game's state.
     * @param currentPlayerName
     *            the current player's name.
     * @param players
     *            the map of the players.
     * @param currentPlayer
     *            The current player playing his turn.
     * @param rng
     *            The random generator used to generate the current state.
     *
     * @return The game's state after the player claimed the road.
     */

    private static GameState claimRoute(GameState gameState,
                                        String currentPlayerName, Map<PlayerId, Player> players,
                                        Player currentPlayer, Random rng) {

        Info info = new Info(currentPlayerName);
        Route claimedRoute = currentPlayer.claimedRoute();
        SortedBag<Card> initialCards = currentPlayer.initialClaimCards();
        SortedBag<Card> drawnCards = SortedBag.of(), drawnCard = SortedBag.of();
        SortedBag<Card> chosenCards = SortedBag.of();
        List<SortedBag<Card>> options = List.of();
        int addCardsCount = 0;

        if (claimedRoute.level().equals(Level.UNDERGROUND)) {

            playersReceiveInfo(players,
                    info.attemptsTunnelClaim(claimedRoute, initialCards));

            for (int i = 0; i < Constants.ADDITIONAL_TUNNEL_CARDS; ++i) {
                gameState = gameState.withCardsDeckRecreatedIfNeeded(rng);
                drawnCard = SortedBag.of(gameState.topCard());
                drawnCards = drawnCards.union(drawnCard);
                gameState = gameState.withoutTopCard()
                        .withMoreDiscardedCards(drawnCard);
            }

            addCardsCount = claimedRoute.additionalClaimCardsCount(initialCards,
                    drawnCards);

            if (addCardsCount > 0) {
                options = gameState.currentPlayerState()
                        .possibleAdditionalCards(addCardsCount, initialCards);

                if (!options.isEmpty())
                    chosenCards = currentPlayer.chooseAdditionalCards(options);
            }
        }

        if (!chosenCards.isEmpty()) {
            SortedBag<Card> cards = initialCards.union(chosenCards);
            playersReceiveInfo(players,
                    info.drewAdditionalCards(drawnCards, addCardsCount));
            playersReceiveInfo(players, info.claimedRoute(claimedRoute,
                    cards));
            gameState = gameState.withClaimedRoute(claimedRoute,
                    cards);

        } else if (addCardsCount == 0) {
            playersReceiveInfo(players,
                    info.claimedRoute(claimedRoute, initialCards));
            gameState = gameState.withClaimedRoute(claimedRoute, initialCards);

        } else
            playersReceiveInfo(players, info.didNotClaimRoute(claimedRoute));

        return gameState;
    }

    /**
     * Method distributing the needed informations to all the players
     *
     * @param players
     *            The map of the players.
     * @param info
     *            The information given to all the players.
     */

    private static void playersReceiveInfo(Map<PlayerId, Player> players,
                                           String info) {
        players.forEach((k, v) -> v.receiveInfo(info));
    }

    /**
     * Method that updates the state each time it is called.
     *
     * @param players
     *            The map of the players.
     * @param newState
     *            The new game's state after the update.
     */

    private static void playersUpdateState(Map<PlayerId, Player> players,
                                           GameState newState) {
        players.forEach(
                (k, v) -> v.updateState(newState, newState.playerState(k)));
    }


    private static Map<PlayerId, Integer> compareTrail(Trail trail1, Trail trail2, Trail trail3,  Map<PlayerId, Integer> playerPoints, Info info1, Info info2, Info info3, Map<PlayerId, Player> players){
        int length1 = trail1.length();
        int length2 = trail2.length();
        int length3 = trail3.length();

        Map<PlayerId, Integer> newPlayerPoints = new EnumMap<>(PlayerId.class);
        newPlayerPoints.forEach((playerId, integer)-> integer = playerPoints.get(playerId));

        if(length1 >= length2 && length1 >= length3){
            newPlayerPoints.replace(PLAYER_1, newPlayerPoints.get(PLAYER_1) + LONGEST_TRAIL_BONUS_POINTS);
            playersReceiveInfo(players, info1.getsLongestTrailBonus(trail1));
        }
        if(length2 >= length1 && length2 >= length3) {
            newPlayerPoints.replace(PLAYER_2, newPlayerPoints.get(PLAYER_2) + LONGEST_TRAIL_BONUS_POINTS);
            playersReceiveInfo(players, info2.getsLongestTrailBonus(trail2));
        }
        if(length3 >= length1 && length3 >= length2){
            newPlayerPoints.replace(PLAYER_3, newPlayerPoints.get(PLAYER_3) + LONGEST_TRAIL_BONUS_POINTS);
            playersReceiveInfo(players, info3.getsLongestTrailBonus(trail3));
        }
        return newPlayerPoints;
    }

    /**
     *
     * @param playerNames, the player names
     * @param playerPoints, the map of the player with their points
     * @param info1, the info of the first player
     * @param info2, the info of the second player
     * @param info3, the info of the third player
     * @param players, the map of the players
     * @return a list of the name of the players in the order of the podium
     */

    private static List<String> whoWon (Map<PlayerId, String> playerNames, Map<PlayerId, Integer> playerPoints, Info info1, Info info2, Info info3, Map<PlayerId, Player> players){
        int player1Points = playerPoints.get(PLAYER_1);
        int player2Points = playerPoints.get(PLAYER_2);
        int player3Points = playerPoints.get(PLAYER_3);
        String PLAYER1 = playerNames.get(PLAYER_1);
        String PLAYER2 = playerNames.get(PLAYER_2);
        String PLAYER3 = playerNames.get(PLAYER_3);
        List <String> podium = new ArrayList<>();

        if (player1Points > player2Points && player2Points > player3Points){
            playersReceiveInfo(players, info1.won(player1Points, player2Points, player3Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);
        }

        if (player1Points > player3Points && player3Points > player2Points) {
            playersReceiveInfo(players, info1.won(player1Points, player3Points, player2Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);
        }

        if (player2Points > player1Points && player1Points > player3Points) {
            playersReceiveInfo(players, info2.won(player2Points, player1Points, player3Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);
        }

        if (player2Points > player3Points && player3Points > player1Points) {
            playersReceiveInfo(players, info2.won(player2Points, player3Points, player1Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);
        }

        if (player3Points > player2Points && player2Points > player1Points) {
            playersReceiveInfo(players, info3.won(player3Points, player2Points, player1Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);

        }

        if (player3Points > player1Points && player1Points > player2Points) {
            playersReceiveInfo(players, info3.won(player3Points, player1Points, player2Points));
            podium.add(PLAYER1);
            podium.add(PLAYER2);
            podium.add(PLAYER3);
        }

        return podium;

    }

    private static List<Route> routes(GameState gameState, PlayerId playerId){ return gameState.playerState(playerId).routes();}

    /**
     *
     * @return true if the game is finished, the last turn also finished
     */
    public static boolean gameIsFinishedCompletely(){
        return isFinished;
    };

}

