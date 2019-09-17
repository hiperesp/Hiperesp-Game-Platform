package br.org.hiperesp.java.flappybird.game.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.org.hiperesp.java.flappybird.game.MyGame;
import br.org.hiperesp.java.game.GameElement;

public class Background implements GameElement {
	
	BufferedImage image;
	MyGame game;
	double offsetX = 0;
	
	public Background(MyGame game) throws IOException {
		this.game = game;
		load();
	}
	public void load() throws IOException {
		image = ImageIO.read(new File("assets/background.png"));
	}
	public void update() {
		offsetX-=0.25;
		if(offsetX<-image.getWidth()) offsetX+=image.getWidth();
	}
	public void render() {
		Graphics2D graphics = game.graphics;
		int width = (int)((float)((float)image.getWidth()/(float)image.getHeight())*game.getHeight());
		int height = game.getHeight();
		int x = (int)((float)width/(float)image.getWidth()*(float)offsetX);
		int backgroundsToRender = 2+game.getWidth()/width;
		for(int i=0; i<backgroundsToRender; i++) {
			int xI = i*width+x;
			if(game.debug_mode==MyGame.DEBUG_ON) {
				graphics.drawRect(xI, 0, width, height);
			} else {
				 graphics.drawImage(image, xI, 0, width, height, null);
			}
		}
	}
}
