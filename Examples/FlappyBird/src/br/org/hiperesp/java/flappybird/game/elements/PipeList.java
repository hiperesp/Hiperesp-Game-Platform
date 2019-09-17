package br.org.hiperesp.java.flappybird.game.elements;

import java.io.IOException;
import java.util.ArrayList;

import br.org.hiperesp.java.flappybird.game.MyGame;
import br.org.hiperesp.java.game.GameElement;

public class PipeList implements GameElement {
	private MyGame game;
	
	int pipesSpace = 300;
	private ArrayList<Pipe> pipes;
	
	public PipeList(MyGame game) throws IOException {
		this.game = game;
		setup();
	}
	public void setup() {
		pipes = new ArrayList<Pipe>();
	}
	public void update() {
		if(game.status==MyGame.PLAYING) {
			int maxPipes = 1+game.getWidth()/pipesSpace;
			while(pipes.size()<maxPipes) {
				int lastPipeX = getLastPipeX();
				Pipe pipe;
				try {
					pipe = new Pipe(game);
					pipe.x = lastPipeX+pipesSpace;
					pipes.add(pipe);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int i=0; i<pipes.size(); i++) pipes.get(i).update();
		Pipe firstPipe = getFirstPipe();
		if(firstPipe!=null&&firstPipe.x<-firstPipe.imageT.getWidth()) pipes.remove(firstPipe);
		if((game.status==MyGame.DIE||game.status==MyGame.FAST_DIE)&&firstPipe==null) {
			game.status = MyGame.STARTING;
			game.bird.setup();
		}
	}
	public void render() {
		for(int i=0; i<pipes.size(); i++) pipes.get(i).render();
	}
	public Pipe getFirstPipe() {
		return pipes.size()>0?pipes.get(0):null;
	}
	public int getLastPipeX() {
		return pipes.size()>0?pipes.get(pipes.size()-1).x:game.getWidth();
	}
	public boolean isColliding(int xa, int ya, int xb, int yb) {
		for(int i=0; i<pipes.size(); i++)
			if(pipes.get(i).isColliding(xa, ya, xb, yb))
				return true;
		return false;
	}
	public Pipe getCollidePipe(int xa, int ya, int xb, int yb) {
		for(int i=0; i<pipes.size(); i++)
			if(pipes.get(i).isColliding(xa, ya, xb, yb))
				return pipes.get(i);
		return null;
	}
}
