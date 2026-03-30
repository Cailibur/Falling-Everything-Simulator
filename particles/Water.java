package particles;

import java.awt.Color;

import main.Gamepanel;

public class Water extends Particle{
    
    public Water(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 1, "Water", Color.blue);
    }

    @Override
    public void update(){
        if(y != Gamepanel.ScreenRow - 1){
            if(Gamepanel.Grid[x][y+1] != null){
                d_y = 1;
                if(d_x == 0){
                    d_x = Math.random() > 0.5f ? 3 : -3;
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
            else d_y++;
        }
        else{
            if(d_x == 0) d_x = Math.random() > 0.5f ? 3 : -3;
            d_y = 0;
        }
        super.update();
    }
    
}
