package particles;

import java.awt.Color;

import main.Gamepanel;

public class Fire extends Particle{
    private int lifetime;
    private int[] dir = {0, 1, 0, -1, 0};
    public Fire(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, -1, "Fire", Color.red);
        this.lifetime = (int)(Math.random() * 10) + 5;
    }

    @Override
    public void update(){
        d_x = Math.random() > 0.5 ? 1 : -1;
        d_y = Math.random() > 0.5 ? 1 : -1;
        lifetime--;
        if(lifetime <= 0){
            Gamepanel.Grid[x][y] = Math.random() > 0.8f ? new Smoke(x, y, 0, 0) : null;
            return;
        }
        for(int i = 0; i < 4; i++){
            if(x+dir[i] >= 0 && x+dir[i] < Gamepanel.ScreenCol && y+dir[i+1] >= 0 && y+dir[i+1] < Gamepanel.ScreenRow && Gamepanel.Grid[x+dir[i]][y+dir[i+1]] != null){
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Wood" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Acid" || Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Gas"){
                    if(Math.random() > 0.75){
                        Gamepanel.Grid[x+dir[i]][y+dir[i+1]] = new Fire(x+dir[i], y+dir[i+1], 0, 0);
                    }
                    else{
                        //if not ignited, stay here without moving and plus lifetime for next update ignite
                        lifetime = Math.min(lifetime + 1, 10);
                        d_x = d_y = 0;
                    }
                }
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Water"){
                    Gamepanel.Grid[x][y] = null;
                    return;
                }
                if(Gamepanel.Grid[x+dir[i]][y+dir[i+1]].particleName == "Dyna"){
                    Gamepanel.Grid[x+dir[i]][y+dir[i+1]].isIgnite = true;
                }
            }
        }
        super.update();
    }
    
}
