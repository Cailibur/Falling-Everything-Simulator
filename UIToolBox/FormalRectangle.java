package UIToolBox;

import java.awt.Color;
import java.awt.Graphics2D;

public class FormalRectangle extends UIContainer{
    Color rectColor;

    public FormalRectangle(int x, int y, int width, int height, boolean visible, Color rectColor) {
        super(x, y, width, height, visible);
        this.rectColor = rectColor;
    }

    public void Draw(Graphics2D g2d){
        g2d.setColor(rectColor);
        g2d.fillRect(x, y, width, height);
    }

    public void ChangeColor(Color newColor){
        rectColor = newColor;
    }
    
}
