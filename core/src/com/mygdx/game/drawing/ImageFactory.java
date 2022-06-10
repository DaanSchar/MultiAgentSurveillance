package com.mygdx.game.drawing;

import com.badlogic.gdx.graphics.Texture;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Factory for all images used in the GUI.
 */
@Slf4j
public final class ImageFactory {

    private HashMap<String, BufferedImage> images;
    private static final String ROOT = "core/assets/textures/";

    private static ImageFactory factoryInstance;

    public static ImageFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new ImageFactory();
        }

        return factoryInstance;
    }

    private ImageFactory() {
        init();
    }

    /**
     * Initializes the image factory by loading all images.
     */
    public void init() {
        images = new HashMap<>();

        // Textures
        images.put("wallTexture", readImage("wall/wall.png"));
        images.put("teleportTexture", readImage("texture/teleport.png"));
        images.put("shadedTexture", readImage("texture/tree.png"));
        images.put("doorTexture", readImage("texture/door.png"));
        images.put("windowTexture", readImage("texture/window.png"));
        images.put("windowTextureAlt", readImage("texture/window2.png"));
        images.put("sentryTowerTexture", readImage("texture/sentrytower.png"));
        images.put("targetTexture", readImage("texture/target.png"));
        images.put("spawnAreaTexture", readImage("texture/spawnarea.png"));
        images.put("unknownTexture", readImage("texture/unknown.png"));
        images.put("teleportDestination", readImage("texture/teleportD.png"));
    }

    /**
     * returns the image with the given name.
     *
     * @param key the name of the image
     * @return the image
     */
    public BufferedImage get(String key) {
        BufferedImage image = images.get(key);

        if (image == null) {
            log.error("Image not found with key: {}", key);
        }
        return image;
    }

    private BufferedImage readImage(String imagePath) {
        try {
            return ImageIO.read(new File(ROOT + imagePath));
        } catch (Exception e) {
            log.error("Could not load image {}", ROOT + imagePath);
            e.printStackTrace();
        }
        return null;
    }

}
