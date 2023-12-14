/**
 * @author: Malak Lahlou Nabil (329571)
 * @author: Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.game;

import ch.epfl.tchu.gui.*;

import java.util.Map;

import static ch.epfl.tchu.game.PlayerId.PLAYER_1;
import static ch.epfl.tchu.gui.Language.*;
import static ch.epfl.tchu.gui.MenuOpcionesReglas.*;
import static ch.epfl.tchu.gui.MenuOptionsRulesIntro.*;
import static ch.epfl.tchu.gui.MenuOptionsRulesFr.*;

/**
 * This class represents the before game where the player chooses his pseudo, his language and options.
 */

public final class BeforeGame {

    public static Language language;
    public static boolean isFinished = false;
    public static boolean isFinishedRules = false;
    public static String pseudo;

    private BeforeGame() {
    }

    /**
     * This method is called for the player to choose his options.
     * @param players, the map of the players with his id
     */

    public static void start(Map<PlayerId, Player> players) {

        Player p = players.get(PLAYER_1);
        pseudo = p.choosePseudo();
        language = p.chooseLanguage();

        while (!isFinished) {

            if (language == ENGLISH) {
                MenuOptionsEnglish options = p.chooseMenu();
                isFinishedRules = false;

                if (options != null) {
                    switch (options) {
                        case READ_THE_RULES:
                            while (!isFinishedRules) {
                                MenuOptionsRulesIntro optionsRules = p.chooseMenuRulesIntro();
                                if (optionsRules == BACK) isFinishedRules = true;
                                if (optionsRules == TICKETS) p.chooseMenuRulesTickets();
                                if (optionsRules == DRAW_CARDS) p.chooseMenuRulesDraw();
                                if (optionsRules == SEIZE_ROUTES_OR_TUNNELS) p.chooseMenuRulesClaim();
                            }
                            break;
                        case START_GAME:
                            isFinished = true;
                        default:
                            break;
                    }
                }
            } else if (language == FRANCAIS) {
                MenuOptionsFrench optionsFrench = p.chooseMenuFr();
                isFinishedRules = false;

                if (optionsFrench != null) {
                    switch (optionsFrench) {
                        case MONTRER_LES_REGLES:
                            while (!isFinishedRules) {
                                MenuOptionsRulesFr optionsRules = p.chooseMenuRulesFr();
                                if (optionsRules == RETOUR_REGLES) isFinishedRules = true;
                                if (optionsRules == BILLETS) p.chooseMenuRulesTickets();
                                if (optionsRules == TIRER_CARTES) p.chooseMenuRulesDraw();
                                if (optionsRules == PRENDRE_ROUTES_TUNNELS) p.chooseMenuRulesClaim();
                            }
                            break;
                        case COMMENCER_LE_JEU:
                            isFinished = true;
                        default:
                            break;
                    }
                }
            } else {
                MenuOptionsSpanish optionsSpanish = p.chooseMenuSp();
                isFinishedRules = false;
                if (optionsSpanish != null) {
                    switch (optionsSpanish) {
                        case MUESTRA_LAS_REGLAS:
                            while (!isFinishedRules) {
                                MenuOpcionesReglas optionsRules = p.chooseMenuRulesSp();
                                if (optionsRules == REGRESO) isFinishedRules = true;
                                if (optionsRules == BILLETES) p.chooseMenuRulesTickets();
                                if (optionsRules == TIRAR_TARJETAS) p.chooseMenuRulesDraw();
                                if (optionsRules == TOMAR_RUTAS_TUNELES) p.chooseMenuRulesClaim();
                            }
                            break;
                        case COMENZAR_EL_JUEGO:
                            isFinished = true;
                        default:
                            break;
                    }
                }
            }
            isFinished = true;
        }
    }

    /**
     *
     * @return the game language
     */

    public static Language getLanguage() {
        return language;
    }

    /**
     *
     * @return true if the before game is finished which means the player has chosen his options
     */

    public static boolean isFinished() {
        return isFinished;
    }

}
