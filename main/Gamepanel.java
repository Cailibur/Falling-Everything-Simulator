package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import particles.*;
import statemachine.*;

/*This is the main gamepanel which I create it as an Instance, and the main game thread was created here for running the project*/

public class Gamepanel extends JPanel implements Runnable, MouseWheelListener{
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
    public int drawFieldR = 5;

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
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation() == 1){
                    drawFieldR = Math.max(drawFieldR - 1 , 1);
                    MainUI.mouseDrawField.ChangeR(drawFieldR);
                }
                if(e.getWheelRotation() == -1){
                    drawFieldR = Math.min(drawFieldR + 1 , 20);
                    MainUI.mouseDrawField.ChangeR(drawFieldR);
                }
            }
            
        });
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
                for(int i = mouseH.mouseX - drawFieldR ; i <= mouseH.mouseX + drawFieldR ; i++){
                    for(int j = mouseH.mouseY - drawFieldR ; j <= mouseH.mouseY + drawFieldR ; j++){
                        if((i-mouseH.mouseX)*(i-mouseH.mouseX)+(j-mouseH.mouseY)*(j-mouseH.mouseY) <= drawFieldR*drawFieldR && i >= 0 && i < ScreenCol && j >= 0 && j < ScreenRow){
                            if(currentParticle == Particle.Sand){
                                Grid[i][j] = new Sand(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Water){
                                Grid[i][j] = new Water(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Stone){
                                Grid[i][j] = new Stone(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Wood){
                                Grid[i][j] = new Wood(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Acid){
                                Grid[i][j] = new Acid(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Gas){
                                Grid[i][j] = new Gas(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Fire){
                                Grid[i][j] = new Fire(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Smoke){
                                Grid[i][j] = new Smoke(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Steam){
                                Grid[i][j] = new Steam(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                            else if(currentParticle == Particle.Lava){
                                Grid[i][j] = new Lava(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                            }
                        }
                    }
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseWheelMoved'");
    }
}