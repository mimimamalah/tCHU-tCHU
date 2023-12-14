/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.game;

import java.util.List;
import java.util.Map;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.gui.*;

/**
 * The public interface player represents a tCHu game player.
 */
public interface Player {

    enum TurnKind {
        DRAW_TICKETS, DRAW_CARDS, CLAIM_ROUTE;

        /**
         * ALL is the list of all the turn kind values.
         */

        final public static List<TurnKind> ALL = List.of(TurnKind.values());
    }

    /**
     * Called at the beginning of the game to communicate to the player his own
     * identity.
     *
     * @param ownId       The player's own identity
     * @param playerNames the names of all the players.
     * @param language
     */

    void initPlayers(PlayerId ownId, Map<PlayerId, String> playerNames, Language language);

    /**
     * Called when an information needs to be transmitted to the player.
     *
     * @param info The information which has to be communicated to the player.
     */

    void receiveInfo(String info);

    /**
     * Called each time the game's state evolves.
     *
     * @param newState Represents the new public Game State, when it changes.
     * @param ownState The player's own state when the game's state changes.
     */

    void updateState(PublicGameState newState, PlayerState ownState);

    /**
     * Called at the beginning of the game to communicate to the player the five
     * tickets he has been distributed.
     *
     * @param tickets The five tickets distributed to the player.
     */

    void setInitialTicketChoice(SortedBag<Ticket> tickets);

    /**
     * Called method to the player which one of the distributed tickets, he
     * wants to keep.
     */

    SortedBag<Ticket> chooseInitialTickets();

    /**
     * Called to know which action will take the player the next turn.
     */

    TurnKind nextTurn();

    /**
     * @param options The choices given to the player from which he will choose
     *                those to keep. Called at the beginning of the turn.
     */

    SortedBag<Ticket> chooseTickets(SortedBag<Ticket> options);

    /**
     * Called when the player has decided to draw wagon / locomotive cards,
     * in order to know where he wishes to draw them from: one of the spaces
     * containing a face-up card or the deck
     */

    int drawSlot();

    /**
     * Called when the player decides to claim a road to know which one he
     * chose.
     */

    Route claimedRoute();

    /**
     * Called when the player decides to claim a road to know which cards he
     * wants to use to do it.
     */

    SortedBag<Card> initialClaimCards();

    /**
     * Called when the player decides to claim a tunnel and he need additional
     * cards to do it, to know which cards he wants to use to do that.
     *
     * @param options The possibilities that he can use to claim the tunnel. If the
     *                List of options is empty, this means that the player doesn't
     *                claim the tunnel.
     */

    SortedBag<Card> chooseAdditionalCards(List<SortedBag<Card>> options);

    /**
     *
     * @return the option in the menu chosen by the player in English
     */

    MenuOptionsEnglish chooseMenu();

    /**
     *
     * @return the option in the menu chosen by the player in French
     */

    MenuOptionsFrench chooseMenuFr();

    /**
     *
     * @return the option in the menu chosen by the player in Spanish
     */

    MenuOptionsSpanish chooseMenuSp();

    /**
     *
     * @return the language chosen by the player
     */

    Language chooseLanguage();

    /**
     *
     * @return the pseudo of the player
     */

    String choosePseudo();

    /**
     * Called when the player wants to read the rules in English
     * @return the rule of the game in the intro chosen by the player
     */

    MenuOptionsRulesIntro chooseMenuRulesIntro();

    /**
     * Called when the player wants to read the rules in French
     * @return the rule of the game in the intro chosen by the player
     */
    MenuOptionsRulesFr chooseMenuRulesFr();

    /**
     * Called when the player wants to read the rules in Spanish
     * @return the rule of the game in the intro chosen by the player
     */
    MenuOpcionesReglas chooseMenuRulesSp();


    /**
     * Called when the player want to go back to menu of the different rules
     *  when he was at the tickets rule explication
     * @return the Back option
     */
    String chooseMenuRulesTickets();

    /**
     * Called when the player want to go back to menu of the different rules
     * when he was at the draw card rule explication
     * @return the Back option
     */
    String chooseMenuRulesDraw();

    /**
     * Called when the player want to go back to menu of the different rules
     * when he was at the routes rules explication
     * @return the Back option
     */
    String chooseMenuRulesClaim();

    /**
     * Called at the end of the game and show the podium of the winners
     * @param first, the first player who won the game
     * @param second, the second player
     * @param third, the third player
     */
    void showPodium(String first, String second, String third);

}
