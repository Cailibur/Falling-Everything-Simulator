package UIToolBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.MouseHandler;

public class Button extends UIContainer{
    private MouseHandler mouseH;
    public boolean activated, mouseFloating;
    private String buttonName;

    public Button(int x, int y, int width, int height, boolean visible, String buttonName){
        super(x, y, width, height, visible);
        this.buttonName = buttonName;
        this.mouseFloating = false;
        mouseH = MouseHandler.getInstance();
    }

    public void Draw(Graphics2D g2d){
        if(visible == true){
            g2d.setColor(Color.darkGray);
            g2d.fillRect(x-5, y-5, width+10, height+10);
            if(activated) g2d.setColor(Color.darkGray);
            else if(mouseFloating == false) g2d.setColor(Color.white);
            else g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(x, y, width, height);
            g2d.setFont(maruMonica);
            g2d.setColor(Color.black);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 43f));
            g2d.drawString(buttonName, getXforCenterText(buttonName, g2d), getYforCenterText(buttonName, g2d));
        }
    }

    public void update(){
        if(this.mouseFloating && mouseH.mouse_dragged && !activated){
            activated = true;

            Onclick();
        }
        if(!mouseH.mouse_dragged && activated){
            activated = false;
        }
        if(visible && mouseH.originMouseX >= x && mouseH.originMouseX <= x + width && mouseH.originMouseY >= y && mouseH.originMouseY <= y + height && !mouseH.mouse_dragged){
            this.mouseFloating = true;
        }
        else{
            this.mouseFloating = false;
        }
    }

    public void Onclick(){

    }

}
