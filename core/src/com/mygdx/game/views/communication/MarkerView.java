package com.mygdx.game.views.communication;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.agents.modules.communication.Mark;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class MarkerView extends Actor {
    private final Mark mark;

    private final TextureRepository textureRepository;

    public MarkerView(Mark mark) {
        this.mark = mark;
        this.textureRepository = TextureRepository.getInstance();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawMarker(batch, mark);
    }

    private void drawMarker(Batch batch, Mark mark) {
        Position position = mark.getPosition();
        Texture texture;
        if (mark.getType().equals(CommunicationType.VISION_BLUE)) {
            texture = textureRepository.get("blueMarker");
        } else if (mark.getType().equals(CommunicationType.VISION_RED)) {
            texture = textureRepository.get("redMarker");
        } else if (mark.getType().equals(CommunicationType.VISION_GREEN)) {
            texture = textureRepository.get("greenMarker");
        } else {
            texture = null;

        }
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }
}
