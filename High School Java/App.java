package term3;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class App extends JFrame {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}
		JFrame fr = new JFrame("Notepad");
		JMenuBar bar = new JMenuBar();
		JMenu File = new JMenu("File");
		JMenuItem saveFile = new JMenuItem("save");
		JMenuItem close = new JMenuItem("close");

		/*
		 * SwingUtilities.invokeLater(new Runnable() { public void run() {
		 * 
		 * } });
		 */

		JTextArea text = new JTextArea();
		text.setBackground(Color.WHITE);
		text.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		text.setFont(new Font("Century Gothic", Font.PLAIN, 14));

		JScrollPane scroll = new JScrollPane(text);
		scroll.setBorder(null);

		File.add(saveFile);
		File.add(close);
		bar.add(File);
		fr.setJMenuBar(bar);
		fr.setSize(500, 400);
		fr.setLocationRelativeTo(null);
		fr.getContentPane().add(scroll); // text -> scroll -> fr
		fr.setVisible(true);

	}
}
