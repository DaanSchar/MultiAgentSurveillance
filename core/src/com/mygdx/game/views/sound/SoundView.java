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
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class SoundView extends Group {

    private @Getter @Setter boolean showSound;

    private final Color color;
    private final Scenario scenario;
    private final HashMap<Integer, Texture> textures;

    public SoundView(Scenario scenario, boolean showSound) {
        this(scenario);
        this.showSound = showSound;
    }

    public SoundView(Scenario scenario) {
        this.scenario = scenario;
        this.color = new Color(0.2f, 0.44f, 0.7f, 0.3f);
        this.textures = new HashMap<>();
        makeTextures();
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

            sounds.forEach(sound -> drawFill(batch, sound));
        }
    }

    private void drawFill(Batch batch, Sound sound) {
        batch.draw(
                getTexture(sound),
                sound.getPosition().getX(),
                sound.getPosition().getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private void makeTextures() {
        for (int i = 0; i < 30; i++) {
            textures.put(i, getPixmapTexture(makeColor(i)));
        }
    }

    private Texture getTexture(Sound sound) {
        Position source = sound.getSource();
        int distance = (int) sound.getPosition().distance(source);
        return textures.get(distance);
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

    private Color makeColor(int distance) {
        return new Color(0.2f + (distance / 20f), 0.44f, 0.7f, 0.2f);
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

}
