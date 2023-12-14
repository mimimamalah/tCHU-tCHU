/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */

package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.*;

public interface ActionHandlers {

    @FunctionalInterface
     interface DrawTicketsHandler {

        /**
         * called when the player wants to draw tickets
         */

        void onDrawTickets();
    }

    @FunctionalInterface
     interface DrawCardHandler {

        /**
         * called when the player wants to draw a card from the given location,
         * @param index,
         *            the index of the card or -1 for the deck
         */

          void onDrawCard(int index);
    }

    @FunctionalInterface
     interface ClaimRouteHandler {

        /**
         * called when the player wants to seize the given route by means of the
         * given (initial) cards,
         * 
         * @param route,
         *            the given route
         * @param initialCards,
         *            the given initial Cards
         */

         void onClaimRoute(Route route, SortedBag<Card> initialCards);
    }

    @FunctionalInterface
     interface ChooseTicketsHandler {

        /**
         * called when the player has chosen to keep the tickets given following
         * a ticket draw
         * 
         * @param tickets, the tickets the player has chosen
         */

         void onChooseTickets(SortedBag<Ticket> tickets);
    }

    @FunctionalInterface
     interface ChooseCardsHandler {

        /**
         * called when the player has chosen to use the given cards as initial
         * or additional cards when taking possession of a route; if they are
         * additional cards, then the multiset can be empty,
         * 
         * @param cards, the cards the player has chosen
         */

         void onChooseCards(SortedBag<Card> cards);
    }

    @FunctionalInterface
    interface ChooseMenuHandler{

        /**
         * called when the player has chosen the menu options in english
         * @param option, the menu option
         */

        void onChooseMenu(MenuOptionsEnglish option);
    }

    @FunctionalInterface
    interface ChooseMenuFrHandler{

        /**
         * called when the player has chosen the menu options in french
         * @param option, the menu option
         */

        void onChooseMenuFr(MenuOptionsFrench option);
    }

    @FunctionalInterface
    interface ChooseMenuSPHandler{

        /**
         * called when the player has chosen the menu options in spanish
         * @param option, the menu option
         */

        void onChooseMenuSP(MenuOptionsSpanish option);
    }

    @FunctionalInterface
    interface ChooseLanguage{

        /**
         * called when the player has chosen the language of the game
         * @param language, the game language
         */
        void onChooseLanguage(Language language);
    }

    @FunctionalInterface
    interface ChooseRulesIntro{
        /**
         * Called when the player want to read the rules
         * @param optionsRules, the intro option rule in English
         */
        void showRulesIntro(MenuOptionsRulesIntro optionsRules);
    }

    @FunctionalInterface
    interface MenuRulesOptionsSp{
        /**
         * Called when the player want to read the rules
         * @param optionsRules, the intro option rule in Spanish
         */
        void menuReglas(MenuOpcionesReglas optionsRules);
    }

    @FunctionalInterface
    interface MenuOptionsRulesFr2{
        /**
         * Called when the player want to read the rules
         * @param optionsRules, the intro option rule in French
         */
        void menuRulesFr(MenuOptionsRulesFr optionsRules);
    }

    @FunctionalInterface
    interface ChooseRulesTickets{
        /**
         * Called when the player want to go back to main intro rules
         * @param option, the option back of Ticket in the correct Language
         */
        void showRulesTickets(String option);
    }

    @FunctionalInterface
    interface ChooseRulesClaim{
        /**
         * Called when the player want to go back to main intro rules
         * @param option, the option back of Ticket in the correct Language
         */
        void showRulesClaim(String option);
    }

    @FunctionalInterface
    interface ChooseRulesDraw{
        /**
         * Called when the player want to go back to main intro rules
         * @param option, the option back of Ticket in the correct Language
         */
        void showRulesDraw(String option);
    }


}
