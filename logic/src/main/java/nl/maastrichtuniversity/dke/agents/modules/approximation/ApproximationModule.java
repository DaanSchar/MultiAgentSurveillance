package nl.maastrichtuniversity.dke.agents.modules.approximation;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.Distribution;

public class ApproximationModule extends AgentModule implements IApproximationModule {
    private final Environment environment;

    public ApproximationModule(Scenario scenario) {
        super(scenario);
        this.environment = scenario.getEnvironment();
    }

    @Override
    public Position getValidGuess(Position source) {
        Position guessedPosition = makeGuess(source, 0 , 2);

        while (!isValid(guessedPosition)) {
            guessedPosition = makeGuess(source, 0 , 2);
        }

        return guessedPosition;
    }

    @Override
    public Position getValidTargetGuess() {
        Tile target = scenario.getEnvironment().get(TileType.TARGET).get(0);
        Position guessedPosition = makeGuess(target.getPosition(), 0, 5);
        while (!isValid(guessedPosition)) {
            guessedPosition = makeGuess(target.getPosition(),0 ,5);
        }

        return guessedPosition;

    }

    private Position makeGuess(Position source, double m, double s ) {
        return source.add(getGuessOffset(m,s));
    }

    private boolean isValid(Position guessedPosition) {
        return isInMap(guessedPosition) && isPassable(guessedPosition) && !isTeleport(guessedPosition);
    }

    private Position getGuessOffset(double mean , double stdDev) {
//        double mean = 0;
//        double stdDev = 2.0;
        int guessX = (int) Distribution.normal(mean, stdDev);
        int guessY = (int) Distribution.normal(mean, stdDev);

        return new Position(guessX, guessY);
    }

    private boolean isInMap(Position position) {
        return position.getX() >= 0 && position.getX() < environment.getWidth()
                && position.getY() >= 0 && position.getY() < environment.getHeight();
    }

    private boolean isPassable(Position position) {
        return environment.getAt(position).isPassable();
    }

    private boolean isTeleport(Position position) {
        Tile tile = scenario.getEnvironment().getAt(position);
        return tile.getType() == TileType.TELEPORT;
    }
}
