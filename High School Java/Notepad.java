/**
 * Created by Khizr ali pardhan on 2/29/2016. 
 * please note* editMune is only to show possible functions 
 */

package term2;

import java.util.Scanner;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/* in case of error, import everything
 import java.io.*;
 import java.awt.*;
 import javax.swing.*;
 */

public class Notepad extends JFrame {
	private static JFrame fr = new JFrame("Notepad");
	private static JFileChooser jfileopen;
	private static JFileChooser jfilesave;

	private static int fileopen;
	private static int filesave;

	public static void main(String[] args) {

		/**
		 * not good compatibility with JMenuBar, leave ugly space try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (Exception ex) {
		 *
		 * }
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				JMenuBar bar = new JMenuBar();
				// file menu
				JMenu fileMenu = new JMenu("File");

				JMenuItem newfile = new JMenuItem("New");
				newfile.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()));

				JMenuItem load = new JMenuItem("Load");
				load.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()));

				JMenuItem save = new JMenuItem("Save");
				save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()));

				JMenuItem exit = new JMenuItem("Exit");
				exit.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask()));

				// edit menu
				JMenu editMenu = new JMenu("Edit");

				JMenuItem sixteen = new JMenuItem("Set text size to sixteen");
				JMenuItem thirteen = new JMenuItem("Set text size to thirteen");
				JMenuItem verdana = new JMenuItem("Set font to verdana");

				JTextArea text = new JTextArea();
				text.setBackground(Color.lightGray);
				text.setBorder(BorderFactory.createMatteBorder(2, 1, 1, 1,
						Color.BLACK));

				JScrollPane scroll = new JScrollPane(text);
				scroll.setBorder(null);
				// lots of action handling
				verdana.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						text.setFont(new Font("verdana", Font.PLAIN, 13));
					}
				});

				thirteen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						text.setFont(new Font("Ubuntu", Font.PLAIN, 13));
					}
				});

				sixteen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						text.setFont(new Font("Ubuntu", Font.PLAIN, 16));
					}
				});

				newfile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						text.setText("");
					}
				});

				load.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						openFile();
						if (fileopen == JFileChooser.APPROVE_OPTION) {

							text.setText("");
							try {

								Scanner scan = new Scanner(new FileReader(
										jfileopen.getSelectedFile().getPath()));

								while (scan.hasNext())
									text.append(scan.nextLine());
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
						}
					}
				});

				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						saveFile();
						if (filesave == JFileChooser.APPROVE_OPTION) {
							try {
								BufferedWriter out = new BufferedWriter(
										new FileWriter(jfilesave
												.getSelectedFile().getPath()));
								out.write(text.getText());
								out.close();
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
						}
					}
				});

				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				fileMenu.add(newfile);
				fileMenu.add(load);
				fileMenu.add(save);
				fileMenu.add(exit);

				editMenu.add(thirteen);
				editMenu.add(sixteen);
				editMenu.add(verdana);

				bar.add(fileMenu);
				bar.add(editMenu);

				fr.setJMenuBar(bar);

				fr.setSize(500, 500);
				fr.setLocationRelativeTo(null);
				fr.getContentPane().add(scroll);
				fr.setVisible(true);
			}
		});
	}

	public static void openFile() {
		JFileChooser open = new JFileChooser();
		int option = open.showOpenDialog(fr);
		fileopen = option;
		jfileopen = open;
		System.out.println("loaded*");
	}

	public static void saveFile() {
		JFileChooser save = new JFileChooser();
		int option = save.showOpenDialog(fr);
		filesave = option;
		jfilesave = save;
		System.out.println("saved*");
	}

}
