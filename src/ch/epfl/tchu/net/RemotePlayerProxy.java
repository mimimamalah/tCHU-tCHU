/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import ch.epfl.tchu.gui.*;

import static ch.epfl.tchu.net.Serdes.*;
import static ch.epfl.tchu.net.MessageId.*;

/**
 * This class represents a remote player proxy. It implements the Player interface and can thus play the role of a player.
 */

public final class RemotePlayerProxy implements Player {

    private final BufferedReader r;
    private final BufferedWriter w;
    private final static String SPACE = " ";

    /**
     *
     * @param s0, the server socket
     * @throws IOException
     * Builds the remote player proxy
     */

    public RemotePlayerProxy(Socket s0) throws IOException {
        this.r = new BufferedReader(new InputStreamReader(s0.getInputStream(), UTF_8));
        this.w = new BufferedWriter(new OutputStreamWriter(s0.getOutputStream(), UTF_8));
    }

    /**
     *
     * @param ownId
     *            The player's own identity
     * @param playerNames
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public void initPlayers(PlayerId ownId, Map<PlayerId, String> playerNames, Language language) {
        List<String> newPlayerNames = new ArrayList<>();
        for(PlayerId id: PlayerId.values()) newPlayerNames.add(playerNames.get(id));
        String message = String.join(SPACE,PLAYER_ID_SERDE.serialize(ownId),
                LIST_SERDE_STRING.serialize(newPlayerNames), LANGUAGE_SERDE.serialize(language));
        sendMessage(INIT_PLAYERS, message);
    }

    /**
     *
     * @param info The information which has to be communicated to the player.
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public void receiveInfo(String info) {
        sendMessage(RECEIVE_INFO, STRING_SERDE.serialize(info));
    }

    /**
     *
     * @param newState Represents the new public Game State, when it changes.
     * @param ownState The player's own state when the game's state changes.
     * @throws UncheckedIOException, when sending the message
     */
    @Override
    public void updateState(PublicGameState newState, PlayerState ownState) {
        sendMessage(UPDATE_STATE, String.join(SPACE, PUBLIC_GAME_STATE_SERDE.serialize(newState),
                PLAYER_STATE_SERDE.serialize(ownState)));
    }

    /**
     *
     * @param tickets The five tickets distributed to the player.
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public void setInitialTicketChoice(SortedBag<Ticket> tickets) {
        sendMessage(SET_INITIAL_TICKETS, BAG_SERDE_TICKET.serialize(tickets));
    }

    /**
     * @throws UncheckedIOException, when sending the message
     * @return the chosen initial tickets
     */

    @Override
    public SortedBag<Ticket> chooseInitialTickets() {
        sendMessage(CHOOSE_INITIAL_TICKETS, SPACE);
        return BAG_SERDE_TICKET.deserialize(receiveMessage());
    }

    /**
     * @throws UncheckedIOException, when sending the message
     * @return the next Turn Kind
     */

    @Override
    public TurnKind nextTurn() {
        sendMessage(MessageId.NEXT_TURN, SPACE);
        return TurnKind.ALL.get(INTEGER_SERDE.deserialize(receiveMessage()));
    }

    /**
     *
     * @param options The choices given to the player from which he will choose
     * @return the chosen tickets
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public SortedBag<Ticket> chooseTickets(SortedBag<Ticket> options) {
        sendMessage(CHOOSE_TICKETS, BAG_SERDE_TICKET.serialize(options));
        return BAG_SERDE_TICKET.deserialize(receiveMessage());
    }

    /**
     *
     * @return an index, the slot of the Card
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public int drawSlot() {
        sendMessage(DRAW_SLOT, SPACE);
        return INTEGER_SERDE.deserialize(receiveMessage());
    }

    /**
     *
     * @return the claimed Route
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public Route claimedRoute() {
        sendMessage(ROUTE, SPACE);
        return ROUTE_SERDE.deserialize(receiveMessage());
    }

    /**
     *
     * @return the initial Claim Cards
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public SortedBag<Card> initialClaimCards() {
        sendMessage(CARDS, SPACE);
        return BAG_SERDE_CARD.deserialize(receiveMessage());
    }

    /**
     *
     * @param options The possibilities that he can use to claim the tunnel. If the
     *                List of options is empty, this means that the player doesn't
     * @return the chosen additional cards
     * @throws UncheckedIOException, when sending the message
     */

    @Override
    public SortedBag<Card> chooseAdditionalCards(
            List<SortedBag<Card>> options) {
        sendMessage(CHOOSE_ADDITIONAL_CARDS, LIST_OF_BAG_CARD.serialize(options));
        return BAG_SERDE_CARD.deserialize(receiveMessage());
    }

    @Override
    public MenuOptionsEnglish chooseMenu() {
        return MenuOptionsEnglish.START_GAME;
    }

    @Override
    public MenuOptionsFrench chooseMenuFr() { return MenuOptionsFrench.COMMENCER_LE_JEU; }

    @Override
    public MenuOptionsSpanish chooseMenuSp() {
        return MenuOptionsSpanish.COMENZAR_EL_JUEGO;
        }


    @Override
    public Language chooseLanguage() {
        return BeforeGame.getLanguage();
    }

    @Override
    public String choosePseudo() {
        return null;
    }

    @Override
    public MenuOptionsRulesIntro chooseMenuRulesIntro() {
        return null;
    }

    @Override
    public String chooseMenuRulesTickets() {
        return null;
    }

    @Override
    public String chooseMenuRulesDraw() {
        return null;
    }

    @Override
    public String chooseMenuRulesClaim() {
        return null;
    }

    @Override
    public MenuOptionsRulesFr chooseMenuRulesFr() {
        return null;
    }

    @Override
    public MenuOpcionesReglas chooseMenuRulesSp() {
        return null;
    }

    @Override
    public void showPodium(String first, String second, String third) {
        List<String> list = new ArrayList<>();
        list.add(Serdes.STRING_SERDE.serialize(first));
        list.add(Serdes.STRING_SERDE.serialize(second));
        list.add(Serdes.STRING_SERDE.serialize(third));
        sendMessage(SHOW_PODIUM, LIST_SERDE_STRING.serialize(list));
    }
    /**
     *
     * @param messageId, the message Id
     * @param message, the message depending of the method
     * @throws UncheckedIOException, here is where the other methods throws the exception
     */
    private void sendMessage(MessageId messageId, String message) {
        try {
            String messageToSend = String.join(SPACE, messageId.name(), message);
            w.write(String.format("%s\n", messageToSend));
            w.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String receiveMessage() {
        try { return r.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}





