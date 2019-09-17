package br.org.hiperesp.java.game;

public class FrameRate {
    private long lastRender = 0;
    public double fps = 0;
    public long frameTimeNs = 0;
    public double frameTimeMs = 0;

    public void calculate(){
        long currentRender = System.nanoTime();
        frameTimeNs = currentRender - lastRender;
        frameTimeMs = (double)frameTimeNs / 1000000;
        fps = frameTimeMs==0?-1:1000/frameTimeMs;
        lastRender = currentRender;
    }
}