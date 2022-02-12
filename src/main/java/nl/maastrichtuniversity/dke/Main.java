package nl.maastrichtuniversity.dke;

import nl.maastrichtuniversity.dke.areas.*;
import nl.maastrichtuniversity.dke.areas.Rectangle;
import nl.maastrichtuniversity.dke.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * visual tester for collision detection
 */
public class Main extends JPanel{

    Area a1 = new Rectangle(50, 50, 70, 70);
    Area a2 = new Circle(70, 70, 20);
    Vector point = new Vector(60, 60);

    public Main() {
        KeyLis listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        g.drawRect(
                (int) a1.getPosition().getX(),
                (int) a1.getPosition().getY(),
                (int) a1.getWidth(),
                (int) a1.getHeight()
        );
        g.drawOval(
                (int) point.getX() - 2,
                (int) point.getY() - 2,
                4,
                4
        );
    }

    public static void init() {
        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(1000, 1000);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });



        Container contentPane = frame.getContentPane();
        contentPane.add(new Main());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::init);
    }


    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> a1.translate(new Vector(-10, 0));
                case KeyEvent.VK_RIGHT -> a1.translate(new Vector(10, 0));
                case KeyEvent.VK_UP -> a1.translate(new Vector(0, -10));
                case KeyEvent.VK_DOWN -> a1.translate(new Vector(0, 10));
                default -> throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
            }

            System.out.println(a1.containsPoint(point.getX(), point.getY()));

            repaint();
        }
    }


















//    public static void pointInPolygon() {
//        Vector p = new Vector(6, 6);
//        Rectangle r = new Rectangle(3, 3, 7, 6);
//
//        int n = r.getVertices().length;
//        int count = 0;
//
//        for (int i = 0; i < n; i++) {
//            Vector a = r.getVertices()[i];
//            Vector b;
//            if (i == n - 1)
//                b = r.getVertices()[0];
//            else
//                b = r.getVertices()[i + 1];
//            System.out.println(a + "  " + b);
//
//            if (p.getY() < a.getY() != p.getY() < b.getY() && p.getY() < (b.getY() - a.getY()) * (p.getY() - a.getY()) / (b.getY() - a.getY()) + a.getY()) {
//                count++;
//            }
//        }
//
//        System.out.println(count % 2 == 1 ? "Outside" : "Inside");
//    }

}
