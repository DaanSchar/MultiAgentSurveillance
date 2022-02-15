package nl.maastrichtuniversity.dke.settings;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.areas.Area;

public class Victory {
    /* Timer Stuff
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0L.

    while (elapsedTime < 2*60*1000) {
        //perform db poll/check
        elapsedTime = (new Date()).getTime() - startTime;
}
     */


    Area targetArea;

    public Victory(Area targetArea) {
        this.targetArea = targetArea;
    }

    /**
     * Should have a global variable denoting Target Area maybe ?
     *
     * @param a current agent
     * @return if in target area
     */
    public boolean checkVictory(Agent a) {
        //if (a.getPosition().equals(targetArea.getPosition())) {

        //}


        return false;
    }


}
