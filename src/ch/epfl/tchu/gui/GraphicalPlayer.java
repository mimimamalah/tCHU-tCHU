/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Platform.*;
import static ch.epfl.tchu.gui.GraphicalConstants.*;

import java.util.List;
import java.util.Map;
import ch.epfl.tchu.gui.ActionHandlers.DrawTicketsHandler;
import ch.epfl.tchu.gui.ActionHandlers.DrawCardHandler;
import ch.epfl.tchu.gui.ActionHandlers.ClaimRouteHandler;
import ch.epfl.tchu.gui.ActionHandlers.ChooseCardsHandler;
import ch.epfl.tchu.gui.ActionHandlers.ChooseTicketsHandler;

import static ch.epfl.tchu.gui.StringsFrEnSp.*;
import static javafx.collections.FXCollections.*;

/**
 * The class containing the player's graphical interface.
 */

public final class GraphicalPlayer {

    private final ObservableGameState gameState;
    private final Language language;
    private final PlayerId playerId;
    private final Map<PlayerId, String> playerNames;
    private final ObservableList<Text> messages;
    private final Stage mainStage;
    private final ObjectProperty<ClaimRouteHandler> claimRoute;
    private final ObjectProperty<DrawTicketsHandler> drawTickets;
    private final ObjectProperty<DrawCardHandler> drawCard;
    private final Node infoView;
    private static final int MESSAGES_SHOWN = 5;


    /**
     * @param playerId the player's Id
     * @param playerNames the map containing for each player, it's name.
     * Builds the player's graphical interface with its window and graph scene.
     */

    public GraphicalPlayer(PlayerId playerId, Map<PlayerId, String> playerNames, Language languageGame) {
        assert isFxApplicationThread();
        language = languageGame;
        this.playerId = playerId;
        this.messages = FXCollections.observableArrayList();
        gameState = new ObservableGameState(playerId);
        this.playerNames = playerNames;
        mainStage = new Stage();
        claimRoute = new SimpleObjectProperty<>();
        drawTickets = new SimpleObjectProperty<>();
        drawCard = new SimpleObjectProperty<>();

        Node mapView = MapViewCreator.createMapView(gameState, claimRoute, this::chooseClaimCards);
        Node cardsView = DecksViewCreator.createCardsView(gameState, drawTickets, drawCard, languageGame);
        Node handView = DecksViewCreator.createHandView(gameState);
        infoView = InfoViewCreator.createInfoView(playerId, playerNames, gameState, messages, languageGame);
        BorderPane mainPane = new BorderPane(mapView, null, cardsView, handView, infoView);
        mainStage.setTitle(String.format(MAIN_TITLE, playerNames.get(playerId)));
        Scene scene = new Scene(mainPane);
        mainStage.setScene(scene);
        mainStage.show();

    }

    /**
     * @param newGameState the new game's state after the update
     * @param newPlayerState the new player's state after the update
     * The methods sets the new game's and player's states after an update.
     */

    public void setState(PublicGameState newGameState, PlayerState newPlayerState) {
        assert isFxApplicationThread();
        gameState.setState(newGameState, newPlayerState);
    }

    /**
     * @param info The information which will be given to the players.
     * This method sends the corresponding information to the players.
     */
    public void receiveInfo(String info) {
        assert isFxApplicationThread();
        if (messages.size() == MESSAGES_SHOWN)
            messages.remove(0);
        messages.add(new Text(info));
    }

    /**
     * @param drawTicketsH the used handler when the player draws tickets
     * @param drawCardH the used handler when the player draws a card.
     * @param claimRouteH the used handler when the player claims a route.
     * This method allows the player to realize an action when he wants to do it depending on its type.
     */
    public void startTurn(DrawTicketsHandler drawTicketsH, DrawCardHandler drawCardH, ClaimRouteHandler claimRouteH) {
        assert isFxApplicationThread();
        claimRoute.set((e, v) -> {
            claimRouteH.onClaimRoute(e, v);
            drawCard.set(null);
            drawTickets.set(null);
            claimRoute.set(null);
        });

        if (gameState.canDrawTickets())
            drawTickets.set(() -> {
                drawTicketsH.onDrawTickets();
                drawTickets.set(null);
                drawCard.set(null);
                claimRoute.set(null);
            });

        if (gameState.canDrawCards())
            drawCard.set((e) -> {
                drawCardH.onDrawCard(e);
                drawCard.set(null);
                drawTickets.set(null);
                claimRoute.set(null);
            });
    }


    /**
     * @param tickets The tickets options that can choose the player
     * @param chooseTicketsHandler the used handler when the player wants to choose tickets.
     * This method is used when the player wants to choose from the given tickets.
     */

    public void chooseTickets(SortedBag<Ticket> tickets, ChooseTicketsHandler chooseTicketsHandler) {
        assert isFxApplicationThread();
        ListView<Ticket> listView = new ListView<>(observableList(tickets.toList()));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        var selection = listView.getSelectionModel();

        final int MINIMUM_TICKETS = tickets.size() - Constants.DISCARDABLE_TICKETS_COUNT;
        TextFlow textFlow = new TextFlow();
        String s = CHOOSE_TICKETS(language);
        Text text = new Text(String.format(s, MINIMUM_TICKETS, plural(MINIMUM_TICKETS)));
        textFlow.getChildren().add(text);

        Button button = new Button(CHOOSE(language));
        button.disableProperty().bind(Bindings.size(selection.getSelectedItems()).lessThan(MINIMUM_TICKETS));

        Stage stage = showStage(TICKETS_CHOICE(language), textFlow, listView, button);
        button.setOnAction((e) -> {
            stage.hide();
            chooseTicketsHandler.onChooseTickets(SortedBag.of(selection.getSelectedItems()));
        });
    }

    /**
     * @param drawCardH the used handler when the player wants to draw a card.
     * This method allows the player to draw a card, either from the face up cards or from the top of the cards.
     */

    public void drawCard(DrawCardHandler drawCardH) {
        assert isFxApplicationThread();
        drawCard.set((e) -> {
            drawCardH.onDrawCard(e);
            drawCard.set(null);
            drawTickets.set(null);
            claimRoute.set(null);
        });
    }

    /**
     * @param initialClaimCards The initial claim cards of the player to claim a route.
     * @param chooseCardsHandler the used handler that allows the player to choose the claim cards.
     */
    public void chooseClaimCards(List<SortedBag<Card>> initialClaimCards, ChooseCardsHandler chooseCardsHandler) {
        assert isFxApplicationThread();
        ListView<SortedBag<Card>> listView = new ListView<>(observableList(initialClaimCards));
        listView.setCellFactory(v -> new TextFieldListCell<>(new CardBagStringConverter()));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MultipleSelectionModel<SortedBag<Card>> selection = listView.getSelectionModel();

        TextFlow textFlow = new TextFlow();
        Text text = new Text(String.format(CHOOSE_CARDS(language), listView));
        textFlow.getChildren().add(text);

        Button button = new Button(CHOOSE(language));
        button.disableProperty().bind(Bindings.size(selection.getSelectedItems()).lessThan(1));

        Stage stage = showStage(CARDS_CHOICE(language), textFlow, listView, button);
        button.setOnAction((e) -> {
            stage.hide();
            chooseCardsHandler.onChooseCards(selection.getSelectedItem());
        });
    }

    /**
     * @param additionalCards the additional cards that can choose the player to claim a route.
     * @param chooseCardsHandler the used handler that allows the player to choose the additional cards.
     * This method is used when the player has to choose between the given additional cards to claim a route.
     */
    public void chooseAdditionalCards(List<SortedBag<Card>> additionalCards, ChooseCardsHandler chooseCardsHandler) {
        assert isFxApplicationThread();
        ListView<SortedBag<Card>> listView = new ListView<>(observableList(additionalCards));
        listView.setCellFactory(v -> new TextFieldListCell<>(new CardBagStringConverter()));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MultipleSelectionModel<SortedBag<Card>> selection = listView.getSelectionModel();

        TextFlow textFlow = new TextFlow();
        Text text = new Text(String.format(CHOOSE_ADDITIONAL_CARDS()));
        textFlow.getChildren().add(text);

        Button button = new Button(CHOOSE(language));
        SortedBag<Card> selectionCard = selection.getSelectedItem();
        Stage stage = showStage(CARDS_CHOICE(language), textFlow, listView, button);
        button.setOnAction((e) -> {
            stage.hide();
            chooseCardsHandler.onChooseCards(selectionCard == null ? SortedBag.of() : selectionCard);

        });
    }

    /**
     * @param title The title of the stage
     * @param textFlow the text that appears on the stage
     * @param listView the list view.
     * @param button The button that can be used by the player on the stage
     * @return the stage that will appear on the graphical interface.
     */
    private Stage showStage(String title, TextFlow textFlow, ListView listView, Button button) {
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setTitle(title);
        stage.initOwner(mainStage);
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(new VBox(textFlow, listView, button));
        stage.setScene(scene);
        scene.getStylesheets().add(CHOOSER_SS);
        stage.show();
        stage.setOnCloseRequest(e -> e.consume());

        return stage;
    }

    /**
     * The method showPodium displays the image of the podium with the corresponding
     * player's Name in top of each ranking.
     * @param first The first player
     * @param second The second player
     * @param third The third player
     */
    public void showPodium(String first, String second, String third){

        Label firstLabel = new Label(first);
        firstLabel.setFont(Font.font(FONT_ARIAL, FontWeight.BOLD, PODIUM_TEXT_SIZE));
        firstLabel.setLayoutX(FIRST_LABEL_WIDTH);
        firstLabel.setLayoutY(FIRST_LABEL_HEIGHT);
        Label secondLabel = new Label(second);
        secondLabel.setFont(Font.font(FONT_ARIAL, FontWeight.BOLD, PODIUM_TEXT_SIZE));
        secondLabel.setLayoutX(SECOND_LABEL_WIDTH);
        secondLabel.setLayoutY(SECOND_LABEL_HEIGHT);
        Label thirdLabel = new Label(third);
        thirdLabel.setFont(Font.font(FONT_ARIAL, FontWeight.BOLD, PODIUM_TEXT_SIZE));
        thirdLabel.setLayoutX(THIRD_LABEL_WIDTH);
        thirdLabel.setLayoutY(THIRD_LABEL_HEIGHT);
        Image podium = new Image(PODIUM_URL_IMAGE, PODIUM_WIDTH, PODIUM_HEIGHT, PRESERVE_RATIO_IMAGE, SMOOTH_IMAGE);
        ImageView podiumView = new ImageView(podium);
        Group group = new Group(podiumView, firstLabel,secondLabel, thirdLabel);
        Scene scene = new Scene(group, Color.CADETBLUE);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initOwner(mainStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(TITLE_MENU);
        stage.setScene(scene);
        stage.show();

    }

}