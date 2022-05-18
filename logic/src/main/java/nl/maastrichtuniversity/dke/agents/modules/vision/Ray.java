package nl.maastrichtuniversity.dke.agents.modules.vision;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Ray {

    private final double viewingDistance;
    private final Scenario scenario;

    public Ray(double viewingDistance, Scenario scenario) {
        this.viewingDistance = viewingDistance;
        this.scenario = scenario;
    }

    private Vector rayUnitStepSize;
    private Position currentTilePosition;
    private Vector step;
    private Vector ray;
    private double rayLength;

    public List<Position> cast(Position position, double radAngle) {
        initRay(position, radAngle);
        List<Position> tiles = new ArrayList<>();

        boolean tileIsSeeThrough = true;
        boolean tileIsInMap = true;

        while (tileIsSeeThrough && tileIsInMap && rayLength < viewingDistance) {
            if (ray.x < ray.y) {
                increaseRayInXAxis();
            } else {
                increaseRayInYAxis();
            }

            if (!isInMap(currentTilePosition)) {
                tileIsInMap = false;
            }

            tiles.add(new Position(currentTilePosition));

            if (!isSeeThrough(currentTilePosition)) {
                tileIsSeeThrough = false;
            }
        }

        return tiles;
    }

    private void increaseRayInXAxis() {
        currentTilePosition.setX(currentTilePosition.getX() + (int) step.x);
        rayLength = ray.x;
        ray.x += rayUnitStepSize.x;
    }

    private void increaseRayInYAxis() {
        currentTilePosition.setY(currentTilePosition.getY() + (int) step.y);
        rayLength = ray.y;
        ray.y += rayUnitStepSize.y;
    }

    private void initRay(Position position, double angle) {
        rayUnitStepSize = getStepSizedVector(angle);
        rayLength = 0;

        Vector rayDirection = getUnitVectorWithAngle(angle);
        step = getStep(rayDirection);

        Vector startPosition = getCenteredPosition(position);
        currentTilePosition = new Position((int) startPosition.x, (int) startPosition.y);
        ray = getAccumulationVector(
                rayDirection, startPosition,
                currentTilePosition, rayUnitStepSize
        );
    }

    private Vector getCenteredPosition(Position position) {
        double tileSize = 1;
        double tileOffset = tileSize / 2.0;
        return new Vector(position.getX() + tileOffset, position.getY() + tileOffset);
    }

    private Vector getUnitVectorWithAngle(double angle) {
        return new Vector(Math.cos(angle), Math.sin(angle)).normalize();
    }

    private Vector getStepSizedVector(double angle) {
        Vector rayDirection = getUnitVectorWithAngle(angle);
        return new Vector(
                Math.sqrt(1 + (rayDirection.y / rayDirection.x) * (rayDirection.y / rayDirection.x)),
                Math.sqrt(1 + (rayDirection.x / rayDirection.y) * (rayDirection.x / rayDirection.y))
        );
    }

    private Vector getStep(Vector direction) {
        Vector step = new Vector(1, 1);

        if (direction.x < 0) {
            step.x = -1;
        }

        if (direction.y < 0) {
            step.y = -1;
        }

        return step;
    }

    private Vector getAccumulationVector(Vector direction, Vector vRayStart,
                                         Position currentTilePosition, Vector vRayUnitStepSize) {
        Vector accumulationVector = new Vector(0, 0);

        if (direction.x < 0) {
            accumulationVector.x = (vRayStart.x - currentTilePosition.getX()) * vRayUnitStepSize.x;
        } else {
            accumulationVector.x = ((currentTilePosition.getX() + 1) - vRayStart.x) * vRayUnitStepSize.x;
        }

        if (direction.y < 0) {
            accumulationVector.y = (vRayStart.y - currentTilePosition.getY()) * vRayUnitStepSize.y;
        } else {
            accumulationVector.y = ((currentTilePosition.getY() + 1) - vRayStart.y) * vRayUnitStepSize.y;
        }

        return accumulationVector;
    }

    private boolean isInMap(Position position) {
        int worldWidth = scenario.getEnvironment().getWidth();
        int worldHeight = scenario.getEnvironment().getHeight();
        return position.getX() >= 0 && position.getX() < worldWidth && position.getY() >= 0
                && position.getY() < worldHeight;
    }

    private boolean isSeeThrough(Position tilePosition) {
        Tile tile = scenario.getEnvironment().getAt(tilePosition);
        return tile.isPassable() || tile.getType().equals(TileType.WINDOW);
    }

    static class Vector {

        double x;
        double y;

        Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Vector normalize() {
            double length = Math.sqrt(x * x + y * y);
            return new Vector(x / length, y / length);
        }

        public double length() {
            return Math.sqrt(x * x + y * y);
        }

    }

}
