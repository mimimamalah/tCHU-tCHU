/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import ch.epfl.tchu.net.RemotePlayerProxy;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.util.*;

import static ch.epfl.tchu.game.PlayerId.*;

/**
 * This class contains the principal program of the tCHu's client.
 */
public class ServerMain extends Application {

    private final static int PORT_DEFAULT = 5108;
    private final static String PLAYER_1_DEFAULT = "Malak";
    private final static String PLAYER_2_DEFAULT = "Mohamed";
    private final static String PLAYER_3_DEFAULT = "Ada";

    /**
     * @param args the list of arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage
     * @throws Exception in case something goes wrong.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            List<String> parameters = getParameters().getRaw();
            String playerName1, playerName2, playerName3;
            playerName1 = (parameters.size() >= 1) ? parameters.get(0) : PLAYER_1_DEFAULT;
            playerName2 = (parameters.size() >= 2) ? parameters.get(1) : PLAYER_2_DEFAULT;
            playerName3 = (parameters.size() >= 3) ? parameters.get(2) : PLAYER_3_DEFAULT;

            Map<PlayerId, String> playerNames = new EnumMap<>(PlayerId.class);
            playerNames.put(PLAYER_1, playerName1);
            playerNames.put(PLAYER_2, playerName2);
            playerNames.put(PLAYER_3, playerName3);
            ServerSocket socket = new ServerSocket(PORT_DEFAULT);
            RemotePlayerProxy proxy1 = new RemotePlayerProxy(socket.accept());
            RemotePlayerProxy proxy2 = new RemotePlayerProxy(socket.accept());
            Map<PlayerId, Player> players = Map.of(PLAYER_1, new GraphicalPlayerAdapter(), PLAYER_2,
                    proxy1, PLAYER_3, proxy2);
            SortedBag<Ticket> tickets = SortedBag.of(ChMap.tickets());
            Random rng = new Random();

            new Thread(() -> {
                BeforeGame.start(players);
                if(BeforeGame.isFinished() == true){
                    if(BeforeGame.pseudo != null && BeforeGame.pseudo != ""){
                        playerNames.put(PLAYER_1, BeforeGame.pseudo);
                    }
                    Game.play(players, playerNames, tickets, rng, BeforeGame.getLanguage());
                }
            }).start();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}