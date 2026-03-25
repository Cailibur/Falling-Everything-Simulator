package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import particles.*;

public class Gamepanel extends JPanel implements Runnable{
    public final int blockSize = 4;
    public final int scale = 1;

    public final int displaySize = blockSize * scale;
    public final int ScreenRow = 200;
    public final int ScreenCol = 300;
    public final int ScreenHeight = ScreenRow * displaySize;
    public final int ScreenWidth = ScreenCol * displaySize;
    public Particle[][] Grid = new Particle[ScreenCol+10][ScreenRow+10];
    private int mouse_dx = 0;
    private int mouse_dy = 0;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouse_dragged = false;

    Thread gameThread;
    Timer MainTimer;
    UI MainUI;
    KeyHandler KeyH = new KeyHandler();
    final private static Gamepanel gamepanelInstance = new Gamepanel();

    //Player(temporary)
    int X = 100, Y = 100, Speed = 4;

    private Gamepanel() {
        this.setPreferredSize(new Dimension(ScreenWidth , ScreenHeight)); // Generate the Window
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Open the double buffer
        this.addKeyListener(KeyH); //Listening the key input
        MouseAdapter adapeter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                mouseX = e.getX() / displaySize;
                mouseY = e.getY() / displaySize;
                mouse_dragged = true;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouse_dx = 3 * (e.getX() / displaySize - mouseX);
                mouse_dy = 3 * (e.getY() / displaySize - mouseY);
                super.mouseDragged(e);
                mouseX = e.getX() / displaySize;
                mouseY = e.getY() / displaySize;
                //System.out.println(mouseX+" "+mouseY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mouse_dragged = false;
            }
        };
        //Listening to the mouse(pressing and dragging)
        this.addMouseListener(adapeter);
        this.addMouseMotionListener(adapeter);
        this.setFocusable(true);
    }

    public void StartGameThread(){
        gameThread = new Thread(this); //Transform this gamepanel to the thread
        gameThread.start();
    }

    public static Gamepanel getInstance(){
        return gamepanelInstance;
    }

    @Override
    public void run() {
        //this place can be transform into an instance
        MainTimer = new Timer();
        MainUI = new UI();
        while(gameThread != null){
            if(MainTimer.TimeUpdate()){
                update();
                repaint();
            }
        }
    }

    public void update(){
        if(mouse_dragged){
            //System.out.println(mouseX+" "+mouseY);
            if(mouseX >= 0 && mouseX < ScreenCol && mouseY >= 0 && mouseY < ScreenRow){
                Grid[1+mouseX][mouseY] = new Water(1+mouseX, mouseY, mouse_dx, mouse_dy);
                Grid[mouseX][1+mouseY] = new Sand(mouseX, 1+mouseY, mouse_dx, mouse_dy);
                Grid[mouseX-1][mouseY] = new Water(mouseX-1, mouseY, mouse_dx, mouse_dy);
                Grid[mouseX][mouseY-1] = new Stone(mouseX, mouseY-1, mouse_dx, mouse_dy);
                Grid[mouseX][mouseY] = new Sand(mouseX, mouseY, mouse_dx, mouse_dy);
            }
        }
        for(int i = ScreenCol - 1 ; i >= 0 ; i--){
            for(int j = ScreenRow - 1 ; j >= 0 ; j--){
                if(Grid[i][j] != null && !Grid[i][j].Updated) Grid[i][j].update();
            }
        }
        if(KeyH.up) Y -= Speed;
        else if(KeyH.down) Y += Speed;
        else if(KeyH.left) X -= Speed;
        else if(KeyH.right) X += Speed;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        // For all those below, transfer g2d into every draw function, and use those draw in each object to finish the rendering period.
        for(int i = ScreenCol - 1 ; i >= 0 ; i--){
            for(int j = 0 ; j <= ScreenRow - 1 ; j++){
                if(Grid[i][j] != null){
                    Grid[i][j].Draw(g2d);
                }
            }
        }
        MainUI.draw(g2d);
        g2d.setColor(Color.white);
        g2d.fillRect(X, Y, displaySize, displaySize);
        g2d.dispose(); //release the resources using
    }
}