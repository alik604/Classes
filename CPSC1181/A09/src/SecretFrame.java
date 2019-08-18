import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SecretFrame extends JFrame {

	private SecretFrame() {
		setContentPane(new SecretComp());
	}

	public static void main(String[] args) {
		JFrame f = new SecretFrame();
		
			
		f.setSize(450, 150);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
