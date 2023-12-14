/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import ch.epfl.tchu.game.PlayerId;

import static ch.epfl.tchu.gui.GraphicalConstants.*;
import static ch.epfl.tchu.gui.StringsFrEnSp.*;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Map;

/**
 * This class represents the Info View Creator.
 */

class InfoViewCreator {

    private InfoViewCreator() {
    }

    /**
     *
     * @param playerId, the player id
     * @param playerNames, the player Names
     * @param gameState, the observable game state
     * @param gameMessages, the game messages providing information on the progress of the game
     * @return an Info View
     */
    public static Node createInfoView(PlayerId playerId, Map<PlayerId, String> playerNames, ObservableGameState gameState, ObservableList<Text> gameMessages, Language language) {

        VBox infoVBox = new VBox();
        infoVBox.getStylesheets().addAll(INFO_SS, COLORS_SS);
        VBox playerStatsVBox = new VBox();
        infoVBox.setId(PLAYER_STATS_ID);
        for (PlayerId id : PlayerId.values()) {
            playerStatsVBox.getChildren().add(setPlayerStatistics(id, gameState, playerNames, language));
        }
        FXCollections.rotate(playerStatsVBox.getChildren(), -playerId.ordinal());
        infoVBox.getChildren().addAll(playerStatsVBox, new Separator());
        TextFlow messages = new TextFlow();
        messages.setId(GAME_INFO_ID);
        if(gameMessages != null){ Bindings.bindContent(messages.getChildren(), gameMessages); }
        infoVBox.getChildren().add(messages);

        return infoVBox;
    }

    /**
     *
     * @param id, the player id
     * @param gameState, the observable game state
     * @param playerNames, the map of the player names
     * @return the text Flow of the player statistics
     */

    private static TextFlow setPlayerStatistics(PlayerId id, ObservableGameState gameState, Map<PlayerId, String> playerNames, Language language){
        TextFlow gameInfoTextFlow = new TextFlow();
        gameInfoTextFlow.getStyleClass().add(id.name());
        Circle circle = new Circle(CIRCLE_RADIUS_INFO);
        circle.getStyleClass().add(FILLED_SC);
        Text text = new Text();
        text.textProperty().bind(Bindings.format(PLAYER_STATS(language)+"\n", playerNames.get(id),
                gameState.getTicketCount(id), gameState.getCardsCount(id),
                gameState.getCarsCount(id), gameState.getClaimPoints(id)));
        text.setFont(Font.font("American Typewriter", FontWeight.BOLD, 12));
        text.setFill(Color.DARKBLUE);
        gameInfoTextFlow.getChildren().addAll(circle, text);

        return gameInfoTextFlow;
    }

}
