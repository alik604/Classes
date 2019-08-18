import java.awt.*;
import javax.swing.*;

/*--khizr ali pardhan|100282129--
 * 
 * when typing "unlock" must hit enter key.
 * 
 */

public class SecretComp extends JComponent {

	// placed any instance variables here
	private final JTextField tf;
	private final JPanel south;
	private final JTextField keyField;
	private final JTextField msgField;
	private final JButton encrypt, decrypt, A, B, C;

	public SecretComp() {
		// get a Secrets object from the factory
		Secrets secretsOBJ = SecretsFactory.makeSecrets();

		// set the initial layout
		setLayout(new BorderLayout());

		// added the output text box
		tf = new JTextField();
		tf.setEditable(false);
		add(tf, BorderLayout.CENTER);

		// added the control panel w/ components
		south = new JPanel();
		south.setSize(200, 50);
		south.setLayout(new GridLayout(3, 2));

		south.add(new JLabel("key"));
		south.add(new JLabel("msg"));

		keyField = new JTextField();
		msgField = new JTextField();

		encrypt = new JButton("Encrypt");
		south.add(encrypt);

		south.add(keyField);
		south.add(msgField);

		decrypt = new JButton("Decrypt");
		south.add(decrypt);

		A = new JButton("A");
		south.add(A);

		B = new JButton("B");
		south.add(B);

		C = new JButton("C");
		south.add(C);

		add(south, BorderLayout.SOUTH);

		//attached listeners to each of the buttons and the key text field
		A.addActionListener(e -> {secretsOBJ.unlock(A.getText());tf.setText(secretsOBJ.getMessage());});
		B.addActionListener(e -> {secretsOBJ.unlock(B.getText());tf.setText(secretsOBJ.getMessage());});
		C.addActionListener(e ->{ secretsOBJ.unlock(C.getText());tf.setText(secretsOBJ.getMessage());});

		keyField.addActionListener(e -> {
			secretsOBJ.unlock(keyField.getText());
			tf.setText(secretsOBJ.getMessage());
		});

		encrypt.addActionListener(e ->tf.setText(secretsOBJ.encrypt(keyField.getText(), msgField.getText())));
		decrypt.addActionListener(e -> tf.setText(secretsOBJ.decrypt(keyField.getText(), msgField.getText())));

		// set the txt field to be whatever is returned Secrets.getMessage
		tf.setText(secretsOBJ.getMessage());
	}
}

/*
 * STEP 09 * Message from assignment.
            key: The.Secret.Key
    cipher-text: cgmMbvbyDONppeM2paS+7A==
     plain-text: You did it!

 * STEP 10 * Message sent to classmate.
            key: Khizr.BubbleBuddy
    cipher-text: y2oJTT02Eq7z3vS4qZV0Uw==
     plain-text: testing...

 * STEP 11 * Message received from classmate.
            key: BubbleBuddy.Khizr
    cipher-text: H8qs+99ayEBEK4P0X2Xukg==
     plain-text: aMessage

 * STEP 12 * Message for marker.
            key: Khizr.Marker
    cipher-text: 8MikCxtvCl5D+s/Mm2duyf4OgJt5dKOboOYJ5xFg0cNn65G/n15O4DLlHaPx8WFSsdpvQxknK0sT+bbi84pnXQ==
  	cipher-textShorter: fAjx0hxUsxlYB81VhskgnQ==
 */
