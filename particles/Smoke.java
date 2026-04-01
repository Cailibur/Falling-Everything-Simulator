package particles;

import java.awt.Color;

import main.Gamepanel;

public class Smoke extends Particle {
    private int lifetime;

    public Smoke(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, -1, "Smoke", Color.darkGray);
        this.lifetime = (int)(Math.random() * 10) + 5;
    }
    
    @Override
    public void update(){
        lifetime--;
        if(lifetime < 0){
            Gamepanel.Grid[x][y] = null;
            return;
        }
        if(y != 0){
            if(Gamepanel.Grid[x][y-1] != null){
                d_y = Math.random() > 0.5 ? -2 : -1;
                if(d_x == 0){
                    d_x = Math.random() > 0.5f ? 1 : -1;
                    if(x+d_x >= Gamepanel.ScreenCol || x + d_x < 0){
                        d_x = -d_x;
                        if(x+d_x >= Gamepanel.ScreenCol || x + d_x < 0) d_x = 0;
                    }
                }
                else if(x+d_x >= Gamepanel.ScreenCol || x + d_x < 0){
                    d_x = -d_x;
                    if(x+d_x >= Gamepanel.ScreenCol || x + d_x < 0) d_x = 0;
                }
            }
            else d_y = Math.random() > 0.5 ? -2 : -1;
        }
        else{
            if(d_x == 0) d_x = Math.random() > 0.5f ? 1 : -1;
            d_y = 0;
        }
        if(d_x > 1) d_x = 1;
        if(d_x < -1) d_x = -1; 
        if(lifetime > 0)super.update();
    }
}
