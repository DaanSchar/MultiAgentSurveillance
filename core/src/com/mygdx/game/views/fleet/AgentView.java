package com.mygdx.game.views.fleet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.util.Position;


@Slf4j
public class AgentView extends Actor {

    private final Agent agent;

    private final TextureRepository textureRepository;


    public AgentView(Agent agent) {
        this.agent = agent;
        this.textureRepository = TextureRepository.getInstance();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (agent instanceof Guard) {
            switch (agent.getDirection()) {
                case NORTH -> drawAgent(batch, agent, getFramedAgentTexture("guard"));
                case SOUTH -> drawAgent(batch, agent, getFramedAgentTexture("guardback"));
                case EAST -> drawAgent(batch, agent, getFramedAgentTexture("guardright"));
                case WEST -> drawAgent(batch, agent, getFramedAgentTexture("guardleft"));
                default -> drawAgent(batch, agent, getFramedAgentTexture("guard"));
            }
        }
        if (agent instanceof Intruder) {
            switch (agent.getDirection()) {
                case NORTH -> drawAgent(batch, agent, getFramedAgentTexture("intruder"));
                case SOUTH -> drawAgent(batch, agent, getFramedAgentTexture("intruderback"));
                case EAST -> drawAgent(batch, agent, getFramedAgentTexture("intruderright"));
                case WEST -> drawAgent(batch, agent, getFramedAgentTexture("intruderleft"));
                default -> drawAgent(batch, agent, getFramedAgentTexture("intruder"));
            }
        }

    }

    private void drawAgent(Batch batch, Agent agent, Texture texture) {
        Position position = agent.getPosition();
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Texture getFramedAgentTexture(String name) {
        int totalActions = agent.getTotalActions();

        if (totalActions % 3 == 0) {
            name += Integer.toString(3);
        } else if (totalActions % 2 == 0) {
            name += Integer.toString(2);
        } else {
            name += Integer.toString(1);
        }

        return textureRepository.get(name);
    }

}
