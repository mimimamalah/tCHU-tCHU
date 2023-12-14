/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import ch.epfl.tchu.game.BeforeGame;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

import static ch.epfl.tchu.gui.Language.*;
import static ch.epfl.tchu.gui.MenuOptionsEnglish.*;
import static ch.epfl.tchu.gui.MenuOptionsFrench.*;
import static ch.epfl.tchu.gui.MenuOptionsSpanish.*;
import static ch.epfl.tchu.gui.StringsFrEnSp.*;
import static javafx.application.Platform.isFxApplicationThread;
import static ch.epfl.tchu.gui.ActionHandlers.*;
import static ch.epfl.tchu.gui.GraphicalConstants.*;

public class FirstGraphicalPlayer {
    private static String pseudo;

    public FirstGraphicalPlayer(){
        isFxApplicationThread();
    }

    /**
     * This method shows the menu for the player
     * @param enHandler, the menu options handler in english
     */

    public void showMenuEnglish(ChooseMenuHandler enHandler ){

        ChoiceBox<MenuOptionsEnglish> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(START_GAME, READ_THE_RULES);
        Stage stage = showStage(choiceBox, OPTIONS_LABEL_EN);

        choiceBox.setOnAction((e)->{ stage.hide();
            enHandler.onChooseMenu(choiceBox.getSelectionModel().getSelectedItem()); });
    }

    /**
     * Does the same as the previous method but in French
     * @param frHandler, the french menu options handler
     */
    public void showMenuFr(ChooseMenuFrHandler frHandler ){

        ChoiceBox<MenuOptionsFrench> choiceBoxFr = new ChoiceBox<>();
        choiceBoxFr.getItems().addAll(COMMENCER_LE_JEU, MONTRER_LES_REGLES);
        Stage stage = showStage(choiceBoxFr , OPTIONS_LABEL_FR);

        choiceBoxFr.setOnAction((e)->{ stage.hide();
            frHandler.onChooseMenuFr(choiceBoxFr.getSelectionModel().getSelectedItem()); });

    }

    /**
     * Does the same as the previous method but in Spanish
     * @param spHandler, the spanish menu options handler
     */

    public void showMenuSP(ChooseMenuSPHandler spHandler ){

        ChoiceBox<MenuOptionsSpanish> choiceBoxSp = new ChoiceBox<>();
        choiceBoxSp.getItems().addAll(COMENZAR_EL_JUEGO, MUESTRA_LAS_REGLAS);
        Stage stage = showStage(choiceBoxSp, OPTIONS_LABEL_SP );

        choiceBoxSp.setOnAction((e)->{ stage.hide();
            spHandler.onChooseMenuSP(choiceBoxSp.getSelectionModel().getSelectedItem()); });
    }

    /**
     *
     * @param choiceBox, the choice box of the menu options
     * @param text, the string label
     * @return the stage of the menu
     */

    private Stage showStage(ChoiceBox choiceBox, String text){
        choiceBox.getStylesheets().add(MENU_CSS);
        Label label = new Label(text);
        label.setFont(new Font(FONT_ARIAL, TEXT_SIZE));
        Group root = new Group(choiceBox, label);
        Scene scene = new Scene(root, SCENE_MENU_WIDTH, SCENE_MENU_HEIGHT, Color.CADETBLUE);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(TITLE_MENU);
        stage.setScene(scene);
        stage.show();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v,oV, nV)-> choiceBox.getItems().add(nV));

        return  stage;
    }

    /**
     * This method shows the stage for the player to choose the game language
     * @param chooseLanguage, the handler to choose a language
     */

    public void showLanguage( ChooseLanguage chooseLanguage){

        ChoiceBox<Language> languageChoiceBox = new ChoiceBox<>();
        Group groupLanguage = createLanguageGroup(languageChoiceBox);
        Scene sceneLanguage = new Scene(groupLanguage, SCENE_MENU_WIDTH, SCENE_MENU_HEIGHT, Color.CORNFLOWERBLUE);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(TITLE_MENU);
        stage.setScene(sceneLanguage);
        stage.show();
        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oV, nV)-> languageChoiceBox.getItems().add(nV));

        languageChoiceBox.setOnAction((e)-> {
            stage.hide();
            chooseLanguage.onChooseLanguage(languageChoiceBox.getSelectionModel().getSelectedItem());
        });

    }

    /**
     *
     * @param choiceBox, the choice box of the different languages
     * @return the group to show the choice box for the languages
     */

    public Group createLanguageGroup(ChoiceBox choiceBox){
        choiceBox.getItems().addAll(FRANCAIS, ENGLISH, SPANISH);
        choiceBox.getStylesheets().add(MENU_CSS);
        Label label = new Label(TEXT_LANGUAGE);
        label.setFont(new Font(FONT_ARIAL, TEXT_SIZE));
        Image UKImage = new Image(UK_IMAGE, ICON_RADIUS, ICON_RADIUS, PRESERVE_RATIO_IMAGE, SMOOTH_IMAGE);
        Image SPImage = new Image(SPAIN_IMAGE, ICON_RADIUS, ICON_RADIUS, PRESERVE_RATIO_IMAGE, SMOOTH_IMAGE);
        Image FRImage = new Image(FRANCE_IMAGE, ICON_RADIUS, ICON_RADIUS, PRESERVE_RATIO_IMAGE, SMOOTH_IMAGE);
        ImageView UKView = new ImageView(UKImage); UKView.setX(UK_ICON_X); UKView.setY(ICON_Y);
        ImageView SPView = new ImageView(SPImage); SPView.setX(SP_ICON_X); SPView.setY(ICON_Y);
        ImageView FRView = new ImageView(FRImage); FRView.setX(FR_ICON_X); FRView.setY(ICON_Y);
        return new Group(choiceBox, label,UKView, SPView, FRView);
    }

    /**
     *
     * @return the pseudo entered by the player
     */

    public String enterPseudo(){
        TextInputDialog dialog = new TextInputDialog(DEFAULT_PSEUDO_TEXT);
        dialog.setHeaderText(HEADER_TEXT);
        dialog.setContentText(CONTEXT_TEXT);
        Image image = new Image(TRAIN_IMAGE, TRAIN_WIDTH, TRAIN_HEIGHT, PRESERVE_RATIO_IMAGE, SMOOTH_IMAGE);
        dialog.setGraphic(new ImageView(image));
        dialog.initStyle(StageStyle.DECORATED);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> pseudo =name);
        return pseudo;
    }

    /**
     * Called when you want to read the rules for the tickets
     * @param chooseRules, the options handler to read the rules for the tickets
     */

    public void showRulesTickets(ActionHandlers.ChooseRulesTickets chooseRules) {

        ChoiceBox<String> choiceBoxEn = new ChoiceBox<>();
        Stage stage = showRulesBis(choiceBoxEn , TICKETS_RULES(BeforeGame.language));
        choiceBoxEn.setOnAction((e) -> { stage.hide();
            chooseRules.showRulesTickets(choiceBoxEn.getSelectionModel().getSelectedItem());
        });
    }

    /**
     * Called when you want to read the rules for the cards
     * @param chooseRules, the options handler to read the rules for the cards
     */

    public void showRulesDraw(ActionHandlers.ChooseRulesDraw chooseRules) {

        ChoiceBox<String> choiceBoxEn = new ChoiceBox<>();
        Stage stage = showRulesBis(choiceBoxEn , DRAW_RULES(BeforeGame.language));
        choiceBoxEn.setOnAction((e) -> { stage.hide();
            chooseRules.showRulesDraw(choiceBoxEn.getSelectionModel().getSelectedItem());
        });
    }

    /**
     * Called when you want to read the rules to claim routes
     * @param chooseRules, the options handler to read the rules to claim routes
     */

    public void showRulesClaim(ActionHandlers.ChooseRulesClaim chooseRules) {

        ChoiceBox<String> choiceBoxEn = new ChoiceBox<>();
        Stage stage = showRulesBis(choiceBoxEn , CLAIM_RULES(BeforeGame.language));
        choiceBoxEn.setOnAction((e) -> { stage.hide();
            chooseRules.showRulesClaim(choiceBoxEn.getSelectionModel().getSelectedItem());
        });
    }

    /**
     *
     * @param choiceBox, the choice box for the specific rule
     * @param text, the description of the specific rule
     * @return the stage where you can read this rules
     */

    public Stage showRulesBis(ChoiceBox choiceBox, String text){

        choiceBox.getItems().add(RETURN(BeforeGame.language));
        choiceBox.getStylesheets().add("menu.css");
        TextArea textArea = new TextArea(text);
        textArea.setFont(new Font("Arial", 15));
        textArea.setLayoutY(100);
        Group root = new Group(choiceBox, textArea);
        Scene scene = new Scene(root, 600, 300, Color.CADETBLUE);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();

        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oV, nV) -> choiceBox.getItems().add(nV));

        return stage;

    }

    /**
     *  Called to show the different possibles options rules in English
     * @param chooseRules, the options rules intro handler in english
     */

    public void showRulesEnIntro(ActionHandlers.ChooseRulesIntro chooseRules) {

        ChoiceBox<MenuOptionsRulesIntro> choiceBoxEn = new ChoiceBox<>();
        for (MenuOptionsRulesIntro rules : MenuOptionsRulesIntro.ALL)
            choiceBoxEn.getItems().add(rules);

        Stage stage = showStageForRules(choiceBoxEn, INTRO_RULES(ENGLISH));
        choiceBoxEn.getSelectionModel().selectedItemProperty().addListener((v, oV, nV) -> choiceBoxEn.getItems().add(nV));
        choiceBoxEn.setOnAction((e) -> {
            stage.hide();
            chooseRules.showRulesIntro(choiceBoxEn.getSelectionModel().getSelectedItem());
        });
    }

    /**
     *  Called to show the different possibles options rules in French
     * @param chooseRules, the options rules intro handler in French
     */


    public void showRulesFr(ActionHandlers.MenuOptionsRulesFr2 chooseRules) {

        ChoiceBox<MenuOptionsRulesFr> choiceBoxFr = new ChoiceBox<>();
        for (MenuOptionsRulesFr règles : MenuOptionsRulesFr.ALL) {
            choiceBoxFr.getItems().add(règles);
        }
        Stage stage = showStageForRules(choiceBoxFr ,INTRO_RULES(FRANCAIS));
        choiceBoxFr.setOnAction((e) -> {
            stage.hide();
            chooseRules.menuRulesFr(choiceBoxFr.getSelectionModel().getSelectedItem());
        });

    }

    /**
     *  Same as previous but in Spanish
     * @param chooseRules,  the options rules intro handler in Spanish
     */


    public void showRulesSp(ActionHandlers.MenuRulesOptionsSp chooseRules){

        ChoiceBox<MenuOpcionesReglas> choiceBoxSp = new ChoiceBox<>();
        for (MenuOpcionesReglas rules: MenuOpcionesReglas.ALL)
            choiceBoxSp.getItems().add(rules);

        Stage stage = showStageForRules(choiceBoxSp , INTRO_RULES(SPANISH) );
        choiceBoxSp.setOnAction((e)->{ stage.hide();
            chooseRules.menuReglas(choiceBoxSp.getSelectionModel().getSelectedItem());
        });

    }

    /**
     *
     * @param choiceBox, the choice box for the options rules
     * @param text, the welcome text to read the rules
     * @return the stage for the options rules
     */

    public Stage showStageForRules(ChoiceBox choiceBox, String text){

        choiceBox.getStylesheets().add(MENU_CSS);
        choiceBox.setLayoutY(50);
        Label label = new Label(text);
        label.setFont(new Font(FONT_ARIAL, TEXT_SIZE));
        Group root = new Group(choiceBox, label);
        Scene scene = new Scene(root, 600, 300, Color.CADETBLUE);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(TITLE_MENU);
        stage.setScene(scene);
        stage.show();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v,oV, nV)-> choiceBox.getItems().add(nV));

        return stage;
    }

}

