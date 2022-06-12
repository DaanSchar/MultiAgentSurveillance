package com.mygdx.game.drawing;


import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.MapSaver;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import nl.maastrichtuniversity.dke.scenario.util.RandomMapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Play class .
 */

public class Drawing extends JLabel {
    private int textureSize = 20;
    private boolean agentMapNumBoolean = false;
    private MapSaver mapSaver = new MapSaver();
    private List<Tile> walltiles = new ArrayList<>();
    private List<Tile> targettiles = new ArrayList<>();
    private List<Tile> spawnguardstiles = new ArrayList<>();
    private List<Tile> spawnintruderstiles = new ArrayList<>();
    private List<Tile> shadedtiles = new ArrayList<>();
    private List<Tile> windowtiles = new ArrayList<>();
    private List<Tile> doortiles = new ArrayList<>();
    private List<Tile> sentrytowertiles = new ArrayList<>();
    private List<Tile> teleporttiles = new ArrayList<>();
    private JFrame window = new JFrame("Draw");
    private TileType texturetype = TileType.WALL;
    private RandomMapGenerator mapcreater = new RandomMapGenerator();

    public Drawing() {
        mapcreater.startBuild();
        MouseSpy mouseListener = new MouseSpy();
        addMouseListener(mouseListener);

    }

    public void paintComponent(Graphics g) {

        drawEnvironment(g);
    }


    private final ImageFactory imageFactory = ImageFactory.getInstance();

    private void drawEnvironment(Graphics g) {
        drawAreas(g, walltiles, imageFactory.get("wallTexture"));
        drawAreas(g, targettiles, imageFactory.get("targetTexture"));
        drawAreas(g, windowtiles, imageFactory.get("windowTexture"));
        drawAreas(g, doortiles, imageFactory.get("doorTexture"));
        drawAreas(g, teleporttiles, imageFactory.get("teleportTexture"));
        drawAreas(g, sentrytowertiles, imageFactory.get("sentryTowerTexture"));
        drawAreas(g, shadedtiles, imageFactory.get("shadedTexture"));
        drawAreas(g, spawnguardstiles, imageFactory.get("spawnAreaTexture"));
        drawAreas(g, spawnintruderstiles, imageFactory.get("spawnAreaTexture"));

    }

    private void drawAreas(Graphics g, List<Tile> tiles, BufferedImage image) {
        for (Tile tile : tiles) {
            drawArea(g, tile, image);
        }
    }

    private void drawArea(Graphics g, Tile tile, BufferedImage image) {
        g.drawImage(
                image,
                tile.getPosition().getX() * (textureSize),
                tile.getPosition().getY() * (textureSize),
                textureSize,
                textureSize,
                null
        );
    }

    private Tile createTile(TileType type, int x, int y) {
        Tile tile = new Tile(new Position(x, y), type);
        mapcreater.createArea(x, y, x + 1, y + 1, type);
        if (type == TileType.WALL) {
            walltiles.add(tile);
        }
        if (type == TileType.TARGET) {
            targettiles.add(tile);
        }
        if (type == TileType.SPAWN_GUARDS) {
            spawnguardstiles.add(tile);
        }
        if (type == TileType.SPAWN_INTRUDERS) {
            spawnintruderstiles.add(tile);
        }
        if (type == TileType.DOOR) {
            doortiles.add(tile);
        }
        if (type == TileType.WINDOW) {
            windowtiles.add(tile);
        }
        if (type == TileType.SENTRY) {
            sentrytowertiles.add(tile);
        }
        if (type == TileType.SHADED) {
            shadedtiles.add(tile);
        }
        if (type == TileType.TELEPORT) {
            teleporttiles.add(tile);
        }
        return tile;
    }

    public TileType setTextureDrawing(Object selectedItem) {
        String string = (String) selectedItem;
        if (string.equals(TileType.WALL.toString())) {
            texturetype = TileType.WALL;
            return TileType.WALL;
        }
        if (string.equals(TileType.TARGET.toString())) {
            texturetype = TileType.TARGET;
            return TileType.TARGET;
        }
        if (string.equals(TileType.DOOR.toString())) {
            texturetype = TileType.DOOR;
            return TileType.DOOR;
        }
        if (string.equals(TileType.WINDOW.toString())) {
            texturetype = TileType.WINDOW;
            return TileType.WINDOW;
        }
        if (string.equals(TileType.SHADED.toString())) {
            texturetype = TileType.SHADED;
            return TileType.SHADED;
        }
        if (string.equals(TileType.SPAWN_GUARDS.toString())) {
            texturetype = TileType.SPAWN_GUARDS;
            return TileType.SPAWN_GUARDS;
        }
        if (string.equals(TileType.SPAWN_INTRUDERS.toString())) {
            texturetype = TileType.SPAWN_INTRUDERS;
            return TileType.SPAWN_INTRUDERS;
        }
        if (string.equals(TileType.SENTRY.toString())) {
            texturetype = TileType.SENTRY;
            return TileType.SENTRY;
        }
        if (string.equals(TileType.TELEPORT.toString())) {
            texturetype = TileType.TELEPORT;
            return TileType.TELEPORT;
        }
        return TileType.WALL;
    }

    public void savemap() {
        mapcreater.buildDraw();
    }

    class MouseSpy implements MouseWheelListener, MouseMotionListener, MouseListener {
        Point point;
        boolean released = false;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            point = MouseInfo.getPointerInfo().getLocation();
            int x = (int) (point.getX() / 20);
            int y = (int) (point.getY() / 20);
            Tile tile = createTile(texturetype, x, y);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            released = true;

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

    public static void main(String[] args) {
        Drawing drawing = new Drawing();

    }
}
