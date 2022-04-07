package com.mygdx.game.views.fleet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;


public class AgentView extends Actor {

    private final Agent agent;

    private final TextureRepository textureRepository;

    public AgentView(Agent agent) {
        this.agent = agent;
        this.textureRepository = TextureRepository.getInstance();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawAgent(batch, agent);
    }

    private void drawAgent(Batch batch, Agent agent) {
        Position position = agent.getPosition();
        Texture texture = agent instanceof Guard ? textureRepository.get("guard1") : textureRepository.get("guard1");
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }
}
