/**
 *  @author:      Malak Lahlou Nabil (329571)
 *  @author:      Benslimane Mohamed (329500)
 */
package ch.epfl.tchu.gui;

import static ch.epfl.tchu.game.Game.*;

/**
 * This class chooseS the right string given the game language.
 * In particular, we habe three languages : english, french and spanish.
 */

public final class StringsFrEnSp {
    private StringsFrEnSp(){ }

    public static String RETURN(Language language){
        if(language == null) return "BACK";
        switch (language){
            case FRANCAIS:
                return "RETOUR";
            case ENGLISH:
                return "BACK";
            case SPANISH:
                return "REGRESSO";
            default:
                throw new Error();
        }
    }

    public static String INTRO_RULES(Language language){
        if(language == null) return "Welcome to the tCHu rules," +
                " \nplease select the category of rules you want to see";

        switch (language){
            case FRANCAIS:
                return "Bienvenue dans les règles tCHu," +
                        " \nVeuillez sélectionner la catégorie de règles que vous voulez voir";
            case ENGLISH:
                return "Welcome to the tCHu rules," +
                        " \nplease select the category of rules you want to see";
            case SPANISH:
                return "Bienvenido a las reglas de tCHu" +
                        "\nseleccione la categoría de reglas que desea ver";
            default:
                throw new Error();
        }
    }

    public static String TICKETS_RULES(Language language){
        String rulesEnglish = "The tCHu players aim to achieve a certain number of objectives," +
                " \ngiven by the tickets in their possession, displayed at the bottom left of the screen." +
                " \nPlayers can also get new tickets during a turn, by pulling the top three from the draw pile to keep at least one. ";

        if(language == null) return rulesEnglish;

        switch (language){
            case FRANCAIS:
                return "Les acteurs de tCHu visent à atteindre un certain nombre d'objectifs," +
                        "\ndonné par les tickets en leur possession, affichés en bas à gauche de l'écran." +
                        " \nLes joueurs peuvent également obtenir de nouveaux tickets pendant un tour, " +
                        " \nen tirant les trois premiers de la pioche pour en garder au moins un. ";
            case ENGLISH:
                return rulesEnglish;
            case SPANISH:
                return "Los jugadores de tCHu apuntan a lograr una cierta cantidad de objetivos, " +
                    "\nproporcionado por las entradas en su poder, que se muestran en la parte inferior izquierda de la pantalla." +
                    "\nLos jugadores también pueden obtener nuevos boletos durante un turno, sacando los tres primeros de la pila para quedarse con al menos uno.";
            default:
                throw new Error();
        }
    }

    public static String DRAW_RULES(Language language){
        String rulesEnglish = "To get new cards, players can draw two each turn." +
                " \nEach of these cards can be either one of the five cards placed face up to the right of the board," +
                " \nor the top card of the draw pile, whose face is not visible";

        if(language == null) return rulesEnglish;
        switch (language){
            case FRANCAIS:
                return "Pour obtenir de nouvelles cartes, les joueurs peuvent en tirer deux à chaque tour." +
                        " \nChacune de ces cartes peut être l'une des cinq cartes placées face visible à droite du plateau," +
                        " \ni la carte du dessus de la pioche, dont la face n'est pas visible";
            case ENGLISH:
                return rulesEnglish;
            case SPANISH:
                return "Para obtener nuevas cartas, los jugadores pueden robar dos en cada turno. "+
                        "\nCada una de estas cartas puede ser una de las cinco cartas colocadas boca arriba a la derecha del tablero, "+
                    "\nni la carta superior del mazo de robo, cuya cara no es visible";
            default:
                throw new Error();
        }
    }

    public static String CLAIM_RULES(Language language){
        String rulesEnglish = "To be able to seize a tunnel," +
                " \na player must fulfill the same conditions as for a road on the surface." +
                " \nIn the case of a tunnel, however, he can also use the locomotive cards," +
                " \nwhich are in a way multicolored and can therefore play the role of a wagon card of any color.\n" +
                " \n" +
                " \nIf a player wishing to seize a tunnel has the necessary cards and wagons," +
                " \nhe must first place the cards he wishes to use in front of him," +
                " \nafter which the three cards at the top of the draw pile are turned over." +
                " \nThese three cards determine any additional cards that the player must play," +
                " \nin addition to those he has already placed, to be able to seize the tunnel.\n";

        if(language == null) return rulesEnglish;
        switch (language){
            case FRANCAIS:
                return "Pour pouvoir saisir un tunnel," +
                        " \nun joueur doit remplir les mêmes conditions que pour une route en surface." +
                        " \nDans le cas d'un tunnel, cependant, il peut aussi utiliser les cartes locomotives," +
                        " \nqui sont en quelque sorte multicolores et peuvent donc jouer le rôle d'une carte wagon de n'importe quelle couleur.\n" +
                        " \n" +
                        " \nSi un joueur souhaitant s'emparer d'un tunnel a les cartes et wagons nécessaires," +
                        " \nil doit d'abord placer les cartes qu'il souhaite utiliser devant lui," +
                        " \naprès quoi les trois cartes du haut de la pioche sont retournées." +
                        " \nCes trois cartes déterminent toutes les cartes supplémentaires que le joueur doit jouer," +
                        " \nin en plus de ceux qu'il a déjà placés, pour pouvoir s'emparer du tunnel.\n";
            case ENGLISH:
                return rulesEnglish;
            case SPANISH:
                return "Para poder tomar un túnel," +
                    "Un jugador debe cumplir las mismas condiciones que para una carretera en superficie." +
                    "\nEn el caso de un túnel, sin embargo, también puede usar las tarjetas de locomotora," +
                    "\nque son de alguna manera multicolores y, por lo tanto, pueden desempeñar el papel de una carta de carro de cualquier color. \n" +
                    "\n" +
                    "\nSi un jugador que desea apoderarse de un túnel tiene las cartas y los vagones necesarios," +
                    "\nprimero debe colocar las cartas que desea usar frente a él," +
                    "\na después de lo cual se voltean las tres cartas en la parte superior del mazo de robo." +
                    "\nEstas tres cartas determinan las cartas adicionales que el jugador debe jugar," +
                    "\n además de los que ya ha colocado, para poder apoderarse del túnel. \n";
            default:
                throw new Error();
        }
    }
    // Nom des cartes
    public static String BLACK_CARD(){
        if(languageGame == null)
            return "black";
        switch (languageGame){
            case FRANCAIS:
                return "noire";
            case ENGLISH:
                return "black";
            case SPANISH:
                return "negro";
            default:
                throw new Error();
        }
    }
    public static String BLUE_CARD() {
        if(languageGame == null)
            return "blue";
        switch (languageGame) {
            case FRANCAIS:
                return "bleue";
            case ENGLISH:
                return "blue";
            case SPANISH:
                return "azul";
            default:
                throw new Error();
        }
    }

    public static String GREEN_CARD(){
        if(languageGame == null)
            return "green";
        switch (languageGame) {
            case FRANCAIS:
                return "verte";
            case ENGLISH:
                return "green";
            case SPANISH:
                return "verde";
            default:
                throw new Error();
        }
    }
    public static String ORANGE_CARD(){
        if(languageGame == null)
            return "orange";
        switch (languageGame) {
            case FRANCAIS:
            case ENGLISH:
                return "orange";
            case SPANISH:
                return "naranja";
            default:
                throw new Error();
        }
    }
    public static String RED_CARD() {
        if(languageGame == null)
            return "red";
        switch (languageGame) {
            case FRANCAIS:
                return "rouge";
            case ENGLISH:
                return "red";
            case SPANISH:
                return "roja";
            default:
                throw new Error();
        }
    }
    public static String VIOLET_CARD() {
        if(languageGame == null)
            return "violet";
        switch (languageGame) {
            case FRANCAIS:
                return "violette";
            case ENGLISH:
                return "violet";
            case SPANISH:
                return "violeta";
            default:
                throw new Error();
        }
    }
    public static String WHITE_CARD(){
        if(languageGame == null)
            return "white";
        switch (languageGame) {
            case FRANCAIS:
                return "blanche";
            case ENGLISH:
                return "white";
            case SPANISH:
                return "blanca";
            default:
                throw new Error();
        }
    }
    public static String YELLOW_CARD(){
        if(languageGame == null)
            return "yellow";
        switch (languageGame) {
            case FRANCAIS:
                return "jaune";
            case ENGLISH:
                return "yellow";
            case SPANISH:
                return "amarillo";
            default:
                throw new Error();
        }
    }
    public static String LOCOMOTIVE_CARD(){
        if(languageGame == null)
            return "locomotive";
        switch (languageGame) {
            case FRANCAIS:
            case ENGLISH:
                return "locomotive";
            case SPANISH:
                return "locomotora";
            default:
                throw new Error();
        }
    }

    // Étiquettes des boutons
    public static String TICKETS(Language language) {
        if(language == null) return "Tickets";
        switch (language) {
            case FRANCAIS:
                return "Billets";
            case ENGLISH:
                return "Tickets";
            case SPANISH:
                return "Entradas";
            default:
                throw new Error();
        }
    }

    public static String CARDS(Language language) {
        if(language== null) return "Cards";
        switch (language) {
            case FRANCAIS:
                return "Cartes";
            case ENGLISH:
                return "Cards";
            case SPANISH:
                return "Tarjetas";
            default:
                throw new Error();
        }
    }

    public static String CHOOSE(Language language) {
        if(language == null) return "Choose";
        switch (language) {
            case FRANCAIS:
                return "Choisir";
            case ENGLISH:
                return "Choose";
            case SPANISH:
                return "Elegir";
            default:
                throw new Error();
        }
    }

    // Titre des fenêtres
    public static String TICKETS_CHOICE(Language language) {
        if(language == null) return "Tickets choice";

        switch (language) {
            case FRANCAIS:
                return "Choix de billets";
            case ENGLISH:
                return "Tickets choice";
            case SPANISH:
                return "Elección de entradas";
            default:
                throw new Error();
        }
    }

    public static String CARDS_CHOICE(Language language) {
        if(language == null) return "Cards choice";
        switch (language) {
            case FRANCAIS:
                return "Choix de cartes";
            case ENGLISH:
                return "Cards choice";
            case SPANISH:
                return "Elección de tarjetas";
            default:
                throw new Error();
        }
    }

    // Invites
    public static String CHOOSE_TICKETS(Language language) {
        if(language == null) return "Choose at least %s ticket%s among these :";

        switch (language) {
            case FRANCAIS:
                return "Choisissez au moins %s billet%s parmi ceux-ci :";
            case ENGLISH:
                return "Choose at least %s ticket%s among these :";
            case SPANISH:
                return "Elija al menos %s entrada %s entre estos:";
            default:
                throw new Error();
        }
    }

    public static String CHOOSE_CARDS(Language language) {

        if(language == null) return "Choose the cards to use to claim this route";

        switch (language) {
            case FRANCAIS:
                return "Choisissez les cartes à utiliser pour vous emparer de cette route :";
            case ENGLISH:
                return "Choose the cards to use to claim this route";
            case SPANISH:
                return "Elija qué mapas usar para tomar esta ruta:";
            default:
                throw new Error();
        }
    }

    public static String CHOOSE_ADDITIONAL_CARDS() {

        if(languageGame == null) return "Choose additional cards to use for you" +
                "to seize this tunnel (or none to cancel and pass your turn):";;

        switch (languageGame) {
            case FRANCAIS:
                return "Choisissez les cartes supplémentaires à utiliser pour vous" +
                        " emparer de ce tunnel (ou aucune pour annuler et passer votre tour) :";
            case ENGLISH:
                return "Choose additional cards to use for you" +
                        "to seize this tunnel (or none to cancel and pass your turn):";
            case SPANISH:
                return "Elija qué tarjetas adicionales usará " +
                        " para aprovechar este túnel (o ninguno para cancelar y saltarte tu turno):";
            default:
                throw new Error();
        }

    }

    // Informations concernant le déroulement de la partie
    public static String WILL_PLAY_FIRST() {

        if(languageGame == null) return "%s will play first.\n\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s jouera en premier.\n\n";
            case ENGLISH:
                return "%s will play first.\n\n";
            case SPANISH:
                return "%s jugará primero. \n\n";
            default:
                throw new Error();
        }
    }

    public static String KEPT_N_TICKETS() {
        if(languageGame == null) return "%s kept %s ticket%s.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s a gardé %s billet%s.\n";
            case ENGLISH:
                return "%s kept %s ticket%s.\n";
            case SPANISH:
                return "%s guardó %s ticket%s.\n";
            default:
                throw new Error();
        }
    }

    public static String CAN_PLAY() {
        if(languageGame == null) return "\nIt is up to %s to play.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\nC'est à %s de jouer.\n";
            case ENGLISH:
                return "\nIt is up to %s to play.\n";
            case SPANISH:
                return "\nEs de %s jugar.\n";
            default:
                throw new Error();
        }
    }

    public static String DREW_TICKETS() {
        if(languageGame == null) return "%s drew %s ticket%s...\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s a tiré %s billet%s...\n";
            case ENGLISH:
                return "%s drew %s ticket%s...\n";
            case SPANISH:
                return "%s sacó %s ticket%s...\n";
            default:
                throw new Error();
        }
    }

    public static String DREW_BLIND_CARD() {

        if(languageGame == null) return "%s drew a blind card.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s a tiré une carte de la pioche.\n";
            case ENGLISH:
                return "%s drew a blind card.\n";
            case SPANISH:
                return "%s sacó una carta ciega.\n";
            default:
                throw new Error();
        }
    }

    public static String DREW_VISIBLE_CARD() {

        if(languageGame == null) return "%s drew a %s visible card.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s a tiré une carte %s visible.\n";
            case ENGLISH:
                return "%s drew a %s visible card.\n";
            case SPANISH:
                return "%s sacó una carta %s visible.\n";
            default:
                throw new Error();
        }
    }

    public static String CLAIMED_ROUTE() {

        if(languageGame == null) return "%s took possession of the route %s with %s.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s a pris possession de la route %s au moyen de %s.\n";
            case ENGLISH:
                return "%s took possession of the route %s with %s.\n";
            case SPANISH:
                return "%s tomó posesión de la ruta %s con %s.\n";
            default:
                throw new Error();
        }
    }

    public static String ATTEMPTS_TUNNEL_CLAIM() {

        if(languageGame == null) return "%s tries to seize the tunnel %s with %s !\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s tente de s'emparer du tunnel %s au moyen de %s !\n";
            case ENGLISH:
                return "%s tries to seize the tunnel %s with %s !\n";
            case SPANISH:
                return "¡ %s intenta apoderarse del túnel %s con %s !\n";
            default:
                throw new Error();
        }
    }

    public static String ADDITIONAL_CARDS_ARE() {

        if(languageGame == null) return "The additional cards are %s. ";
        switch (languageGame) {
            case FRANCAIS:
                return "Les cartes supplémentaires sont %s. ";
            case ENGLISH:
                return "The additional cards are %s. ";
            case SPANISH:
                return "Las tarjetas adicionales son %s.\n";
            default:
                throw new Error();
        }
    }

    public static String NO_ADDITIONAL_COST() {

        if(languageGame == null) return "They do not involve any additional costs.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "Elles n'impliquent aucun coût additionnel.\n";
            case ENGLISH:
                return "They do not involve any additional costs.\n";
            case SPANISH:
                return "No suponen ningún coste adicional.\n";
            default:
                throw new Error();
        }
    }

    public static String SOME_ADDITIONAL_COST() {

        if(languageGame == null) return "They involve an additional cost of %s card%s.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "Elles impliquent un coût additionnel de %s carte%s.\n";
            case ENGLISH:
                return "They involve an additional cost of %s card%s.\n";
            case SPANISH:
                return "Implican un coste adicional de %s tarjeta %s.\n";
            default:
                throw new Error();
        }
    }

    public static String DID_NOT_CLAIM_ROUTE() {

        if(languageGame == null) return "%s could not (or wanted) to seize the route %s.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "%s n'a pas pu (ou voulu) s'emparer de la route %s.\n";
            case ENGLISH:
                return "%s could not (or wanted) to seize the route %s.\n";
            case SPANISH:
                return "%s no pudo (o quiso) tomar la ruta %s.\n";
            default:
                throw new Error();
        }
    }

    public static String LAST_TURN_BEGINS() {

        if(languageGame == null) return "\n%s has only %s car%s, last turn begins !\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s n'a plus que %s wagon%s, le dernier tour commence !\n";
            case ENGLISH:
                return "\n%s has only %s car%s, last turn begins !\n";
            case SPANISH:
                return "\n %s tiene sólo %s coche %s, ¡comienza el último turno! \n";
            default:
                throw new Error();
        }
    }

    public static String GETS_BONUS() {

        if(languageGame == null) return "\n%s receives a bonus of 10 points for the longest trip (%s).\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s reçoit un bonus de 10 points pour le plus long trajet (%s).\n";
            case ENGLISH:
                return "\n%s receives a bonus of 10 points for the longest trip (%s).\n";
            case SPANISH:
                return "\n%s recibe una bonificación de 10 puntos por el viaje más largo (%s).\n";
            default:
                throw new Error();
        }
    }

    public static String GAIN_TICKETS_POINTS() {

        if(languageGame == null) return "\n%s just won %s points with his tickets.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s vient de gagner %s points grâce à ses billets .\n";
            case ENGLISH:
                return "\n%s just won %s points with his tickets.\n";
            case SPANISH:
                return "\n%s acaba de ganar %s puntos con sus boletos.\n";
            default:
                throw new Error();
        }
    }

    public static String LOST_TICKETS_POINTS() {

        if(languageGame == null) return "\n%s just lost %s points because of his tickets.\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s vient de perde %s points à cause de ses billets (%s).\n";
            case ENGLISH:
                return "\n%s just lost %s points because of his tickets.\n";
            case SPANISH:
                return "\n%s acaba de perder %s puntos debido a sus tickets(%s).\n";
            default:
                throw new Error();
        }
    }

    public static String WINS() {

        if(languageGame == null) return "\n%s wins with %s point%s, against %s point%s !\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s remporte la victoire avec %s point%s, contre %s point%s !\n";
            case ENGLISH:
                return "\n%s wins with %s point%s, against %s point%s !\n";
            case SPANISH:
                return "\n¡ %s gana con %s punto %s, contra %s punto%s!\n";
            default:
                throw new Error();
        }
    }

    public static String DRAW() {

        if(languageGame == null) return "\n%s are ex æqo with %s points !\n";
        switch (languageGame) {
            case FRANCAIS:
                return "\n%s sont ex æqo avec %s points !\n";
            case ENGLISH:
                return "\n%s are ex æqo with %s points !\n";
            case SPANISH:
                return "\n¡ %s son ex æqo con %s puntos!\n";
            default:
                throw new Error();
        }
    }

    //Titre fenetre principale
    public static String MAIN_TITLE = "tCHu %s";

    public static final String PLAYER_STATS(Language language){
        if(language == null) return "%s :\n– %s tickets,\n– %s cards,\n– %s cars,\n– %s points.";
        switch (language) {
            case FRANCAIS:
                return  " %s :\n– %s billets,\n– %s cartes,\n– %s wagons,\n– %s points.";
            case ENGLISH:
                return "%s :\n– %s tickets,\n– %s cards,\n– %s cars,\n– %s points.";
            case SPANISH:
                return " %s  :\n– %s boletos, \n– %s tarjetas, \n– %s autos, \n– %s puntos.";
            default:
                throw new Error();
        }
    }

    // Séparateurs textuels
    public final static String AND_SEPARATOR = " et ";
    public final static String EN_DASH_SEPARATOR = " – ";

    /**
     * Retourne une chaîne marquant le pluriel, ou la chaîne vide.
     * @param value la valeur déterminant la chaîne retournée
     * @return la chaîne vide si la valeur vaut ±1, la chaîne "s" sinon
     */
    public static String plural(int value) {
        return Math.abs(value) == 1 || value == 0 ? "" : "s";
    }

}
