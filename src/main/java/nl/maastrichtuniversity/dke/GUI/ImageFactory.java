package nl.maastrichtuniversity.dke.GUI;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFactory {

    // Icon
    private static @Getter BufferedImage gameIcon;
    private static @Getter BufferedImage guardIcon;
    private static @Getter BufferedImage intruderIcon;

    // Menu backgrounds
    private static @Getter BufferedImage menuBackground;
    private static @Getter BufferedImage mapSettingsBackground;
    private static @Getter BufferedImage selectBackground;
    private static @Getter BufferedImage settingsBackground1;
    private static @Getter BufferedImage settingsBackground2;
    private static @Getter BufferedImage settingsBackground3;

    // Buttons
    private static @Getter BufferedImage backButton;
    private static @Getter BufferedImage designButton;
    private static @Getter BufferedImage exitButton;
    private static @Getter BufferedImage manuallyButton;
    private static @Getter BufferedImage nextButton;
    private static @Getter BufferedImage playButton;
    private static @Getter BufferedImage selectButton;
    private static @Getter BufferedImage startButton;
    private static @Getter BufferedImage uploadButton;

    // Text
    private static @Getter BufferedImage uploadText;

    // Textures
    private static @Getter BufferedImage targetAreaTexture;
    private static @Getter BufferedImage wallTexture;
    private static @Getter BufferedImage teleportTexture;
    private static @Getter BufferedImage shadedTexture;
    private static @Getter BufferedImage doorTexture;
    private static @Getter BufferedImage windowTexture;
    private static @Getter BufferedImage windowTextureAlt;
    private static @Getter BufferedImage sentryTowerTexture;
    private static @Getter BufferedImage targetTexture;
    private static @Getter BufferedImage spawnAreaTexture;


    private static final String ROOT = "src/main/resources/images/";

    private static final Logger logger = LoggerFactory.getLogger(ImageFactory.class);

    public static void init() {
        // Icons
        gameIcon = readImage("settings/icon.png");
        guardIcon = readImage("settings/guard.png");
        intruderIcon = readImage("settings/intruders.png");

        // Buttons
        backButton = readImage("settings/back.png");
        designButton = readImage("settings/design.png");
        exitButton = readImage("settings/exit.jpg");
        manuallyButton = readImage("settings/manually.png");
        nextButton = readImage("settings/next.jpg");
        playButton = readImage("settings/play.jpg");
        selectButton = readImage("settings/select.png");
        selectBackground = readImage("settings/selectmap.png");
        startButton = readImage("settings/start.jpg");
        uploadButton = readImage("settings/upload.png");

        // Text
        uploadText = readImage("settings/uploadfile.png");

        // Menu backgrounds
        mapSettingsBackground = readImage("settings/mapsettings.jpg");
        menuBackground = readImage("texture/targetarea.png");
        settingsBackground1 = readImage("settings/settings1.jpg");
        settingsBackground2 = readImage("settings/settings2.jpg");
        settingsBackground3 = readImage("settings/settings3.jpg");

        // Textures
        targetAreaTexture = readImage("texture/targetarea.png");
        wallTexture = readImage("texture/wall.png");
        teleportTexture = readImage("texture/teleport.png");
        shadedTexture = readImage("texture/tree.png");
        doorTexture = readImage("texture/door.png");
        windowTexture = readImage("texture/window.png");
        windowTextureAlt = readImage("texture/window2.png");
        sentryTowerTexture = readImage("texture/sentrytower.png");
        targetTexture = readImage("texture/target.png");
        spawnAreaTexture = readImage("texture/spawnarea.png");
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
