package particles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import main.Gamepanel;

public class Particle{
    public static int ParticleCategories = 10;
    public static int Sand = 0;
    public static int Water = 1;
    public static int Stone = 2;
    public static int Wood = 3;
    public static int Acid = 4;
    public static int Gas = 5;
    public static int Fire = 6;
    public static int Smoke = 7;
    public static int Steam = 8;
    public static int Lava = 9;

    private static final Map<Integer, String> toString = new HashMap<>();
    static {
        toString.put(0, "Sand");
        toString.put(1, "Water");
        toString.put(2, "Stone");
        toString.put(3, "Wood");
        toString.put(4, "Acid");
        toString.put(5, "Gas");
        toString.put(6, "Fire");
        toString.put(7, "Smoke");
        toString.put(8, "Steam");
        toString.put(9, "Lava");

    }

    private static final Map<Integer, Color> toColor = new HashMap<>();
    static {
        toColor.put(0, Color.yellow);
        toColor.put(1, new Color(176, 224, 230));
        toColor.put(2, Color.gray);
        toColor.put(3, new Color(139,69,19));
        toColor.put(4, Color.green);
        toColor.put(5, new Color(152,251,152));
        toColor.put(6, Color.red);
        toColor.put(7, Color.darkGray);
        toColor.put(8, Color.LIGHT_GRAY);
        toColor.put(9, new Color(207, 16, 32));

    }

    public static String getString(int code) {
        String value = toString.get(code);
        if (value == null) {
            return "未知"; // Exception
        }
        return value;
    }

    public static Color getColor(int code){
        Color value = toColor.get(code);
        if(value == null){
            return Color.white;
        }
        return value; //Exception
    }

    public int x, y, d_x, d_y;
    protected Color particle_color;
    protected int density;
    public boolean Updated;
    protected Gamepanel gp;
    public String particleName;
    public Particle(int x, int y, int d_x, int d_y, int density, String particleName, Color particle_color){
        this.x = x;
        this.y = y;
        this.d_x = d_x;
        this.d_y = d_y;
        this.density = density;
        this.particleName = particleName;
        this.particle_color = particle_color;
        this.gp = Gamepanel.getInstance();
    }

    public void ChangePlace(int x, int y){
        Gamepanel.Grid[this.x][this.y] = null;
        Gamepanel.Grid[x][y] = this;
        if(x - this.x != 0) this.d_x += (x - this.x) / Math.abs(x - this.x);
        if(y - this.y != 0) this.d_y += y - this.y / Math.abs(y - this.y);
        this.x = x;
        this.y = y;
    }

    public void update(){
        Gamepanel.Grid[x][y] = null;
        //Bresenham算法，遍历向量方向上所有物块并判断是否可放置
        boolean flg = false;
        if(d_x == 0 && d_y != 0){
            int dy = d_y / Math.abs(d_y), _y = y + d_y;
            while(y + dy >= 0 && y != _y && y <= Gamepanel.ScreenRow - 1){
                if(Gamepanel.Grid[x][y+dy] == null){
                    y += dy;
                }
                else if(Gamepanel.Grid[x][y+dy].density < this.density){
                    Gamepanel.Grid[x][y+dy].ChangePlace(x, y);
                    y += dy;
                }
                else if(Gamepanel.Grid[x][y+dy].density == this.density && Math.random() > 0.5f){
                    Gamepanel.Grid[x][y+dy].ChangePlace(x, y);
                    y += dy;
                }
                else break;
            }
            if(y != _y) flg = true;
        }
        else if (d_y == 0 && d_x != 0){
            int dx = d_x / Math.abs(d_x), _x = x + d_x;
            while(x + dx >= 0 && x != _x && x <= Gamepanel.ScreenCol - 1){
                if(Gamepanel.Grid[x+dx][y] == null){
                    x += dx;
                }
                else if(Gamepanel.Grid[x+dx][y].density < this.density){
                    Gamepanel.Grid[x+dx][y].ChangePlace(x, y);
                    x += dx;
                }
                else if(Gamepanel.Grid[x+dx][y].density == this.density && Math.random() > 0.5f){
                    Gamepanel.Grid[x+dx][y].ChangePlace(x, y);
                    x += dx;
                }
                else break;
            }
            if(x != _x) flg = true;
        }
        else{
            int x0 = x, x1 = x + d_x, y0 = y, y1 = y + d_y;
            if(x1 < 0) x1 = 0;
            if(x1 > Gamepanel.ScreenCol - 1) x1 = Gamepanel.ScreenCol - 1;
            if(y1 < 0) y1 = 0;
            if(y1 > Gamepanel.ScreenRow - 1) y1 = Gamepanel.ScreenRow - 1;
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
                    if(Gamepanel.Grid[_x][_y] == null){
                        x = _x;
                        y = _y;
                    }
                    else if(Gamepanel.Grid[_x][_y].density < this.density){
                        Gamepanel.Grid[_x][_y].ChangePlace(x, y);
                        x = _x;
                        y = _y;
                    }
                    else if(Gamepanel.Grid[_x][_y].density == this.density && Math.random() > 0.5f){
                        Gamepanel.Grid[_x][_y].ChangePlace(x, y);
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
                    if(Gamepanel.Grid[_x][_y] == null){
                        x = _x;
                        y = _y;
                    }
                    else if(Gamepanel.Grid[_x][_y].density < this.density){
                        Gamepanel.Grid[_x][_y].ChangePlace(x, y);
                        x = _x;
                        y = _y;
                    }
                    else if(Gamepanel.Grid[_x][_y].density == this.density && Math.random() > 0.5f){
                        Gamepanel.Grid[_x][_y].ChangePlace(x, y);
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
                if(Gamepanel.Grid[x1][y1] == null){
                    x = x1;
                    y = y1;
                }
                else if(Gamepanel.Grid[x1][y1].density < this.density){
                    Gamepanel.Grid[x1][y1].ChangePlace(x, y);
                    x = x1;
                    y = y1;
                }
            }
        }
        if(flg) d_x = d_y = 0;
        Gamepanel.Grid[x][y] = this;
        Updated = true;
    }

    public void Draw(Graphics2D g2d){
        Updated = false;
        g2d.setColor(particle_color);
        g2d.fillRect(x * Gamepanel.displaySize, y * Gamepanel.displaySize, Gamepanel.displaySize , Gamepanel.displaySize);
    }

}