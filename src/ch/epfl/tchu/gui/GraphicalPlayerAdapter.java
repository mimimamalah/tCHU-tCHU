/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static javafx.application.Platform.*;
import ch.epfl.tchu.gui.ActionHandlers.DrawCardHandler;
import ch.epfl.tchu.gui.ActionHandlers.ClaimRouteHandler;
import ch.epfl.tchu.gui.ActionHandlers.DrawTicketsHandler;

/**
 * This class adapts an instance of a graphical player in order to make it a player type.
 */
public final class GraphicalPlayerAdapter implements Player {

    private GraphicalPlayer graphicalPlayer;
    private FirstGraphicalPlayer firstGraphicalPlayer;
    private final BlockingQueue<SortedBag<Ticket>> playerTicketsChoice;
    private final BlockingQueue<Integer> playerCardChoice;
    private final BlockingQueue<SortedBag<Card>> initialClaimCards;
    private DrawCardHandler drawCardHandler;
    private ClaimRouteHandler claimRouteHandler;
    private DrawTicketsHandler drawTicketsHandler;
    private final BlockingQueue<Route> playerRoutes;
    private final BlockingQueue<TurnKind> turnKind;
    private final BlockingQueue<SortedBag<Card>> additionalCards;
    private final BlockingQueue<GraphicalPlayer> queuePlayer;
    private final BlockingQueue<FirstGraphicalPlayer> queuePlayerFirst;
    private final BlockingQueue<MenuOptionsEnglish> menuChoice;
    private final BlockingQueue<MenuOptionsFrench> menuChoiceFr;
    private final BlockingQueue<MenuOptionsSpanish> menuChoiceSp;
    private final BlockingQueue<Language> languageChoice;
    private final BlockingQueue<String> playerName;
    private final BlockingQueue<MenuOptionsRulesIntro> menuRulesIntro;
    private final BlockingQueue<String> menuRulesTicket;
    private final BlockingQueue<String> menuRulesClaim;
    private final BlockingQueue<String> menuRulesDraw;
    private final BlockingQueue<MenuOptionsRulesFr> menuRulesFr;
    private final BlockingQueue<MenuOpcionesReglas> menuRulesSp;

    /**
     * Builds a graphical player adapter.
     */
    public GraphicalPlayerAdapter() {
        playerTicketsChoice = new ArrayBlockingQueue<>(1);
        playerCardChoice = new ArrayBlockingQueue<>(1);
        playerRoutes = new ArrayBlockingQueue<>(1);
        turnKind = new ArrayBlockingQueue<>(1);
        initialClaimCards = new ArrayBlockingQueue<>(1);
        additionalCards = new ArrayBlockingQueue<>(1);
        queuePlayer = new ArrayBlockingQueue<>(1);
        menuChoice = new ArrayBlockingQueue<>(1);
        menuChoiceFr = new ArrayBlockingQueue<>(1);
        languageChoice = new ArrayBlockingQueue<>(1);
        queuePlayerFirst = new ArrayBlockingQueue<>(1);
        menuChoiceSp = new ArrayBlockingQueue<>(1);
        playerName = new ArrayBlockingQueue<>(1);
        menuRulesIntro = new ArrayBlockingQueue<>(1);
        menuRulesTicket = new ArrayBlockingQueue<>(1);
        menuRulesClaim = new ArrayBlockingQueue<>(1);
        menuRulesDraw = new ArrayBlockingQueue<>(1);
        menuRulesFr = new ArrayBlockingQueue<>(1);
        menuRulesSp = new ArrayBlockingQueue<>(1);

    }

    /**
     *
     * @param ownId The player's own identity
     * @param playerNames the map giving to each player its corresponding name.
     * Builds the graphical player's instance on the JavaFx field.
     */
    @Override
    public void initPlayers(PlayerId ownId, Map<PlayerId, String> playerNames, Language language) {
        runLater(() ->{
            queuePlayer.add(new GraphicalPlayer(ownId, playerNames,language)); });
        try {
            graphicalPlayer = queuePlayer.take();
        } catch (InterruptedException e) {
            throw new Error();
        }

    }

    /**
     * @param info The information given to the players.
     * Calls, on the field of JavaFx, the same graphical player's method.
     */

    @Override
    public void receiveInfo(String info) {
        runLater(() -> graphicalPlayer.receiveInfo(info));
    }

    /**
     * @param newState
     *            Represents the new public Game State, when it changes.
     * @param ownState the player's own state
     * Updates the game each time a change occurs (On the JavaFx field).
     */

    @Override
    public void updateState(PublicGameState newState, PlayerState ownState) {
        runLater(() -> graphicalPlayer.setState(newState, ownState));
    }

    /**
     * @param tickets the choices of tickets.
     * Gives the player a choice of tickets and asks him to choose (on the JavaFx field).
     */

    @Override
    public void setInitialTicketChoice(SortedBag<Ticket> tickets) {
        runLater(() -> graphicalPlayer.chooseTickets(tickets, (e) -> playerTicketsChoice.add(e)));
    }

    /**
     * @return the player's choice of tickets.
     * Blocks until the player has made his choices.
     */

    @Override
    public SortedBag<Ticket> chooseInitialTickets() {
        try {
            return playerTicketsChoice.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @return the next turn's type of action.
     * Blocks until the player has decided to play his next turn and has made a choice of action.
     */

    @Override
    public TurnKind nextTurn() {
        drawTicketsHandler = ()-> turnKind.add(TurnKind.DRAW_TICKETS);
        drawCardHandler = (i) -> {
            playerCardChoice.add(i);
            turnKind.add(TurnKind.DRAW_CARDS);
        };
        claimRouteHandler = (r, cs) ->{
            playerRoutes.add(r);
            initialClaimCards.add(cs);
            turnKind.add(TurnKind.CLAIM_ROUTE);
        } ;

        runLater(() -> graphicalPlayer.startTurn(drawTicketsHandler, drawCardHandler, claimRouteHandler));

        try {
            return turnKind.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @param options
     *            The choices given to the player from which he will choose
     * @return the choice of tickets of the player.
     */
    @Override
    public SortedBag<Ticket> chooseTickets(SortedBag<Ticket> options) {

        runLater(() -> graphicalPlayer.chooseTickets(options, (e) -> playerTicketsChoice.add(e)));

        try {
            return playerTicketsChoice.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @return the place of the card that has been drew.
     * Determines if the player has drew for the first or second time.
     * If it is the first time, it returns the index of the first drew card.
     * If it is the second time, it returns the index of the second drew after blocking the queue.
     */

    @Override
    public int drawSlot() {

        if (playerCardChoice.isEmpty())
            runLater(() -> graphicalPlayer.drawCard(playerCardChoice::add));

        try {
            return playerCardChoice.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @return the claimed route by the player.
     */

    @Override
    public Route claimedRoute() {
        //Media sound = new Media(getClass().getResource("train.mp3").toExternalForm());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
        try {
            return playerRoutes.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @return the initial cards used to claim a route by the player.
     */
    @Override
    public SortedBag<Card> initialClaimCards() {
        try {
            return initialClaimCards.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    /**
     * @param options
     *            The possibilities that he can use to claim the tunnel. If the
     *            List of options is empty, this means that the player doesn't
     * @return the additional cards used by the player to claim a route.
     */

    @Override
    public SortedBag<Card> chooseAdditionalCards(List<SortedBag<Card>> options) {
        runLater(()-> graphicalPlayer.chooseAdditionalCards(options,(e)-> additionalCards.add(e)));

        try {
            return additionalCards.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOptionsEnglish chooseMenu() {
        runLater(() -> firstGraphicalPlayer.showMenuEnglish((e)-> menuChoice.add(e)));

        try {
            return menuChoice.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOptionsFrench chooseMenuFr() {
        runLater(() -> firstGraphicalPlayer.showMenuFr((e)-> menuChoiceFr.add(e)));

        try {
            return menuChoiceFr.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOptionsSpanish chooseMenuSp() {
        runLater(() -> firstGraphicalPlayer.showMenuSP((e)-> menuChoiceSp.add(e)));

        try {
            return menuChoiceSp.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public Language chooseLanguage() {

        runLater(()-> firstGraphicalPlayer.showLanguage((e)-> languageChoice.add(e)));

        try {
            return languageChoice.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }


    @Override
    public String choosePseudo() {

        runLater(() -> { queuePlayerFirst.add(new FirstGraphicalPlayer());
        });

        try {
            firstGraphicalPlayer = queuePlayerFirst.take();
        } catch (InterruptedException e) {
            throw new Error();
        }

        runLater(()-> playerName.add(firstGraphicalPlayer.enterPseudo()));
        try {
            return playerName.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOptionsRulesIntro chooseMenuRulesIntro() {
        runLater(() -> firstGraphicalPlayer.showRulesEnIntro((e)-> menuRulesIntro.add(e)));

        try {
            return menuRulesIntro.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public String chooseMenuRulesTickets() {
        runLater(() -> firstGraphicalPlayer.showRulesTickets((e)-> menuRulesTicket.add(e)));

        try {
            return menuRulesTicket.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public String chooseMenuRulesClaim() {
        runLater(() -> firstGraphicalPlayer.showRulesClaim((e)-> menuRulesClaim.add(e)));

        try {
            return menuRulesClaim.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOptionsRulesFr chooseMenuRulesFr() {
        runLater(() -> firstGraphicalPlayer.showRulesFr((e)-> menuRulesFr.add(e)));

        try {
            return menuRulesFr.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public MenuOpcionesReglas chooseMenuRulesSp() {
        runLater(() -> firstGraphicalPlayer.showRulesSp((e)-> menuRulesSp.add(e)));

        try {
            return menuRulesSp.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

    @Override
    public void showPodium(String first, String second, String third) {
        runLater(() -> graphicalPlayer.showPodium(first, second, third));
    }

    @Override
    public String chooseMenuRulesDraw() {
        runLater(() -> firstGraphicalPlayer.showRulesDraw((e)-> menuRulesDraw.add(e)));

        try {
            return menuRulesDraw.take();
        } catch (InterruptedException e) {
            throw new Error();
        }
    }

}
