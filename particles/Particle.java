package particles;
import java.awt.Color;
import java.awt.Graphics2D;

import main.Gamepanel;

public class Particle{
    protected int x, y, d_x, d_y;
    protected Color particle_color;
    protected int density;
    public boolean Updated;
    protected Gamepanel gp;
    public Particle(int x, int y, int d_x, int d_y, int density){
        this.x = x;
        this.y = y;
        this.d_x = d_x;
        this.d_y = d_y;
        this.density = density;
        this.gp = Gamepanel.getInstance();
    }

    public void ChangePlace(int x, int y){
        gp.Grid[this.x][this.y] = null;
        gp.Grid[x][y] = this;
        this.d_x += x - this.x;
        this.d_y += y - this.y;
        this.x = x;
        this.y = y;
    }

    public void update(){
        gp.Grid[x][y] = null;
        //Bresenham算法，遍历向量方向上所有物块并判断是否可放置
        boolean flg = false;
        if(d_x == 0 && d_y != 0){
            int dy = d_y / Math.abs(d_y), _y = y + d_y;
            while(y + dy >= 0 && y != _y && y <= gp.ScreenRow - 1){
                if(gp.Grid[x][y+dy] == null){
                    y += dy;
                }
                else if(gp.Grid[x][y+dy].density < this.density){
                    gp.Grid[x][y+dy].ChangePlace(x, y);
                    y += dy;
                    if(particle_color == Color.red) particle_color = Color.GREEN;
                }
                else break;
            }
            if(y != _y) flg = true;
        }
        else if (d_y == 0 && d_x != 0){
            int dx = d_x / Math.abs(d_x), _x = x + d_x;
            while(x + dx >= 0 && gp.Grid[x+dx][y] == null && x != _x && x <= gp.ScreenCol - 1) x += dx;
            if(x != _x) flg = true;
        }
        else{
            int x0 = x, x1 = x + d_x, y0 = y, y1 = y + d_y;
            if(x1 < 0) x1 = 0;
            if(x1 > gp.ScreenCol - 1) x1 = gp.ScreenCol - 1;
            if(y1 < 0) y1 = 0;
            if(y1 > gp.ScreenRow - 1) y1 = gp.ScreenRow - 1;
            int dx = x1 - x0, dy = y1 - y0;
            int stepX = dx > 0 ? 1 : -1;
            int stepY = dy > 0 ? 1 : -1;
            dx = Math.abs(dx);
            dy = Math.abs(dy);
            if(dx > dy){
                int p = 2 * dy - dx;
                int _y = y0;
                for(int _x = x0; _x != x1; _x += stepX){
                    if(p > 0){
                        _y += stepY;
                        p -= 2 * dx;
                    }
                    p += 2 * dy;
                    if(gp.Grid[_x][_y] == null){
                        x = _x;
                        y = _y;
                    }
                    else if(gp.Grid[_x][_y].density < this.density){
                        gp.Grid[_x][_y].ChangePlace(x, y);
                        x = _x;
                        y = _y;
                    }
                    else{
                        flg = true;
                        break;
                    }
                }
            }
            else{
                int p = 2 * dx - dy;
                int _x = x0;
                for(int _y = y0; _y != y1; _y += stepY){
                    if(p > 0){
                        _x += stepX;
                        p -= 2 * dy;
                    }
                    p += 2 * dx;
                    if(gp.Grid[_x][_y] == null){
                        x = _x;
                        y = _y;
                    }
                    else if(gp.Grid[_x][_y].density < this.density){
                        gp.Grid[_x][_y].ChangePlace(x, y);
                        x = _x;
                        y = _y;
                    }
                    else{
                        flg = true;
                        break;
                    }
                }
            }
            if(!flg){
                if(gp.Grid[x1][y1] == null){
                    x = x1;
                    y = y1;
                }
                else if(gp.Grid[x1][y1].density < this.density){
                    gp.Grid[x1][y1].ChangePlace(x, y);
                    x = x1;
                    y = y1;
                }
            }
        }
        if(flg) d_x = d_y = 0;
        gp.Grid[x][y] = this;
        Updated = true;
    }

    public void Draw(Graphics2D g2d){
        Updated = false;
        g2d.setColor(particle_color);
        g2d.fillRect(x * gp.displaySize, y * gp.displaySize, gp.displaySize , gp.displaySize);
    }

}