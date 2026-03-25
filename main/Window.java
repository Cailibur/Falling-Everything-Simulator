package main;
import javax.swing.JFrame;
//Main window control system
public class Window{
    final private JFrame window;
    final private Gamepanel gamePanel = Gamepanel.getInstance(); //single instance
    public Window(){
        this.window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Falling Everything Simulator");
        window.add(gamePanel); // bind the gamepanel to the window
        window.pack();
        window.setLocationRelativeTo(null);
    }
    public void Show(){
        window.setVisible(true);
        gamePanel.StartGameThread();
    }
}