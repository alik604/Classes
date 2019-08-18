package ark_2016;

import java.util.Calendar;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;

/*
 import java.io.BufferedReader;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.BufferedReader;
 import java.io.IOException;
 import javax.swing.border.Border;
 */
public class TJWgui extends JFrame {
	private static JTextArea text = new JTextArea();
	private static JFrame frame = new JFrame("TJW with gui!");

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// JFrame frame = new JFrame("TJW with gui!");
				/*
				 * JMenuBar bar = new JMenuBar(); JMenu File = new
				 * JMenu("File"); JMenuItem saveFile = new JMenuItem("save");
				 * JMenuItem close = new JMenuItem("close");
				 */
				JPanel panelRight = new JPanel();
				panelRight.setBackground(Color.blue);
				panelRight.setLayout(new GridLayout(0, 1));

				JButton sunbtn = new JButton("Sunday");
				JButton monbtn = new JButton("Monday");
				JButton tuebtn = new JButton("Tuesday");
				JButton wedbtn = new JButton("Wednesday");
				JButton thursbtn = new JButton("Thursday");
				JButton fribtn = new JButton("Friday");
				panelRight.add(sunbtn);
				panelRight.add(monbtn);
				panelRight.add(tuebtn);
				panelRight.add(wedbtn);
				panelRight.add(thursbtn);
				panelRight.add(fribtn);

				ActionListener all_listen = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton src = (JButton) e.getSource();
						// We're doing this, since we know this is only one
						// JButton objects. No need to check types.
						switch (src.getText().toLowerCase()) {
						case "sunday":
							Switch(Calendar.SUNDAY);
							break;
						case "monday":
							Switch(Calendar.MONDAY);
							break;
						case "tuesday":
							Switch(Calendar.TUESDAY);
							break;
						case "wednesday":
							Switch(Calendar.WEDNESDAY);
							break;
						case "thursday":
							Switch(Calendar.THURSDAY);
							break;
						case "friday":
							Switch(Calendar.FRIDAY);
							break;

						}
					}
				};

				sunbtn.addActionListener(all_listen);
				monbtn.addActionListener(all_listen);
				tuebtn.addActionListener(all_listen);
				wedbtn.addActionListener(all_listen);
				thursbtn.addActionListener(all_listen);
				fribtn.addActionListener(all_listen);

				// end of right panel's shit

				// JPanel panelLeft = new JPanel();

				// ////////////////////// JTextArea text = new JTextArea();
				text.setLineWrap(true);
			
				text.setWrapStyleWord(true);
				// text.setBackground(Color.WHITE);
				text.setFont(new Font("Ubuntu", Font.PLAIN, 14));// "Century Gothic"
				text.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 3,
						Color.blue));

				JScrollPane scroll = new JScrollPane(text);
				scroll.setBorder(null);
				scroll.setPreferredSize(new Dimension(530, 300));
				// panelLeft.add(scroll);

				frame.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent we) {
						Load();
						Switch(-1);
					}

				});

				/*
				 * frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				 * frame.addWindowListener(new WindowAdapter() {
				 * 
				 * @Override public void windowClosing(WindowEvent we) { // make
				 * note pad save System.out.println("closing..."); } });
				 */
				// http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowAdapter.html

				frame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						Save();
						// System.exit(0); unneeded
					}
				});
				/*
				 * File.add(saveFile); File.add(close); bar.add(File);
				 * frame.add(bar);
				 */

				// frame.add(panelNorth, BorderLayout.NORTH);
				// frame.add(panelLeft, BorderLayout.WEST);
				frame.add(panelRight, BorderLayout.EAST);
				frame.add(scroll);
				//

				//
				frame.setSize(650, 350);
				// frame.setResizable(false);

				frame.setLocationRelativeTo(null);

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			}

		});
	}

	public static void Load() {
		try {
			FileReader reader = new FileReader(
					"c:\\Users\\kali\\Desktop\\TV_shows.txt");
			BufferedReader buffreader = new BufferedReader(reader);
			text.read(buffreader, null);
			// buffreader.close(); // why???????
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
	}

	public static void Switch(int DAY_OF_WEEK) {
		Calendar dateTime = Calendar.getInstance();
		if (DAY_OF_WEEK != -1) {
			dateTime.set(Calendar.DAY_OF_WEEK, DAY_OF_WEEK);
		}

		Desktop d = Desktop.getDesktop();

		// dateTime.setTimeInMillis(1413831601032L);
		// dateTime.setFirstDayOfWeek(dateTime.MONDAY); // useless crap
		// System.out.print(dateTime.get(dateTime.DAY_OF_WEEK));
		// System.out.println("of the week");
		// String a
		// ="magnet:?xt=urn:btih:6196923B8FDB84DB3CC157B30E62BA4A5F56671D&dn=spectre+2015+dvdscr+xvid+ift&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80%2Fannounce&tr=udp%3A%2F%2Fglotorrents.pw%3A6969%2Fannounce";
		// d.browse(new URI(a));

		// throws IOException, URISyntaxException
		System.out.println("Switch method triggered");

		try {

			switch (dateTime.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Brooklyn%20Nine-Nine%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/family%20guy%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Quantico%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/simpsons%20category%3Atv/?field=time_add&sorder=desc"));

				break;

			case Calendar.MONDAY:

				d.browse(new URI(
						"https://kat.cr/usearch/Scorpion%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Gotham%20category%3Atv//?field=time_add&sorder=desc"));
				break;
			case Calendar.TUESDAY:

				d.browse(new URI(
						"https://kat.cr/usearch/The%20Flash%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.WEDNESDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Blackish%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/Arrow%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.THURSDAY:
				d.browse(new URI(
						"https://kat.cr/usearch/Gotham%20category%3Atv//?field=time_add&sorder=desc"));
				d.browse(new URI(
						"https://kat.cr/usearch/How%20to%20Get%20Away%20with%20Murder%20category%3Atv//?field=time_add&sorder=desc"));
				break;

			case Calendar.FRIDAY:

				break;

			case Calendar.SATURDAY:

				break;

			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

	}

	public static void Save() {
		System.out.println("save method triggered");

		FileWriter pw;
		try {
			pw = new FileWriter("c:\\Users\\kali\\Desktop\\TV_shows.txt");
			text.write(pw); // Object of JTextArea
			System.out.println("saved*");
		} catch (IOException e1) {
			// e1.printStackTrace();
		}

	}

}
