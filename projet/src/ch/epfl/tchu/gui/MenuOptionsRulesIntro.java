/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import java.util.List;

/**
 * This enumerated type represents the option to read rules in the menu in english.
 */

public enum MenuOptionsRulesIntro {
    BACK, TICKETS, DRAW_CARDS, SEIZE_ROUTES_OR_TUNNELS;

    public final static List<MenuOptionsRulesIntro> ALL = List.of(MenuOptionsRulesIntro.values());
}
