package particles;

import java.awt.Color;

import main.Gamepanel;

public class Sand extends Particle{
    
    public Sand(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Sand", Color.yellow);
    }

    @Override
    public void update(){
        if(y != Gamepanel.ScreenRow - 1){
            if(Gamepanel.Grid[x][y+1] != null){
                if(Gamepanel.Grid[x][y+1].density < this.density){
                    d_y = Math.random() > 0.5f ? 1 : 2;
                }
                else{
                    d_x = Math.random() > 0.5f ? 1 : -1;
                    d_y = 1;
                    if(x+d_x >= 0 && x+d_x <= Gamepanel.ScreenCol-1){
                        if(Gamepanel.Grid[x+d_x][y+d_y] != null){
                            if(Gamepanel.Grid[x+d_x][y+d_y].density >= this.density){
                                d_x = -d_x;
                                if(x+d_x >= 0 && x+d_x <= Gamepanel.ScreenCol-1){
                                    if(Gamepanel.Grid[x+d_x][y+d_y] != null){
                                        if(x+d_x >= 0 && x+d_x <= Gamepanel.ScreenCol-1){
                                            if(Gamepanel.Grid[x+d_x][y+d_y].density >= this.density){
                                                d_x = d_y = 0;
                                                if(particle_color == Color.red) particle_color = Color.GREEN;
                                            } 
                                        } 
                                        else d_x = d_y = 0;
                                    }
                                    else d_x = d_y = 0;
                                }
                                else d_x = d_y = 0;
                            }
                        } 
                    }
                    else d_x = d_y = 0;
                }
            }
            else d_y++;
        }
        else d_x = d_y = 0;
        super.update();
    }
    
}
