package br.org.hiperesp.java.game;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Game extends Canvas implements GameElement {
    private static final long serialVersionUID = 3534714927685620012L;
    
    private boolean canLoop = false;
    private long loopDelayMillis = 0;
    
    private Image canvas;
    public Graphics2D graphics;
    
    private void callSetup() {
    	canvas = createImage(getWidth(), getHeight());
    	setup();
    }
    private void callUpdate() {
    	update();
    }
    private void callRender() {
    	graphics = (Graphics2D)canvas.getGraphics();
    	render();
    	getGraphics().drawImage(canvas, 0, 0, null);
    }
    public void start() throws InterruptedException {
    	canLoop = true;
    	callSetup();
    	loop();
    }
    public void stop() {
    	canLoop = false;
    }
    private void loop() throws InterruptedException {
    	while(canLoop) {
			callUpdate();
			callRender();
			Thread.sleep(loopDelayMillis);
    	}
    }
    public void setLoopDelay(long millis) {
        if (millis < 0) throw new IllegalArgumentException("timeout value is negative");
    	loopDelayMillis = millis;
    }
    public long getLoopDelay() {
    	return loopDelayMillis;
    }
}
