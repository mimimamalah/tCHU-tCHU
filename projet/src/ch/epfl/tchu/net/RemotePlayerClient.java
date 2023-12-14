/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.net;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *  This class represents a remote player client.
 */

public class RemotePlayerClient {

    private final Player player;
    private final String name;
    private final int port;
    private static final String SEPARATOR_SPACE = " ";

    /**
     * @param player, the player
     * @param name, the name used to connect the proxy
     * @param port, the port used to connect the proxy
     *  This constructor provides remote access to the player
     */

    public RemotePlayerClient(Player player, String name, int port) {
        this.name = name;
        this.player = player;
        this.port = port;
    }

    /**
     * The method run performs a loop which does the following :
     * - Waits for a message from the ...
     * - Separates it using the space character.
     * - defines the type of the message depending on the first part of the separated string.
     * - Depending on this type, the method deserialises the arguments,
     * calls the corresponding player's method and if it returns a result, the method serialises it and sends it back to the ....
     */

    public void run() {

        try (Socket s = new Socket(name, port);

             BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream(), UTF_8));
             BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), UTF_8))) {

            String message;

            while ((message =r.readLine())!= null) {
                String[] split = message.split(Pattern.quote(SEPARATOR_SPACE), -1);

                switch (MessageId.valueOf(split[0])) {

                    case INIT_PLAYERS:
                        List<String> playerNamesList = Serdes.LIST_SERDE_STRING.deserialize(split[2]);
                        Map<PlayerId, String> playerNames = new TreeMap<>();
                        playerNames.put(PlayerId.PLAYER_1, playerNamesList.get(0));
                        playerNames.put(PlayerId.PLAYER_2, playerNamesList.get(1));
                        playerNames.put(PlayerId.PLAYER_3, playerNamesList.get(2));
                        player.initPlayers(Serdes.PLAYER_ID_SERDE.deserialize(split[1]), playerNames, Serdes.LANGUAGE_SERDE.deserialize(split[3]));
                        break;

                    case RECEIVE_INFO:
                        player.receiveInfo(Serdes.STRING_SERDE.deserialize(split[1]));
                        break;

                    case UPDATE_STATE:
                        player.updateState(Serdes.PUBLIC_GAME_STATE_SERDE.deserialize(split[1]), Serdes.PLAYER_STATE_SERDE.deserialize(split[2]));
                        break;

                    case SET_INITIAL_TICKETS:
                        player.setInitialTicketChoice(Serdes.BAG_SERDE_TICKET.deserialize(split[1]));
                        break;

                    case CHOOSE_INITIAL_TICKETS:
                        w.write(Serdes.BAG_SERDE_TICKET.serialize(player.chooseInitialTickets()));
                        w.write("\n");
                        w.flush();
                        break;

                    case NEXT_TURN:
                        w.write(Serdes.TURN_KIND_SERDE.serialize(player.nextTurn()));
                        w.write("\n");
                        w.flush();
                        break;

                    case CHOOSE_TICKETS:
                        SortedBag<Ticket> optionsTicket = Serdes.BAG_SERDE_TICKET.deserialize(split[1]);
                        w.write(Serdes.BAG_SERDE_TICKET.serialize(player.chooseTickets(optionsTicket)));
                        w.write("\n");
                        w.flush();
                        break;

                    case DRAW_SLOT:
                        w.write(Serdes.INTEGER_SERDE.serialize(player.drawSlot()));
                        w.write("\n");
                        w.flush();
                        break;

                    case ROUTE:
                        w.write(Serdes.ROUTE_SERDE.serialize(player.claimedRoute()));
                        w.write("\n");
                        w.flush();
                        break;

                    case CARDS:
                        w.write(Serdes.BAG_SERDE_CARD.serialize(player.initialClaimCards()));
                        w.write("\n");
                        w.flush();
                        break;

                    case CHOOSE_ADDITIONAL_CARDS:
                        List<SortedBag<Card>> optionsCard = Serdes.LIST_OF_BAG_CARD.deserialize(split[1]);
                        w.write(Serdes.BAG_SERDE_CARD.serialize(player.chooseAdditionalCards(optionsCard)));
                        w.write("\n");
                        w.flush();
                        break;
                    case SHOW_PODIUM:
                        List<String> playerPodiumList = Serdes.LIST_SERDE_STRING.deserialize(split[1]);
                        player.showPodium(Serdes.STRING_SERDE.deserialize(playerPodiumList.get(0)),
                                Serdes.STRING_SERDE.deserialize(playerPodiumList.get(1)),
                                Serdes.STRING_SERDE.deserialize(playerPodiumList.get(2)));
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
