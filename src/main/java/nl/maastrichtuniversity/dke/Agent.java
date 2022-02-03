package nl.maastrichtuniversity.dke;

public class Agent {

    private static int agentCount;

    private Vector position;
    private double baseSpeed;
    private int id;

    public Agent(int x, int y, double baseSpeed) {
        this.position = new Vector(x, y);
        this.baseSpeed = baseSpeed;
        this.id = agentCount++;
    }

}
