package particles;

import java.awt.Color;

import main.Gamepanel;

public class Dyna extends Particle{
    private int lifetime = (int)(Math.random() * 10) + 10;
    private int[][] dir = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
    static final Color[] Dyna_Colors = {new Color(195, 95, 75), new Color(165, 65, 55), new Color(120, 45,40)};

    public Dyna(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Dyna", Dyna_Colors[(int)(Math.random()*3.0f)]);
        
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
        if(isIgnite){
            lifetime--;
            for(int i = 0 ; i < 8 ; i++){
                if(x + dir[i][0] >= 0 && x + dir[i][0] < Gamepanel.ScreenCol && y + dir[i][1] >= 0 && y + dir[i][1] <= Gamepanel.ScreenRow && Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]] != null){
                    if(Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]].particleName == "Water"){
                        isIgnite = false;
                        break;
                    }
                    Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]].d_x += dir[i][0] * 3;
                    Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]].d_y += dir[i][1] * 3;
                }
            }
        }
        if(lifetime <= 0){
            Gamepanel.Grid[x][y] = new Fire(x, y, d_x, d_y);
            for(int i = 0 ; i < 8 ; i++){
                if(x + dir[i][0] >= 0 && x + dir[i][0] < Gamepanel.ScreenCol && y + dir[i][1] >= 0 && y + dir[i][1] <= Gamepanel.ScreenRow && Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]] != null){
                    if(Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]].particleName == "Dyna") Gamepanel.Grid[x+dir[i][0]][y+dir[i][1]].isIgnite = true;
                }
            }
            return;
        }
        super.update();
    }
}
