package com.mygdx.game.views.fleet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class MarkerView extends Actor {
    private final CommunicationMark mark;

    private final TextureRepository textureRepository;

    public MarkerView(CommunicationMark mark) {
        this.mark = mark ;
        this.textureRepository = TextureRepository.getInstance();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawMarker(batch, mark);
    }
    private void drawMarker(Batch batch, CommunicationMark mark) {
        Position position = mark.getPosition();
        Texture texture;
        if(mark.getType().equals(CommunicationType.VISION_BLUE)){
            texture = textureRepository.get("blueMarker");
        }else if(mark.getType().equals(CommunicationType.VISION_RED)){
            texture = textureRepository.get("redMarker");
        }else if(mark.getType().equals(CommunicationType.VISION_GREEN)){
            texture = textureRepository.get("greenMarker");
        }else{
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
