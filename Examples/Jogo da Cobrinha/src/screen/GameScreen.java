package screen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.MyGame;

public class GameScreen extends JFrame {
	private static final long serialVersionUID = 4764505886706857376L;
	MyGame game;
	public GameScreen() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
		game = new MyGame();
		setTitle("Jogo da Cobrinha");
		setSize(new Dimension(640, 480));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		init();
		setVisible(true);
		game.start();
	}
	public void init() {
		initMenu();
		add(game);
	}
	public void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		{
			JMenu menuArquivo = new JMenu("Arquivo");
			{
				JMenuItem menuItemSair = new JMenuItem("Sair");
				menuItemSair.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				menuArquivo.add(menuItemSair);
			}
			menuBar.add(menuArquivo);
			JMenu menuAjuda = new JMenu("Ajuda");
			GameScreen self = this;
			{
				JMenuItem menuItemControles = new JMenuItem("Controles");
				menuItemControles.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(self, "Não definido ainda", "Controles", JOptionPane.PLAIN_MESSAGE);
					}
				});
				menuAjuda.add(menuItemControles);
				JMenuItem menuItemSobre = new JMenuItem("Sobre");
				menuItemSobre.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(self, "Criado por Hiperesp", "Sobre", JOptionPane.PLAIN_MESSAGE);
					}
				});
				menuAjuda.add(menuItemSobre);
			}
			menuBar.add(menuAjuda);
		}
		setJMenuBar(menuBar);
	}
}
