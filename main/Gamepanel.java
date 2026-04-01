package main;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import javax.imageio.ImageIO;

import particles.*;
import statemachine.*;

/*This is the main gamepanel which I create it as an Instance, and the main game thread was created here for running the project*/

public class Gamepanel extends JPanel implements Runnable{
    //Default Settings
    public final static int blockSize = 3;
    public final static int scale = 1;
    public final static int displaySize = blockSize * scale;
    public final static int ScreenRow = 300;
    public final static int ScreenCol = 400;
    public final static int BufferedImageScale = 250;
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
        mouseH.gp = this;
        keyH.gp = this;
        this.setPreferredSize(new Dimension(ScreenWidth , ScreenHeight)); // Generate the Window
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //Open the double buffer
        this.addKeyListener(keyH); //Listening the key input
        //Listening to the mouse(pressing and dragging)
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addMouseWheelListener(mouseH);
        setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                if(PanelStateMachine.currentState != titleState) try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable tr = dtde.getTransferable();
                    if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> files = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
                        if (files != null && !files.isEmpty()) {
                            File file = files.get(0);
                            keyH.pause = true;
                            loadAndDisplayImage(file);
                        }
                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                    dtde.rejectDrop();
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
                        if((i-mouseH.mouseX)*(i-mouseH.mouseX)+(j-mouseH.mouseY)*(j-mouseH.mouseY) <= drawFieldR*drawFieldR && i >= 0 && i < ScreenCol && j >= 0 && j < ScreenRow && Grid[i][j] == null){
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
                            else if(currentParticle == Particle.Dyna){
                                Grid[i][j] = new Dyna(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
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

    private void loadAndDisplayImage(File file) {
        if (file == null) return;
        String fileName = file.getName().toLowerCase();
        if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".png") || fileName.endsWith(".gif") ||
                fileName.endsWith(".bmp"))) {
            JOptionPane.showMessageDialog(this, "不支持的文件类型，请选择图片文件。");
            return;
        }
        try {
            BufferedImage originalImage = ImageIO.read(file);
            if (originalImage == null) {
                JOptionPane.showMessageDialog(this, "无法读取图片，文件可能损坏或格式不支持。");
                return;
            }
            for(int i = 0 ; i < ScreenCol ; i++){
                for(int j = 0 ; j < ScreenRow ; j++){
                    int rgb = originalImage.getRGB(i * originalImage.getWidth() / ScreenCol , j * originalImage.getHeight() / ScreenRow);
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8)  & 0xFF;
                    int blue = rgb & 0xFF;
                    int minC = 0;
                    if(red == 0 && green == 0 && blue == 0) continue;
                    for(int p = 0 ; p < Particle.ParticleCategories ; p++){
                        if((alpha - Particle.getColor(p).getAlpha())*(alpha - Particle.getColor(p).getAlpha()) + (red - Particle.getColor(p).getRed())*(red - Particle.getColor(p).getRed()) + (blue - Particle.getColor(p).getBlue())*(blue - Particle.getColor(p).getBlue()) + (green - Particle.getColor(p).getGreen())*(green - Particle.getColor(p).getGreen()) < (alpha - Particle.getColor(minC).getAlpha())*(alpha - Particle.getColor(minC).getAlpha()) + (red - Particle.getColor(minC).getRed())*(red - Particle.getColor(minC).getRed()) + (blue - Particle.getColor(minC).getBlue())*(blue - Particle.getColor(minC).getBlue()) + (green - Particle.getColor(minC).getGreen())*(green - Particle.getColor(minC).getGreen())) minC = p;
                    }
                    if(minC == Particle.Sand){
                        Grid[i][j] = new Sand(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Water){
                        Grid[i][j] = new Water(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Stone){
                        Grid[i][j] = new Stone(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Wood){
                        Grid[i][j] = new Wood(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Acid){
                        Grid[i][j] = new Acid(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Gas){
                        Grid[i][j] = new Gas(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Fire){
                        Grid[i][j] = new Fire(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Smoke){
                        Grid[i][j] = new Smoke(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Steam){
                        Grid[i][j] = new Steam(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                    else if(minC == Particle.Lava){
                        Grid[i][j] = new Lava(i, j, mouseH.mouse_dx, mouseH.mouse_dy);
                    }
                }
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "读取图片失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}