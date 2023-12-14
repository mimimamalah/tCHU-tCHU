/**
 *  @author:      Malak Lahlou Nabil (329571)
 *               Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import ch.epfl.tchu.game.*;
import ch.epfl.tchu.gui.ActionHandlers.DrawCardHandler;
import ch.epfl.tchu.gui.ActionHandlers.DrawTicketsHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static ch.epfl.tchu.gui.GraphicalConstants.*;
import static ch.epfl.tchu.gui.StringsFrEnSp.*;

public class DecksViewCreator {

    private final static int PERCENTAGE_FACTOR = 100;

    /**
     * @param gameState, the observable game state @return, the view of the hand
     * @return Node
     */

    public static Node createHandView(ObservableGameState gameState) {

        HBox hbox = new HBox();
        HBox handPane = new HBox();
        hbox.getStylesheets().addAll(DECK_SS, COLORS_SS);
        handPane.setId(HAND_PANE_ID);
        ListView<Text> listView = new ListView<>(gameState.getTicketsWithPoints());
        listView.setId(TICKETS_ID);

        for (Card card : Card.ALL) {

            StackPane stackPane = new StackPane();
            ReadOnlyIntegerProperty count = gameState.getNumberOfCard(card);
            stackPane.visibleProperty().bind(Bindings.greaterThan(count, MINIMUM_VISIBLE_CARD));
            stackPane.getStyleClass().addAll((card == Card.LOCOMOTIVE) ? NEUTRAL_SC : card.name(), CARD_SC);

            Text blackCounter = new Text();
            blackCounter.visibleProperty().bind(Bindings.greaterThan(count, MINIMUM_VISIBLE_TEXT));
            blackCounter.textProperty().bind(Bindings.convert(count));
            blackCounter.getStyleClass().add(COUNT_SC);
            setGraphicStackPaneCard(stackPane);
            stackPane.getChildren().add(blackCounter);
            handPane.getChildren().add(stackPane);

        }
        hbox.getChildren().addAll(listView, handPane);

        return hbox;
    }

    /**
     * @param gameState,   the observable game state
     * @param drawTickets, a property managing the drawing of tickets,
     * @param drawCard,    a property managing the drawing of cards. @return, the view of
     *                     the hand
     * @return Node
     */

    public static Node createCardsView(ObservableGameState gameState, ObjectProperty<DrawTicketsHandler> drawTickets, ObjectProperty<DrawCardHandler> drawCard, Language language) {

        VBox vBox = new VBox();
        vBox.getStylesheets().addAll(DECK_SS, COLORS_SS);
        vBox.setId(CARD_PANE_ID);
        Button bTicket = new Button(TICKETS(language));
        bTicket.setOnMouseClicked(e -> drawTickets.getValue().onDrawTickets());
        setGraphicButton(bTicket, TICKETS(language),gameState.getPercentageTickets() );
        bTicket.disableProperty().bind(drawTickets.isNull());
        vBox.getChildren().add(bTicket);

        for (int i : Constants.FACE_UP_CARD_SLOTS) {
            StackPane stackPane = new StackPane();
            stackPane.getStyleClass().addAll(CARD_SC,"");
            gameState.faceUpCard(i).addListener((o, ov, nv) -> stackPane.getStyleClass().set(1, (nv == Card.LOCOMOTIVE ) ? NEUTRAL_SC : nv.name()));
            stackPane.setOnMouseClicked(e -> drawCard.getValue().onDrawCard(i));
            stackPane.disableProperty().bind(drawCard.isNull());
            setGraphicStackPaneCard(stackPane);
            vBox.getChildren().add(stackPane);
        }

        Button bCard = new Button(CARDS(language));
        bCard.setOnMouseClicked(e -> drawCard.getValue().onDrawCard(-1));
        setGraphicButton(bCard,CARDS(language),gameState.getPercentageCards());
        bCard.disableProperty().bind(drawCard.isNull());
        vBox.getChildren().add(bCard);

        return vBox;
    }

    /**
     *
     * @param button, the button
     * @param text, the string text to set the graphic
     * @param percentageProperty, the property containing the percentage of the Object represented by the button
     * @return the new button with the new graphic scene
     */

    private static void setGraphicButton(Button button, String text, ReadOnlyIntegerProperty percentageProperty ){
        button.getStyleClass().add(GAUGED_SC);
        Group group = new Group();
        Rectangle background = new Rectangle(GAUGE_RECTANGLE_WIDTH, GAUGE_RECTANGLE_HEIGHT);
        background.getStyleClass().add(BACKGROUND_SC);
        Rectangle foreground = new Rectangle(GAUGE_RECTANGLE_WIDTH, GAUGE_RECTANGLE_HEIGHT);
        foreground.getStyleClass().add(FOREGROUND_SC);
        group.getChildren().addAll(background, foreground);
        button.setGraphic(group);
        button.setText(text);
        if(percentageProperty != null)
            foreground.widthProperty().bind(percentageProperty.multiply(MAXIMUM_WIDTH_GAUGE).divide(PERCENTAGE_FACTOR));

    }

    /**
     * Called to set the graphic for the stack pane of the Cards
     * @param stackPane, the stack Pane Card
     */

    private static void setGraphicStackPaneCard(StackPane stackPane){
        Rectangle outsideRectangle = new Rectangle(OUTSIDE_RECTANGLE_WIDTH, OUTSIDE_RECTANGLE_HEIGHT);
        outsideRectangle.getStyleClass().add(OUTSIDE_SC);
        Rectangle insideRectangle = new Rectangle(INSIDE_RECTANGLE_WIDTH, INSIDE_RECTANGLE_HEIGHT);
        insideRectangle.getStyleClass().addAll(FILLED_SC, INSIDE_SC);
        Rectangle trainRectangle = new Rectangle(INSIDE_RECTANGLE_WIDTH, INSIDE_RECTANGLE_HEIGHT);
        trainRectangle.getStyleClass().add(TRAIN_IMAGE_SC);
        stackPane.getChildren().addAll(outsideRectangle, insideRectangle, trainRectangle);

    }

}
