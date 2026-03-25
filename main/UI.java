package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private Gamepanel gp;
    private Font maruMonica;

    public UI(){
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

    public void draw(Graphics2D g2d){
        if(gp.PanelStateMachine.currentState.stateName == "PauseState"){
            drawPauseScreen(g2d);
        }
        if(gp.PanelStateMachine.currentState.stateName == "TitleState"){
            drawTitleScreen(g2d);
        }
    }

    public void drawPauseScreen(Graphics2D g2d){
        g2d.setFont(maruMonica);
        g2d.setColor(Color.white);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 32f));
        g2d.drawString("Paused", 30, 50);
    }

    public void drawTitleScreen(Graphics2D g2d){
        g2d.setFont(maruMonica);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 150f));
        String maintext = "Falling Everything";
        String subtext = "Simulator";
        int mainX = getXforCenterText(maintext, g2d);
        int mainY = 350, subY = 550;
        g2d.setColor(Color.gray);
        g2d.drawString(maintext, mainX+5, mainY+5);
        g2d.setColor(Color.white);
        g2d.drawString(maintext, mainX, mainY);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 100f));
        int subX = getXforCenterText(subtext, g2d);
        g2d.drawString(subtext, subX, subY);
        //System.out.println(X);
    }

    public int getXforCenterText(String text, Graphics2D g2d){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gp.ScreenWidth / 2 - length / 2;
        return x;
    }
}
