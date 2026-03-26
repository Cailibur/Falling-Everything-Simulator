package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean up, down, left, right, pause = false, start = false;
    public boolean OnpressUp = false, OnpressDown = false, OnpressLeft = false, OnpressRight = false;

    private static KeyHandler keyHInstance;

    public KeyHandler(){
        if(keyHInstance == null) keyHInstance = this;
    }

    public static KeyHandler getInstance(){
        return keyHInstance;
    } 

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(code);
        if(code == KeyEvent.VK_W){
            up = true;
        }
        if(code == KeyEvent.VK_S){
            down = true;
        }
        if(code == KeyEvent.VK_A){
            left = true;
        }
        if(code == KeyEvent.VK_D){
            right = true;
        }
        if(code == KeyEvent.VK_SPACE){
            pause = !pause;
            //System.out.println("YES");
        }
        if(code == KeyEvent.VK_ENTER){
            start = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            up = false;
            OnpressUp = false;
        }
        if(code == KeyEvent.VK_S){
            down = false;
            OnpressDown = false;
        }
        if(code == KeyEvent.VK_A){
            left = false;
            OnpressLeft = false;
        }
        if(code == KeyEvent.VK_D){
            right = false;
            OnpressRight = false;
        }
    }
    
}