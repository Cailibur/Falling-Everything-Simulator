package particles;

import java.awt.Color;

public class Stone extends Particle{

    public Stone(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Stone");
        this.particle_color = Color.gray;
    }

    @Override
    public void update(){
        d_x = d_y = 0;
        super.update(); 
    }
}
