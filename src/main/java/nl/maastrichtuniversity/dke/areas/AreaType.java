package nl.maastrichtuniversity.dke.areas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AreaType {

    WALL("wall", TransparentLevel.NOT_TRANSPARENT, WalkingLevel.WALL),
    SENTRY_TOWER("sentryTower", TransparentLevel.NOT_TRANSPARENT, WalkingLevel.SENTRY_TOWER),
    TELEPORT("teleport", TransparentLevel.TRANSPARENT, WalkingLevel.TELEPORT),
    SHADED_AREA("shaderArea", TransparentLevel.SHADED, WalkingLevel.SHADED_AREA),
    WINDOW("window", TransparentLevel.TRANSPARENT, WalkingLevel.WINDOW),
    DOOR("door", TransparentLevel.NOT_TRANSPARENT, WalkingLevel.DOOR),
    SHAPE("shape", TransparentLevel.NOT_TRANSPARENT, WalkingLevel.WALL);


    private String type;
    private TransparentLevel transparentLevel;
    private WalkingLevel walkingLevel;

    enum TransparentLevel {
        TRANSPARENT,
        SHADED,
        NOT_TRANSPARENT;
    }

    enum WalkingLevel {
        DOOR,
        WINDOW,
        SHADED_AREA,
        TELEPORT,
        SENTRY_TOWER,
        WALL,
    }

}
