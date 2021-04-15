package Views;

import Models.Tournament;

/**
 * The class represents view for the tournament objects.
 */
public class TournamentView {

    /**
     * Tournament Object to be displayed.
     */
    Tournament d_tournament;

    /**
     * Constructor setting for tournament object.
     *
     * @param p_tournament tournament object
     */
    public TournamentView(Tournament p_tournament){
        d_tournament = p_tournament;
    }
}
