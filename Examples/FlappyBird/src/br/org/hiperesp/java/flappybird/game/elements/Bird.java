package br.org.hiperesp.java.flappybird.game.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.org.hiperesp.java.flappybird.game.MyGame;
import br.org.hiperesp.java.game.GameElement;

public class Bird implements GameElement {
	private MyGame game;
	
	BufferedImage image;
	
	public int score;
	public int x;
	public int y;
	private double gravityOffset = 0;
	
	public Bird(MyGame game) throws IOException {
		this.game = game;
		load();
		setup();
	}
	public void load() throws IOException {
		image = ImageIO.read(new File("assets/bird.png"));
	}
	public void setup() {
		score = 0;
		x = -image.getWidth();
		y = 150;
	}
	public void update() {
		if(game.status==MyGame.STARTING) {
			if(x<150) x++;
			else game.status = MyGame.START_SCREEN;
		}
		if(game.status==MyGame.STARTING||game.status==MyGame.START_SCREEN) {
			if(y>150) jump();
		}
		if(game.status==MyGame.PLAYING||game.status==MyGame.DIE||game.status==MyGame.FAST_DIE) {
			calculateCollision();
		}
		if(!isCollidingWithBottom()) {
			gravityOffset+=0.2;
			y+=gravityOffset;
		}
	}
	public void render() {
		Graphics2D graphics = (Graphics2D) game.graphics;
		if(game.debug_mode==MyGame.DEBUG_ON) {
			graphics.drawRect(x, y, image.getWidth(), image.getHeight());
		}
		graphics.translate(x+38/2, y+26/2);
		double rotateLevel = gravityOffset/4;
		rotateLevel = rotateLevel>-1.5?rotateLevel:-1.5;
		rotateLevel = rotateLevel<+1.5?rotateLevel:+1.5;
		graphics.rotate(rotateLevel);
		if(game.debug_mode==MyGame.DEBUG_ON) {
			graphics.setColor(new Color(255, 0, 0));
			graphics.drawRect(-38/2, -26/2, 38, 26);
			graphics.setColor(new Color(0, 0, 0));
		} else {
			graphics.drawImage(image, -38/2, -26/2, 38, 26, null);
		}
		graphics.rotate(-rotateLevel);
		graphics.translate(-x-38/2, -y-26/2);
	}
	public void jump() {
		gravityOffset = -6;
	}
	public void calculateCollision() {
		if(isCollidingWithBottom()||isCollidingWithPipes()) {
			if(!(game.status==MyGame.DIE||game.status==MyGame.FAST_DIE)) {
				game.status = MyGame.DIE;
			}
			x-=game.pipeSpeed;
		}
		if(isCollidingWithTop()) {
			y = 0;
		}
	}
	public boolean isCollidingWithPipes() {
		return game.pipeList.isColliding(x, y, x+image.getWidth(), y+image.getHeight());
	}
	public boolean isCollidingWithTop() {
		return y<0;
	}
	public boolean isCollidingWithBottom() {
		return y+image.getHeight()>game.getHeight()-game.foreground.image.getHeight();
	}
	public Pipe getCollidePipe() {
		return game.pipeList.getCollidePipe(x, y, x+image.getWidth(), y+image.getHeight());
	}
}
