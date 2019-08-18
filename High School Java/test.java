package term3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

public class test {

	public static void main(String[] arguments) {

		// better button
		// http://stackoverflow.com/questions/14159536/creating-jbutton-with-customized-look

		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// looks OK ^
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// too big ^
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JFrame frame = new JFrame("SpringLayout");

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		contentPane.setBackground(Color.DARK_GRAY);
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JButton send = new JButton("send");

		JTextField to = new JTextField(25);
		to.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		to.setBackground(Color.LIGHT_GRAY);

		JTextArea msg = new JTextArea(12, 30);
		msg.setBorder(BorderFactory.createTitledBorder("Message"));
		msg.setBackground(Color.LIGHT_GRAY);

		JScrollPane bar = new JScrollPane(msg);
		bar.setBackground(Color.BLACK);

		contentPane.add(send);
		contentPane.add(to);
		contentPane.add(bar);

		// X and Y bases scaling

		layout.putConstraint(SpringLayout.SOUTH, send, -30, SpringLayout.SOUTH,
				contentPane);
		layout.putConstraint(SpringLayout.EAST, send, -30, SpringLayout.EAST,
				contentPane);

		layout.putConstraint(SpringLayout.NORTH, to, 20, SpringLayout.NORTH,
				contentPane);
		layout.putConstraint(SpringLayout.WEST, to, 30, SpringLayout.WEST,
				contentPane);

		layout.putConstraint(SpringLayout.SOUTH, bar, -30, SpringLayout.SOUTH,
				contentPane);
		layout.putConstraint(SpringLayout.WEST, bar, 30, SpringLayout.WEST,
				contentPane);
		/*
		 * layout.putConstraint(SpringLayout.NORTH, one, 80, SpringLayout.NORTH,
		 * msg); layout.putConstraint(SpringLayout.WEST, one, 8,
		 * SpringLayout.WEST, contentPane);
		 * 
		 * layout.putConstraint(SpringLayout.NORTH, two, 80, SpringLayout.NORTH,
		 * msg); layout.putConstraint(SpringLayout.WEST, two, 8,
		 * SpringLayout.EAST, one);
		 * 
		 * layout.putConstraint(SpringLayout.NORTH, three, 80,
		 * SpringLayout.NORTH, msg); layout.putConstraint(SpringLayout.WEST,
		 * three, 8, SpringLayout.EAST, two);
		 */
		// frame.setLocationRelativeTo(null);
		frame.setSize(550, 400);
		frame.setVisible(true);
		// /////////////////////
		Scanner scan = new Scanner(System.in);
		System.out.println("enter 1 to print");
		double i = scan.nextDouble();
		if (i == 1) {
			String outgoing = msg.getText();
			System.out.println("if triggered*");
			System.out.println(outgoing);
		}
		scan.close();
		// ////////////////////

	}
}
