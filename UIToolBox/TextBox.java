package UIToolBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TextBox extends UIContainer {
    public String text;

    public TextBox(int x, int y, int width, int height, boolean visible, String text) {
        super(x, y, width, height, visible);
        this.text = text;
    }

    public void Draw(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.setFont(maruMonica);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 60f));
        g2d.drawString(text, getXforCenterText(text, g2d), getYforCenterText(text, g2d));
    }
    
    public void ChangeText(String newText){
        text = newText;
    }
}
