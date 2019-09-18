package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import br.org.hiperesp.java.game.FrameRate;
import br.org.hiperesp.java.game.Game;

public class MyGame extends Game implements KeyListener {
	private static final long serialVersionUID = -5685074460264408018L;
	
	static final byte UP = 0;
	static final byte DOWN = 1;
	static final byte LEFT = 2;
	static final byte RIGHT = 3;
	
	int direction = DOWN;
	
	int mapWidth = 48;
	int mapHeight = 32;

	int gridWidth = 0;
	int gridHeight = 0;
	
	long nanoTime;
	
	FrameRate fr = new FrameRate();
	
	ArrayList<Vector2> snakeBody = new ArrayList<Vector2>();
	
	Vector2 apple;

	public MyGame() {
		addKeyListener(this);
		//setFPS(10);
		//setup();
	}
	public void setup() {
		apple = new Vector2(-1, -1);
		
		snakeBody.removeAll(snakeBody);
		snakeBody.add(new Vector2(mapWidth/2, mapHeight/2));
		fr.calculate();
		nanoTime = System.nanoTime();
	}
	public void update(){
		gridWidth = getWidth()/mapWidth;
		gridHeight = getHeight()/mapHeight;
		
		long currentNanoTime = System.nanoTime();
		if(currentNanoTime-nanoTime>100000000l) {
			nanoTime = currentNanoTime;
			Vector2 snakeHead = snakeBody.get(0);
			
			int newX = snakeHead.x, newY = snakeHead.y;
			if(direction==UP) newY--;
			if(direction==DOWN) newY++;
			if(direction==LEFT) newX--;
			if(direction==RIGHT) newX++;
			
			Vector2 snakeLastBody = snakeBody.get(snakeBody.size()-1);
			snakeLastBody.x = newX;
			snakeLastBody.y = newY;
			snakeBody.remove(snakeLastBody);
			snakeBody.add(0, snakeLastBody);
			
			for(int i=0; i<snakeBody.size(); i++) {
				Vector2 snakeBodyPart = snakeBody.get(i);
				if(snakeBodyPart.x<0) snakeBodyPart.x = mapWidth-1;
				if(snakeBodyPart.x>mapWidth-1) snakeBodyPart.x = 0;
				if(snakeBodyPart.y<0) snakeBodyPart.y = mapHeight-1;
				if(snakeBodyPart.y>mapHeight-1) snakeBodyPart.y = 0;
				for(int j=0; j<snakeBody.size(); j++) {
					Vector2 snakeBodyPart2 = snakeBody.get(j);
					if(snakeBodyPart!=snakeBodyPart2&&snakeBodyPart.x==snakeBodyPart2.x&&snakeBodyPart2.y==snakeBodyPart.y) {
						setup();
					}
				}
			}
			
			if((apple.x==snakeHead.x&&apple.y==snakeHead.y)||(apple.x==-1||apple.y==-1)) {
				apple.x = (int)(Math.random()*mapWidth);
				apple.y = (int)(Math.random()*mapHeight);
				addSnakeBody();
			}
		}
		fr.calculate();
	}
	public void render(){
		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		graphics.setColor(new Color(255, 0, 0));
		graphics.fillRect(apple.x*gridWidth, apple.y*gridHeight, gridWidth, gridHeight);
		
		graphics.setColor(new Color(255, 255, 255));
		for(int i=0; i<snakeBody.size(); i++) {
			graphics.fillRect(snakeBody.get(i).x*gridWidth, snakeBody.get(i).y*gridHeight, gridWidth, gridHeight);
		}		graphics.drawString("FPS: "+fr.fps, 10, 10);
		
		graphics.setColor(new Color(255, 255, 255));
		graphics.drawString("FPS: "+fr.fps, 10, 40);
		graphics.drawString("FrameTime: "+fr.frameTimeMs+"ms "+fr.frameTimeNs+"ns", 10, 20);
	}
	public void setDirection(byte direction) {
		if(this.snakeBody.size()>2&&((this.direction==UP&&direction==DOWN)||(this.direction==DOWN&&direction==UP)||(this.direction==LEFT&&direction==RIGHT)||(this.direction==RIGHT&&direction==LEFT))) {
			return;
		}
		this.direction = direction;
	}
	
	public void addSnakeBody() {
		Vector2 lastSnakeBody = snakeBody.get(snakeBody.size()-1);
		Vector2 newSnakeBody = new Vector2(lastSnakeBody.x, lastSnakeBody.y);
		snakeBody.add(newSnakeBody);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==87||key==38) setDirection(UP);
		if(key==83||key==40) setDirection(DOWN);
		if(key==65||key==37) setDirection(LEFT);
		if(key==68||key==39) setDirection(RIGHT);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}