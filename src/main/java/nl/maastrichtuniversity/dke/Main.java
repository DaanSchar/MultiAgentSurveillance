package nl.maastrichtuniversity.dke;

import nl.maastrichtuniversity.dke.areas.Circle;
import nl.maastrichtuniversity.dke.areas.Collider;
import nl.maastrichtuniversity.dke.areas.Rectangle;
import nl.maastrichtuniversity.dke.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * visual tester for collision detection
 */
public class Main extends JPanel{

    nl.maastrichtuniversity.dke.areas.Polygon r1 = new Circle(50, 50, 100);
    nl.maastrichtuniversity.dke.areas.Polygon r2 = new Rectangle(60, 70, 150, 200);
    nl.maastrichtuniversity.dke.areas.Polygon middle = new Circle(300, 300, 5);
    Polygon p1;
    Polygon p2;
    Polygon mink;

    public Main() {
        KeyLis listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        p1 = new Polygon();
        p2 = new Polygon();
        mink = new Polygon();
        Polygon nul = new Polygon();

        var gjk = new Collider(r1, r2);

        for (Vector v : r1.getVertices()) {
            p1.addPoint((int) v.getX(), (int) v.getY());
        }

        for (Vector v : r2.getVertices()) {
            p2.addPoint((int) v.getX(), (int) v.getY());
        }

        for (Vector v : middle.getVertices()) {
            nul.addPoint((int) v.getX(), (int) v.getY());
        }

        gjk.isHit();

        for (Vector v : gjk.getSimplex().getVertices()) {
            mink.addPoint((int) v.getX() + 300, (int) v.getY() + 300);
        }

        g.drawPolygon(p1);
        g.drawPolygon(p2);
        g.drawPolygon(mink);
        g.drawPolygon(nul);

//        System.out.println(r1.isColliding(r2));
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
                case KeyEvent.VK_LEFT -> r1.move(new Vector(-10, 0));
                case KeyEvent.VK_RIGHT -> r1.move(new Vector(10, 0));
                case KeyEvent.VK_UP -> r1.move(new Vector(0, -10));
                case KeyEvent.VK_DOWN -> r1.move(new Vector(0, 10));
                default -> throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
            }
            System.out.println(r1.isColliding(r2));

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
