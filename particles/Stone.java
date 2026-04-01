package particles;

import java.awt.Color;

public class Stone extends Particle{
    static final Color[] Stone_Colors = {new Color(192, 188, 176), new Color(158, 152, 138), new Color(122, 114,102)};

    public Stone(int x, int y, int d_x, int d_y) {
        super(x, y, d_x, d_y, 5, "Stone", Stone_Colors[(int)(Math.random()*3.0f)]);
    }

    @Override
    public void update(){
        d_x = d_y = 0;
        super.update(); 
    }
}
