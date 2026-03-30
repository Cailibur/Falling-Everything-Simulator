package UIToolBox;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Gamepanel;

public class DrawField extends UIContainer{
    private int r = 5;
    public DrawField(int x, int y, int width, int height, boolean visible) {
        super(x, y, width, height, visible);
    }

    void plot_circle_points(int xc, int yc, int x, int y, Graphics2D g2d)//根据对称性画出另外7部分的点
    {
        g2d.fillRect((xc+x)*Gamepanel.displaySize, (yc+y)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc-x)*Gamepanel.displaySize, (yc+y)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc+x)*Gamepanel.displaySize, (yc-y)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc-x)*Gamepanel.displaySize, (yc-y)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc+y)*Gamepanel.displaySize, (yc+x)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc-y)*Gamepanel.displaySize, (yc+x)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc+y)*Gamepanel.displaySize, (yc-x)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
        g2d.fillRect((xc-y)*Gamepanel.displaySize, (yc-x)*Gamepanel.displaySize, Gamepanel.displaySize, Gamepanel.displaySize);
    }

    
    public void Draw(Graphics2D g2d){
        int x1, y1, p;
        x1 = 0;
        y1 = r;
        p = 3-2*r;
        g2d.setColor(Color.white);
        while(x1 < y1)
        {
            plot_circle_points(x, y, x1, y1, g2d);
            if(p < 0)
                p = p+4*x1+6;
            else
            {
                p = p+4*(x1-y1)+10;
                y1 -= 1;
            }
            x1 += 1;
        }
        if(x1 == y1) plot_circle_points(x, y, x1, y1, g2d);
    }

    public void ChangeR(int _r){
        r = _r;
    }
    
}
