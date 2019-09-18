import javax.swing.UnsupportedLookAndFeelException;

import screen.GameScreen;

public class Init {
	static GameScreen gameScreen;
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
		gameScreen = new GameScreen();
	}
}
