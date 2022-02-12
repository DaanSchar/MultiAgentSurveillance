package nl.maastrichtuniversity.dke.GUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Factory for all images used in the GUI.
 */
public class ImageFactory {

    private static HashMap<String, BufferedImage> images;

    private static final String ROOT = "src/main/resources/images/";

    private static final Logger logger = LoggerFactory.getLogger(ImageFactory.class);

    public static void init() {
        images = new HashMap<>();

        // Icons
        images.put("gameIcon", readImage("settings/icon.png"));
        images.put("guardIcon", readImage("settings/guard.png"));
        images.put("intruderIcon", readImage("settings/intruders.png"));

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

        // Menu backgrounds
        images.put("menuBackground", readImage("settings/background.jpg"));
        images.put("mapSettingsBackground", readImage("settings/mapsettings.jpg"));
        images.put("selectBackground", readImage("settings/selectmap.png"));
        images.put("settingsBackground1", readImage("settings/settings1.jpg"));
        images.put("settingsBackground2", readImage("settings/settings2.jpg"));
        images.put("settingsBackground3", readImage("settings/settings3.jpg"));

        // Textures
        images.put("targetAreaTexture", readImage("texture/targetarea.png"));
        images.put("wallTexture", readImage("texture/wall.png"));
        images.put("teleportTexture", readImage("texture/teleport.png"));
        images.put("shadedTexture", readImage("texture/tree.png"));
        images.put("doorTexture", readImage("texture/door.png"));
        images.put("windowTexture", readImage("texture/window.png"));
        images.put("windowTextureAlt", readImage("texture/window2.png"));
        images.put("sentryTowerTexture", readImage("texture/sentrytower.png"));
        images.put("targetTexture", readImage("texture/target.png"));
        images.put("spawnAreaTexture", readImage("texture/spawnarea.png"));

        // Guard
        images.put("guard1", readImage("guard/guard1.png"));
    }

    /**
     * returns the image with the given name
     * @param key the name of the image
     * @return the image
     */
    public static BufferedImage get(String key) {
        BufferedImage image = images.get(key);

        if (image == null)
            logger.error("Image not found with key: {}", key);

        return image;
    }

    private static BufferedImage readImage(String imagePath) {
        try {
            return ImageIO.read(new File(ROOT + imagePath));
        } catch (Exception e) {
            logger.error("Could not load image {}", ROOT + imagePath);
            e.printStackTrace();
        }
        return null;
    }

}