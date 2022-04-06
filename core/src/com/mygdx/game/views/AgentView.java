package com.mygdx.game.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class AgentView {

    private final List<Guard> guards;
    private final List<Intruder> intruders;

    private final TextureRepository texturesRepository;

    public AgentView(List<Guard> guards, List<Intruder> individuals) {
        this.guards = guards;
        this.intruders = individuals;
        this.texturesRepository = TextureRepository.getInstance();
    }

    public void draw(Batch batch) {
        guards.forEach(guard -> drawGuard(batch, guard));
    }

    private void drawGuard(Batch batch, Guard guard) {
        Position position = guard.getPosition();
        batch.draw(texturesRepository.get("guard1"),
                position.getX() * texturesRepository.getTextureWidth(),
                position.getY() * texturesRepository.getTextureHeight()
        );
    }
}
