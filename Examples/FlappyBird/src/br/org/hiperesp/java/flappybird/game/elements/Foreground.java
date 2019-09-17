package br.org.hiperesp.java.flappybird.game.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.org.hiperesp.java.flappybird.game.MyGame;
import br.org.hiperesp.java.game.GameElement;

public class Foreground implements GameElement {
	
	BufferedImage image;
	MyGame game;
	double offsetX = 0;
	
	public Foreground(MyGame game) throws IOException {
		this.game = game;
		load();
	}
	public void load() throws IOException {
		image = ImageIO.read(new File("assets/foreground.png"));
	}
	public void update() {
		offsetX-=game.pipeSpeed/2;
		if(offsetX<-image.getWidth()) offsetX+=image.getWidth();
	}
	public void render() {
		Graphics2D graphics = game.graphics;
		int foregroundsToRender = 2+game.getWidth()/image.getWidth();
		int y = game.getHeight()-image.getHeight();
		int width = image.getWidth();
		int height = image.getHeight();
		for(int i=0; i<foregroundsToRender; i++) {
			int x = (int)(i*image.getWidth()+offsetX);
			if(game.debug_mode==MyGame.DEBUG_ON) {
				graphics.drawRect(x, y, width, height);
			} else {
				 graphics.drawImage(image, x, y, width, height, null);
			}
		}
	}
}
