package UIToolBox;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import main.Gamepanel;

public class UIContainer {
    protected Gamepanel gp;
    protected Font maruMonica;
    protected int x, y, height, width;
    protected boolean visible;

    public UIContainer(int x, int y, int width, int height, boolean visible){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
        this.gp = Gamepanel.getInstance();
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            if (is == null) {
                throw new IOException("Font file not found");
            }
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(IOException | FontFormatException e){
            e.printStackTrace();
        }
    }

    public void SetVisible(boolean visible){
        this.visible = visible;
    }

    protected int getXforCenterText(String text, Graphics2D g2d){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int _x = (2*x + width) / 2 - length / 2;
        return _x;
    }

    protected int getYforCenterText(String text, Graphics2D g2d){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getHeight();
        int _y = (2*y + height) / 2 + length / 3;
        return _y;
    }

    public void ChangePlace(int _x , int _y){
        x = _x;
        y = _y;
    }
}
