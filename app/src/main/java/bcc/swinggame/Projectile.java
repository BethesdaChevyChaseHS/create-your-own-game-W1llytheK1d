package bcc.swinggame;

import java.awt.Graphics;
import java.lang.Math;

public class Projectile {
    private int x, y;
    private int width, height;
    private double angle;
    private int speed = 10;
    private boolean active = true;

    public Projectile(int x, int y, int width, int height, double angle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    public void move() {
        // Move in both x and y based on angle
        double radians = Math.toRadians(angle);
        x += (int) (speed * Math.cos(radians));
        y += (int) (speed * Math.sin(radians));
        // Deactivate if out of bounds (simple check)
        if (x < 0 || x > 400 || y < 0 || y > 400) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void deactivate() { active = false; }
}
