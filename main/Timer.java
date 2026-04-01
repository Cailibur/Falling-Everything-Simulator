package main;
public class Timer {
    final int FPS = 60;
    double drawInternal = 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    public boolean TimeUpdate(){
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawInternal;
        lastTime = currentTime;
        if(delta >= 1.0f){
            delta--;
            return true;
        }
        else return false;
    }
}
