package com.mygdx.game.views.sound;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

@Slf4j
public class SoundView extends Actor {

    private @Getter @Setter boolean showSound;

    private final Color color;
    private final Scenario scenario;

    private final TextureRepository textureRepository;

    public SoundView(Scenario scenario, boolean showSound) {
        this(scenario);
        this.showSound = showSound;
    }

    public SoundView(Scenario scenario) {
        this.scenario = scenario;
        this.color = new Color(0.2f, 0.44f, 0.7f, 0.3f);
        this.textureRepository = TextureRepository.getInstance();
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (showSound) {
            List<Sound> sounds = scenario.getSoundMap();

            sounds.forEach(sound -> {
                Position position = sound.getPosition();
                drawSoundOutline(shapeRenderer, position);
            });
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (showSound) {
            List<Sound> sounds = scenario.getSoundMap();

            sounds.forEach(sound -> drawTile(batch, sound));
        }
    }

    private void drawTile(Batch batch, Sound sound) {
        batch.draw(
                getTileTexture(sound),
                sound.getPosition().getX(),
                sound.getPosition().getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Texture getTileTexture(Sound sound) {
        Position source = sound.getSource();
        float distance = (float) sound.getPosition().distance(source);
        return textureRepository.getTile(makeColor(distance));
    }

    private void drawSoundOutline(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX(),
                position.getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                color, color, color, color
        );
    }

    private Color makeColor(float distance) {
        return new Color(0.2f + (distance / 20f), 0.44f, 0.7f, 0.2f);
    }

}
