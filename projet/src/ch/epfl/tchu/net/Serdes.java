package ch.epfl.tchu.net;
/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */


import java.util.List;
import java.util.regex.Pattern;
import java.util.*;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import ch.epfl.tchu.gui.Language;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ch.epfl.tchu.game.PlayerId.*;

/**
 * The non instantiable class Serdes contains all the different Serdes used for different types.
 */
public final class Serdes {

    public static final String SEPARATOR_COMMA = ",";
    public static final String SEPARATOR_SEMICOLON = ";";
    public static final String SEPARATOR_TWO_POINTS = ":";

    private Serdes() {
    }

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize an Integer.
     */

    public static final Serde<Integer> INTEGER_SERDE = Serde.of(i -> Integer.toString(i), Integer::parseInt);

    /**
     * The attribute STRING_SERDE is used to (de)serialize an String.
     */

    public static final Serde<String> STRING_SERDE = Serde.of(i -> Base64.getEncoder().encodeToString(i.getBytes(UTF_8)),
            i -> new String(Base64.getDecoder().decode(i), UTF_8));

    /**
     * The attribute PLAYER_ID_SERDE is used to (de)serialize a PlayerId.
     */

    public static final Serde<PlayerId> PLAYER_ID_SERDE = Serde.oneOf(PlayerId.ALL);

    /**
     * The attribute LANGUAGE_SERDE is used to (de)serialize a language.
     */

    public static final Serde<Language> LANGUAGE_SERDE = Serde.oneOf(Language.ALL);

    /**
     * The attribute TURN_KIND_SERDE is used to (de)serialize a TurnKind.
     */

    public static final Serde<Player.TurnKind> TURN_KIND_SERDE = Serde.oneOf(Player.TurnKind.ALL);

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a Card.
     */

    public static final Serde<Card> CARD_SERDE = Serde.oneOf(Card.ALL);

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a Ticket.
     */

    public static final Serde<Ticket> TICKET_SERDE = Serde.oneOf(ChMap.tickets());

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a Route.
     */

    public static final Serde<Route> ROUTE_SERDE = Serde.oneOf(ChMap.routes());

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a list of Strings.
     */

    public static final Serde<List<String>> LIST_SERDE_STRING = Serde.listOf(STRING_SERDE, SEPARATOR_COMMA);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a list of Cards.
     */

    public static final Serde<List<Card>> LIST_SERDE_CARD = Serde.listOf(CARD_SERDE, SEPARATOR_COMMA);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a list of Routes.
     */

    public static final Serde<List<Route>> LIST_SERDE_ROUTE = Serde.listOf(ROUTE_SERDE, SEPARATOR_COMMA);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a bag of Cards.
     */

    public static final Serde<SortedBag<Card>> BAG_SERDE_CARD = Serde
            .bagOf(CARD_SERDE, SEPARATOR_COMMA);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a bag of Tickets.
     */

    public static final Serde<SortedBag<Ticket>> BAG_SERDE_TICKET = Serde.bagOf(TICKET_SERDE, SEPARATOR_COMMA);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a list of bag of Cards.
     */


    public static final Serde<List<SortedBag<Card>>> LIST_OF_BAG_CARD = Serde.listOf(BAG_SERDE_CARD, SEPARATOR_SEMICOLON);
    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a PublicCardState.
     */


    public static final Serde<PublicCardState> PUBLIC_CARD_STATE_SERDE = new Serde<>() {

        @Override
        public String serialize(PublicCardState cardState) {
            return String.join(SEPARATOR_SEMICOLON, LIST_SERDE_CARD.serialize(cardState.faceUpCards()),
                    INTEGER_SERDE.serialize(cardState.deckSize()), INTEGER_SERDE.serialize(cardState.discardsSize()));
        }

        @Override
        public PublicCardState deserialize(String s) {
            String[] string = s.split(Pattern.quote(SEPARATOR_SEMICOLON), -1);
            return new PublicCardState(LIST_SERDE_CARD.deserialize(string[0]),
                    INTEGER_SERDE.deserialize(string[1]), INTEGER_SERDE.deserialize(string[2]));
        }
    };

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a PublicPlayerState.
     */

    public static final Serde<PublicPlayerState> PUBLIC_PLAYER_STATE_SERDE = new Serde<>() {

        @Override
        public String serialize(PublicPlayerState playerState) {
            return String.join(SEPARATOR_SEMICOLON, INTEGER_SERDE.serialize(playerState.ticketCount()),
                    INTEGER_SERDE.serialize(playerState.cardCount()), LIST_SERDE_ROUTE.serialize(playerState.routes()));
        }

        @Override
        public PublicPlayerState deserialize(String s) {
            String[] string = s.split(Pattern.quote(SEPARATOR_SEMICOLON), -1);
            return new PublicPlayerState(INTEGER_SERDE.deserialize(string[0]),
                    INTEGER_SERDE.deserialize(string[1]), LIST_SERDE_ROUTE.deserialize(string[2]));
        }
    };

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a PlayerState.
     */

    public static final Serde<PlayerState> PLAYER_STATE_SERDE = new Serde<>() {

        @Override
        public String serialize(PlayerState playerState) {
            return String.join(SEPARATOR_SEMICOLON,
                    BAG_SERDE_TICKET.serialize(playerState.tickets()),
                    BAG_SERDE_CARD.serialize(playerState.cards()),
                    LIST_SERDE_ROUTE.serialize(playerState.routes()));
        }

        @Override
        public PlayerState deserialize(String s) {
            String[] string = s.split(Pattern.quote(SEPARATOR_SEMICOLON), -1);
            return new PlayerState(BAG_SERDE_TICKET.deserialize(string[0]),
                    BAG_SERDE_CARD.deserialize(string[1]),
                    LIST_SERDE_ROUTE.deserialize(string[2]));
        }
    };

    /**
     * The attribute INTEGER_SERDE is used to (de)serialize a PublicGameState.
     */

    public static final Serde<PublicGameState> PUBLIC_GAME_STATE_SERDE = new Serde<>() {

        @Override
        public String serialize(PublicGameState publicGameState) {
            List<String> allStrings = new ArrayList<>();

            allStrings.add(INTEGER_SERDE.serialize(publicGameState.ticketsCount()));
            allStrings.add(PUBLIC_CARD_STATE_SERDE.serialize(publicGameState.cardState()));
            allStrings.add(PLAYER_ID_SERDE.serialize(publicGameState.currentPlayerId()));
            allStrings.add(PUBLIC_PLAYER_STATE_SERDE.serialize(publicGameState.playerState(PlayerId.PLAYER_1)));
            allStrings.add(PUBLIC_PLAYER_STATE_SERDE.serialize(publicGameState.playerState(PlayerId.PLAYER_2)));
            allStrings.add(PUBLIC_PLAYER_STATE_SERDE.serialize(publicGameState.playerState(PlayerId.PLAYER_3)));

            allStrings.add((publicGameState.lastPlayer() == null) ? "" : PLAYER_ID_SERDE.serialize(publicGameState.lastPlayer()));

            return String.join(SEPARATOR_TWO_POINTS, allStrings);
        }

        @Override
        public PublicGameState deserialize(String s) {

            String[] string = s.split(Pattern.quote(SEPARATOR_TWO_POINTS), -1);
            int ticketsCount = INTEGER_SERDE.deserialize(string[0]);
            PublicCardState cardState = PUBLIC_CARD_STATE_SERDE.deserialize(string[1]);
            PlayerId currentPlayerId = PLAYER_ID_SERDE.deserialize(string[2]);
            PublicPlayerState statePlayer1 = PUBLIC_PLAYER_STATE_SERDE.deserialize(string[3]);
            PublicPlayerState statePlayer2 = PUBLIC_PLAYER_STATE_SERDE.deserialize(string[4]);
            PublicPlayerState statePlayer3 = PUBLIC_PLAYER_STATE_SERDE.deserialize(string[5]);
            PlayerId lastPlayer = (string[6].isEmpty()) ? null : PLAYER_ID_SERDE.deserialize(string[6]);
            Map<PlayerId, PublicPlayerState> playerState = Map.of(PlayerId.PLAYER_1, statePlayer1, PlayerId.PLAYER_2, statePlayer2, PlayerId.PLAYER_3, statePlayer3);

            return new PublicGameState(ticketsCount, cardState, currentPlayerId, playerState, lastPlayer);
        }
    };
}
