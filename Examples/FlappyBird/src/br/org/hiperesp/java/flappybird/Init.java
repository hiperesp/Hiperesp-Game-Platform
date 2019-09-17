package br.org.hiperesp.java.flappybird;

import java.awt.FontFormatException;
import java.io.IOException;

import br.org.hiperesp.java.flappybird.game.GameScreen;

public class Init {
	@SuppressWarnings("unused")
	private static GameScreen gameScreen;
	public static void main(String[] args) throws InterruptedException, StackOverflowError, IOException, FontFormatException {
		gameScreen = new GameScreen();
	}
}
