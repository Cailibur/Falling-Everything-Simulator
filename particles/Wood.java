package particles;

import java.awt.Color;

public class Wood extends Particle{

    public Wood(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Wood", new Color(139,69,19));
    }
    
    @Override
    public void update(){
        d_x = d_y = 0;
        super.update();
    }
}
