package particles;

import java.awt.Color;

import main.Gamepanel;

public class Acid extends Particle{

    public Acid(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 1, "Acid");
        particle_color = Color.green;
    }
    
    @Override
    public void update(){
        if(y != Gamepanel.ScreenRow - 1){
            if(Gamepanel.Grid[x][y+1] != null){
                d_y = 1;
                //BUG here
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
            else d_y++;
        }
        else{
            if(d_x == 0) d_x = Math.random() > 0.5f ? 1 : -1;
            d_y = 0;
        }
        int[] dir = {0, 1, 0, -1, 0};
        for(int i = 0; i < 4; i++){
            if(x+dir[i] >= 0 && x+dir[i] < Gamepanel.ScreenCol && y+dir[i+1] >= 0 && y+dir[i+1] < Gamepanel.ScreenCol && Gamepanel.Grid[x+dir[i]][y+dir[i+1]] != null){
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Stone" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Wood" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Sand"){
                    Gamepanel.Grid[x+dir[i]][y+dir[i+1]] = null;
                    Gamepanel.Grid[x][y] = null;
                    return;
                }
            }
        }
        for(int i = 0; i < 4; i++){
            if(x+dir[i] >= 0 && x+dir[i] < Gamepanel.ScreenCol && y+dir[i+1] >= 0 && y+dir[i+1] < Gamepanel.ScreenCol && Gamepanel.Grid[x+dir[i]][y+dir[i+1]] != null){
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Water"){
                    if(Math.random() >= 0.7f){
                        Gamepanel.Grid[x+dir[i]][y+dir[i+1]] = new Acid(x+dir[i], y+dir[i+1], Gamepanel.Grid[x+dir[i]][y+dir[i+1]].d_x, Gamepanel.Grid[x+dir[i]][y+dir[i+1]].d_y);
                        Gamepanel.Grid[x+dir[i]][y+dir[i+1]].Updated = false;
                    }
                }
            }
        }
        super.update();
    }
}
