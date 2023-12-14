/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import java.util.List;

/**
 * This enumerated type represents the option to read rules in the menu in french.
 */


public enum MenuOptionsRulesFr {

    RETOUR_REGLES, BILLETS, TIRER_CARTES, PRENDRE_ROUTES_TUNNELS;

    public final static List<MenuOptionsRulesFr> ALL = List.of(MenuOptionsRulesFr.values());
}
