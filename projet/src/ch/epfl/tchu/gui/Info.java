/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.Card;
import ch.epfl.tchu.game.Route;
import ch.epfl.tchu.game.Trail;
import static ch.epfl.tchu.gui.StringsFrEnSp.*;

public final class Info {


    private final String playerName;
    private final String playerName2;
    private final String playerName3;

    public Info(String playerName) {
        this(playerName, null, null);
    }

    public Info(String playerName, String playerName2, String playerName3) {
        this.playerName = playerName;
        this.playerName2 = playerName2;
        this.playerName3 = playerName3;
    }

    public static String cardName(Card card, int count) {

        switch (card) {
            case BLACK:
                return BLACK_CARD() + plural(count);
            case VIOLET:
                return VIOLET_CARD() + plural(count);
            case BLUE:
                return BLUE_CARD() + plural(count);
            case GREEN:
                return GREEN_CARD() + plural(count);
            case YELLOW:
                return YELLOW_CARD() + plural(count);
            case ORANGE:
                return ORANGE_CARD() + plural(count);
            case RED:
                return RED_CARD() + plural(count);
            case WHITE:
                return WHITE_CARD() + plural(count);
            case LOCOMOTIVE:
                return LOCOMOTIVE_CARD() + plural(count);
            default:
                throw new Error();
        }
    }

    public static String draw(List<String> playerNames, int points) {

        String names = "";

        if (playerNames.size() == 1)
            names = playerNames.get(0);

        if (playerNames.size() == 2)
            names = playerNames.get(0) + AND_SEPARATOR
                    + playerNames.get(1);

        if (playerNames.size() > 2) {

            for (int i = 0; i < playerNames.size() - 2; i++) {
                names += playerNames.get(i) + ", ";
            }
            names += playerNames.get(playerNames.size() - 2)
                    + AND_SEPARATOR
                    + playerNames.get(playerNames.size() - 1);
        }

        return String.format(DRAW(), names, points);
    }

    public String willPlayFirst() {
        return String.format(WILL_PLAY_FIRST(), playerName);
    }

    public String keptTickets(int count) {

        return String.format(KEPT_N_TICKETS(), playerName, count,
                plural(count));

    }

    public String canPlay() {

        return String.format(CAN_PLAY(), playerName);
    }

    public String drewTickets(int count) {

        return String.format(DREW_TICKETS(), playerName, count, plural(count));
    }

    public String drewBlindCard() {

        return String.format(DREW_BLIND_CARD(), playerName);
    }

    public String drewVisibleCard(Card card) {

        return String.format(DREW_VISIBLE_CARD(), playerName,
                cardName(card, 1));
    }

    public String claimedRoute(Route route, SortedBag<Card> cards) {

        return String.format(
                CLAIMED_ROUTE(), playerName, route.station1()
                        + EN_DASH_SEPARATOR + route.station2(),
                cards(cards));
    }

    public String attemptsTunnelClaim(Route route,
                                      SortedBag<Card> initialCards) {

        return String.format(
                ATTEMPTS_TUNNEL_CLAIM(), playerName, route.station1()
                        + EN_DASH_SEPARATOR + route.station2(),
                cards(initialCards));
    }

    public String drewAdditionalCards(SortedBag<Card> drawnCards,
                                      int additionalCost) {

        if (additionalCost == 0) {
            return String.format(ADDITIONAL_CARDS_ARE(),
                    cards(drawnCards)) + NO_ADDITIONAL_COST();
        } else {
            return String.format(ADDITIONAL_CARDS_ARE(),
                    cards(drawnCards))
                    + String.format(SOME_ADDITIONAL_COST(),
                    additionalCost, plural(additionalCost));
        }
    }

    public String didNotClaimRoute(Route route) {

        return String.format(DID_NOT_CLAIM_ROUTE(), playerName,
                route.station1() + EN_DASH_SEPARATOR
                        + route.station2());
    }

    public String lastTurnBegins(int carCount) {

        return String.format(LAST_TURN_BEGINS(), playerName, carCount,
                plural(carCount));
    }

    public String getsLongestTrailBonus(Trail longestTrail) {

        return String.format(GETS_BONUS(), playerName,
                longestTrail.station1() + EN_DASH_SEPARATOR
                        + longestTrail.station2());
    }

    public String won(int points, int loserPoints1, int loserPoints2) {

        return String.format(WINS(), playerName, points,
                plural(points),playerName2, loserPoints1,
                plural(loserPoints1), playerName3, loserPoints2, plural(loserPoints2));
    }

    public static String cards(SortedBag<Card> cards) {

        String string = "";

        List<String> card = new ArrayList<>();

        for (Card c : cards.toSet()) {
            int n = cards.countOf(c);
            card.add(n + " " + cardName(c, n));
        }

        if (card.size() == 1)
            string = card.get(0);

        if (card.size() == 2)
            string = card.get(0) + AND_SEPARATOR + card.get(1);

        if (card.size() > 2) {
            for (int i = 0; i < card.size() - 2; i++)
                string += card.get(i) + ", ";

            string += card.get(card.size() - 2) + AND_SEPARATOR
                    + card.get(card.size() - 1);
        }

        return string;
    }
}
