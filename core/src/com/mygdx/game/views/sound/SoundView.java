package com.mygdx.game.views.sound;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class SoundView extends Group {

    private @Getter @Setter boolean showSound;

    private final Color color;
    private final Scenario scenario;

    public SoundView(Scenario scenario, boolean showSound) {
        this(scenario);
        this.showSound = showSound;
    }

    public SoundView(Scenario scenario) {
        this.scenario = scenario;
        this.color = new Color(0.2f, 0.44f, 0.7f, 0.3f);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        System.out.println(showSound);

        if (showSound) {
            System.out.println("drawing sound");
            List<Sound> sounds = scenario.getSoundMap();

            sounds.forEach(sound -> {
                Position position = sound.getPosition();
                drawSoundLines(shapeRenderer, position);
            });
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (showSound) {
            List<Sound> sounds = scenario.getSoundMap();

            sounds.forEach(sound -> {
                Color color = makeColor(sound.getPosition(), sound.getSource());
                drawRectangle(batch, sound.getPosition(), color);
            });
        }
    }

    private void drawSoundLines(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX(),
                position.getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                color, color, color, color
        );
    }

    private void drawRectangle(Batch batch, Position position, Color color) {
        batch.draw(
                getPixmapTexture(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Texture getPixmapTexture(Color color) {
        return new Texture(getPixmapRectangle(color));
    }

    private static Pixmap getPixmapRectangle(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

    private Color makeColor(Position position, Position source) {
        float distance = (float) source.distance(position);
        return new Color(0.2f + (distance / 20f), 0.44f, 0.7f, 0.2f);
    }

}
