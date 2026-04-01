package particles;

import java.awt.Color;

public class Wood extends Particle{
    static final Color[] Wood_Colors = {new Color(188, 150, 108), new Color(155, 115, 80), new Color(115, 80, 55)};

    public Wood(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Wood", Wood_Colors[(int)(Math.random()*3.0f)]);
    }
    
    @Override
    public void update(){
        d_x = d_y = 0;
        super.update();
    }
}
