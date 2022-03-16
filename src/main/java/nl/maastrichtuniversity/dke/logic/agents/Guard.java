package nl.maastrichtuniversity.dke.logic.agents;

public class Guard extends Agent {

    public Guard() {
        super();
    }

    /**
     * if guard see intruder the intruder is out otf the game
     * @param intruder intruder
     */
    public void getIntruder(Intruder intruder){
        intruder.setAlive(false);
    }

}
