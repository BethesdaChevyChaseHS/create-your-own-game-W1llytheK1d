package bcc.swinggame;
import java.awt.Image;
import java.awt.event.KeyEvent;

//import java.awt.event.KeyEvent;
import javax.swing.JComponent;

public class PlaneBritish extends plane {
    private JComponent parentComponent;

    public PlaneBritish(int x, int y, int width, int height, int angle, int xvelocity, int yvelocity, int ammoWidth, int ammoHeight, int ammoX, int ammoY, Image url) {
        super(0, 200, 30, 30, 90, 20, 20, 15, 15, 0, 200, Utilities.BRITISH_PLANE_TOP_WW2);    
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
        if (Utilities.keysPressed.contains(KeyEvent.VK_W)) {
            if(y > 40) {
                y -= yvelocity;
                angle += 5;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_S)) {
            if(y < 360) {
                y += yvelocity;
                angle -= 5;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_A)) {
            if(x > 40) {
                x -= xvelocity;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_D)) {
            if(x < 360) {
                x += xvelocity;
            }
        }
        if (Utilities.keysPressed.contains(KeyEvent.VK_F)){
            // Fire ammo logic can be added here
            // For now, just print a message or handle ammo firing
            System.out.println("British plane fired ammo!");
        }
        if (parentComponent != null) {
            parentComponent.repaint();
        }
    }
}