package bcc.swinggame;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App  {
    public static void main(String[] args) {
        Utilities.loadImages();
        HelloWorldExample helloWorldExample = new HelloWorldExample();
        helloWorldExample.setVisible(true);

        // Game loop using Timer (calls every 16 ms ~ 60 FPS)
        //Timer timer = new Timer(16, new ActionListener() {
            //@Override
            //public void actionPerformed(ActionEvent e) {
                //helloWorldExample.updateGame(); // You should implement this method in HelloWorldExample
                //helloWorldExample.repaint();
            //}
        //});
        //timer.start();
    }

}
