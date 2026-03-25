package particles;

import java.awt.Color;

public class Water extends Particle{
    
    public Water(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 1);
        this.particle_color = Color.blue;
    }

    @Override
    public void update(){
        if(y != gp.ScreenRow - 1){
            if(gp.Grid[x][y+1] != null){
                d_y = 0;
                if(d_x == 0) d_x = Math.random() > 0.5f ? 1 : -1;
            }
            else d_y++;
        }
        else{
            if(d_x == 0) d_x = Math.random() > 0.5f ? 1 : -1;
            d_y = 0;
        }
        super.update();
    }
    
}
