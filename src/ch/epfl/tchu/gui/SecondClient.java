/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import ch.epfl.tchu.net.RemotePlayerClient;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;

/**
 * This class contains the principal program of the tCHu's client.
 */
public class SecondClient extends Application {

    private final static int PORT_DEFAULT = 5108;
    private final static String LOCAL_HOST_DEFAULT = "localhost";

    /**
     * @param args the list of arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage
     * @throws Exception if something goes wrong.
     * This method starts the client.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> parameters = getParameters().getRaw();
        String localHost = parameters.size() >= 1 ? parameters.get(0) : LOCAL_HOST_DEFAULT ;
        int port = parameters.size() >= 2 ? Integer.parseInt(parameters.get(1)) : PORT_DEFAULT;
        GraphicalPlayerAdapter graphicalPlayerAdapter = new GraphicalPlayerAdapter();

        RemotePlayerClient remotePlayerClient = new RemotePlayerClient(graphicalPlayerAdapter, localHost, port);
        new Thread(remotePlayerClient::run).start();

    }

}
