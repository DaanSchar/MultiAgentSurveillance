package com.mygdx.game.views.fleet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.awt.image.BufferedImage;


public class AgentView extends Actor {

    private final Agent agent;
    private int frame = 0; //Current animation frame

    private final TextureRepository textureRepository;

    public AgentView(Agent agent) {
        this.agent = agent;
        this.textureRepository = TextureRepository.getInstance();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //drawAgent(batch, agent);
        if (agent instanceof Guard){
            switch (agent.getDirection()) {
                case NORTH -> drawAgent(batch, agent, getFramedAgentTexture("guard"));
                case SOUTH -> drawAgent(batch, agent, getFramedAgentTexture("guardback"));
                case EAST -> drawAgent(batch, agent, getFramedAgentTexture("guardright"));
                case WEST -> drawAgent(batch, agent, getFramedAgentTexture("guardleft"));
            }
        }
        if (agent instanceof Intruder){
            switch (agent.getDirection()) {
                case NORTH -> drawAgent(batch, agent, getFramedAgentTexture("intruder"));
                case SOUTH -> drawAgent(batch, agent, getFramedAgentTexture("intruderback"));
                case EAST -> drawAgent(batch, agent, getFramedAgentTexture("intruderright"));
                case WEST -> drawAgent(batch, agent, getFramedAgentTexture("intruderleft"));
            }
        }

    }

    private void drawAgent(Batch batch, Agent agent , Texture texture) {
        Position position = agent.getPosition();
        //Texture texture = agent instanceof Guard ? textureRepository.get("guard1") : textureRepository.get("guard1");
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
        updateFrames();
    }
    private Texture getFramedAgentTexture(String name) {
        name += Integer.toString(frame + 1);
        return textureRepository.get(name);
    }
    private void updateFrames() {
        if (frame < 2) {
            frame++;
        } else {
            frame = 0;
        }
    }

}
