package br.org.hiperesp.java.flappybird.game;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import br.org.hiperesp.java.flappybird.game.elements.Background;
import br.org.hiperesp.java.flappybird.game.elements.Bird;
import br.org.hiperesp.java.flappybird.game.elements.Foreground;
import br.org.hiperesp.java.flappybird.game.elements.PipeList;
import br.org.hiperesp.java.game.FrameRate;
import br.org.hiperesp.java.game.Game;

public class MyGame extends Game implements KeyListener {
	private static final long serialVersionUID = -8687943380444154700L;
	
	private static final long NANO_TIME = 1000000000l;
	
	public static final int DEBUG_OFF = 0;
	public static final int DEBUG_ON = 1;

	public static final int STARTING = 0;
	public static final int START_SCREEN = 1;
	public static final int PLAYING = 2;
	public static final int DIE = 3;
	public static final int FAST_DIE = 4;
	
	
	public int debug_mode = DEBUG_OFF;
	public int fps = (int)(NANO_TIME/60);
	
	
	public int status = STARTING;
	public int pipeSpeed = 2;
	
	public Bird bird;
	public Background background;
	public Foreground foreground;
	public PipeList pipeList;
	public FrameRate fr;
	public FrameRate forcedFr;
	
	private long lastNanoTime = 0;
	
	public MyGame() throws IOException, FontFormatException {
		//setFPS(140);
		frameDelayMs = 0l;
		frameDelayNs = 1;
		background = new Background(this);
		pipeList = new PipeList(this);
		bird = new Bird(this);
		foreground = new Foreground(this);
		fr = new FrameRate();
		forcedFr = new FrameRate();
		addKeyListener(this);
	}
	
	@Override
	public void update() {
		if(canUpdate()) {
			background.update();
			pipeList.update();
			bird.update();
			foreground.update();
			forcedFr.calculate();
		}
		fr.calculate();
	}
	@Override
	public void render() {
		background.render();
		pipeList.render();
		bird.render();
		foreground.render();
		graphics.drawString("FPS: "+fr.fps, 10, 10);
		graphics.drawString("FrameTime: "+fr.frameTimeMs+"ms "+fr.frameTimeNs+"ns", 10, 20);
		graphics.drawString("FPS (Forced): "+forcedFr.fps, 10, 40);
		graphics.drawString("FrameTime (Forced): "+forcedFr.frameTimeMs+"ms "+forcedFr.frameTimeNs+"ns", 10, 50);
	}
	
	public boolean canUpdate() {
		long currentNanoTime = System.nanoTime();
		if(currentNanoTime-lastNanoTime>fps) {
			lastNanoTime = currentNanoTime;
			return true;
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(status==PLAYING) bird.jump();
		if(status==START_SCREEN) status = PLAYING;
		if(status==DIE) status = FAST_DIE;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
