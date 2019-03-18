import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any jyway you like, but make the game start by
 * running main here.
 * 
 * Also don't forget to write 8javadocs for your classes and functions!
 * 
 * @author Buffalo
 *
 */
public class Main {
	private static final int DELAY = 50;

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */

	/**
	 * 
	 * Control Scheme: U and D to switch level, UP DOWN LEFT RIGHT to control the movement of hero
	 * A is attack
	 * Still looking for great weapon pic. 
	 * We are pretty buzy these days and we will optimize functions laters. Forgive us
	 *
	 * @param args
	 */
	
	public static void main(String[] args) {
		System.out.println("Write your cool arcade game here!");
		JFrame frame = new JFrame("arcade games");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel();
		JPanel panel = new JPanel();
		panel.add(label);
		frame.add(label, BorderLayout.NORTH);
		label.setFont(new Font("Dialog",1,25));
		label.setForeground(Color.RED);
		frame.setSize(700, 720);
		Environment environment = new Environment(frame,label);
		frame.add(environment);
		frame.setVisible(true);
		

		GameAdvanceListener advanceListener = new GameAdvanceListener(environment);
		
		Timer time = new Timer(DELAY, advanceListener);
		time.start();
	}
}