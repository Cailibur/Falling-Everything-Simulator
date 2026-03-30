package particles;

import java.awt.Color;

import main.Gamepanel;

public class Gas extends Particle{

    public Gas(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 0, "Gas", new Color(152,251,152));
    }
    
    @Override
    public void update(){
        if(y != 0){
            if(Gamepanel.Grid[x][y-1] != null){
                d_y = Math.random() > 0.5 ? -2 : -1;
                if(d_x == 0){
                    d_x = Math.random() > 0.5f ? 2 : -2;
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
            if(d_x == 0) d_x = Math.random() > 0.5f ? 2 : -2;
            d_y = 0;
        }
        if(d_x > 1) d_x = 2;
        if(d_x < -1) d_x = -2; 
        super.update();
    }
}
