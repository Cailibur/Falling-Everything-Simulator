package particles;

import java.awt.Color;

import main.Gamepanel;

public class Lava extends Particle{
    private int[] dir = {0, 1, 0, -1, 0};

    public Lava(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 4, "Lava", new Color(207, 16, 32));
    }
    
    @Override
    public void update(){
        if(y != Gamepanel.ScreenRow - 1){
            if(Gamepanel.Grid[x][y+1] != null){
                d_y = Math.random() > 0.5f ? -1 : 1;
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
            d_y = Math.random() > 0.5f ? -1 : 0;
        }
        for(int i = 0; i < 4; i++){
            if(x+dir[i] >= 0 && x+dir[i] < Gamepanel.ScreenCol && y+dir[i+1] >= 0 && y+dir[i+1] < Gamepanel.ScreenRow && Gamepanel.Grid[x+dir[i]][y+dir[i+1]] != null){
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Wood" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Acid" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Gas"){
                    if(Math.random() > 0.75){
                        Gamepanel.Grid[x+dir[i]][y+dir[i+1]] = new Fire(x+dir[i], y+dir[i+1], 0, 0);
                    }
                }
                else if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Water"){
                    Gamepanel.Grid[x+dir[i]][y+dir[i+1]] = new Steam(x+dir[i], y+dir[i+1], Math.random() > 0.5f ? -1 : 1, -1);
                    Gamepanel.Grid[x][y] = new Stone(x, y, 0, 0);
                    return;
                }
            }
        }
        super.update();
    }
}
