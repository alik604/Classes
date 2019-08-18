import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.*;

/**
 * @Name			gradius
 * @Author 			khizr ali pardhan 
 * @Date   			july 5th, 2017
 * @version 		1.0
 * 
 */

/**
 * The Class GameFrame.
 */
public class GameFrame extends JFrame {

	private final static int WIDTH = 900;
	public final static int HEIGHT = 730;

	private final GameComponent comp;
	public static JTextField textArea;
	public static GameFrame frame;
	public static long time = System.currentTimeMillis();

	// should be more efficient then timer OBJ, even if from gamecomponent

	/**
	 * Instantiates a new game frame.
	 */
	public GameFrame() {
		setResizable(false);
		comp = new GameComponent();
		add(comp);

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// reference https://howtoprogramwithjava.com/java-multithreading/
		/*
		 * ive done a full media player using javaFX in high school but im not
		 * able to do it in swing. once game starts music stops :( i thought its
		 * Because there is a single thread so i tried making a new one.. i
		 * fail. i left the code for part makes
		 */

		// ***multi threading***/
		Worker worker = new Worker();
		Thread thread = new Thread(worker);
		thread.start();
		// ***
		frame = new GameFrame();
		frame.setTitle("Gradius by khizr ali pardhan -100282129-");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ***score counter ***/
		textArea = new JTextField();

		textArea.setEditable(false);
		textArea.setFont(new Font("Ubuntu", Font.PLAIN, 14));
		textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.lightGray));
		textArea.setOpaque(false);
		updateText();
		frame.add(textArea, BorderLayout.SOUTH);

		// ***
		System.out.println(thread.isAlive());// it kills itself?
		frame.setVisible(true);
		frame.comp.start();

	}

	/**
	 * Update text.
	 */
	public static void updateText() {
		textArea.setText("score is: " + Integer.toString(frame.comp.getScore())
				+ "\t time:"
				+ (Math.round(System.currentTimeMillis() - time) / 1000));
	}

}