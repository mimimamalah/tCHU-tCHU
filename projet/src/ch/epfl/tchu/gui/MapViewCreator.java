/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import java.util.List;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;
import ch.epfl.tchu.gui.ActionHandlers.ChooseCardsHandler;
import ch.epfl.tchu.gui.ActionHandlers.ClaimRouteHandler;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static ch.epfl.tchu.gui.GraphicalConstants.*;

/**
 * This class create the map view.
 */

class MapViewCreator {

    private MapViewCreator(){ }

    /**
     * create the map view
     *
     * @param gameState,   the observable game state
     * @param claimRoute,  a property containing the action handler to use when the
     *                     player wants to seize a route
     * @param cardChooser, a “card selector”, of the CardChooser type
     * @return Node
     */

    public static Node createMapView(ObservableGameState gameState, ObjectProperty<ClaimRouteHandler> claimRoute, CardChooser cardChooser) {

        Pane mapPane = new Pane();
        mapPane.getStylesheets().addAll(MAP_SS, COLORS_SS);
        ImageView map = new ImageView();
        mapPane.getChildren().add(map);

        for (Route route : ChMap.routes()) {

            Group groupRoute = new Group();
            groupRoute.setId(route.id());
            groupRoute.getStyleClass().addAll("", ROUTE_SC, route.level().name(),
                    route.color() != null ? route.color().name() : NEUTRAL_COLOR_SC);

            for (int i = 1; i <= route.length(); i++) {
                groupRoute.getChildren().add(setGroupCase(route, i));
            }

            groupRoute.setOnMouseClicked(e -> {
                List<SortedBag<Card>> possibleClaimCards = gameState.possibleClaimCards(route);

                if (possibleClaimCards.size() == 1) {
                    claimRoute.getValue().onClaimRoute(route, possibleClaimCards.get(0));
                } else {
                    ClaimRouteHandler claimRouteHandler = claimRoute.getValue();
                    ChooseCardsHandler chooseCardsHandler = chosenCards -> claimRouteHandler.onClaimRoute(route, chosenCards);
                    cardChooser.chooseCards(possibleClaimCards, chooseCardsHandler);
                }
            });

            gameState.getRouteId(route).addListener((o, oV, nV) -> {if(nV != null) groupRoute.getStyleClass().set(0, nV.name());});
            for(PlayerId id : PlayerId.ALL)
                gameState.getLongestTrails(id).addListener((o, oV, nV) -> {
                    if(Game .gameIsFinishedCompletely() && nV.getTrailRoutes().contains(route))
                        groupRoute.getStyleClass().set(0, "LONGEST");
                });

            groupRoute.disableProperty().bind(claimRoute.isNull().or(gameState.canClaimRoute(route).not()));

            mapPane.getChildren().add(groupRoute);
        }
        return mapPane;
    }

    /**
     *
     * @param route, the route
     * @param index, the index of the car of the corresponding route
     * @return the group case attached to the given route
     */

    private static Group setGroupCase(Route route, int index){
        Group groupCase = new Group();
        groupCase.setId(String.format("%s_%d", route.id(), index));

        Rectangle rectangleTrack = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        rectangleTrack.getStyleClass().addAll(FILLED_SC, TRACK_SC);

        Group gCar = new Group();
        gCar.getStyleClass().add(CAR_SC);
        Rectangle rectangleCar = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        rectangleCar.getStyleClass().add(FILLED_SC);
        gCar.getChildren().addAll(rectangleCar,
                new Circle(CIRCLE1_CAR_X, CIRCLE_CAR_Y, CIRCLE_RADIUS_MAP),
                new Circle(CIRCLE2_CAR_X, CIRCLE_CAR_Y, CIRCLE_RADIUS_MAP));
        groupCase.getChildren().addAll(rectangleTrack, gCar);

        return groupCase;
    }

    @FunctionalInterface
    interface CardChooser {

        /**
         * called when the player must choose the cards he wants to use to seize
         * a road.
         *
         * @param options, card possibilities available
         * @param handler, intended for use when he has made his choice.
         */

        void chooseCards(List<SortedBag<Card>> options, ChooseCardsHandler handler);
    }

}
