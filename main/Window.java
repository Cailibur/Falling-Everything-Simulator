package main;

import javax.swing.JFrame;
//Main window control system
public class Window extends JFrame{
    final private Gamepanel gamePanel = Gamepanel.getInstance(); //single instance
    public Window(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Falling Everything Simulator");
        this.add(gamePanel); // bind the gamepanel to the window
        this.pack();
        this.setLocationRelativeTo(null);
    }
    public void Show(){
        this.setVisible(true);
        gamePanel.StartGameThread();
    }
}