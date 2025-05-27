package bcc.swinggame;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class Utilities {
    public static double PLAYER_SPEED = 7;
    public static final int PROJECTILE_SIZE = 25;

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static ArrayList<Integer> keysPressed = new ArrayList<Integer>(); 

    public static  BufferedImage JAPANESE_ZERO_TOP_WW2;
    public static BufferedImage BRITISH_PLANE_TOP_WW2;
    public static void loadImages(){
        try {
            JAPANESE_ZERO_TOP_WW2 = ImageIO.read(new File("app/src/main/java/bcc/swinggame/JapaneseZeroTopWW2.png"));
            BRITISH_PLANE_TOP_WW2 = ImageIO.read(new File("app/src/main/java/bcc/swinggame/BritishSpitfireTopWW2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static BufferedImage cropToContent(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
    
        int minX = width;
        int minY = height;
        int maxX = 0;
        int maxY = 0;
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = src.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
    
                if (alpha > 0) {
                    if (x < minX) minX = x;
                    if (y < minY) minY = y;
                    if (x > maxX) maxX = x;
                    if (y > maxY) maxY = y;
                }
            }
        }
    
        if (maxX < minX || maxY < minY) {
            // No visible content
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    
        return src.getSubimage(minX, minY, (maxX - minX + 1), (maxY - minY + 1));
    }

    public static void handleKeyPressed(int keyCode) {
        if (!keysPressed.contains(keyCode)) {
            keysPressed.add(keyCode);
        }
    }
    
    public static void handleKeyReleased(int keyCode) {
        for (int i = 0; i < keysPressed.size(); i++) {
            if (keysPressed.get(i) == keyCode) {
                keysPressed.remove(i); // Remove using index for clarity
                break;
            }
        }
    }
    
    // For testing purposes only
private static boolean[] testKeyStates = new boolean[256];

// Test helper methods
public static void resetKeyStates() {
    for (int i = 0; i < testKeyStates.length; i++) {
        testKeyStates[i] = false;
    }
}

public static void setKeyPressed(int keyCode, boolean isPressed) {
    if (keyCode < testKeyStates.length) {
        testKeyStates[keyCode] = isPressed;
    }
}

// Modify the existing isKeyPressed method to use the test states in test mode
private static boolean inTestMode = false;

public static void setTestMode(boolean enabled) {
    inTestMode = enabled;
}

// Update the existing isKeyPressed method
public static boolean isKeyPressed(int keyCode) {
    if (inTestMode) {
        return keyCode < testKeyStates.length && testKeyStates[keyCode];
    } else {
        // Original implementation
        return keysPressed.contains(keyCode);
    }
}
}
