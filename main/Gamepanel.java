package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import particles.*;
import statemachine.*;

/*This is the main gamepanel which I create it as an Instance, and the main game thread was created here for running the project*/

public class Gamepanel extends JPanel implements Runnable{
    //Default Settings
    public final static int blockSize = 4;
    public final static int scale = 1;
    public final static int displaySize = blockSize * scale;
    public final static int ScreenRow = 200;
    public final static int ScreenCol = 300;
    public final static int ScreenHeight = ScreenRow * displaySize;
    public final static int ScreenWidth = ScreenCol * displaySize;
    public static Particle[][] Grid = new Particle[ScreenCol+10][ScreenRow+10];

    //System
    final private static Gamepanel gamepanelInstance = new Gamepanel();
    Thread gameThread;
    Timer MainTimer;
    UI MainUI;
    KeyHandler keyH;
    MouseHandler mouseH;

    //Player(temporary)
    public int currentParticle = Particle.Sand;
    public int originMouseX, originMouseY;

    //PanelStateMachine
    Statemachine PanelStateMachine = new Statemachine();
    State runningState = new State("RunningState", PanelStateMachine);
    State pauseState = new State("PauseState", PanelStateMachine);
    State titleState = new State("TitleState", PanelStateMachine);

    private Gamepanel() {
        keyH = new KeyHandler();
        mouseH = new MouseHandler();
        this.setPreferredSize(new Dimension(ScreenWidth , ScreenHeight)); // Generate the Window
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Open the double buffer
        this.addKeyListener(keyH); //Listening the key input
        //Listening to the mouse(pressing and dragging)
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
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
        PanelStateMachine.ChangeState(titleState);
        while(gameThread != null){
            if(MainTimer.TimeUpdate()){
                update();
                repaint();
            }
        }
    }

    public void update(){
        if(mouseH.mouse_dragged && PanelStateMachine.currentState.stateName != "TitleState"){
            //System.out.println(mouseX+" "+mouseY);
            if(mouseH.mouseX >= 0 && mouseH.mouseX < ScreenCol && mouseH.mouseY >= 0 && mouseH.mouseY < ScreenRow){
                if(currentParticle == Particle.Sand){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Sand(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Sand(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Sand(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Sand(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Sand(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Water){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Water(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Water(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Water(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Water(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Water(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Stone){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Stone(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Stone(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Stone(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Stone(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Stone(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Wood){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Wood(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Wood(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Wood(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Wood(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Wood(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Acid){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Acid(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Acid(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Acid(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Acid(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Acid(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Gas){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Gas(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Gas(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Gas(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Gas(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Gas(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Fire){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Fire(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Fire(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Fire(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Fire(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Fire(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Smoke){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Smoke(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Smoke(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Smoke(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Smoke(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Smoke(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
                else if(currentParticle == Particle.Steam){
                    Grid[1+mouseH.mouseX][mouseH.mouseY] = new Steam(1+mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][1+mouseH.mouseY] = new Steam(mouseH.mouseX, 1+mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX-1][mouseH.mouseY] = new Steam(mouseH.mouseX-1, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY-1] = new Steam(mouseH.mouseX, mouseH.mouseY-1, mouseH.mouse_dx, mouseH.mouse_dy);
                    Grid[mouseH.mouseX][mouseH.mouseY] = new Steam(mouseH.mouseX, mouseH.mouseY, mouseH.mouse_dx, mouseH.mouse_dy);
                }
            }
        }
        for(int i = ScreenCol - 1 ; i >= 0 ; i--){
            for(int j = ScreenRow - 1 ; j >= 0 ; j--){
                if(Grid[i][j] != null && !Grid[i][j].Updated && PanelStateMachine.currentState.stateName == "RunningState") Grid[i][j].update();
            }
        }
        if(keyH.up && !keyH.OnpressUp){
            currentParticle = (currentParticle - 1 + Particle.ParticleCategories) % Particle.ParticleCategories;
            keyH.OnpressUp = true;
        }
        else if(keyH.down && !keyH.OnpressDown){
            currentParticle = (currentParticle + 1) % Particle.ParticleCategories;
            keyH.OnpressDown = true;
        }

        if(PanelStateMachine.currentState.stateName == "TitleState"){
            if(keyH.start) PanelStateMachine.ChangeState(runningState);
        }
        else{
            if(keyH.pause){
                PanelStateMachine.ChangeState(pauseState);
            }
            else{
                PanelStateMachine.ChangeState(runningState);
            }
        }
        MainUI.update();
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
        if(MainUI != null) MainUI.draw(g2d);
        g2d.dispose(); //release the resources using
    }
}