package bcc.swinggame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class HelloWorldExample extends JPanel{
    private PlaneBritish planeBritish;
    private PlaneJapanese planeJapanese;
    private ArrayList<Projectile> britishProjectiles = new ArrayList<>();
    private ArrayList<Projectile> japaneseProjectiles = new ArrayList<>();

    public HelloWorldExample() {
        JFrame frame = new JFrame("Hello World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Good luck!");
        frame.add(this);
        frame.setSize(400, 400);
        frame.setVisible(true);
        // Initialize planeBritish facing right (angle 0)
        planeBritish = new PlaneBritish(50, 200, 30, 30, 0, 20, 20, 15, 15, 50, 200, Utilities.BRITISH_PLANE_TOP_WW2);
        // Initialize planeJapanese facing left (angle 180)
        planeJapanese = new PlaneJapanese(300, 200, 30, 30, 180, 20, 20, 15, 15, 300, 200, Utilities.JAPANESE_ZERO_TOP_WW2);

        // Set up a timer to run updateGame() 30 times per second
        Timer timer = new Timer(33, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("WW2 Dogfight", 20, 20);
        Graphics2D g2d = (Graphics2D) g.create();
        // Draw Japanese plane (facing left)
        if (planeJapanese != null && planeJapanese.isAlive()) {
            int xJ = planeJapanese.getX();
            int yJ = planeJapanese.getY();
            int wJ = 30, hJ = 30;
            double angleJ = Math.toRadians(planeJapanese.getAngle());
            g2d.rotate(angleJ, xJ + wJ/2, yJ + hJ/2);
            g2d.drawImage(Utilities.JAPANESE_ZERO_TOP_WW2, xJ, yJ, wJ, hJ, null);
            g2d.setTransform(new java.awt.geom.AffineTransform()); // Reset
        }
        // Draw British plane (facing right)
        if (planeBritish != null && planeBritish.isAlive()) {
            int xB = planeBritish.getX();
            int yB = planeBritish.getY();
            int wB = 30, hB = 30;
            double angleB = Math.toRadians(planeBritish.getAngle());
            g2d.rotate(angleB, xB + wB/2, yB + hB/2);
            g2d.drawImage(Utilities.BRITISH_PLANE_TOP_WW2, xB, yB, wB, hB, null);
        }
        g2d.dispose();
        // Draw projectiles
        for (Projectile p : britishProjectiles) {
            if (p.isActive()) p.draw(g);
        }
        for (Projectile p : japaneseProjectiles) {
            if (p.isActive()) p.draw(g);
        }
    }
    // Call this method from the game loop to update game state
    public void updateGame() {
        if (planeBritish != null && planeBritish.isAlive()) {
            planeBritish.move();
        }
        if (planeJapanese != null && planeJapanese.isAlive()) {
            planeJapanese.move();
        }
        // Move projectiles and remove inactive ones
        britishProjectiles.removeIf(p -> { p.move(); return !p.isActive(); });
        japaneseProjectiles.removeIf(p -> { p.move(); return !p.isActive(); });
        // Collision: British projectiles hit Japanese plane
        if (planeJapanese != null && planeJapanese.isAlive()) {
            for (Projectile p : britishProjectiles) {
                if (p.isActive() && planeIntersectsProjectile(planeJapanese, p)) {
                    System.out.println("Collision! Plane: " + planeJapanese.getX() + "," + planeJapanese.getY() + " Projectile: " + p.getX() + "," + p.getY());
                    planeJapanese.hit();
                    p.deactivate();
                }
            }
        }
        // Collision: Japanese projectiles hit British plane
        if (planeBritish != null && planeBritish.isAlive()) {
            for (Projectile p : japaneseProjectiles) {
                if (p.isActive() && planeIntersectsProjectile(planeBritish, p)) {
                    System.out.println("Collision! Plane: " + planeJapanese.getX() + "," + planeJapanese.getY() + " Projectile: " + p.getX() + "," + p.getY());
                    planeBritish.hit();
                    p.deactivate();
                }
            }
        }
    }
    // Add key listener to the panel to capture key events
    {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (planeBritish != null) planeBritish.keyPressed(e);
                if (planeJapanese != null) planeJapanese.keyPressed(e);
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_F) {
                    // Fire from British plane
                    britishProjectiles.add(new Projectile(
                        planeBritish.getX() + 15, planeBritish.getY() + 15, 10, 10, planeBritish.getAngle()
                    ));
                }
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    // Fire from Japanese plane
                    japaneseProjectiles.add(new Projectile(
                        planeJapanese.getX() + 15, planeJapanese.getY() + 15, 10, 10, planeJapanese.getAngle()
                    ));
                }
            }
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (planeBritish != null) planeBritish.keyReleased(e);
                if (planeJapanese != null) planeJapanese.keyReleased(e);
            }
        });
    }
    private boolean planeIntersectsProjectile(plane planeObj, Projectile proj) {
        int px = proj.getX(), py = proj.getY(), pw = proj.getWidth(), ph = proj.getHeight();
        int x = planeObj.getX(), y = planeObj.getY(), w = planeObj.getWidth(), h = planeObj.getHeight();
        return px < x + w && px + pw > x && py < y + h && py + ph > y;
    }
}