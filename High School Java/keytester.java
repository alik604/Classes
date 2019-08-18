/**
 Created by Khizr ali pardhan on 2/29/2016.
 was originally a useless tutorial showing swing functions, i scraped to program
 and turned it in to a keyboard,mouse button and mouse wheel tester.
 works will all 5 of my mouse buttons, wheel, and all alphanumeric keys. 
 please note does properly display keys outputs for windows key and others as such
 */
package term2;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.event.*;
import java.util.Arrays;

// Extends JFrame so it can create frames

public class keytester extends JFrame {

	JButton button1;
	JTextField textField1;
	JTextArea textArea1;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}
		new keytester();
	}

	public keytester() {
		this.setSize(275, 540);
		// this.setLocationRelativeTo(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 3) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("My First Frame");
		JPanel thePanel = new JPanel();

		button1 = new JButton("Click tester");
		textField1 = new JTextField("Key tester: ", 15);

		ListenForMouse lForMouse = new ListenForMouse(); // for verifying mouse
															// buttons work
		button1.addMouseListener(lForMouse);

		ListenForButton lForButton = new ListenForButton(); // useless action
															// handler, will
															// only trigger of
															// left click
		button1.addActionListener(lForButton);

		ListenForKeys lForKeys = new ListenForKeys(); // for verifying keyboard
														// buttons work
		textField1.addKeyListener(lForKeys);

		MouseWheelListener lForWheel = new MouseWheelListener() // for verifying
																// mouse scroll
																// wheel works
		{
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getSource() == button1) {
					textArea1.append("mousewheel scrolled\n");
				}
			}
		};
		button1.addMouseWheelListener(lForWheel);

		thePanel.add(button1);
		thePanel.add(textField1);

		textArea1 = new JTextArea(25, 20);

		textArea1.setText("Displaying listener\n");
		textArea1.setLineWrap(true);
		textArea1.setWrapStyleWord(true);

		JScrollPane scrollbar1 = new JScrollPane(textArea1,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // VERTICAL_SCROLLBAR_ALWAYS
																// Automatically
																// scrolls down

		// Other options: VERTICAL_SCROLLBAR_ALWAYS, VERTICAL_SCROLLBAR_NEVER

		thePanel.add(scrollbar1);

		this.add(thePanel);
		this.setVisible(true);

		// Track the mouse if it is inside of the panel

	}

	private class ListenForButton implements ActionListener {

		// This method is called when an event occurs

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {

				// textArea1.append("Button clicked\n");

			}

		}
	}

	private class ListenForKeys implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			textArea1.append("Key Hit: " + e.getKeyChar() + "\n");
			/*
			 * char[] keys = new char[103]; for (int i = 1; i > 103; i++) {
			 * keys[i] = e.getKeyChar(); } String [] qwert = new String[103];
			 * Arrays.equals(keys, qwert);
			 */
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class ListenForMouse implements MouseListener {

		public void mouseClicked(MouseEvent e) {

			textArea1.append("Mouse Button: " + e.getButton() + "\n");

		}

		// Called when the mouse enters the component assigned
		// the MouseListener

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		// Called when the mouse leaves the component assigned
		// the MouseListener

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		// Mouse button pressed

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		// Mouse button released

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}