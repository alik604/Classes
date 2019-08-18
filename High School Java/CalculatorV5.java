package term3;

/** README 			
 * @author kali 
 * 		
 # did you know that you can use both the numpad or the num keys for numbers and operations 
 *
 # Hit P or HOME key to save text, .txt will be saved to documents folder on both OSX and Windows (name: calc print out.txt) (looks very ugly because all in 1 line) 
 # Hit C to copy to clipboard 
 # Hit V to paste to clipboard
 * 
 # known issues and notes 
 * 
 * 1. Can't do multiple operations is one line (only last numbers recognized as ones needed 2+3+4=7(3+4)) 
 * 2. UI manager wont work on scroll bar
 * 3. bad Unicode for Pi key (lower-left corner) (only on windows)                  
 * 4. 5.03+3 = 8.0300000...001 (I blame java)
 * 5. PI key [ 2 + Pi = 3.14]                     fixed* 
 * 6. weird clipboard pasting issue after pasting and editing decimals ( 3.0002 -> 3.00021, pasting back in will give error) 
 *  
 * z. based on windows 10 calculator (layout) and Ti-83 (base arithmetic) 
 * a. back button is for term 1 or 2. not sum  (same as TI-83)
 * b. Ans added, |x| hidden 
 * c. under score key doubles as "-" and other unused keys double as used keys  
 * d. CA key is to "clear all" including memory #1 & #2 
 * e. program should ignore letters that are pasted in
 * f. "M²" means 2nd mem variable . this calculator App has 2 mem variables (mem and mem1) 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class CalculatorV5 {

	// --------------------------------------------

	private static JFrame frame = new JFrame("Calculator");
	private static JTextArea top = new JTextArea();
	private static JScrollPane scroll = new JScrollPane(top);

	private static String co = "";
	private static String numb = "";
	private static String str_copy;

	private static double numb1 = 0;
	private static double numb2 = 0;

	private static double x = 0;
	private static double y = 0;

	private static double mem = 0;
	private static double mem1 = 0;

	private static int unused = 0; // believe it or not but this is needed

	private static DecimalFormat f = new DecimalFormat("##.00"); // decimal
																	// points
	private static Clipboard CB = Toolkit.getDefaultToolkit()
			.getSystemClipboard();

	// -------------------------------------------- not my work* ( was not
	// working with KeyEvents)
	private static class CalcButton extends JButton {
		public CalcButton(String label) {
			super(label);
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.requestFocusInWindow();
				}
			});
		}
	}

	// --------------------------------------------

	public static void main(String args[]) {

		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// looks OK ^
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// too big ^
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY); // edit color here
		contentPane.setLayout((new GridLayout(8, 5)));
		contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(1, 0, 0, 0, new Color(182, 182, 182)),
				BorderFactory.createMatteBorder(2, 1, 1, 1,
						contentPane.getBackground())));
		// I'm not even going to try take credit for this ^

		// top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		top.setFont(new Font("Ubuntu", Font.PLAIN, 16));// change to 13 or 14
		// "Century Gothic"
		top.setEnabled(false);
		top.setDisabledTextColor(Color.black);
		top.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				contentPane.getBackground()));
		top.setLineWrap(true); // no X axis scroll bar
		top.setWrapStyleWord(true); // will not cut off mid work

		scroll.setBorder(null);
		// scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		final ActionListener one_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '1';
				top.append("1");
			}
		};
		final ActionListener two_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '2';
				top.append("2");
			}
		};
		final ActionListener three_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '3';
				top.append("3");
			}
		};
		final ActionListener four_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '4';
				top.append("4");
			}
		};
		final ActionListener five_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '5';
				top.append("5");
			}
		};
		final ActionListener six_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '6';
				top.append("6");
			}
		};
		final ActionListener seven_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '7';
				top.append("7");
			}
		};
		final ActionListener eight_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '8';
				top.append("8");
			}
		};
		final ActionListener nine_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '9';
				top.append("9");
			}
		};
		final ActionListener zero_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '0';
				top.append("0");
			}
		};
		final ActionListener multi_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		final ActionListener divide_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		final ActionListener minus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		final ActionListener plus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		final ActionListener equal_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numb == null || numb.isEmpty())
					return;
				EQUAL();
			}
		};
		final ActionListener dec_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '.';
				top.append(".");
			}
		};
		final ActionListener ans_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (x != 0) {
					numb = Double.toString(x);
					numb1 = x;
					numb2 = x;
					y = x;
					unused = 0;
					top.append("\nANS");

				}
			}
		};
		final ActionListener print_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String path = System.getProperty("user.home") + File.separator
						+ "Documents";
				File customDir = new File(path);
				PrintWriter out;
				try {
					out = new PrintWriter(new BufferedWriter(new FileWriter(
							customDir + "/calc print out.txt")));
					// out = new PrintWriter(new BufferedWriter(new
					// FileWriter("/Users/myself/Desktop/area1.txt")));
					out.write(top.getText());

					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("e1 printed ^^^");
				}

			}
		};

		final ActionListener copy_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (numb == "") {
					str_copy = Double.toString(x);
				} else {
					str_copy = numb;
				}

				StringSelection sele = new StringSelection(str_copy);

				CB.setContents(sele, sele);
				System.out.println("clipboard is: " + CB);
			}
		};

		final ActionListener paste_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unused = 1; // enable ans (almost forgot this)

				Transferable transfer = CB.getContents(this);

				try {
					top.append((String) transfer
							.getTransferData(DataFlavor.stringFlavor) + "\n");
				} catch (Exception err) {
					err.printStackTrace();
				}

				numb = top.getText();
				System.out.println("numb is:" + numb);
				numb1 = Double.parseDouble(numb);

			}
		};

		final JButton paste = new CalcButton("paste");
		paste.addActionListener(paste_listener);

		final JButton copy = new CalcButton("copy");
		copy.addActionListener(copy_listener);

		final JButton print = new CalcButton("save");
		print.addActionListener(print_listener);

		final JButton ans = new CalcButton("Ans");
		ans.addActionListener(ans_listener);

		final JButton one = new CalcButton("1");
		one.addActionListener(one_listener);

		final JButton two = new CalcButton("2");
		two.addActionListener(two_listener);

		final JButton three = new CalcButton("3");
		three.addActionListener(three_listener);

		final JButton four = new CalcButton("4");
		four.addActionListener(four_listener);

		final JButton five = new CalcButton("5");
		five.addActionListener(five_listener);

		final JButton six = new CalcButton("6");
		six.addActionListener(six_listener);

		final JButton seven = new CalcButton("7");
		seven.addActionListener(seven_listener);

		final JButton eight = new CalcButton("8");
		eight.addActionListener(eight_listener);

		final JButton nine = new CalcButton("9");
		nine.addActionListener(nine_listener);

		final JButton zero = new CalcButton("0");
		zero.addActionListener(zero_listener);

		final JButton Dec = new CalcButton(".");
		Dec.addActionListener(dec_listener);

		final JButton Equal = new CalcButton("=");
		Equal.addActionListener(equal_listener);

		final JButton Plus = new CalcButton("+");
		Plus.addActionListener(plus_listener);

		final JButton Multi = new CalcButton("*");
		Multi.addActionListener(multi_listener);

		final JButton Minus = new CalcButton("-");
		Minus.addActionListener(minus_listener);

		final JButton Divide = new CalcButton("/");
		Divide.addActionListener(divide_listener);

		/*
		 * .addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * } });
		 */

		// \u03C0
		final JButton Pi = new CalcButton("π");
		Pi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// numb1 = 0;
				// numb2 = 0;
				numb = Double.toString(Math.PI);
				top.append("\n" + numb);
			}
		});

		JButton Ce = new CalcButton("CA");
		Ce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top.setText(null); // or " "
				co = "";
				numb = "";
				numb1 = 0;
				numb2 = 0;
				x = 0;
				y = 0;
				mem = 0;
				mem1 = 0;
			}
		});

		JButton RootX = new CalcButton("√x");
		RootX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
					System.out.println("root error caught");
				}
				x = Math.sqrt(x);
				numb = Double.toString(x);
				top.append("\n" + numb);
			}
		});

		JButton C = new CalcButton("C");
		C.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = "";
				numb1 = 0;
				numb2 = 0;
				top.append("\n\n\n----------------------\n");
			}
		});

		JButton Del = new CalcButton("←");
		Del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (numb.length() > 0) {
					numb = numb.substring(0, numb.length() - 1);
					top.append("\n" + numb);
				}
				/*
				 * try { numb = numb.substring(0, numb.length() - 1); } catch
				 * (Exception E) { } top.append("\n" + numb);
				 */

			}
		});

		JButton Log = new CalcButton("LOG");
		Log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if (x != 0)
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
					System.out.println("log error caught");
				}

				x = Math.log10(x);
				top.append("\nLOG(" + numb + ")");
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton Sin = new CalcButton("SIN");
		Sin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nSIN(" + x + ")");

				x = Math.sin(x);

				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton Cos = new CalcButton("COS");
		Cos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nCOS(" + x + ")");
				x = Math.cos(x);
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton Tan = new CalcButton("TAN");
		Tan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nTAN(" + x + ")");
				y = Math.tan(x);

				numb = Double.toString(y);
				y = 0;
				top.append("\n=" + numb);
			}
		});

		JButton AbsX = new CalcButton("|X|");
		AbsX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if (x != 0){
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.abs(x);
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
			// }
		});

		JButton TentoThe = new CalcButton("10^X");
		TentoThe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.pow(10, x);
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton XtotheSecound = new CalcButton("X²");
		XtotheSecound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				// x = Math.pow(x, 2);
				x = x * x; // this is faster... i think
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton XtotheY = new CalcButton("X³");
		XtotheY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.pow(x, 3);
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton OneoverX = new CalcButton("1/X");
		OneoverX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = 1 / x;
				numb = Double.toString(x);
				top.append("\n=" + numb);
			}
		});

		JButton Mc = new CalcButton("MC");
		Mc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mem = 0;
				mem1 = 0;
				top.append("\nmem 1 now is: " + mem);
				top.append("\nmem 2 now is: " + mem1 + "\n");

			}
		});

		JButton Mr = new CalcButton("MR");
		Mr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				x = mem;
				numb = Double.toString(x);
				top.append("\n" + numb);
			}
		});

		JButton Ms = new CalcButton("MS");
		Ms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numb != null || numb.equals(""))
					try {
						x = Double.parseDouble(numb);
					} catch (Exception i) {
						System.out
								.println("numb is null, if seen i need it prevent error from happening");
					}

				// unoptimized code
				// {
				mem = x;
				numb = Double.toString(mem);
				top.append("\n" + numb + "\n");

				// }
			}
		});

		JButton MPlus = new CalcButton("M²R");
		MPlus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
					System.out.println("numb was null");
				}
				x = mem1;
				numb = Double.toString(x);
				top.append("\n" + numb);
			}
		});

		JButton MMin = new CalcButton("M²S");
		MMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				mem1 = x;
				numb = Double.toString(mem1);
				top.append("\n" + numb + "\n");
			}
		});

		JButton RootXtotheY = new CalcButton("1/MR");
		RootXtotheY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = 1 / mem;
				numb = Double.toString(x);
				top.append("\n=" + numb + "\n");
			}
		});

		JButton PlusorMin = new CalcButton("+/-");
		PlusorMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				// ----
				x = x * -1;
				numb = Double.toString(x);
				top.append("\n" + numb);
				/*
				 * int temp = numb.length(); if (numb2 != 0){ for (int i = 0; i
				 * < temp; i++) { numb = numb.substring(0, numb.length() - 1);
				 * top.append("\n" + x); } // numb = Double.toString(x); //
				 * top.append("\n" + numb); }
				 */
			}
		});
		JButton SinMin1 = new CalcButton("SIN-¹ ");
		SinMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nSIN-1(" + x + ")");
				x = Math.asin(x);
				numb = Double.toString(x);
				top.append("\n=" + numb);

			}
		});
		JButton CosMin1 = new CalcButton("COS-¹ ");
		CosMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nCOS-1(" + x + ")");
				x = Math.acos(x);
				numb = Double.toString(x);
				top.append("\n=" + numb);

			}
		});
		JButton TanMin1 = new CalcButton("TAN-¹ ");
		TanMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				top.append("\nTAN-1(" + x + ")");
				x = Math.asin(x);
				numb = Double.toString(x);
				top.append("\n=" + numb);

			}
		});

		contentPane.add(Mc);
		contentPane.add(Mr);
		contentPane.add(Ms);
		contentPane.add(MPlus); // M²r
		contentPane.add(MMin); // m²s

		contentPane.add(RootXtotheY);
		// contentPane.add(AbsX);
		contentPane.add(ans);
		contentPane.add(SinMin1);
		contentPane.add(CosMin1);
		contentPane.add(TanMin1);

		contentPane.add(RootX);
		contentPane.add(Log);
		contentPane.add(Sin);
		contentPane.add(Cos);
		contentPane.add(Tan);

		contentPane.add(XtotheY);
		contentPane.add(Ce);
		contentPane.add(C);
		contentPane.add(Del);
		contentPane.add(Divide);

		contentPane.add(XtotheSecound);
		contentPane.add(seven);
		contentPane.add(eight);
		contentPane.add(nine);
		contentPane.add(Multi);

		contentPane.add(OneoverX);
		contentPane.add(four);
		contentPane.add(five);
		contentPane.add(six);
		contentPane.add(Minus);

		contentPane.add(TentoThe);
		contentPane.add(one);
		contentPane.add(two);
		contentPane.add(three);
		contentPane.add(Plus);

		contentPane.add(Pi);
		contentPane.add(PlusorMin);
		contentPane.add(zero);
		contentPane.add(Dec);
		contentPane.add(Equal);

		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setSize(390, 490);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_V:
					paste_listener.actionPerformed(new ActionEvent(paste,
							99990, null));
					break;

				case KeyEvent.VK_C:
					copy_listener.actionPerformed(new ActionEvent(copy, 99990,
							null));
					break;

				case KeyEvent.VK_HOME:
				case KeyEvent.VK_P:
					print_listener.actionPerformed(new ActionEvent(print,
							99990, null));
					break;

				case KeyEvent.VK_CLEAR:
					ans_listener.actionPerformed(new ActionEvent(ans, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD0:
				case KeyEvent.VK_0:
					zero_listener.actionPerformed(new ActionEvent(zero, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD1:
				case KeyEvent.VK_1:
					one_listener.actionPerformed(new ActionEvent(one, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD2:
				case KeyEvent.VK_2:
					two_listener.actionPerformed(new ActionEvent(two, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD3:
				case KeyEvent.VK_3:
					three_listener.actionPerformed(new ActionEvent(three,
							99990, null));
					break;

				case KeyEvent.VK_NUMPAD4:
				case KeyEvent.VK_4:
					four_listener.actionPerformed(new ActionEvent(four, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD5:
				case KeyEvent.VK_5:
					five_listener.actionPerformed(new ActionEvent(five, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD6:
				case KeyEvent.VK_6:
					six_listener.actionPerformed(new ActionEvent(six, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD7:
				case KeyEvent.VK_7:
					seven_listener.actionPerformed(new ActionEvent(seven,
							99990, null));
					break;

				case KeyEvent.VK_NUMPAD8:
				case KeyEvent.VK_8:

					if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
						multi_listener.actionPerformed(new ActionEvent(Multi,
								99990, null));
					} else {
						eight_listener.actionPerformed(new ActionEvent(eight,
								99990, null));
					}

					break;

				case KeyEvent.VK_NUMPAD9:
				case KeyEvent.VK_9:
					nine_listener.actionPerformed(new ActionEvent(nine, 99990,
							null));
					break;

				case KeyEvent.VK_ADD:
					plus_listener.actionPerformed(new ActionEvent(Plus, 99990,
							null));
					break;

				case KeyEvent.VK_DIVIDE:
					divide_listener.actionPerformed(new ActionEvent(Divide,
							99990, null));
					break;

				case KeyEvent.VK_SLASH:
					divide_listener.actionPerformed(new ActionEvent(Divide,
							99990, null));
					break;

				case KeyEvent.VK_MULTIPLY:
					multi_listener.actionPerformed(new ActionEvent(Multi,
							99990, null));
					break;

				case KeyEvent.VK_SUBTRACT:
					minus_listener.actionPerformed(new ActionEvent(Minus,
							99990, null));
					break;

				case KeyEvent.VK_MINUS:
					minus_listener.actionPerformed(new ActionEvent(Minus,
							99990, null));
					break;

				case KeyEvent.VK_ENTER:
					equal_listener.actionPerformed(new ActionEvent(Equal,
							99990, null));
					break;

				case KeyEvent.VK_DECIMAL:
				case KeyEvent.VK_PERIOD:
					dec_listener.actionPerformed(new ActionEvent(Dec, 99990,
							null));
					break;

				case KeyEvent.VK_EQUALS:
					if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
						plus_listener.actionPerformed(new ActionEvent(Plus,
								99990, null));
					} else {
						equal_listener.actionPerformed(new ActionEvent(Equal,
								99990, null));
					}

				}
			}
		});

		Toolkit TK = Toolkit.getDefaultToolkit();
		Dimension dimen = TK.getScreenSize();
		int xPos = (dimen.width / 2) - (frame.getWidth() / 2);
		int yPos = (dimen.height / 2) - (frame.getHeight() / 2);

		frame.setLocation(xPos, yPos);
		frame.setLocationRelativeTo(null); // was not working property
		frame.add(contentPane, BorderLayout.SOUTH);
		frame.add(scroll);
		frame.setVisible(true);
	}

	// combine PLUS,MINUS,DIVIDE and MULTI into OPERATION
	public static void OPERATION() {
		frame.requestFocus();
		System.out.println(unused);

		// 0 means unused
		if (unused == 1 && co != null) {
			top.append("\nAns"); // "If It's Stupid But It Works... It's Not Stupid!"
		}
		if (numb == null || numb.isEmpty() || numb == " ") {
			return;
		}
		numb1 = Double.parseDouble(numb);
		numb = "";
	}

	public static void EQUAL() {

		if (numb == null || numb.isEmpty() || co == null || co.isEmpty())
			return;

		numb2 = Double.parseDouble(numb);
		if (numb2 == 0) {
			top.append(" = " + numb1);
			return;
		}

		switch (co) {

		case "+":
			System.out.println("is PLUS");
			x = numb1 + numb2;
			break;

		case "-":
			System.out.println("is MINUS");
			x = numb1 - numb2;
			break;

		case "*":
			System.out.println("is MULTI");
			x = numb1 * numb2;
			break;

		case "/":
			System.out.println("is DIVIDE");
			x = numb1 / numb2;
			break;

		}

		top.append(" = " + f.format(x) + " \n" + x + " \n");
		// top.append(" = " + f.format(x) + "\n" + x);
		co = null; // needed
		numb = "";
		numb1 = x; // the new x
		numb2 = 0; // unneeded
		unused = 1; // now its been used

		// top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// scroll.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	} // end of EQUAL();
}
// /////////// TO_DO /////////////////////////////// (done)
// rcCOSR
// ASIN
// INV SIN
// arcSIN
// switch abs value and log. Then replace abs value replace with ans DONE*
// switch C and Ce