package nl.maastrichtuniversity.dke.gui;

import javax.swing.*;

public class Window {

    private static JFrame windowInstance;

    public static JFrame getInstance() {
        if (windowInstance == null) {
            createWindowInstance();
        }

        return windowInstance;
    }

    private static void createWindowInstance() {
        windowInstance = new JFrame();
        setDefaultLayout();
    }

    private static void setDefaultLayout() {
        windowInstance.setUndecorated(true);
        windowInstance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowInstance.setSize(800, 600);
        windowInstance.setLocationRelativeTo(null);
        windowInstance.setIconImage(ImageFactory.getInstance().get("gameIcon"));
    }

}
