package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    private Gamepanel gp;
    private Font arial_40;

    public UI(){
        this.gp = Gamepanel.getInstance();
        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2d){
        g2d.setFont(arial_40);
        g2d.setColor(Color.white);
        g2d.drawString("Hello, world!", 50, 50);
    }
}
