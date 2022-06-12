package com.mygdx.game.drawing;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
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
        // Menu backgrounds
        images.put("menuBackground", readImage("settings/background.jpg"));
        images.put("mapSettingsBackground", readImage("settings/mapsettings.jpg"));
        images.put("selectBackground", readImage("settings/selectmap.png"));
        images.put("settingsBackground1", readImage("settings/settings1.jpg"));
        images.put("settingsBackground2", readImage("settings/settings2.jpg"));
        images.put("settingsBackground3", readImage("settings/settings3.jpg"));
        // Buttons
        images.put("backButton", readImage("settings/back.png"));
        images.put("designButton", readImage("settings/design.png"));
        images.put("exitButton", readImage("settings/exit.jpg"));
        images.put("manuallyButton", readImage("settings/manually.png"));
        images.put("nextButton", readImage("settings/next.jpg"));
        images.put("playButton", readImage("settings/play.jpg"));
        images.put("selectButton", readImage("settings/select.png"));
        images.put("startButton", readImage("settings/start.jpg"));
        images.put("uploadButton", readImage("settings/upload.png"));

        // Text
        images.put("uploadText", readImage("settings/uploadfile.png"));

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
