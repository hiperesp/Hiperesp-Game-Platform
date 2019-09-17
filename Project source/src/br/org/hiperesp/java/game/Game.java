package br.org.hiperesp.java.game;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;

public class Game extends Canvas implements GameElement {
    private static final long serialVersionUID = 3534714927685620012L;

    public Graphics2D graphics;
    public Image canvas = null;
    public long frameDelayMs = 0;
    public int frameDelayNs = 0;

    public void start() throws InterruptedException {
        loop();
    }
    public void callUpdate() {
        update();
    }
    public void callRender() {
    	canvas = createImage(getWidth(), getHeight());
        graphics = (Graphics2D)canvas.getGraphics();
        render();
        //paint(graphics);
        getGraphics().drawImage(canvas, 0, 0, this);
    }
    public void loop() throws InterruptedException {
    	callUpdate();
        callRender();
        if(!(frameDelayMs==0&&frameDelayNs==0)) Thread.sleep(frameDelayMs, frameDelayNs);
        loop();
    }
    public void setFPS(long fps) {
        frameDelayMs = fps<0?0:(long)(1000d/fps);
        frameDelayNs = 0;
    }
}
