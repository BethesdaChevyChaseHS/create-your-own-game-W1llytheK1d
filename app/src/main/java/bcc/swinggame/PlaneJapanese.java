package bcc.swinggame;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

public class PlaneJapanese extends plane {
    private JComponent parentComponent;

    public PlaneJapanese(int x, int y, int width, int height, int angle, int xvelocity, int yvelocity, int ammoWidth, int ammoHeight, int ammoX, int ammoY, Image url) {
        super(300, 200, 30, 30, 270, 20, 20, 15, 15, 400, 200, Utilities.JAPANESE_ZERO_TOP_WW2);
        this.parentComponent = null;
    }
    
    public void keyPressed(KeyEvent e) {
        Utilities.handleKeyPressed(e.getKeyCode());
    }
    public void keyReleased(KeyEvent e) {
        Utilities.handleKeyReleased(e.getKeyCode());
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getAngle() {
        return angle;
    }
    public void move() {
        if (Utilities.keysPressed.contains(KeyEvent.VK_UP)) {
            if(y > 40) {
                y -= yvelocity;
                angle -= 5;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_DOWN)) {
            if(y < 360) {
                y += yvelocity;
                angle += 5;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_LEFT)) {
            if(x > 40){
                x -= xvelocity;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_RIGHT)) {
            if(x < 360){
                x += xvelocity;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_SPACE)){
            // Fire ammo logic can be added here
            // For now, just print a message or handle ammo firing
            System.out.println("Japanese plane fired ammo!");
        }
        if (parentComponent != null) {
            parentComponent.repaint();
        }
    }
}
