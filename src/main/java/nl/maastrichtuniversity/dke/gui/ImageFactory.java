package nl.maastrichtuniversity.dke.gui;

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
public class ImageFactory {

    private HashMap<String, BufferedImage> images;

    private static final String ROOT = "src/main/resources/images/";

    private static final Logger logger = LoggerFactory.getLogger(ImageFactory.class);

    private static ImageFactory factoryInstance;

    public static ImageFactory getInstance() {
        if (factoryInstance == null) { factoryInstance = new ImageFactory(); }

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
        images.put("unknownTexture", readImage("texture/unknown.png"));
        images.put("smellTexture", readImage("texture/sound.png"));
        images.put("soundTexture", readImage("texture/smell.png"));


        // Guard
        images.put("guard1", readImage("guard/guard1.png"));
        images.put("guard2", readImage("guard/guard2.png"));
        images.put("guard3", readImage("guard/guard3.png"));
        images.put("guard4", readImage("guard/guard4.png"));

        images.put("guardback1", readImage("guard/guardback1.png"));
        images.put("guardback2", readImage("guard/guardback2.png"));
        images.put("guardback3", readImage("guard/guardback3.png"));
        images.put("guardback4", readImage("guard/guardback4.png"));


        images.put("guardleft1", readImage("guard/guardleft.png"));
        images.put("guardleft2", readImage("guard/guardleft2.png"));
        images.put("guardleft3", readImage("guard/guardleft3.png"));
        images.put("guardleft4", readImage("guard/guardleft4.png"));

        images.put("guardright1", readImage("guard/guardright1.png"));
        images.put("guardright2", readImage("guard/guardright2.png"));
        images.put("guardright3", readImage("guard/guardright3.png"));
        images.put("guardright4", readImage("guard/guardright4.png"));
    }

    /**
     * returns the image with the given name
     * @param key the name of the image
     * @return the image
     */
    public BufferedImage get(String key) {
        BufferedImage image = images.get(key);

        if (image == null)
            logger.error("Image not found with key: {}", key);

        return image;
    }

    private BufferedImage readImage(String imagePath) {
        try {
            return ImageIO.read(new File(ROOT + imagePath));
        } catch (Exception e) {
            logger.error("Could not load image {}", ROOT + imagePath);
            e.printStackTrace();
        }
        return null;
    }

}
