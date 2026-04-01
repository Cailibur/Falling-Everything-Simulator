package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.event.MouseInputAdapter;

public class MouseHandler extends MouseInputAdapter{
    public Gamepanel gp;
    public int mouse_dx = 0;
    public int mouse_dy = 0;
    public int Acceleration = 1;
    public int mouseX = 0;
    public int mouseY = 0;
    public int originMouseX = 0;
    public int originMouseY = 0;
    public boolean mouse_dragged = false, mouse_Erase = false;

    private static MouseHandler mouseHInstance;

    public MouseHandler(){
        if(mouseHInstance == null) mouseHInstance = this;
    }

    public static MouseHandler getInstance(){
        return mouseHInstance;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        super.mouseMoved(e);
        mouse_dx = Acceleration * (e.getX() / Gamepanel.displaySize - mouseX);
        mouse_dy = Acceleration * (e.getY() / Gamepanel.displaySize - mouseY);
        originMouseX = e.getX();
        originMouseY = e.getY();
        mouseX = originMouseX / Gamepanel.displaySize;
        mouseY = originMouseY / Gamepanel.displaySize;
    }

    @Override
    public void mousePressed(MouseEvent e){
        super.mousePressed(e);
        mouse_dx = Acceleration * (e.getX() / Gamepanel.displaySize - mouseX);
        mouse_dy = Acceleration * (e.getY() / Gamepanel.displaySize - mouseY);
        originMouseX = e.getX();
        originMouseY = e.getY();
        mouseX = originMouseX / Gamepanel.displaySize;
        mouseY = originMouseY / Gamepanel.displaySize;
        if(e.getButton() == MouseEvent.BUTTON1){
            mouse_dragged = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            mouse_Erase = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        mouse_dx = Acceleration * (e.getX() / Gamepanel.displaySize - mouseX);
        mouse_dy = Acceleration * (e.getY() / Gamepanel.displaySize - mouseY);
        originMouseX = e.getX();
        originMouseY = e.getY();
        mouseX = originMouseX / Gamepanel.displaySize;
        mouseY = originMouseY / Gamepanel.displaySize;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        mouse_dx = Acceleration * (e.getX() / Gamepanel.displaySize - mouseX);
        mouse_dy = Acceleration * (e.getY() / Gamepanel.displaySize - mouseY);
        originMouseX = e.getX();
        originMouseY = e.getY();
        mouseX = originMouseX / Gamepanel.displaySize;
        mouseY = originMouseY / Gamepanel.displaySize;
        if(e.getButton() == MouseEvent.BUTTON1){
            mouse_dragged = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3){
            mouse_Erase = false;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() == 1){
            gp.drawFieldR = Math.max(gp.drawFieldR - 1 , 1);
            gp.MainUI.mouseDrawField.ChangeR(gp.drawFieldR);
        }
        if(e.getWheelRotation() == -1){
            gp.drawFieldR = Math.min(gp.drawFieldR + 1 , 20);
            gp.MainUI.mouseDrawField.ChangeR(gp.drawFieldR);
        }
    }
    
}
