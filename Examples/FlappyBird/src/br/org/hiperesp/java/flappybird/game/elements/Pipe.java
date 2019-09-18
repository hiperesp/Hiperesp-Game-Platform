package br.org.hiperesp.java.flappybird.game.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.org.hiperesp.java.flappybird.game.MyGame;
import br.org.hiperesp.java.game.GameElement;

public class Pipe implements GameElement {
	private MyGame game;

	BufferedImage imageT, imageD;
	
	int x;
	int y;

	private final int minY = 120;
	private int maxY;
	
	public double gap = 150;
	public double speed_acc;
	
	public boolean dynamic = false;
	public int dynamic_direction = 1;
	
	public Pipe(MyGame game) throws IOException {
		this.game = game;
		load();
		setup();
	}
	public void load() throws IOException {
		imageT = ImageIO.read(new File("assets/pipe-north.png"));
		imageD = ImageIO.read(new File("assets/pipe-south.png"));
	}
	public void setup() {
		speed_acc = game.pipeSpeed;
		maxY = game.getHeight()-game.foreground.image.getHeight()-minY;
		x = game.getWidth()-maxY;
		y = (int)(minY+(maxY-minY)*Math.random());
		if(y%2==0&&y%4!=0) {
			dynamic = true;
		}
	}
	public void update() {
		if(game.status==MyGame.FAST_DIE) {
			speed_acc+=game.pipeSpeed/2;
		}
		if(game.status!=MyGame.START_SCREEN) {
			x-=speed_acc;
		}
		if(dynamic) {
			if(dynamic_direction>0) y++; else y--;
			if(y>maxY||y<minY) dynamic_direction*=-1;
		}
	}
	public void render() {
		Graphics2D graphics = (Graphics2D) game.graphics;
		int xt = x;
		int yt = (int)(y-imageT.getHeight()-gap/2);
		int xd = x;
		int yd = (int)(y+gap/2);
		if(game.debug_mode==MyGame.DEBUG_ON) {
			graphics.drawRect(xt, yt, imageT.getWidth(), imageT.getHeight());
			graphics.drawRect(xd, yd, imageD.getWidth(), imageD.getHeight());
		} else {
			graphics.drawImage(imageT, xt, yt, imageT.getWidth(), imageT.getHeight(), null);
			graphics.drawImage(imageD, xd, yd, imageD.getWidth(), imageD.getHeight(), null);
		}
	}
	public boolean isColliding(int xa, int ya, int xb, int yb) {
		if(xb>x&&xa<x+imageT.getWidth()&&(ya>y+gap/2||yb<y-gap/2)) return true;
		return false;
	}
}
