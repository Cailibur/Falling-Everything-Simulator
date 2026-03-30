package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import UIToolBox.*;
import particles.Particle;

public class UI {
    private Gamepanel gp;
    private Font maruMonica;
    private TextBox currentParticleTextBox;
    private FormalRectangle currentParticleRect;
    public DrawField mouseDrawField;
    private MouseHandler mouseH = MouseHandler.getInstance();

    public UI(){
        this.gp = Gamepanel.getInstance();
        this.currentParticleTextBox = new TextBox(1000, 30, 100, 50, true, "Sand");
        this.currentParticleRect = new FormalRectangle(1140, 45, 30, 30, false, Color.yellow);
        this.mouseDrawField = new DrawField(0, 0, 0, 0, false);
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

    public void update(){
        
    }

    public void draw(Graphics2D g2d){
        if(gp.PanelStateMachine.currentState.stateName != "TitleState"){
            currentParticleTextBox.ChangeText(Particle.getString(gp.currentParticle));
            currentParticleRect.ChangeColor(Particle.getColor(gp.currentParticle));
            mouseDrawField.ChangePlace(mouseH.mouseX, mouseH.mouseY);
            currentParticleTextBox.Draw(g2d);
            currentParticleRect.Draw(g2d);
            mouseDrawField.Draw(g2d);
        }
        if(gp.PanelStateMachine.currentState.stateName == "PauseState"){
            drawPauseScreen(g2d);
        }
        if(gp.PanelStateMachine.currentState.stateName == "TitleState"){
            drawTitleScreen(g2d);
        }
    }

    public void drawPauseScreen(Graphics2D g2d){
        g2d.setFont(maruMonica);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 32f));
        g2d.setColor(Color.gray);
        g2d.drawString("Paused", 32, 52);
        g2d.setColor(Color.white);
        g2d.drawString("Paused", 30, 50);
    }

    public void drawTitleScreen(Graphics2D g2d){
        g2d.setFont(maruMonica);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 150f));
        String maintext = "Falling Everything";
        String subtext = "Simulator";
        String quest = "Press Enter to Start";
        int mainX = getXforCenterText(maintext, g2d);
        int mainY = 350, subY = 430;
        g2d.setColor(Color.gray);
        g2d.drawString(maintext, mainX+5, mainY+5);
        g2d.setColor(Color.white);
        g2d.drawString(maintext, mainX, mainY);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,50f));
        int subX = getXforCenterText(subtext, g2d);
        g2d.drawString(subtext, subX, subY);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,80f));
        int questX = getXforCenterText(quest, g2d);
        int questY = 600;
        g2d.drawString(quest, questX, questY);
        //System.out.println(X);
    }

    private int getXforCenterText(String text, Graphics2D g2d){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = Gamepanel.ScreenWidth / 2 - length / 2;
        return x;
    }
}
