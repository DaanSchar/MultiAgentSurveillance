package com.mygdx.game.drawing;


import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.MapSaver;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Play class .
 */

public class Drawing extends JLabel {
    private int textureSize = 20;
    private boolean agentMapNumBoolean = false;
    private MapSaver mapSaver = new MapSaver();
    List<Tile> walltiles = new ArrayList<>();
    List<Tile> targettiles = new ArrayList<>();
    JFrame window = new JFrame("Draw");
    TileType texturetype = TileType.WALL;

    public Drawing() {
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
        if (type == TileType.WALL) {
            walltiles.add(tile);
        }
        if (type == TileType.TARGET) {
            targettiles.add(tile);
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
        return TileType.WALL;
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
