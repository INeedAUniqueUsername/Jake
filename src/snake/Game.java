package snake;
import java.awt.Component;

import javax.swing.JFrame;

public class Game extends JFrame{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 1920;//1920;  //1920
	private static int HEIGHT = 1080;//1080;  //960
	
	public Game() {
		super("Jake");
		setSize(WIDTH, HEIGHT);
		GameArea theGame = new GameArea(WIDTH-32, HEIGHT-64);
		((Component) theGame).setFocusable(true);
		getContentPane().add(theGame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
