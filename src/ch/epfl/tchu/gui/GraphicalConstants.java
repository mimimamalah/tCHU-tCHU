package ch.epfl.tchu.gui;
/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */

/**
 *  This class represents the constants for the graphical design.
 */

public final class GraphicalConstants {
    private GraphicalConstants(){}

    //Constants for DeckViewCreator
    public static final int OUTSIDE_RECTANGLE_WIDTH = 60;
    public static final int OUTSIDE_RECTANGLE_HEIGHT = 90;
    public static final int INSIDE_RECTANGLE_WIDTH = 40;
    public static final int INSIDE_RECTANGLE_HEIGHT = 70;
    public static final int GAUGE_RECTANGLE_WIDTH = 50;
    public static final int GAUGE_RECTANGLE_HEIGHT = 5;
    public static final int MINIMUM_VISIBLE_CARD = 0;
    public static final int MINIMUM_VISIBLE_TEXT = 1;
    public static final int MAXIMUM_WIDTH_GAUGE = 50;

    //Constants for MapViewCreator
    public static final int CIRCLE_RADIUS_MAP = 3;
    public static final int RECTANGLE_WIDTH = 36;
    public static final int RECTANGLE_HEIGHT = 12;
    public static final int CIRCLE1_CAR_X = 12;
    public static final int CIRCLE2_CAR_X = 24;
    public static final int CIRCLE_CAR_Y = 6;

    //Constants for infoViewCreator
    public final static int CIRCLE_RADIUS_INFO = 5;

    //SS represents Style Sheet
    public static final String CHOOSER_SS = "chooser.css";
    public static final String DECK_SS = "decks.css";
    public static final String COLORS_SS = "colors.css";
    public static final String INFO_SS = "info.css";
    public static final String MAP_SS = "map.css";
    public static final String PODIUM_SS = "podium.css";
    public final static String MENU_CSS = "menu.css";

    // SC represents style class
    public static final String NEUTRAL_SC = "NEUTRAL";
    public static final String FILLED_SC = "filled";
    public static final String COUNT_SC = "count";
    public static final String CARD_SC = "card";
    public static final String OUTSIDE_SC = "outside";
    public static final String INSIDE_SC = "inside";
    public static final String BACKGROUND_SC = "background";
    public static final String FOREGROUND_SC = "foreground";
    public static final String GAUGED_SC ="gauged";
    public static final String TRAIN_IMAGE_SC = "train-image";
    public static final String ROUTE_SC = "route";
    public static final String NEUTRAL_COLOR_SC = "NEUTRAL";
    public static final String TRACK_SC = "track";
    public static final String CAR_SC = "car";

    //ID represents to set id
    public static final String HAND_PANE_ID = "hand-pane";
    public static final String CARD_PANE_ID = "card-pane";
    public static final String TICKETS_ID = "tickets";
    public static final String PLAYER_STATS_ID = "player-stats";
    public static final String GAME_INFO_ID = "game-info";


    //Constants for the url of an image
    public final static String UK_IMAGE = "UK.png";
    public final static String SPAIN_IMAGE = "Spain.png";
    public final static String FRANCE_IMAGE = "france.png";
    public final static String TRAIN_IMAGE = "train.png";

    //Constants for First Graphical Player
    public final static String OPTIONS_LABEL_EN ="Choose your option";
    public final static String OPTIONS_LABEL_FR ="Choisissez votre option";
    public final static String OPTIONS_LABEL_SP ="Elige tu opción";
    public final static String TEXT_LANGUAGE = "Choose your language : ";
    public final static String HEADER_TEXT = "Look, here you should write it";
    public final static String CONTEXT_TEXT = "Please enter your name:";
    public final static String DEFAULT_PSEUDO_TEXT = "Your name";
    public final static String FONT_ARIAL = "Arial";
    public final static String TITLE_MENU = "Menu";
    public final static boolean PRESERVE_RATIO_IMAGE = false;
    public final static boolean SMOOTH_IMAGE = true;
    public final static int TEXT_SIZE = 15;
    public final static int ICON_RADIUS = 60;
    public final static int UK_ICON_X = 70;
    public final static int SP_ICON_X = 170;
    public final static int FR_ICON_X = 270;
    public final static int TRAIN_WIDTH = 60;
    public final static int TRAIN_HEIGHT = 40;
    public final static int ICON_Y = 170;
    public final static int SCENE_MENU_WIDTH = 400;
    public final static int SCENE_MENU_HEIGHT = 300;

    //Constants for the podium
    public final static int PODIUM_TEXT_SIZE = 30;
    public final static int FIRST_LABEL_WIDTH = 180;
    public final static int FIRST_LABEL_HEIGHT = 120;
    public final static int SECOND_LABEL_WIDTH = 35;
    public final static int SECOND_LABEL_HEIGHT = 200;
    public final static int THIRD_LABEL_WIDTH = 329;
    public final static int THIRD_LABEL_HEIGHT = 240;
    public final static int PODIUM_WIDTH = 500;
    public final static int PODIUM_HEIGHT = 700;
    public final static String PODIUM_URL_IMAGE = "podium.png";

}
