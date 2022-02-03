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

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getId() {
        return id;
    }
}
