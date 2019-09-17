package br.org.hiperesp.java.flappybird.game;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

public class GameScreen extends JFrame {
	private static final long serialVersionUID = 8121711083297599398L;
	
	private MyGame game;
	public GameScreen() throws InterruptedException, StackOverflowError, IOException, FontFormatException {
		game = new MyGame();
		setTitle("No Flappy September");
		setSize(new Dimension(1280, 720));
		setLocationRelativeTo(null);
		setResizable(false);
		add(game);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.start();
	}
}
