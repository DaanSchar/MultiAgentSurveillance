package com.mygdx.game.views.sound;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class SoundView {

    private final List<Sound> sounds;
    private final Color color;

    public SoundView(Scenario scenario) {
        this.sounds = scenario.getSoundMap();
        color = new Color(0, 0.1f, 0.1f, 0.5f);
    }

    public void draw(ShapeRenderer batch, float parentAlpha) {
        sounds.forEach(sound -> {
            Position position = sound.getPosition();
            batch.rect(
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT,
                    color, color, color, color
            );
        });
    }

}
